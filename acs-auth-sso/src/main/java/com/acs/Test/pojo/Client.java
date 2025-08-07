package com.acs.Test.pojo;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ToString.Include @EqualsAndHashCode.Include
    @Column(name = "client_code", nullable = false, unique = true, length = 50)
    private String clientCode;

    @ToString.Include @EqualsAndHashCode.Include
    @Column(name = "client_name", nullable = false, length = 200)
    private String clientName;


    @Column(name = "is_active")
    private Boolean isActive = true;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "uuid", nullable = false, unique = true, length = 36, updatable = false)
    private String uuid;
}

