package com.acs.Test.commons.mapper;

import com.acs.Test.dto.AddressDTO;
import com.acs.Test.dto.ContactDTO;
import com.acs.Test.dto.FcResponse;
import com.acs.Test.dto.SupplierResponse;
import com.acs.Test.pojo.Supplier;
import com.acs.Test.pojo.SupplierAddress;
import com.acs.Test.pojo.SupplierContact;

import java.util.List;
import java.util.stream.Collectors;

public class SupplierMapper {
    public SupplierResponse toResponse(Supplier supplier, Integer productCount, List<String> skus, List<FcResponse> fcResponses){
        return new SupplierResponse(
                supplier.getId(),
                supplier.getSupplierName(),
                supplier.getSupplierCode(),
                supplier.getStatus(),
                supplier.getAddresses() != null
                        ? supplier.getAddresses().stream().map(this::toAddressDTO).collect(Collectors.toList())
                        : List.of(),
                supplier.getContacts() != null
                        ? supplier.getContacts().stream().map(this::toContactDTO).collect(Collectors.toList())
                        : List.of(),
                supplier.getCreatedAt(),
                supplier.getUpdatedAt(),
                productCount,
                skus,
                fcResponses,
                supplier.isIntegrationReceived()

        );
    }

    public AddressDTO toAddressDTO(SupplierAddress address) {
        return new AddressDTO(
                address.getAddressTypeCode(),
                address.getAddressLine1(),
                address.getAddressLine2(),
                address.getCity(),
                address.getState(),
                address.getCountry(),
                address.getZipCode(),
                address.getIsDefault(),
                address.getIsCopiedFromPrimary()
        );
    }

    public ContactDTO toContactDTO(SupplierContact contact) {
        return new ContactDTO(
                contact.getName(),
                contact.getEmail(),
                contact.getPhone(),
                contact.getIsPrimary()
        );
    }
}