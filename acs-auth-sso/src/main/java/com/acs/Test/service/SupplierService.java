package com.acs.Test.service;

import com.acs.Test.dto.response.supplier.SupplierResponse;
import com.acs.Test.dto.request.supplier.SupplierSearchRequest;
import org.springframework.data.domain.Page;

public interface SupplierService {
    SupplierResponse getSupplierById(Integer id);

    Page<SupplierResponse> searchSuppliers(SupplierSearchRequest filters, int page, int size, String sortField);

    List<SupplierResponse> getAllSuppliers();
}
