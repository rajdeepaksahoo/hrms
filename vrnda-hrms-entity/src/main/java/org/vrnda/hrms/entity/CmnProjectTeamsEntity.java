package org.vrnda.hrms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "CMN_PROJECT_TEAMS")
public class CmnProjectTeamsEntity extends EntityBaseModel {

	@Id
	@Column(name = "TEAM_ID")
	private Long teamId;

	@Column(name = "TEAM_NAME")
	private String teamName;

	@Column(name = "PROJECT_ID")
	private Long projectId;

	@Column(name = "SYSTEM_CONFIG_FLAG")
	private String systemConfigFlag;
	

}
