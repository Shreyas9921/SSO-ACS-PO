package com.acs.Test.commons.mapper;

import com.acs.Test.dto.response.product.ProductResponse;
import com.acs.Test.pojo.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductResponse toResponse(Product p) {
        return new ProductResponse(
                p.getId(),
                p.getSku(),
                p.getProductName(),
                p.getUpc(),
                p.getUom(),
                p.getCategory(),
                p.getUnitCost(),
                p.getTempStorageType(),
                p.getProductType(),
                p.getDescription()
        );
    }
}

