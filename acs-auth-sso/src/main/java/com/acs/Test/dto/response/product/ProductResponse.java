package com.acs.Test.dto.response.product;

import java.math.BigDecimal;

public class ProductResponse {
    private Long id;
    private String sku;
    private String productName;
    private String upc;
    private String uom;
    private String category;
    private BigDecimal unitCost;
    private String tempStorageType;
    private String productType;
    private String description;

    public ProductResponse() {
    }

    public ProductResponse(Long id, String sku, String productName, String upc, String uom, String category, BigDecimal unitCost, String tempStorageType, String productType, String description) {
        this.id = id;
        this.sku = sku;
        this.productName = productName;
        this.upc = upc;
        this.uom = uom;
        this.category = category;
        this.unitCost = unitCost;
        this.tempStorageType = tempStorageType;
        this.productType = productType;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(BigDecimal unitCost) {
        this.unitCost = unitCost;
    }

    public String getTempStorageType() {
        return tempStorageType;
    }

    public void setTempStorageType(String tempStorageType) {
        this.tempStorageType = tempStorageType;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ProductResponse{" +
                "id=" + id +
                ", sku='" + sku + '\'' +
                ", productName='" + productName + '\'' +
                ", upc='" + upc + '\'' +
                ", uom='" + uom + '\'' +
                ", category='" + category + '\'' +
                ", unitCost=" + unitCost +
                ", tempStorageType='" + tempStorageType + '\'' +
                ", productType='" + productType + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
