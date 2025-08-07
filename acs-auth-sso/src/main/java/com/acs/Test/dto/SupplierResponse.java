package com.acs.Test.dto;


import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
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

    public SupplierResponse(Integer id, String supplierName, String supplierCode, String status, List<AddressDTO> addresses, List<ContactDTO> contacts, LocalDateTime createdAt, LocalDateTime updatedAt, Integer productCount, List<String> skus, List<FcResponse> fulfilmentCenters, boolean integrationReceived) {
        this.id = id;
        this.supplierName = supplierName;
        this.supplierCode = supplierCode;
        this.status = status;
        this.addresses = addresses;
        this.contacts = contacts;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.productCount = productCount;
        this.skus = skus;
        this.fulfilmentCenters = fulfilmentCenters;
        this.integrationReceived = integrationReceived;
    }


}
