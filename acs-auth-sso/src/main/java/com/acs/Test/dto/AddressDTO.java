package com.acs.Test.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class AddressDTO {
    @NotBlank
    private String addressTypeCode;
    @NotBlank
    private String addressLine1;

    private String addressLine2;
    @NotBlank
    private String city;
    @NotBlank
    private String state;
    @NotBlank
    private String country;
    @NotBlank
    private String zipCode;
    @NotBlank
    private Boolean isDefault;

    private Boolean isCopiedFromPrimary;
}
