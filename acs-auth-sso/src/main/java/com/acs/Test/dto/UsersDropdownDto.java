package com.acs.Test.dto;

import java.io.Serializable;

public class UsersDropdownDto implements Serializable {

	private static final long serialVersionUID = -8370977958236326573L;

	private Long id;

	private String userName;
	
	private Boolean isDisplay;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Boolean getIsDisplay() {
		return isDisplay;
	}

	public void setIsDisplay(Boolean isDisplay) {
		this.isDisplay = isDisplay;
	}

}
