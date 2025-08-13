package com.acs.Test.controller;

import com.acs.Test.dto.response.supplier.SupplierResponse;
import com.acs.Test.service.SupplierService;
import com.acs.common.annotation.Authenticated;
import com.acs.common.dto.UsersAuthDto;
import com.acs.common.enums.DeviceType;
import com.acs.common.utils.Constant;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/suppliers")
@Validated
public class SupplierController {
    /*
     * Get data by id endpoint
     * */

    private final SupplierService supplierService;

    public  SupplierController(SupplierService supplierService) { this.supplierService = supplierService; }

    /*
     * Get data by id endpoint
     * */
//    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "/{id}")
//    public ResponseEntity<SupplierResponse> getOne(
    public ResponseEntity<ApiResponse<SupplierResponse>> getOne(
        @Authenticated(required = true) UsersAuthDto user,
            @RequestHeader(name = Constant.AUTH_TOKEN) String authToken,
            @RequestHeader(name = Constant.DEVICE_TYPE) DeviceType deviceType,
            @RequestHeader(name = Constant.APP_VERSION) String appVersion,
            @PathVariable Integer id) {
        SupplierResponse singleRecordById = supplierService.getSupplierById(id);
        // return ResponseEntity.ok(ApiResponse.ok(singleRecordById, "Individual supplier record"));

        /*if (singleRecordById == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Supplier not found"));
        }*/
        return ResponseEntity.ok(new ApiResponse<>(true, "Fetched supplier successfully", singleRecordById));
        // System.out.println("Supplier response : " + singleRecordById.toString());


//        return ResponseEntity.ok(response);
//        return ResponseEntity.ok(singleRecordById);
    }

    /*
     * Get all suppliers list endpoint
     * */
//    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping
    public ResponseEntity<ApiResponse<List<SupplierResponse>>> getAll() {
//    public ResponseEntity<List<SupplierResponse>> getAll() {
            // return ResponseEntity.ok(supplierService.getAllSuppliers());
        List<SupplierResponse> listAll = supplierService.getAllSuppliers();

        return ResponseEntity.ok(new ApiResponse<>(true , "All suppliers list", listAll));
//        return ResponseEntity.ok(listAll);
    }
}
