package com.acs.Test.service.impl;

import com.acs.Test.commons.mapper.SupplierMapper;
import com.acs.Test.commons.specification.SupplierSpecifications;
import com.acs.Test.dto.misc.FcResponse;
import com.acs.Test.dto.response.supplier.SupplierResponse;
import com.acs.Test.dto.request.supplier.SupplierSearchRequest;
import com.acs.Test.exception.ResourceNotFoundException;
import com.acs.Test.pojo.Supplier;
import com.acs.Test.pojo.SupplierFcMappings;
import com.acs.Test.repository.SupplierFcMappingRepository;
import com.acs.Test.repository.SupplierProductMappingRepository;
import com.acs.Test.repository.SupplierRepository;
import com.acs.Test.service.SupplierService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
//@Transactional(transactionManager = "poTransactionManager") // Use PO transaction manager
@Transactional
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;

    private final SupplierProductMappingRepository supplierProductMappingRepository;
    private final SupplierFcMappingRepository supplierFcMappingRepository;


    public SupplierServiceImpl(SupplierRepository supplierRepository,
                               SupplierMapper supplierMapper,
                               SupplierProductMappingRepository supplierProductMappingRepository,
                               SupplierFcMappingRepository supplierFcMappingRepository)
    {
        this.supplierRepository = supplierRepository;
        this.supplierMapper = supplierMapper;
        this.supplierProductMappingRepository = supplierProductMappingRepository;
        this.supplierFcMappingRepository = supplierFcMappingRepository;
    }

    @Override
    public SupplierResponse getSupplierById(Integer id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));

//        Integer productCount = supplierProductMappingRepository.countBySupplierId(Long.valueOf(supplier.getId()));
        Integer productCount = supplierProductMappingRepository.countBySupplierId(supplier.getId());
        List<String> skus = supplierProductMappingRepository.findProductsBySupplierId(supplier.getId());
//        Integer productCount = 0;
//        List<String> skus = List.of();

        List<SupplierFcMappings> fcMappings = supplierFcMappingRepository.findBySupplierId(supplier.getId());
//        List<String> fcMappings = List.of();

        List<FcResponse> fcResponses = fcMappings.stream()
                .filter(SupplierFcMappings::getStatus)
                .map(mapping -> supplierMapper.toFcResponse(mapping.getFulfilmentCenter()))
                .collect(Collectors.toList());
//        List<FcResponse> fcResponses = List.of();

        return supplierMapper.toResponse(supplier, productCount, skus, fcResponses);
    }

    @Override
    public List<SupplierResponse> getAllSuppliers() {
        return supplierRepository.findAll().stream()
                .map(supplier -> {
//                    Integer productCount = supplierProductMappingRepository.countBySupplierId(Long.valueOf(supplier.getId()));
                    Integer productCount = supplierProductMappingRepository.countBySupplierId(supplier.getId());
                    List<String> skus = supplierProductMappingRepository.findProductsBySupplierId(supplier.getId());

                    List<SupplierFcMappings> fcMappings = supplierFcMappingRepository.findBySupplierId(supplier.getId());
                    List<FcResponse> fcResponses = fcMappings.stream()
                            .filter(SupplierFcMappings::getStatus) // Only active ones
                            .map(mapping -> supplierMapper.toFcResponse(mapping.getFulfilmentCenter()))
                            .collect(Collectors.toList());

                    return supplierMapper.toResponse(supplier, productCount, skus, fcResponses);
                })
                .collect(Collectors.toList());
    }


    @Override
    public Page<SupplierResponse> searchSuppliers(SupplierSearchRequest filters,
                                                  int page,
                                                  int size,
                                                  String sortField) {
        String sortDirection = sortField.charAt(0) == '!' ? "desc" : "asc";
        sortField = sortField.replace("!", "");

        Pageable pageable = PageRequest.of(
                Math.max(page - 1, 0),
                size,
                Sort.by(Sort.Direction.fromString(sortDirection), sortField)
        );

        Specification<Supplier> spec = SupplierSpecifications.buildSpecification(filters);
        Page<Supplier> supplierPage = supplierRepository.findAll(spec, pageable);

        return supplierPage.map(supplier -> {
            Integer supplierId = supplier.getId();

//            Integer productCount = supplierProductMappingRepository.countBySupplierId(Long.valueOf(supplierId));
            Integer productCount = supplierProductMappingRepository.countBySupplierId(supplier.getId());
            List<String> skus = supplierProductMappingRepository.findProductsBySupplierId(Math.toIntExact(Long.valueOf(supplierId)));
//            Integer productCount = 0;
//            List<String> skus = List.of();

            List<SupplierFcMappings> fcMappings = supplierFcMappingRepository.findBySupplierId(supplierId);
//            List<String> fcMappings = List.of();

            List<FcResponse> fcResponses = fcMappings.stream()
                    .map(mapping -> new FcResponse(
                            mapping.getFulfilmentCenter().getId(),     // Adjust if different
                            mapping.getFulfilmentCenter().getFcName()    // Adjust if different
                    ))
                    .collect(Collectors.toList());
//            List<FcResponse> fcResponses = List.of();

            return supplierMapper.toResponse(supplier, productCount, skus, fcResponses);
        });
    }

}
