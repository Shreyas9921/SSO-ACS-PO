package com.acs.Test.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
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

    public ContactDTO(String name, String email, String phone, Boolean isPrimary) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.isPrimary = isPrimary;
    }
}
