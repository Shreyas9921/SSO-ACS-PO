package com.acs.Test.dto;

import java.io.Serializable;
import java.util.List;
import com.acs.common.dto.UserClients;
import com.acs.Test.pojo.AdminShipperDetail;
import com.acs.Test.pojo.UserRoles;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Users implements Serializable {

    private Long id;
    private String userId;
    private String userName;
    private Integer role;
    private UserRoles userRoles;
    private String userEmail;
    private String firstName;
    private String lastName;
    private String tokenString;
    private String refreshTokenString;
    private List<UserClients> client;
    private List<String> customer;
    private List<String> merchant;
    private List<String> location;
    private List<AdminShipperDetail> adminShipperDetails;
    private String shipperNames;
    private Integer status;
    private Boolean isDisplay;

    public void setShipperNames(String shipperNames) {
        this.shipperNames = shipperNames;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public void setUserRoles(UserRoles userRoles) {
        this.userRoles = userRoles;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setTokenString(String tokenString) {
        this.tokenString = tokenString;
    }

    public void setRefreshTokenString(String refreshTokenString) {
        this.refreshTokenString = refreshTokenString;
    }

    public void setClient(List<UserClients> client) {
        this.client = client;
    }

    public void setCustomer(List<String> customer) {
        this.customer = customer;
    }

    public void setMerchant(List<String> merchant) {
        this.merchant = merchant;
    }

    public void setLocation(List<String> location) {
        this.location = location;
    }

    public void setAdminShipperDetails(List<AdminShipperDetail> adminShipperDetails) {
        this.adminShipperDetails = adminShipperDetails;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setDisplay(Boolean display) {
        isDisplay = display;
    }

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public Integer getRole() {
        return role;
    }

    public UserRoles getUserRoles() {
        return userRoles;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getTokenString() {
        return tokenString;
    }

    public String getRefreshTokenString() {
        return refreshTokenString;
    }

    public List<UserClients> getClient() {
        return client;
    }

    public List<String> getCustomer() {
        return customer;
    }

    public List<String> getMerchant() {
        return merchant;
    }

    public List<String> getLocation() {
        return location;
    }

    public List<AdminShipperDetail> getAdminShipperDetails() {
        return adminShipperDetails;
    }

    public String getShipperNames() {
        return shipperNames;
    }

    public Integer getStatus() {
        return status;
    }

    public Boolean getDisplay() {
        return isDisplay;
    }
}

