package com.acs.Test.controller;

import com.acs.Test.dto.PageResult;
import com.acs.Test.dto.response.supplier.SupplierResponse;
import com.acs.Test.dto.request.supplier.SupplierSearchPageRequest;
import com.acs.Test.dto.request.supplier.SupplierSearchRequest;
import com.acs.Test.service.SupplierService;
import com.acs.common.annotation.Authenticated;
import com.acs.common.dto.UsersAuthDto;
import com.acs.common.enums.DeviceType;
import com.acs.common.utils.Constant;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/suppliers")
@Validated
public class SupplierSearchController {

    private final SupplierService supplierService;

    public  SupplierSearchController(SupplierService supplierService) { this.supplierService = supplierService; }

    /*
     * New endpoint for filter & pagination api/suppliers/search
     * */
    // Ambiguous mapping - IOC bean mapping illegalStateException -
    @PostMapping
    public ResponseEntity <PageResult<SupplierResponse>> searchSuppliers(
            @Authenticated(required = true) UsersAuthDto user,
            @RequestHeader(name = Constant.AUTH_TOKEN) String authToken,
            @RequestHeader(name = Constant.DEVICE_TYPE) DeviceType deviceType,
            @RequestHeader(name = Constant.APP_VERSION) String appVersion,
            @RequestBody SupplierSearchPageRequest pageRequest
    ) {
        SupplierSearchRequest filters = pageRequest.getFilters() == null ?
                SupplierSearchRequest.empty() :
                pageRequest.getFilters();

        int page = pageRequest.getCurrentPage();
        int size = pageRequest.getPageSize();
        String sortField = (pageRequest.getSortBy() == null || pageRequest.getSortBy().isEmpty()) ? "!id" : pageRequest.getSortBy();

        Page<SupplierResponse> resultPage = supplierService.searchSuppliers(filters, page, size, sortField);

        PageResult<SupplierResponse> result = new PageResult<>(
                resultPage.getContent(),
                resultPage.getNumber() + 1,
                resultPage.getTotalPages(),
                resultPage.getTotalElements(),
                resultPage.getSize()
        );

        return ResponseEntity.ok(result);
    }
}
