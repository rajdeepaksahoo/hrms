package org.vrnda.hrms.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "EMPLOYEE_CHECKIN_CHECKOUT_HIST")
public class EmployeeCheckinCheckoutHistEntity extends EntityBaseModel {

	@Id
	@Column(name = "EMP_CHECKIN_ID")
	private Long empCheckinId;

	@Column(name = "EMPLOYEE_ID")
	private Long employeeId;

	@Column(name = "CHECKIN_TIME")
	private Date checkinTime;

	@Column(name = "CHECKOUT_TIME")
	private Date checkoutTime;

	@Column(name = "PARENT_EMP_CHECKIN_ID")
	private Long parentEmpCheckinId;

}
