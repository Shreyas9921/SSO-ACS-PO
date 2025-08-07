package com.acs.Test.service.impl;

import com.acs.Test.commons.mapper.SupplierMapper;
import com.acs.Test.dto.FcResponse;
import com.acs.Test.dto.SupplierResponse;
import com.acs.Test.exception.ResourceNotFoundException;
import com.acs.Test.pojo.Supplier;
import com.acs.Test.repository.SupplierRepository;
import com.acs.Test.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(transactionManager = "poTransactionManager") // Use PO transaction manager
public class SupplierServiceImpl implements SupplierService {


    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;

    public SupplierServiceImpl(SupplierRepository supplierRepository, SupplierMapper supplierMapper) {
        this.supplierRepository = supplierRepository;
        this.supplierMapper = supplierMapper;
    }

    @Override
    public SupplierResponse getSupplierById(Integer id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));

        // Integer productCount = supplierProductMappingRepository.countBySupplierId(Long.valueOf(supplier.getId()));
        // List<String> skus = supplierProductMappingRepository.findProductsBySupplierId(supplier.getId());
        Integer productCount = 0;
        List<String> skus = List.of();

        // List<SupplierFcMappings> fcMappings = supplierFcMappingRepository.findBySupplierId(supplier.getId());
        List<String> fcMappings = List.of();

        /*List<FcResponse> fcResponses = fcMappings.stream()
                .filter(SupplierFcMappings::getStatus)
                .map(mapping -> supplierMapper.toFcResponse(mapping.getFulfilmentCenter()))
                .toList();*/
        List<FcResponse> fcResponses = List.of();

        return supplierMapper.toResponse(supplier, productCount, skus, fcResponses);
    }

}
