package com.acs.Test.pojo;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;



@Data
@Entity
@Table(name = UserRoles.TABLE_NAME)
public class UserRoles {
    public static final String TABLE_NAME = "user_roles";

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "role")
    private String role;

    @Column(name = "role_type")
    private Integer roleType;

    @Column(name = "role_descr")
    private String roleDescr;

    @Column(name = "mobile")
    private Integer mobile;

    @Column(name = "web")
    private Integer web;

    @Column(name = "status")
    private Integer status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "redirect_page")
    private String redirectPage;

    @Column(name = "default_landing_page")
	private Long defaultLandingPage;

}
