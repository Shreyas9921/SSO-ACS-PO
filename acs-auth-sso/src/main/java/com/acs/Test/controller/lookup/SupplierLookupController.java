package com.acs.Test.controller.lookup;

import com.acs.Test.controller.ApiResponse;
import com.acs.Test.dto.lookup.SupplierLookupDTO;
import com.acs.Test.dto.lookup.SupplierLookupRequest;
import com.acs.Test.pojo.Supplier;
import com.acs.Test.repository.SupplierRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/suppliers/lookup")
public class SupplierLookupController {

    private final SupplierRepository supplierRepository;

    public SupplierLookupController(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    /*@Operation(summary = "Get all active suppliers (filtered by code and/or name if provided)",
            description = "Performs partial match on code and/or name. If both are blank, returns all active suppliers.",
            tags = {"Supplier-Product-Mapping"})*/
    @PostMapping
    public ResponseEntity<ApiResponse<List<SupplierLookupDTO>>> getFilteredSuppliers(
            @RequestBody SupplierLookupRequest request) {

        String code = request.getSupplierCode() != null ? request.getSupplierCode().trim() : "";
        String name = request.getSupplierName() != null ? request.getSupplierName().trim() : "";

        List<Supplier> supplierEntities;

        if (code.isBlank() && name.isBlank()) {
            supplierEntities = supplierRepository.findAllActiveSuppliers();
        } else {
            supplierEntities = supplierRepository.findBySupplierCodeOrNamePartial(code, name);
        }

        List<SupplierLookupDTO> suppliers = supplierEntities.stream()
                .limit(100)
                .map(s -> new SupplierLookupDTO(s.getId(), s.getSupplierName(), s.getSupplierCode()))
                .collect(Collectors.toList());

        if (suppliers.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse<>(false, "No supplier found", suppliers));
        }

        return ResponseEntity.ok(ApiResponse.ok(suppliers));
    }
}
