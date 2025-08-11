package com.acs.Test.dto.supplier;

import org.springframework.lang.Nullable;

public class SupplierSearchPageRequest {

    private int currentPage;
    private int pageSize;
    @Nullable
    private String sortBy;
    // String sortDirection,
    private SupplierSearchRequest filters;

    public SupplierSearchPageRequest() {
    }

    public SupplierSearchPageRequest(int currentPage, int pageSize, @Nullable String sortBy, SupplierSearchRequest filters) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.sortBy = sortBy;
        this.filters = filters;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Nullable
    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(@Nullable String sortBy) {
        this.sortBy = sortBy;
    }

    public SupplierSearchRequest getFilters() {
        return filters;
    }

    public void setFilters(SupplierSearchRequest filters) {
        this.filters = filters;
    }

    @Override
    public String toString() {
        return "SupplierSearchPageRequest{" +
                "currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", sortBy='" + sortBy + '\'' +
                ", filters=" + filters +
                '}';
    }
}
