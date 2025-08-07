package com.acs.Test.dto;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserShippers implements Serializable {
    private Long id;

    private String username;
    private Integer status;

    private String companyName;

    private Integer roleType;

    private String marketDesc;

    private String accountId;

    private String locationCode;

    private String statusDesc;
}
