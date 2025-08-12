package com.acs.Test.controller.lookup;

import com.acs.Test.controller.ApiResponse;
import com.acs.Test.dto.lookup.ProductDetailsDTO;
import com.acs.Test.dto.lookup.ProductLookupRequest;
import com.acs.Test.pojo.Product;
import com.acs.Test.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products/get")
public class ProductLookupController {


    private final ProductRepository productRepository;

    public ProductLookupController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /*@Operation(summary = "Search products by SKU or name (for dropdown)",
            description = "Fetches products by their unique identifier.",
            tags = {"Supplier-Product-Mapping"})*/
    @PostMapping
    public ResponseEntity<ApiResponse<List<ProductDetailsDTO>>> searchProducts(
//    public ResponseEntity<List<ProductDetailsDTO>> searchProducts(
            @RequestBody(required = false) ProductLookupRequest request) {

        String sku = (request != null && request.getSku() != null) ? request.getSku().trim() : "";
        String name = (request != null && request.getProductName() != null) ? request.getProductName().trim() : "";
        boolean filterMapped = request != null && request.isGetSuppliersProduct();

        List<Product> products = filterMapped
                ? productRepository.searchMappedProductsBySkuAndName(sku, name)
                : productRepository.searchAllProducts(sku, name);

        List<ProductDetailsDTO> results = products.stream()
                .limit(100)
                .map(p -> new ProductDetailsDTO(
                        p.getId().intValue(), p.getSku(), p.getProductName(), p.getUpc(),
                        p.getUom(), p.getTempStorageType(), p.getUnitCost()
                ))
                .collect(Collectors.toList());

        if (results.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse<>(true, "No product found", results));
        }

        return ResponseEntity.ok(ApiResponse.ok(results));
    }
}
