package com.acs.Test.controller.lookup;

import com.acs.Test.controller.ApiResponse;
import com.acs.Test.dto.lookup.ProductDetailsDTO;
import com.acs.Test.dto.lookup.ProductLookupRequest;
import com.acs.Test.pojo.Product;
import com.acs.Test.repository.ProductRepository;
import com.acs.common.annotation.Authenticated;
import com.acs.common.dto.UsersAuthDto;
import com.acs.common.enums.DeviceType;
import com.acs.common.utils.Constant;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        @Authenticated(required = true) UsersAuthDto user,
        @RequestHeader(name = Constant.AUTH_TOKEN) String authToken,
        @RequestHeader(name = Constant.DEVICE_TYPE) DeviceType deviceType,
        @RequestHeader(name = Constant.APP_VERSION) String appVersion,
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
            return ResponseEntity.ok(new ApiResponse<>(false, "No product found", results));
        }

        return ResponseEntity.ok(ApiResponse.ok(results));
    }
}
