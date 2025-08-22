package org.vrnda.hrms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "CMN_DESIGNATIONS_MST")
public class CmnDesignationsMstEntity extends EntityBaseModel {

	@Id
	@Column(name = "DESIGNATION_ID")
	private Long designationId;

	@Column(name = "DESIGNATION_NAME")
	private String designationName;

	@Column(name = "DESIGNATION_DESCRIPTION")
	private String designationDescription;

	@Column(name = "SYSTEM_CONFIG_FLAG")
	private String systemConfigFlag;

}
