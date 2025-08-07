package com.acs.Test.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class SupplierResponse {
    private Integer id;
    private String supplierName;
    private String supplierCode;
    private String status;
    private List<AddressDTO> addresses;
    private List<ContactDTO> contacts;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer productCount;
    private List<String> skus;
    private List<FcResponse> fulfilmentCenters ;
    boolean integrationReceived;

}
