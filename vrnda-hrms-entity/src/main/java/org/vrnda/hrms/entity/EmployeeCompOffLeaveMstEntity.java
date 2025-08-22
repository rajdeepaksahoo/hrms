package org.vrnda.hrms.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "EMPLOYEE_COMPOFF_LEAVES_MST")
public class EmployeeCompOffLeaveMstEntity extends EntityBaseModel {

	@Id
	@Column(name = "EMP_CO_LV_MST_ID")
	private Long empCoLvMstId;

	@Column(name = "EMPLOYEE_ID")
	private Long employeeId;

	@Column(name = "IS_HALF_DAY")
	private String isHalfDay;

	@Column(name = "NO_OF_LEAVES")
	private BigDecimal noOfLeaves;

	@Column(name = "FROM_DATE")
	private Timestamp fromDate;

	@Column(name = "TO_DATE")
	private Timestamp toDate;

	@Column(name = "LEAVE_EXPIRY_DATE")
	private Timestamp leaveExpiryDate;
	
	@Column(name = "COMP_OFF_USED")
	private String compoffUsed;

}
