package com.acs.Test.dto.lookup;

public class SupplierLookupDTO {
    private Integer id;
    private String supplierName;
    private String supplierCode;

    public SupplierLookupDTO() {
    }

    public SupplierLookupDTO(Integer id, String supplierName, String supplierCode) {
        this.id = id;
        this.supplierName = supplierName;
        this.supplierCode = supplierCode;
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
}
