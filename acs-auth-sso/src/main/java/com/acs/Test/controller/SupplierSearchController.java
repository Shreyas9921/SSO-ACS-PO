package com.acs.Test.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class SupplierSearchController {

    /*
     * New endpoint for filter & pagination api/suppliers/search
     * */
    // Ambiguous mapping - IOC bean mapping illegalStateException -
    @PostMapping
    public ResponseEntity<ApiResponse<PageResult<SupplierResponse>>> searchSuppliers(
            @RequestBody SupplierSearchPageRequest pageRequest
    ) {
        SupplierSearchRequest filters = pageRequest.filters() == null ?
                SupplierSearchRequest.empty() :
                pageRequest.filters();

        int page = pageRequest.currentPage();
        int size = pageRequest.pageSize();
        String sortField = (pageRequest.sortBy() == null || pageRequest.sortBy().isEmpty()) ? "!id" : pageRequest.sortBy();

        Page<SupplierResponse> resultPage = supplierFilterService.searchSuppliers(filters, page, size, sortField);

        PageResult<SupplierResponse> result = new PageResult<>(
                resultPage.getContent(),
                resultPage.getNumber() + 1,
                resultPage.getTotalPages(),
                resultPage.getTotalElements(),
                resultPage.getSize()
        );

        return ResponseEntity.ok(ApiResponse.ok(result, "paged and filter response list"));
    }
}
