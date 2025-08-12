package com.acs.Test.service;

import com.acs.Test.dto.SupplierResponse;
import com.acs.Test.dto.supplier.SupplierSearchRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SupplierService {
    SupplierResponse getSupplierById(Integer id);

    Page<SupplierResponse> searchSuppliers(SupplierSearchRequest filters, int page, int size, String sortField);

    List<SupplierResponse> getAllSuppliers();
}
