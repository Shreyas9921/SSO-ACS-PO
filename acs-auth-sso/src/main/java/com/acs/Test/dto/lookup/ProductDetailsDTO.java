package com.acs.Test.dto.lookup;

import java.math.BigDecimal;

public class ProductDetailsDTO {
    private Integer id;
    private String sku;
    private String productName;
    private String upc;
    private String uom;
    private String tempStorageType;
    private BigDecimal unitCost;

    public ProductDetailsDTO() {
    }

    public ProductDetailsDTO(Integer id, String sku, String productName, String upc, String uom, String tempStorageType, BigDecimal unitCost) {
        this.id = id;
        this.sku = sku;
        this.productName = productName;
        this.upc = upc;
        this.uom = uom;
        this.tempStorageType = tempStorageType;
        this.unitCost = unitCost;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public String getTempStorageType() {
        return tempStorageType;
    }

    public void setTempStorageType(String tempStorageType) {
        this.tempStorageType = tempStorageType;
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(BigDecimal unitCost) {
        this.unitCost = unitCost;
    }
}
