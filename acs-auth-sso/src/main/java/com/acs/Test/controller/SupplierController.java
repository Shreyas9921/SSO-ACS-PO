package com.acs.Test.controller;

import com.acs.Test.dto.SupplierResponse;
import com.acs.Test.service.SupplierService;
import com.acs.common.annotation.Authenticated;
import com.acs.common.dto.UsersAuthDto;
import com.acs.common.enums.DeviceType;
import com.acs.common.utils.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
@Validated
public class SupplierController {
    /*
     * Get data by id endpoint
     * */

    private final SupplierService supplierService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SupplierResponse>> getOne(
            @Authenticated(required = true) UsersAuthDto user,
            @RequestHeader(name = Constant.AUTH_TOKEN) String authToken,
            @RequestHeader(name = Constant.DEVICE_TYPE) DeviceType deviceType,
            @RequestHeader(name = Constant.APP_VERSION) String appVersion,
            @PathVariable Integer id) {
        SupplierResponse singleRecordById = supplierService.getSupplierById(id);
        return ResponseEntity.ok(ApiResponse.ok(singleRecordById, "Individual supplier record"));
    }
}
