package org.vrnda.hrms.entity;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "CMN_LEAVE_CONFIG_DETAILS")
public class CmnLeaveConfigDetailsEntity  extends EntityBaseModel  {

	@Id
	@Column(name = "CONFIGURATION_ID")
	private Long configurationId;

	@Column(name = "LEAVE_CONFIGS")
	private Blob leaveConfigs;

	@Column(name = "SYSTEM_CONFIG_FLAG")
	private String systemConfigFlag;

}
