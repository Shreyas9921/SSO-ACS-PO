package com.acs.Test.dto.request.product;

import org.springframework.lang.Nullable;

public class ProductSearchPageRequest {
    private Integer currentPage;
    private Integer pageSize;
    @Nullable
    private String sortBy;
    private ProductSearchRequest filters;

    public ProductSearchPageRequest() {
    }

    public ProductSearchPageRequest(Integer currentPage, Integer pageSize, @Nullable String sortBy, ProductSearchRequest filters) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.sortBy = sortBy;
        this.filters = filters;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Nullable
    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(@Nullable String sortBy) {
        this.sortBy = sortBy;
    }

    public ProductSearchRequest getFilters() {
        return filters;
    }

    public void setFilters(ProductSearchRequest filters) {
        this.filters = filters;
    }

    @Override
    public String toString() {
        return "ProductSearchPageRequest{" +
                "currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", sortBy='" + sortBy + '\'' +
                ", filters=" + filters +
                '}';
    }
}
