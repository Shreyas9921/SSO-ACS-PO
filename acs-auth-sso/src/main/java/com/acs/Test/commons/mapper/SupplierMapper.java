package com.acs.Test.commons.mapper;

import com.acs.Test.dto.misc.AddressDTO;
import com.acs.Test.dto.misc.ContactDTO;
import com.acs.Test.dto.misc.FcResponse;
import com.acs.Test.dto.response.supplier.SupplierResponse;
import com.acs.Test.pojo.FulfilmentCenter;
import com.acs.Test.pojo.Supplier;
import com.acs.Test.pojo.SupplierAddress;
import com.acs.Test.pojo.SupplierContact;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SupplierMapper {

    public SupplierMapper() {
    }

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
                address.getDefault(),
                address.getCopiedFromPrimary()
        );
    }

    public ContactDTO toContactDTO(SupplierContact contact) {
        return new ContactDTO(
                contact.getName(),
                contact.getEmail(),
                contact.getPhone(),
                contact.getPrimary()
        );
    }

    public FcResponse toFcResponse(FulfilmentCenter fc) {
        return new FcResponse(
                fc.getId(),
                fc.getFcName()
        );
    }

}