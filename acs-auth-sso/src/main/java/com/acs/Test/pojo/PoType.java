package com.acs.Test.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "po_types")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PoType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "po_type", nullable = false, unique = true)
    private String poType;

/*
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "client_id", nullable = false)
  private Client client;
*/

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPoType(String poType) {
        this.poType = poType;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Integer getId() {
        return id;
    }

    public String getPoType() {
        return poType;
    }

    public Client getClient() {
        return client;
    }

    @JsonIgnore
    @ToString.Exclude                // ← prevents recursive toString
    @EqualsAndHashCode.Exclude       // ← prevents recursive equals/hashCode
    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
}
