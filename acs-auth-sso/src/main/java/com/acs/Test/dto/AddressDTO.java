package com.acs.Test.dto;

import javax.validation.constraints.NotBlank;

import lombok.*;

@Data
@NoArgsConstructor
@Builder
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

    public AddressDTO(String addressTypeCode, String addressLine1, String addressLine2, String city, String state, String country, String zipCode, Boolean isDefault, Boolean isCopiedFromPrimary) {
        this.addressTypeCode = addressTypeCode;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipCode = zipCode;
        this.isDefault = isDefault;
        this.isCopiedFromPrimary = isCopiedFromPrimary;
    }
}
