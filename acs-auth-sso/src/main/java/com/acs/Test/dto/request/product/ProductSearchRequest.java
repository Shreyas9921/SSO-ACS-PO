package com.acs.Test.dto.request.product;

import org.springframework.lang.Nullable;

import java.util.List;

public class ProductSearchRequest {
    @Nullable
    private String sku;
    @Nullable
    private String productName;
    @Nullable
    private String upc;
    @Nullable
    private String uom;
    @Nullable
    private List<String> productType;
    @Nullable
    private List<String> tempStorageType;
    @Nullable
    private List<String> category;
    @Nullable
    private String description;

    public static ProductSearchRequest default_filter() {
        ProductSearchRequest req = new ProductSearchRequest();
        req.setSku("");
        req.setProductName("");
        req.setUpc("");
        req.setUom("");
        req.setProductType(List.of());
        req.setTempStorageType(List.of());
        req.setCategory(List.of());
        req.setDescription("");

        return req;
    }

    public ProductSearchRequest() {
    }

    public ProductSearchRequest(@Nullable String sku, @Nullable String productName, @Nullable String upc, @Nullable String uom, @Nullable List<String> productType, @Nullable List<String> tempStorageType, @Nullable List<String> category, @Nullable String description) {
        this.sku = sku;
        this.productName = productName;
        this.upc = upc;
        this.uom = uom;
        this.productType = productType;
        this.tempStorageType = tempStorageType;
        this.category = category;
        this.description = description;
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

    @Nullable
    public String getUpc() {
        return upc;
    }

    public void setUpc(@Nullable String upc) {
        this.upc = upc;
    }

    @Nullable
    public String getUom() {
        return uom;
    }

    public void setUom(@Nullable String uom) {
        this.uom = uom;
    }

    @Nullable
    public List<String> getProductType() {
        return productType;
    }

    public void setProductType(@Nullable List<String> productType) {
        this.productType = productType;
    }

    @Nullable
    public List<String> getTempStorageType() {
        return tempStorageType;
    }

    public void setTempStorageType(@Nullable List<String> tempStorageType) {
        this.tempStorageType = tempStorageType;
    }

    @Nullable
    public List<String> getCategory() {
        return category;
    }

    public void setCategory(@Nullable List<String> category) {
        this.category = category;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ProductSearchRequest{" +
                "sku='" + sku + '\'' +
                ", productName='" + productName + '\'' +
                ", upc='" + upc + '\'' +
                ", uom='" + uom + '\'' +
                ", productType=" + productType +
                ", tempStorageType=" + tempStorageType +
                ", category=" + category +
                ", description='" + description + '\'' +
                '}';
    }
}
