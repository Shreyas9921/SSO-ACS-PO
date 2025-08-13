package com.acs.Test.dto.lookup;

public class SupplierLookupRequest {
    private String supplierCode;
    private String supplierName;

    public SupplierLookupRequest() {
    }

    public SupplierLookupRequest(String supplierCode, String supplierName) {
        this.supplierCode = supplierCode;
        this.supplierName = supplierName;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
}
