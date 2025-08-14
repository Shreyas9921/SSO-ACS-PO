package com.acs.Test.dto.acs;

import java.util.List;

public class AcsCreateVendorRequest {

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

    public AcsCreateVendorRequest() {
    }

    public AcsCreateVendorRequest(String addressLine1, String addressLine2, String businessPhone, String city, String country, String clientId, String postalCode, String state, String email, String vendorName, String vendorCode, List<String> warehouseLocationName, String vendorType, boolean emailNotificationEnable) {
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.businessPhone = businessPhone;
        this.city = city;
        this.country = country;
        this.clientId = clientId;
        this.postalCode = postalCode;
        this.state = state;
        this.email = email;
        this.vendorName = vendorName;
        this.vendorCode = vendorCode;
        this.warehouseLocationName = warehouseLocationName;
        this.vendorType = vendorType;
        this.emailNotificationEnable = emailNotificationEnable;
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

    public String getBusinessPhone() {
        return businessPhone;
    }

    public void setBusinessPhone(String businessPhone) {
        this.businessPhone = businessPhone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public List<String> getWarehouseLocationName() {
        return warehouseLocationName;
    }

    public void setWarehouseLocationName(List<String> warehouseLocationName) {
        this.warehouseLocationName = warehouseLocationName;
    }

    public String getVendorType() {
        return vendorType;
    }

    public void setVendorType(String vendorType) {
        this.vendorType = vendorType;
    }

    public boolean isEmailNotificationEnable() {
        return emailNotificationEnable;
    }

    public void setEmailNotificationEnable(boolean emailNotificationEnable) {
        this.emailNotificationEnable = emailNotificationEnable;
    }
}
