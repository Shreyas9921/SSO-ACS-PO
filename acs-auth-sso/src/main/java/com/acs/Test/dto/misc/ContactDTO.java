package com.acs.Test.dto.misc;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/*@Data
@NoArgsConstructor
@Builder*/
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getPrimary() {
        return isPrimary;
    }

    public void setPrimary(Boolean primary) {
        isPrimary = primary;
    }

    @Override
    public String toString() {
        return "ContactDTO{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", isPrimary=" + isPrimary +
                '}';
    }
}
