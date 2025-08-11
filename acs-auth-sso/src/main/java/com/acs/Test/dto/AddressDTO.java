package com.acs.Test.dto;

import javax.validation.constraints.NotBlank;

import lombok.*;

/*@Data
@NoArgsConstructor
@Builder*/
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

    public String getAddressTypeCode() {
        return addressTypeCode;
    }

    public void setAddressTypeCode(String addressTypeCode) {
        this.addressTypeCode = addressTypeCode;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    public Boolean getCopiedFromPrimary() {
        return isCopiedFromPrimary;
    }

    public void setCopiedFromPrimary(Boolean copiedFromPrimary) {
        isCopiedFromPrimary = copiedFromPrimary;
    }

    @Override
    public String toString() {
        return "AddressDTO{" +
                "addressTypeCode='" + addressTypeCode + '\'' +
                ", addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", isDefault=" + isDefault +
                ", isCopiedFromPrimary=" + isCopiedFromPrimary +
                '}';
    }
}
