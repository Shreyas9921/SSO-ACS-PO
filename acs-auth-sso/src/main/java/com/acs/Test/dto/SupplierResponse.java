package com.acs.Test.dto;


import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/*@Data
@NoArgsConstructor
@Builder*/
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<AddressDTO> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressDTO> addresses) {
        this.addresses = addresses;
    }

    public List<ContactDTO> getContacts() {
        return contacts;
    }

    public void setContacts(List<ContactDTO> contacts) {
        this.contacts = contacts;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    public List<String> getSkus() {
        return skus;
    }

    public void setSkus(List<String> skus) {
        this.skus = skus;
    }

    public List<FcResponse> getFulfilmentCenters() {
        return fulfilmentCenters;
    }

    public void setFulfilmentCenters(List<FcResponse> fulfilmentCenters) {
        this.fulfilmentCenters = fulfilmentCenters;
    }

    public boolean isIntegrationReceived() {
        return integrationReceived;
    }

    public void setIntegrationReceived(boolean integrationReceived) {
        this.integrationReceived = integrationReceived;
    }

    @Override
    public String toString() {
        return "SupplierResponse{" +
                "id=" + id +
                ", supplierName='" + supplierName + '\'' +
                ", supplierCode='" + supplierCode + '\'' +
                ", status='" + status + '\'' +
                ", addresses=" + addresses +
                ", contacts=" + contacts +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", productCount=" + productCount +
                ", skus=" + skus +
                ", fulfilmentCenters=" + fulfilmentCenters +
                ", integrationReceived=" + integrationReceived +
                '}';
    }
}
