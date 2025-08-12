package com.acs.Test.controller;

import com.acs.Test.dto.PageResult;
import com.acs.Test.dto.request.product.ProductSearchPageRequest;
import com.acs.Test.dto.request.product.ProductSearchRequest;
import com.acs.Test.dto.response.product.ProductResponse;
import com.acs.Test.service.ProductService;
import com.acs.common.annotation.Authenticated;
import com.acs.common.dto.UsersAuthDto;
import com.acs.common.enums.DeviceType;
import com.acs.common.utils.Constant;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@Validated
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/all-items")
//    public ResponseEntity<ApiResponse<PageResult<ProductResponse>>> getPaginatedProducts(
    public ResponseEntity<PageResult<ProductResponse>> getPaginatedProducts(
            @Authenticated(required = true) UsersAuthDto user,
            @RequestHeader(name = Constant.AUTH_TOKEN) String authToken,
            @RequestHeader(name = Constant.DEVICE_TYPE) DeviceType deviceType,
            @RequestHeader(name = Constant.APP_VERSION) String appVersion,
            @RequestBody ProductSearchPageRequest request) {

        ProductSearchRequest filter = (request.getFilters() == null)
                ? ProductSearchRequest.default_filter()
                : request.getFilters();

        int page = (request.getCurrentPage() == null || request.getCurrentPage() < 1) ? 1 : request.getCurrentPage();
        int size = (request.getPageSize() == null || request.getPageSize() < 1) ? 10 : request.getPageSize();
        String sortBy = (request.getSortBy() == null || request.getSortBy().isBlank()) ? "id" : request.getSortBy();

        Page<ProductResponse> paged = productService.getPaginatedProducts(filter, page, size, sortBy);

        PageResult<ProductResponse> result = new PageResult<>(
                paged.getContent(),
                paged.getNumber() + 1,
                paged.getTotalPages(),
                paged.getTotalElements(),
                paged.getSize()
        );

//        return ResponseEntity.ok(ApiResponse.ok(result, "Paginated product list"));
        return ResponseEntity.ok(result);
    }

}
