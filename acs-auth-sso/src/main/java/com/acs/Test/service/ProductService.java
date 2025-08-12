package com.acs.Test.service;

import com.acs.Test.dto.request.product.ProductSearchRequest;
import com.acs.Test.dto.response.product.ProductResponse;
import org.springframework.data.domain.Page;

public interface ProductService {

    Page<ProductResponse> getPaginatedProducts(ProductSearchRequest filter, int page, int size, String sortBy);
}
