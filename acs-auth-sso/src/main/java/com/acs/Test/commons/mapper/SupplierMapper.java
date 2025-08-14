package com.acs.Test.commons.mapper;

import com.acs.Test.commons.constant.AppConstants;
import com.acs.Test.dto.misc.AddressDTO;
import com.acs.Test.dto.misc.ContactDTO;
import com.acs.Test.dto.misc.FcResponse;
import com.acs.Test.dto.request.supplier.SupplierCreateRequest;
import com.acs.Test.dto.request.supplier.SupplierUpdateRequest;
import com.acs.Test.dto.response.supplier.SupplierResponse;
import com.acs.Test.pojo.*;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Data
public class SupplierMapper {

    public SupplierMapper() {
    }

    public Supplier toEntity(SupplierCreateRequest request, Client client) {

        Supplier supplier = Supplier.builder()
                .supplierName(request.getSupplierName())
                .supplierCode(request.getSupplierCode())
                .client(client)
                .status(AppConstants.SUPPLIER_STATUS_ACTIVE)
                .build();

        supplier.setAddresses(new HashSet<>());
        supplier.setContacts(new HashSet<>());

        if (request.getAddresses() != null) {
            Set<SupplierAddress> addressEntities = request.getAddresses().stream()
                    .map(addr -> toAddressEntity(addr, supplier))
                    .collect(Collectors.toSet());

            Optional<SupplierAddress> primary = addressEntities.stream()
                    .filter(a -> Boolean.TRUE.equals(a.getDefault()))
                    .findFirst();

            boolean hasBilling = addressEntities.stream()
                    .anyMatch(a -> AppConstants.ADDRESS_TYPE_BILLING.equalsIgnoreCase(a.getAddressTypeCode()));
            boolean hasShipping = addressEntities.stream()
                    .anyMatch(a -> AppConstants.ADDRESS_TYPE_SHIPPING.equalsIgnoreCase(a.getAddressTypeCode()));

            if (Boolean.TRUE.equals(request.getCopyPrimaryToBilling()) && primary.isPresent() && !hasBilling) {
                addressEntities.add(cloneAddress(primary.get(), AppConstants.ADDRESS_TYPE_BILLING, supplier));
            }

            if (Boolean.TRUE.equals(request.getCopyPrimaryToShipping()) && primary.isPresent() && !hasShipping) {
                addressEntities.add(cloneAddress(primary.get(), AppConstants.ADDRESS_TYPE_SHIPPING, supplier));
            }

            supplier.setAddresses(addressEntities);
        }

        if (request.getContacts() != null) {
            supplier.setContacts(
                    request.getContacts().stream()
                            .map(contact -> toContactEntity(contact, supplier))
                            .collect(Collectors.toSet())
            );
        }

        return supplier;
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

    public void updateEntity(Supplier existing, SupplierUpdateRequest req) {
        existing.setSupplierName(req.getSupplierName());
        existing.setSupplierCode(req.getSupplierCode());
        existing.setStatus(req.getStatus());

        // Update addresses
        existing.getAddresses().clear();
        Set<SupplierAddress> addressEntities = new HashSet<>();

        if (req.getAddresses() != null) {
            for (AddressDTO dto : req.getAddresses()) {
                SupplierAddress addr = toAddressEntity(dto, existing);
                addressEntities.add(addr);
            }

            Optional<SupplierAddress> primary = addressEntities.stream()
                    .filter(a -> Boolean.TRUE.equals(a.getDefault()))
                    .findFirst();

            boolean hasBilling = addressEntities.stream()
                    .anyMatch(a -> AppConstants.ADDRESS_TYPE_BILLING.equalsIgnoreCase(a.getAddressTypeCode()));
            boolean hasShipping = addressEntities.stream()
                    .anyMatch(a -> AppConstants.ADDRESS_TYPE_SHIPPING.equalsIgnoreCase(a.getAddressTypeCode()));

            if (Boolean.TRUE.equals(req.getCopyPrimaryToBilling()) && primary.isPresent() && !hasBilling) {
                addressEntities.add(cloneAddress(primary.get(), AppConstants.ADDRESS_TYPE_BILLING, existing));
            }

            if (Boolean.TRUE.equals(req.getCopyPrimaryToShipping()) && primary.isPresent() && !hasShipping) {
                addressEntities.add(cloneAddress(primary.get(), AppConstants.ADDRESS_TYPE_SHIPPING, existing));
            }

            existing.getAddresses().addAll(addressEntities);
        }

        // Update contacts
        existing.getContacts().clear();
        if (req.getContacts() != null) {
            req.getContacts().forEach(dto -> {
                SupplierContact c = toContactEntity(dto, existing);
                existing.getContacts().add(c);
            });
        }
    }

    private SupplierAddress cloneAddress(SupplierAddress original, String type, Supplier supplier) {
        return SupplierAddress.builder()
                .supplier(supplier)
                .addressTypeCode(type)
                .addressLine1(original.getAddressLine1())
                .addressLine2(original.getAddressLine2())
                .city(original.getCity())
                .state(original.getState())
                .country(original.getCountry())
                .zipCode(original.getZipCode())
                .isDefault(false)
                .isCopiedFromPrimary(true)
                .build();
    }

    private SupplierAddress toAddressEntity(AddressDTO dto, Supplier supplier) {
        return SupplierAddress.builder()
                .supplier(supplier)
                .addressTypeCode(dto.getAddressTypeCode())
                .addressLine1(dto.getAddressLine1())
                .addressLine2(dto.getAddressLine2())
                .city(dto.getCity())
                .state(dto.getState())
                .country(dto.getCountry())
                .zipCode(dto.getZipCode())
                .isDefault(Boolean.TRUE.equals(dto.getIsDefault()))
                .isCopiedFromPrimary(Boolean.TRUE.equals(dto.getIsCopiedFromPrimary()))
                .build();
    }

    private SupplierContact toContactEntity(ContactDTO dto, Supplier supplier) {
        return SupplierContact.builder()
                .supplier(supplier)
                .name(dto.getName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .isPrimary(Boolean.TRUE.equals(dto.getIsPrimary()))
                .build();
    }

}