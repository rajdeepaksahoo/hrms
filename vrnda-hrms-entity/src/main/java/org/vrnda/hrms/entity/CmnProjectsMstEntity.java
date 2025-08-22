package org.vrnda.hrms.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "CMN_PROJECTS_MST")
public class CmnProjectsMstEntity extends EntityBaseModel {

	@Id
	@Column(name = "PROJECT_ID")
	private Long projectId;

	@Column(name = "PROJECT_NAME")
	private String projectName;

	@Column(name = "CLIENT_NAME")
	private String clientName;

	@Column(name = "START_DATE")
	private Date startDate;

	@Column(name = "SYSTEM_CONFIG_FLAG")
	private String systemConfigFlag;

	@Column(name = "JIRA_NAME")
	private String jiraName;

	@Column(name = "ORGANIZATION")
	private String organization;

	@Column(name = "PROJECT_HEAD")
	private Long projectHead;

	@Column(name = "JIRA_URL")
	private String jiraURL;

}
