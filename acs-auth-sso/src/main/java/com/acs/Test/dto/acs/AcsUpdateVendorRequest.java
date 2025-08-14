package com.acs.Test.dto.acs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AcsUpdateVendorRequest {

    private String addressLine1;
    private String addressLine2;
    private String businessPhone;
    private String city;
    private String country;
    private String clientId;
    private String postalCode;
    private String state;
    private String email;
    private String vendorName;
    private String vendorCode;
    private List<String> warehouseLocationName;
    private String vendorType = "Supplier";
    private boolean emailNotificationEnable = false;
}
