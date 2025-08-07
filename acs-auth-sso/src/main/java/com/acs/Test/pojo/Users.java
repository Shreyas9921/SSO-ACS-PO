package com.acs.Test.pojo;

import com.acs.Test.commons.utils.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import lombok.Data;
import lombok.ToString;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@ToString
@Entity

@Table(name = Users.TABLE_NAME)
public class Users implements Serializable {

	private static final long serialVersionUID = 3390328084948717274L;

	public static final String TABLE_NAME = "users";

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "created")
	@CreatedDate
	@JsonFormat(shape = Shape.STRING, pattern = Constant.DATE_PATTEREN)
	private Date created;

	@Column(name = "updated")
	@LastModifiedDate
	@JsonFormat(shape = Shape.STRING, pattern = Constant.DATE_PATTEREN)
	private Date updated;

	@Column(name = "key_cloak_user_id")
	private String keyCloakUserId;

	@Column(name = "username")
	private String userName;

	@Column(name = "status")
	private Integer status;

	@Column(name = "email")
	private String email;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "client")
	private String client;
	@Column(name = "customer")
	private String customer;

	@Column(name = "merchant")
	private String merchant;

	@Column(name = "location")
	private String location;

	@Column(name = "mobile")
	private Long mobile;

	@Column(name = "number_of_login")
	private Long numberOfLogin;
	@Column(name = "is_online")
	private Integer isOnline;

	@Column(name = "role_id")
	private Integer roleId;

	@Column(name = "role")
	private String userRole;

	@Column(name = "is_display")
	private Boolean isDisplay;

	@Column(name = "created_by")
	private Long createdBy;

	@Column(name = "updated_by")
	private Long updatedBy;

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public String getKeyCloakUserId() {
		return keyCloakUserId;
	}

	public void setKeyCloakUserId(String keyCloakUserId) {
		this.keyCloakUserId = keyCloakUserId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getMerchant() {
		return merchant;
	}

	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Long getMobile() {
		return mobile;
	}

	public void setMobile(Long mobile) {
		this.mobile = mobile;
	}

	public Long getNumberOfLogin() {
		return numberOfLogin;
	}

	public void setNumberOfLogin(Long numberOfLogin) {
		this.numberOfLogin = numberOfLogin;
	}

	public Integer getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(Integer isOnline) {
		this.isOnline = isOnline;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public Boolean getIsDisplay() {
		return isDisplay;
	}

	public void setIsDisplay(Boolean isDisplay) {
		this.isDisplay = isDisplay;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}
}

/*
import com.acs.Test.commons.utils.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import lombok.Data;
import lombok.ToString;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@ToString
@Entity

@Table(name = Users.TABLE_NAME)
public class Users implements Serializable {

	private static final long serialVersionUID = 3390328084948717274L;

	public static final String TABLE_NAME = "users";

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "created")
	@CreatedDate
	@JsonFormat(shape = Shape.STRING, pattern = Constant.DATE_PATTEREN)
	private Date created;

	@Column(name = "updated")
	@LastModifiedDate
	@JsonFormat(shape = Shape.STRING, pattern = Constant.DATE_PATTEREN)
	private Date updated;

	@Column(name = "key_cloak_user_id")
	private String keyCloakUserId;

	@Column(name = "username")
	private String userName;

	@Column(name = "status")
	private Integer status;

	@Column(name = "email")
	private String email;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "client")
	private String client;
	@Column(name = "customer")
	private String customer;

	@Column(name = "merchant")
	private String merchant;

	@Column(name = "location")
	private String location;

	@Column(name = "mobile")
	private Long mobile;

	@Column(name = "number_of_login")
	private Long numberOfLogin;
	@Column(name = "is_online")
	private Integer isOnline;
	
	@Column(name = "role_id")
	private Integer roleId;

	@Column(name = "role")
	private String userRole;
	
	@Column(name = "is_display")
	private Boolean isDisplay;
	
	@Column(name = "created_by")
	private Long createdBy;
	
	@Column(name = "updated_by")
	private Long updatedBy;

}
*/
