package com.acs.Test.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class ContactDTO {
    @NotBlank
    @Pattern(regexp = "^[A-Za-z\\s-]+$" ,message = "Name must contain only letters and spaces")
    private String name;
    @Email(message = "Please provide a valid email address")
    @NotBlank
    private String email;
    private String phone;
    private Boolean isPrimary;
}
