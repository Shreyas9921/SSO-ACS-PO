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
    @Column(name = "status")
    private String status = "Active";

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "integration_received")
    private boolean integrationReceived;

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

//    @ToString.Exclude                // ← prevents recursive toString
//    @EqualsAndHashCode.Exclude       // ← prevents recursive equals/hashCode

//    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<SupplierProductMapping> supplierProductMappings;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getSupplierName() {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<SupplierAddress> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<SupplierAddress> addresses) {
        this.addresses = addresses;
    }

    public Set<SupplierContact> getContacts() {
        return contacts;
    }

    public void setContacts(Set<SupplierContact> contacts) {
        this.contacts = contacts;
    }

    public boolean isIntegrationReceived() {
        return integrationReceived;
    }

    public void setIntegrationReceived(boolean integrationReceived) {
        this.integrationReceived = integrationReceived;
    }
}
