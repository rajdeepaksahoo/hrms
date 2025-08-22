package org.vrnda.hrms.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "CMN_PROJECT_TEAM_MEMBERS")
@IdClass(CmnProjectTeamMembersEntityPk.class)
public class CmnProjectTeamMembersEntity extends EntityBaseModel implements Serializable {

	@Column(name = "PROJECT_ID")
	@Id
	private Long projectId;

	@Column(name = "EMPLOYEE_ID")
	@Id
	private Long employeeId;

	@Column(name = "TEAM_ID")
	@Id
	private Long teamId;

	@Column(name = "SYSTEM_CONFIG_FLAG")
	private String systemConfigFlag;

	@Column(name = "JIRA_USER_NAME")
	private String jiraUserName;

	@Column(name = "JIRA_USER_TOKEN")
	private String jiraUserToken;

	@Column(name = "SCRUM_MASTER")
	private String scrumMaster;

	@Column(name = "ROLE_ID")
	private Long roleId;

}
