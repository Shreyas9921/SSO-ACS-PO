package com.acs.Test.pojo;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name = "suppliers", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"client_id", "supplier_name"}),
        @UniqueConstraint(columnNames = {"client_id", "supplier_code"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @ToString.Exclude                // ← prevents recursive toString
    @EqualsAndHashCode.Exclude       // ← prevents recursive equals/hashCode
    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "supplier_name", nullable = false)
    private String supplierName;

    @ToString.Include
    @EqualsAndHashCode.Include
    @Column(name = "supplier_code", nullable = false)
    private String supplierCode;

    // private String status = SUPPLIER_STATUS_ACTIVE;
    private String status = "Active";

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ToString.Exclude                // ← prevents recursive toString
    @EqualsAndHashCode.Exclude       // ← prevents recursive equals/hashCode
    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SupplierAddress> addresses;
    //private Set<SupplierAddress> addresses = new HashSet<>();

    @ToString.Exclude                // ← prevents recursive toString
    @EqualsAndHashCode.Exclude       // ← prevents recursive equals/hashCode

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SupplierContact> contacts;
    //private Set<SupplierContact> contacts = new HashSet<>();

    @ToString.Exclude                // ← prevents recursive toString
    @EqualsAndHashCode.Exclude       // ← prevents recursive equals/hashCode

//    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<SupplierProductMapping> supplierProductMappings;


    /*@Transient
    @JsonIgnore
    @Getter @Setter
    private String originalState;*/


    @Column(name = "integration_received")
    private boolean integrationReceived;


    /*@Transient
    @JsonIgnore
    private static final ObjectMapper mapper = new ObjectMapper();*/

    /*@PostLoad
    private void cacheOriginalState() {
        try {
            this.originalState = mapper.writeValueAsString(this);
        } catch (Exception e) {
            this.originalState = null;
        }
    }*/

    /*public Supplier(Supplier other) {
        this.id = other.id;
        this.client = other.client; // If you want a deep copy, clone client as well
        this.supplierName = other.supplierName;
        this.supplierCode = other.supplierCode;
        this.status = other.status;
        this.createdAt = other.createdAt;
        this.updatedAt = other.updatedAt;

        // Shallow copy of addresses, contacts, and supplierProductMappings
        this.addresses = (other.addresses != null) ? new HashSet<>(other.addresses) : null;
        this.contacts = (other.contacts != null) ? new HashSet<>(other.contacts) : null;
        this.supplierProductMappings = (other.supplierProductMappings != null) ? new HashSet<>(other.supplierProductMappings) : null;

        this.originalState = other.originalState;
    }*/
}
