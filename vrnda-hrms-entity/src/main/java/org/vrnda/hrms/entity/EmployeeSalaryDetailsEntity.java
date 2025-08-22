package org.vrnda.hrms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity
@Table(name = "EMPLOYEE_SALARY_DETAILS")
public class EmployeeSalaryDetailsEntity extends EntityBaseModel{

	@Id
	@Column(name="EMPLOYEE_ID")
	private Long employeeId;

	@Column(name="SALARY_DETAILS")
	@Lob
	private byte[] employeeSalaryDetails;

	@Transient
	private String empSalaryDetailsData;

}
