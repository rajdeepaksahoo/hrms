package org.vrnda.hrms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "CMN_DEPARTMENTS_MST")
public class CmnDepartmentsMstEntity extends EntityBaseModel {

	@Id
	@Column(name = "DEPARTMENT_ID")
	private Long departmentId;

	@Column(name = "DEPARTMENT_NAME")
	private String departmentName;

	@Column(name = "DEPARTMENT_DESCRIPTION")
	private String departmentDescription;

	@Column(name = "SYSTEM_CONFIG_FLAG")
	private String systemConfigFlag;

}