package com.acs.Test.dto.request.supplier;

import org.springframework.lang.Nullable;

import java.util.Collections;
import java.util.List;

//Generates constructor, getters, equals, hashCode and toString
//@Builder(toBuilder = true) //Enables the builder pattern
//@Jacksonized //For JSON serialization/deserialization with Lombok builder pattern
public class SupplierSearchRequest {
    @Nullable
    String supplierName;
    @Nullable
    String supplierCode;
    @Nullable
    String status; // "Active", "Inactive"
    @Nullable
    List<String> country;
    @Nullable
    List<String> state;
    @Nullable
    String city;
    @Nullable
    String contactName;
    @Nullable
    String contactEmail;
    @Nullable
    String contactPhone;
    @Nullable
    String sku;
    @Nullable
    String productName;

    public static SupplierSearchRequest empty() {
        SupplierSearchRequest req = new SupplierSearchRequest();
        req.setSupplierName("");
        req.setSupplierCode("");
        req.setStatus("");
        req.setCountry(Collections.emptyList());
        req.setState(Collections.emptyList());
        req.setCity("");
        req.setContactName("");
        req.setContactEmail("");
        req.setContactPhone("");
        req.setSku("");
        req.setProductName("");
        return req;
    }

    public SupplierSearchRequest() {
    }

    public SupplierSearchRequest(@Nullable String supplierName, @Nullable String supplierCode, @Nullable String status, @Nullable List<String> country, @Nullable List<String> state, @Nullable String city, @Nullable String contactName, @Nullable String contactEmail, @Nullable String contactPhone, @Nullable String sku, @Nullable String productName) {
        this.supplierName = supplierName;
        this.supplierCode = supplierCode;
        this.status = status;
        this.country = country;
        this.state = state;
        this.city = city;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
        this.sku = sku;
        this.productName = productName;
    }

    @Nullable
    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(@Nullable String supplierName) {
        this.supplierName = supplierName;
    }

    @Nullable
    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(@Nullable String supplierCode) {
        this.supplierCode = supplierCode;
    }

    @Nullable
    public String getStatus() {
        return status;
    }

    public void setStatus(@Nullable String status) {
        this.status = status;
    }

    @Nullable
    public List<String> getCountry() {
        return country;
    }

    public void setCountry(@Nullable List<String> country) {
        this.country = country;
    }

    @Nullable
    public List<String> getState() {
        return state;
    }

    public void setState(@Nullable List<String> state) {
        this.state = state;
    }

    @Nullable
    public String getCity() {
        return city;
    }

    public void setCity(@Nullable String city) {
        this.city = city;
    }

    @Nullable
    public String getContactName() {
        return contactName;
    }

    public void setContactName(@Nullable String contactName) {
        this.contactName = contactName;
    }

    @Nullable
    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(@Nullable String contactEmail) {
        this.contactEmail = contactEmail;
    }

    @Nullable
    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(@Nullable String contactPhone) {
        this.contactPhone = contactPhone;
    }

    @Nullable
    public String getSku() {
        return sku;
    }

    public void setSku(@Nullable String sku) {
        this.sku = sku;
    }

    @Nullable
    public String getProductName() {
        return productName;
    }

    public void setProductName(@Nullable String productName) {
        this.productName = productName;
    }

    @Override
    public String toString() {
        return "SupplierSearchRequest{" +
                "supplierName='" + supplierName + '\'' +
                ", supplierCode='" + supplierCode + '\'' +
                ", status='" + status + '\'' +
                ", country=" + country +
                ", state=" + state +
                ", city='" + city + '\'' +
                ", contactName='" + contactName + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                ", sku='" + sku + '\'' +
                ", productName='" + productName + '\'' +
                '}';
    }
}
