package com.acs.Test.dto.lookup;

public class FcLookupFilterRequest {
    private Integer supplierId;
    private String search;

    public FcLookupFilterRequest() {
    }

    public FcLookupFilterRequest(Integer supplierId, String search) {
        this.supplierId = supplierId;
        this.search = search;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
