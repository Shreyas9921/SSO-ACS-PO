package com.acs.Test.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

//@PasswordMatches
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateUserDto {
    private static final long serialVersionUID = -1874496044499201875L;

    @NotNull(message = "First Name cannot be null")
    @NotBlank(message = "First Name cannot be empty")
    private String firstName;

    @NotNull(message = "Last Name cannot be null")
    @NotBlank(message = "Last Name cannot be empty")
    private String lastName;

    @NotNull(message = "User name cannot be null")
    @NotBlank(message = "User name cannot be empty")
    private String userName;

//    @NotNull
//    @NotBlank(message = "Password cannot be blank")
//    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$", message = "Invalid password format. Must conatain one UpperCase, one LowerCase, one digit, one special char and must be 8 char or more.")
//    private String password;
//
//    @NotNull(message = "Confirm Password cannot be null")
//    @NotBlank(message = "Confirm Password cannot be empty")
//    private String confirmPassword;

    @NotNull(message = "Status cannot be null")
    private Integer status;

    private String userEmail;


    private Long phoneNo;

    private Integer roleId;
    




 
}
