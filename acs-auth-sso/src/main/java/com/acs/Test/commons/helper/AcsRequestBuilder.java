package com.acs.Test.commons.helper;

import com.acs.Test.dto.acs.AcsCreateVendorRequest;
import com.acs.Test.exception.BadRequestException;
import com.acs.Test.pojo.*;
import com.acs.Test.repository.SupplierFcMappingRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AcsRequestBuilder {

    public static AcsCreateVendorRequest buildAcsRequest(Supplier supplier, List<String> fcNames) {
        AcsCreateVendorRequest request = new AcsCreateVendorRequest();
        request.setVendorName(supplier.getSupplierName());
        request.setVendorCode(supplier.getSupplierCode());
        request.setClientId(String.valueOf(supplier.getClient().getAccountId())); // for reference

        // Set default address
        if (supplier.getAddresses() != null) {
            supplier.getAddresses().stream()
                    .filter(SupplierAddress::getDefault)
                    .findFirst()
                    .ifPresent(addr -> {
                        request.setAddressLine1(addr.getAddressLine1());
                        request.setAddressLine2(addr.getAddressLine2());
                        request.setCity(addr.getCity());
                        request.setState(addr.getState());
                        request.setCountry(addr.getCountry().equalsIgnoreCase("us") ? "United States" : "Canada");
                        request.setPostalCode(addr.getZipCode());
                    });
        }

        // Set primary contact
        if (supplier.getContacts() != null) {
            supplier.getContacts().stream()
                    .filter(SupplierContact::getPrimary)
                    .findFirst()
                    .ifPresent(contact -> {
                        request.setEmail(contact.getEmail());
                        request.setBusinessPhone(contact.getPhone());
                    });
        }

        request.setWarehouseLocationName(fcNames);

        // Hardcoded fields
        request.setVendorType("Supplier");
        request.setEmailNotificationEnable(false);

        return request;
    }

    /*private AcsUpdateVendorRequest buildAcsUpdateRequest(Supplier supplier) {
        AcsUpdateVendorRequest request = new AcsUpdateVendorRequest();
        request.setVendorName(supplier.getSupplierName());
        request.setVendorCode(supplier.getSupplierCode());
        request.setClientId(String.valueOf(supplier.getClient().getAccountId()));

        supplier.getAddresses().stream()
                .filter(SupplierAddress::getIsDefault)
                .findFirst()
                .ifPresent(addr -> {
                    request.setAddressLine1(addr.getAddressLine1());
                    request.setAddressLine2(addr.getAddressLine2());
                    request.setCity(addr.getCity());
                    request.setState(addr.getState());
                    request.setCountry(addr.getCountry().equalsIgnoreCase("us") ? "United States" : "Canada");
                    request.setPostalCode(addr.getZipCode());
                });

        supplier.getContacts().stream()
                .filter(SupplierContact::getIsPrimary)
                .findFirst()
                .ifPresent(contact -> {
                    request.setEmail(contact.getEmail());
                    request.setBusinessPhone(contact.getPhone());
                });

        List<String> fcNames = supplierFcMappingRepository.findBySupplierId(supplier.getId())
                .stream()
                .map(m -> m.getFulfilmentCenter().getFcName())
                .toList();

        request.setWarehouseLocationName(fcNames);
        request.setVendorType("Supplier");
        request.setEmailNotificationEnable(false);

        return request;
    }*/

}
