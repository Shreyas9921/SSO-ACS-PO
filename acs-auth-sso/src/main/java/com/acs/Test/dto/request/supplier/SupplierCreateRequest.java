package com.acs.Test.dto.request.supplier;

import com.acs.Test.dto.misc.AddressDTO;
import com.acs.Test.dto.misc.ContactDTO;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupplierCreateRequest {

    @NotBlank(message = "Supplier name is required")
    private String supplierName;

    @NotBlank(message = "Supplier code is required")
    private String supplierCode;

    @Valid
    private List<@Valid AddressDTO> addresses;

    @Valid
    private List<@Valid ContactDTO> contacts;

    // @NotNull(message = "Fulfillment Center list is required")
    @Size(min = 1, message = "At least one Fulfillment Center must be selected.")
    private List<@NotNull(message = "Fulfillment Center list is required") Integer> fcIds;

    // conditional address duplication
    private Boolean copyPrimaryToBilling;
    private Boolean copyPrimaryToShipping;

    /*public SupplierCreateRequest() {
    }

    public SupplierCreateRequest(String supplierName, String supplierCode, List<@Valid AddressDTO> addresses, List<@Valid ContactDTO> contacts, List<@NotNull(message = "Fulfillment Center list is required") Integer> fcIds, Boolean copyPrimaryToBilling, Boolean copyPrimaryToShipping) {
        this.supplierName = supplierName;
        this.supplierCode = supplierCode;
        this.addresses = addresses;
        this.contacts = contacts;
        this.fcIds = fcIds;
        this.copyPrimaryToBilling = copyPrimaryToBilling;
        this.copyPrimaryToShipping = copyPrimaryToShipping;
    }*/

    /*public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierCode() {
        return supplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        this.supplierCode = supplierCode;
    }

    public List<AddressDTO> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressDTO> addresses) {
        this.addresses = addresses;
    }

    public List<ContactDTO> getContacts() {
        return contacts;
    }

    public void setContacts(List<ContactDTO> contacts) {
        this.contacts = contacts;
    }

    public List<Integer> getFcIds() {
        return fcIds;
    }

    public void setFcIds(List<Integer> fcIds) {
        this.fcIds = fcIds;
    }

    public Boolean getCopyPrimaryToBilling() {
        return copyPrimaryToBilling;
    }

    public void setCopyPrimaryToBilling(Boolean copyPrimaryToBilling) {
        this.copyPrimaryToBilling = copyPrimaryToBilling;
    }

    public Boolean getCopyPrimaryToShipping() {
        return copyPrimaryToShipping;
    }

    public void setCopyPrimaryToShipping(Boolean copyPrimaryToShipping) {
        this.copyPrimaryToShipping = copyPrimaryToShipping;
    }*/
}
