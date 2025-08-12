package com.acs.Test.service.impl;

import com.acs.Test.commons.mapper.ProductMapper;
import com.acs.Test.commons.specification.ProductSpecifications;
import com.acs.Test.dto.request.product.ProductSearchRequest;
import com.acs.Test.dto.response.product.ProductResponse;
import com.acs.Test.pojo.Product;
import com.acs.Test.repository.ProductRepository;
import com.acs.Test.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepo;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepo, ProductMapper productMapper) {
        this.productRepo = productRepo;
        this.productMapper = productMapper;

    }

    @Override
    public Page<ProductResponse> getPaginatedProducts(ProductSearchRequest filter, int page, int size, String sortField) {
        String sortDirection = sortField.charAt(0) == '!' ? "desc" : "asc";
        String sortBy = sortField.replace("!", "");

        Pageable pageable = PageRequest.of(
                Math.max(page - 1, 0),
                size,
                Sort.by(Sort.Direction.fromString(sortDirection), sortBy)
        );

        // Build specification using the filter object
        Specification<Product> spec = ProductSpecifications.buildSpecification(filter);

        // Apply spec + pagination
        Page<Product> pageResult = productRepo.findAll(spec, pageable);

        return pageResult.map(productMapper::toResponse);
    }
}
