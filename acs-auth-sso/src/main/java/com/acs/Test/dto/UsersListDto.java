package com.acs.Test.dto;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UsersListDto implements Serializable {

    private Long id;
    private String userId;
    private String userName;
    private Integer role;
    private String userRole ;
    private String userEmail;
    private String firstName;
    private String lastName;
    private String tokenString;
    private String refreshTokenString;
    private Integer status;
    private Integer roleType;
    private Boolean isDisplay;

}
