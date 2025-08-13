package com.acs.Test.service;

import com.acs.Test.dto.request.supplier.SupplierCreateRequest;
import com.acs.Test.dto.response.supplier.SupplierResponse;
import com.acs.Test.dto.request.supplier.SupplierSearchRequest;
import com.acs.common.dto.UsersAuthDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SupplierService {

    /*
     * Generic CRUD request contract
     * */
    SupplierResponse createSupplier(SupplierCreateRequest request, UsersAuthDto user);

    SupplierResponse getSupplierById(Integer id);

    Page<SupplierResponse> searchSuppliers(SupplierSearchRequest filters, int page, int size, String sortField);

    List<SupplierResponse> getAllSuppliers();
}
