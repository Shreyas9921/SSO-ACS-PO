package com.acs.Test.controller.lookup;

import com.acs.Test.dto.lookup.FcLookupDTO;
import com.acs.Test.dto.lookup.FcLookupFilterRequest;
import com.acs.Test.dto.lookup.PoTypeLookupDTO;
import com.acs.Test.pojo.FulfilmentCenter;
import com.acs.Test.repository.FulfilmentCenterRepository;
import com.acs.common.annotation.Authenticated;
import com.acs.common.dto.UsersAuthDto;
import com.acs.common.enums.DeviceType;
import com.acs.common.utils.Constant;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/fulfilment-centers/get")
public class FcLookupController {

    private final FulfilmentCenterRepository fcRepository;

    public FcLookupController(FulfilmentCenterRepository fcRepository) {
        this.fcRepository = fcRepository;
    }

    /*@Operation(
            summary = "Get fulfilment centers (optionally filtered by supplier)",
            description = "Returns FC list for PO creation, filtered by supplierId if provided, otherwise limited to 100 records",
            tags = {"PO Management"}
    )*/
    @PostMapping
//    public ResponseEntity<ApiResponse<List<FcLookupDTO>>> getFcLookup(
    public ResponseEntity<List<FcLookupDTO>> getFcLookup(
            @Authenticated(required = true) UsersAuthDto user,
            @RequestHeader(name = Constant.AUTH_TOKEN) String authToken,
            @RequestHeader(name = Constant.DEVICE_TYPE) DeviceType deviceType,
            @RequestHeader(name = Constant.APP_VERSION) String appVersion,
            @RequestBody(required = false) FcLookupFilterRequest filter) {

        Integer supplierId = null;
        String search = "";

        if (filter != null) {
            supplierId = filter.getSupplierId();
            search = (filter.getSearch() != null) ? filter.getSearch().trim() : "";
        }

        List<FulfilmentCenter> fcList = fcRepository.findBySupplierAndFcName(supplierId, search);

        List<FcLookupDTO> results = fcList.stream()
                .limit(100)
                .map(fc -> new FcLookupDTO(
                        fc.getId(),
                        fc.getFcName(),
                        fc.getAddressLine1(),
                        fc.getAddressLine2(),
                        fc.getCountry(),
                        fc.getState(),
                        fc.getCity(),
                        fc.getZipCode()
                ))
                .collect(Collectors.toList());

//        return ResponseEntity.ok(ApiResponse.ok(results));
        return ResponseEntity.ok(results);
    }


}
