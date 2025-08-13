package com.acs.Test.service.impl;

import com.acs.Test.commons.mapper.SupplierMapper;
import com.acs.Test.commons.specification.SupplierSpecifications;
import com.acs.Test.dto.acs.AcsCreateVendorRequest;
import com.acs.Test.dto.misc.FcResponse;
import com.acs.Test.dto.request.supplier.SupplierCreateRequest;
import com.acs.Test.dto.response.supplier.SupplierResponse;
import com.acs.Test.dto.request.supplier.SupplierSearchRequest;
import com.acs.Test.exception.BadRequestException;
import com.acs.Test.exception.DuplicateEntityException;
import com.acs.Test.exception.ResourceNotFoundException;
import com.acs.Test.pojo.Client;
import com.acs.Test.pojo.FulfilmentCenter;
import com.acs.Test.pojo.Supplier;
import com.acs.Test.pojo.SupplierFcMappings;
import com.acs.Test.repository.*;
import com.acs.Test.service.SupplierService;
import com.acs.Test.service.acs.AcsService;
import com.acs.common.dto.UsersAuthDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.acs.Test.commons.constant.AppConstants.SUPPLIER;
import static com.acs.Test.commons.helper.AcsRequestBuilder.buildAcsRequest;

@Service
//@Transactional(transactionManager = "poTransactionManager") // Use PO transaction manager
@RequiredArgsConstructor
@Transactional
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private AcsService acsService;

    private final ClientRepository clientRepository;

    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;

    private final FulfilmentCenterRepository fulfilmentCenterRepository;

    private final SupplierProductMappingRepository supplierProductMappingRepository;
    private final SupplierFcMappingRepository supplierFcMappingRepository;

    /*public SupplierServiceImpl(SupplierRepository supplierRepository,
                               SupplierMapper supplierMapper,
                               SupplierProductMappingRepository supplierProductMappingRepository,
                               SupplierFcMappingRepository supplierFcMappingRepository,
                               ClientRepository clientRepository,
                               FulfilmentCenterRepository fulfilmentCenterRepository)
    {
        this.supplierRepository = supplierRepository;
        this.supplierMapper = supplierMapper;
        this.supplierProductMappingRepository = supplierProductMappingRepository;
        this.supplierFcMappingRepository = supplierFcMappingRepository;
        this.clientRepository = clientRepository;
        this.fulfilmentCenterRepository = fulfilmentCenterRepository;
    }*/

    @Override
    public SupplierResponse createSupplier(SupplierCreateRequest request, UsersAuthDto user) {
//        Long clientId = userContext.getClientId();

//        Client client = clientRepository.findById(clientId.intValue())
//                .orElseThrow(() -> new ResourceNotFoundException("Client not found"));

        // Get the ACS clientId from the token (first entry) or throw

//        String acsClientId = (user.getClientIds() != null && !user.getClientIds().isEmpty())
//                ? user.getClientIds().get(0)
//                : throw new BadRequestException("No clientId found in ACS token");

//        // Lookup PO client record using accountId
//        Client client = clientRepository.findByAccountId(acsClientId)
//                .orElseThrow(() -> new ResourceNotFoundException("Client not found for accountId: "));


        // Multi-Client Lookup Version
        // 1️Validate and get clientIds from ACS token
        /*List<String> acsClientIds = (user.getClientIds() != null && !user.getClientIds().isEmpty())
            ? user.getClientIds()
            : throw new BadRequestException("No clientIds found in ACS token");*/

        List<String> acsClientIds; /*= List.of();*/

        if(user.getClientIds() != null && !user.getClientIds().isEmpty())
            acsClientIds = user.getClientIds();
        else
            throw new BadRequestException("No clientId found in ACS token");

        // Lookup PO client record using accountId & Find the first matching client in the PO DB
        Client client = acsClientIds.stream()
            .map(clientRepository::findByAccountId)   // returns Optional<Client>
            .filter(Optional::isPresent)              // keep only present
            .map(Optional::get)                       // unwrap Client
            .findFirst()                              // get the first match
            .orElseThrow(() -> new ResourceNotFoundException(
                    "No matching client found for any ACS clientId: " + acsClientIds));

        System.out.println("Client ID for client context: " +acsClientIds.stream().toArray());

        // Validate ACS config
        if (client.getAccountId() == null || client.getAcsApiKey() == null) {
            throw new BadRequestException("ACS Account ID or API Key is missing for this client");
        }

        // Validate cookie/session token
        String cookieValue = client.getCookie();
        clientRepository.findByCookie(cookieValue)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid session token"));

        supplierRepository.findBySupplierNameAndClientId(request.getSupplierName(), client.getId())
                .ifPresent(s -> { throw new DuplicateEntityException("Supplier name already exists"); });

        supplierRepository.findBySupplierCodeAndClientId(request.getSupplierCode(), client.getId())
                .ifPresent(s -> { throw new DuplicateEntityException("Supplier code already exists"); });

        if (request.getFcIds() == null || request.getFcIds().isEmpty()) {
            throw new BadRequestException("At least one Fulfillment Center must be selected.");
        }

        if (Boolean.TRUE.equals(request.getCopyPrimaryToBilling()) ||
                Boolean.TRUE.equals(request.getCopyPrimaryToShipping())) {

            boolean hasDefault = request.getAddresses().stream()
                    .anyMatch(a -> Boolean.TRUE.equals(a.getIsDefault()));
            if (!hasDefault) {
                throw new BadRequestException("Default supplier address is required if copying to billing/shipping");
            }
        }

        // Save supplier
        Supplier supplier = supplierMapper.toEntity(request, client);
        Supplier saved = supplierRepository.save(supplier);

        // Map FCs
        List<FcResponse> fcResponses = List.of();
        if (request.getFcIds() != null && !request.getFcIds().isEmpty()) {
            List<FulfilmentCenter> fcList = fulfilmentCenterRepository.findAllById(request.getFcIds());

            List<SupplierFcMappings> fcMappings = fcList.stream()
                    .map(fc -> SupplierFcMappings.builder()
                            .supplier(saved)
                            .fulfilmentCenter(fc)
                            .status(true)
                            .build())
                    .collect(Collectors.toList());

            supplierFcMappingRepository.saveAll(fcMappings);

            // Prepare response FC list
            fcResponses = fcMappings.stream()
                    .map(mapping -> new FcResponse(
                            mapping.getFulfilmentCenter().getId(),
                            mapping.getFulfilmentCenter().getFcName()))
                    .collect(Collectors.toList());
        }

        // ✅ Map FCs to warehouseLocationName
        List<SupplierFcMappings> supplierFcMappings = supplierFcMappingRepository.findBySupplierId(supplier.getId());
        List<String> fcNames = supplierFcMappings.stream()
                .map(mapping -> mapping.getFulfilmentCenter().getFcName())
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (fcNames.isEmpty()) {
            throw new BadRequestException("At least one Fulfillment Center is required to sync with ACS.");
        }

        // request.setWarehouseLocationName(fcNames);

        // 🔄 Sync with ACS
        try {  AcsCreateVendorRequest acsRequest = buildAcsRequest(saved, fcNames);

//System.out.println("Sending ACS Request Body: {}"+ new ObjectMapper().writeValueAsString(acsRequest));
//
//      //  Log header values explicitly
//      System.out.println(
//        "ACS Request Headers: AccountId=" + client.getAccountId() +
//          ", ApiKey=" + (client.getAcsApiKey() != null ? client.getAcsApiKey().substring(0, 4) + "****" : "null") +
//          ", DEVICE-TYPE=" + acsService.getDeviceType() +
//          ", VER=" + acsService.getVersion()
//      );
            // Ensure getter exists or expose config


//      AcsCreateVendorRequest acsRequest = buildAcsRequest(saved);
            boolean isSuccess = acsService.createVendor(acsRequest, client.getAccountId(), client.getAcsApiKey(), client.getCookie());

            if (isSuccess) {
                saved.setIntegrationReceived(true);
                supplierRepository.save(saved);
            }
        } catch (Exception e) {
            System.out.println("Failed to sync supplier to ACS: {}"+ e.getMessage() + e);
        }

        // Audit
        /*auditLogService.log(
                "CREATE",
                SUPPLIER,
                saved.getId().toString(),
                null,
                saved,
                userContext.getUserId(),
                userContext.getUsername(),
                SUPPLIER
        );*/

        return supplierMapper.toResponse(saved, 0, List.of(), fcResponses);
    }

    @Override
    public SupplierResponse getSupplierById(Integer id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));

//        Integer productCount = supplierProductMappingRepository.countBySupplierId(Long.valueOf(supplier.getId()));
        Integer productCount = supplierProductMappingRepository.countBySupplierId(supplier.getId());
        List<String> skus = supplierProductMappingRepository.findProductsBySupplierId(supplier.getId());
//        Integer productCount = 0;
//        List<String> skus = List.of();

        List<SupplierFcMappings> fcMappings = supplierFcMappingRepository.findBySupplierId(supplier.getId());
//        List<String> fcMappings = List.of();

        List<FcResponse> fcResponses = fcMappings.stream()
                .filter(SupplierFcMappings::getStatus)
                .map(mapping -> supplierMapper.toFcResponse(mapping.getFulfilmentCenter()))
                .collect(Collectors.toList());
//        List<FcResponse> fcResponses = List.of();

        return supplierMapper.toResponse(supplier, productCount, skus, fcResponses);
    }

    @Override
    public List<SupplierResponse> getAllSuppliers() {
        return supplierRepository.findAll().stream()
                .map(supplier -> {
//                    Integer productCount = supplierProductMappingRepository.countBySupplierId(Long.valueOf(supplier.getId()));
                    Integer productCount = supplierProductMappingRepository.countBySupplierId(supplier.getId());
                    List<String> skus = supplierProductMappingRepository.findProductsBySupplierId(supplier.getId());

                    List<SupplierFcMappings> fcMappings = supplierFcMappingRepository.findBySupplierId(supplier.getId());
                    List<FcResponse> fcResponses = fcMappings.stream()
                            .filter(SupplierFcMappings::getStatus) // Only active ones
                            .map(mapping -> supplierMapper.toFcResponse(mapping.getFulfilmentCenter()))
                            .collect(Collectors.toList());

                    return supplierMapper.toResponse(supplier, productCount, skus, fcResponses);
                })
                .collect(Collectors.toList());
    }


    @Override
    public Page<SupplierResponse> searchSuppliers(SupplierSearchRequest filters,
                                                  int page,
                                                  int size,
                                                  String sortField) {
        String sortDirection = sortField.charAt(0) == '!' ? "desc" : "asc";
        sortField = sortField.replace("!", "");

        Pageable pageable = PageRequest.of(
                Math.max(page - 1, 0),
                size,
                Sort.by(Sort.Direction.fromString(sortDirection), sortField)
        );

        Specification<Supplier> spec = SupplierSpecifications.buildSpecification(filters);
        Page<Supplier> supplierPage = supplierRepository.findAll(spec, pageable);

        return supplierPage.map(supplier -> {
            Integer supplierId = supplier.getId();

//            Integer productCount = supplierProductMappingRepository.countBySupplierId(Long.valueOf(supplierId));
            Integer productCount = supplierProductMappingRepository.countBySupplierId(supplier.getId());
            List<String> skus = supplierProductMappingRepository.findProductsBySupplierId(Math.toIntExact(Long.valueOf(supplierId)));
//            Integer productCount = 0;
//            List<String> skus = List.of();

            List<SupplierFcMappings> fcMappings = supplierFcMappingRepository.findBySupplierId(supplierId);
//            List<String> fcMappings = List.of();

            List<FcResponse> fcResponses = fcMappings.stream()
                    .map(mapping -> new FcResponse(
                            mapping.getFulfilmentCenter().getId(),     // Adjust if different
                            mapping.getFulfilmentCenter().getFcName()    // Adjust if different
                    ))
                    .collect(Collectors.toList());
//            List<FcResponse> fcResponses = List.of();

            return supplierMapper.toResponse(supplier, productCount, skus, fcResponses);
        });
    }

}
