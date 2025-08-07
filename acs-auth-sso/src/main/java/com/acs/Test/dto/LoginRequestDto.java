package com.acs.Test.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class LoginRequestDto {

	  @NotNull(message = "Username cannot null")
	  @NotBlank(message = "Username cannot empty")
	  @ApiModelProperty(name = "userName", notes = "User Name", required = true)
	  @Setter(AccessLevel.NONE)
	  @Getter(AccessLevel.NONE)
	  private String userName;

	  @NotNull(message = "Password cannot null")
	  @NotBlank(message = "Password cannot empty")
	  @ApiModelProperty(name = "password", notes = "Password", required = true)
	  private String password;

	public String getUserName() {
		return userName.toLowerCase();
	}

	public void setUserName(String userName) {
		this.userName = userName.toLowerCase();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}