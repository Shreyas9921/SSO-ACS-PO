package com.acs.Test.pojo;

import com.acs.Test.dto.UserShippers;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = AdminShipperDetail.TABLE_NAME)
public class AdminShipperDetail {

  public static final String TABLE_NAME = "admin_shipper_details";

  @Id
  @Column(name = "Id", columnDefinition = "bigint(15)")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "admin_id", columnDefinition = "bigint(15)")
  private Long adminId;

  @Column(name = "shipper_id", columnDefinition = "bigint(15)")
  private Long shipperId;

  @Column(name = "status", columnDefinition = "int(3)")
  private Integer status;

  /**
   * Separate table defined for this ACS 2.0
   */
	/*
	 * @Column(name = "account_id", columnDefinition = "varchar(30)") private String
	 * accountId;
	 */
  @Transient
  private UserShippers usershipper;
  
  @Transient
  private String usershipperName;

}
