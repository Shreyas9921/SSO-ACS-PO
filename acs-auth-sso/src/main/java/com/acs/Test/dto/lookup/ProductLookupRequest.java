package com.acs.Test.dto.lookup;

public class ProductLookupRequest {
    private String sku;
    private String productName;
    private boolean getSuppliersProduct;

    public ProductLookupRequest() {
    }

    public ProductLookupRequest(String sku, String productName, boolean getSuppliersProduct) {
        this.sku = sku;
        this.productName = productName;
        this.getSuppliersProduct = getSuppliersProduct;
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

    public boolean isGetSuppliersProduct() {
        return getSuppliersProduct;
    }

    public void setGetSuppliersProduct(boolean getSuppliersProduct) {
        this.getSuppliersProduct = getSuppliersProduct;
    }
}
