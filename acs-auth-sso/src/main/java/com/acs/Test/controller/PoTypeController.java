package com.acs.Test.controller;

import com.acs.Test.dto.PoTypeLookupDTO;
import com.acs.Test.repository.PoTypeRepository;
import com.acs.common.annotation.Authenticated;
import com.acs.common.dto.UsersAuthDto;
import com.acs.common.enums.DeviceType;
import com.acs.common.utils.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/po-types/get")
public class PoTypeController {

//    @Autowired
    public PoTypeRepository poTypeRepository;

/*
    @Autowired
    @Qualifier("authEntityManagerFactory")
    private EntityManagerFactory authEntityManagerFactory;
*/

    public PoTypeController(PoTypeRepository poTypeRepository) {
        this.poTypeRepository = poTypeRepository;
    }

//    @Autowired
//    public PoTypeController(@Qualifier("poEntityManagerFactory") EntityManagerFactory emf) {
//        EntityManager em = emf.createEntityManager();
//        this.poTypeRepository = new PoTypeRepository(em); // Only if using custom repo impl
//    }

//        @Operation(summary = "Get PO types (e.g., In-Stock, Direct Ship)",
//            description = "Returns all PO types for Create PO dropdown",
//            tags = {"PO Management"})
    @GetMapping
    public ResponseEntity<List<PoTypeLookupDTO>> getPoTypes(
            /*@Authenticated(required = true) UsersAuthDto user,
            @RequestHeader(name = Constant.AUTH_TOKEN) String authToken,
            @RequestHeader(name = Constant.DEVICE_TYPE) DeviceType deviceType,
            @RequestHeader(name = Constant.APP_VERSION) String appVersion*/) {
        List<PoTypeLookupDTO> types = poTypeRepository.findAll().stream()
                .map(t -> new PoTypeLookupDTO(t.getId(), t.getPoType()))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(types);
    }
}
