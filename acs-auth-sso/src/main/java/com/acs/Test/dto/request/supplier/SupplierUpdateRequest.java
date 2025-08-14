package com.acs.Test.dto.request.supplier;

import com.acs.Test.dto.misc.AddressDTO;
import com.acs.Test.dto.misc.ContactDTO;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * Request DTO for updating an existing Supplier.
 * All fields optional except clientId is not required (client unchanged).
 */

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupplierUpdateRequest {
    @NotBlank(message = "Supplier name is required")
    private String supplierName;

    @NotBlank(message = "Supplier code is required")
    private String supplierCode;

    @NotBlank(message = "Status is required")
    private String status;

    @Valid
    private List<@Valid AddressDTO> addresses;

    @Valid
    private List<@Valid ContactDTO> contacts;

    @NotNull(message = "Fulfillment Centers must not be null")
    @NotEmpty(message = "At least one Fulfillment Center must be selected.")
    private List<Integer> fcIds;

    // conditional address duplication
    private Boolean copyPrimaryToBilling;
    private Boolean copyPrimaryToShipping;
}
