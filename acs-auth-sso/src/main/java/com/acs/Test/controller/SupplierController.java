package com.acs.Test.controller;

import com.acs.Test.dto.request.supplier.SupplierCreateRequest;
import com.acs.Test.dto.request.supplier.SupplierUpdateRequest;
import com.acs.Test.dto.response.supplier.SupplierResponse;
import com.acs.Test.exception.BadRequestException;
import com.acs.Test.service.SupplierService;
import com.acs.common.annotation.Authenticated;
import com.acs.common.dto.UsersAuthDto;
import com.acs.common.enums.DeviceType;
import com.acs.common.utils.Constant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/suppliers")
@RequiredArgsConstructor
@Validated
public class SupplierController {

    private final SupplierService supplierService;

//    public  SupplierController(SupplierService supplierService) { this.supplierService = supplierService; }

    /*
     * Create new supplier endpoint
     * */
    // Ambiguous mapping - IOC bean mapping illegalStateException -
    /*
     * New endpoint for filter & pagination api/suppliers/search
     * */
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<SupplierResponse>> create(
            @Authenticated(required = true) UsersAuthDto user,
            @RequestHeader(name = Constant.AUTH_TOKEN) String authToken,
            @RequestHeader(name = Constant.DEVICE_TYPE) DeviceType deviceType,
            @RequestHeader(name = Constant.APP_VERSION) String appVersion,
            @Valid @RequestBody SupplierCreateRequest request) {

        SupplierResponse created = supplierService.createSupplier(request, user);

        if (created.isIntegrationReceived()) {
            //  Success case
            return ResponseEntity.ok(
//                    ApiResponse.ok(created, "Supplier created successfully")
                    new ApiResponse<>(true, "Supplier created successfully", created)
            );
        } else {
            //  Failure case → throw BadRequestException
            throw new BadRequestException("Supplier created successfully but not sent to ACS ");
        }

    }

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

    /*
     * Update supplier information endpoint
     * */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SupplierResponse>> update(
            @Authenticated(required = true) UsersAuthDto user,
            @RequestHeader(name = Constant.AUTH_TOKEN) String authToken,
            @RequestHeader(name = Constant.DEVICE_TYPE) DeviceType deviceType,
            @RequestHeader(name = Constant.APP_VERSION) String appVersion,
            @PathVariable Integer id,
            @Valid @RequestBody SupplierUpdateRequest request) {
        SupplierResponse updated = supplierService.updateSupplier(id, request, user);
        // return ResponseEntity.ok(updated);
        if (updated.isIntegrationReceived()) {
            //  Success case
            return ResponseEntity.ok(
                    ApiResponse.ok(updated, "Supplier updated successfully ")
            );
        } else {
            //  Failure case → throw BadRequestException
            throw new BadRequestException("Supplier updated successfully but not sent to ACS ");
        }

    }

}
