package com.acs.Test.service;

import com.acs.Test.dto.request.supplier.SupplierCreateRequest;
import com.acs.Test.dto.request.supplier.SupplierUpdateRequest;
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

    void deleteSupplier(Integer id);

    SupplierResponse updateSupplier(Integer id, SupplierUpdateRequest request, UsersAuthDto user);

    /*
     * Status toggle method contract
     * */
    void toggleStatus(Integer id);

    /*
    * State and Country lookups
    * */
    List<String> getDistinctCountries();
    List<String> getStatesForCountries(List<String> countries);

}
