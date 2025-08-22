package org.vrnda.hrms.entity;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "EMPLOYEE_ALL_CONFIG_DETAILS")
public class EmployeeAllConfigDetailsEntity extends EntityBaseModel{

	@Id
	@Column(name = "EMP_ALL_CONFIG_ID")
	private Long empAllConfigId;

	@Column(name = "EMPLOYEE_ID")
	private Long employeeId;

	@Column(name = "YEAR_ID")
	private Long yearId;

	@Column(name = "ALL_CONFIGS")
	private Blob allConfigs;

}
