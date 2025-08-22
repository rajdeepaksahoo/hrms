package org.vrnda.hrms.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "TASKS_AND_ALLOCATIONS")
public class TasksAndAllocationsEntity {

	@Id
	@Column(name = "TASK_ID")
	private String taskId;

	@Column(name = "TASK_TYPE")
	private Long taskType;

	@Column(name = "PRIORITY")
	private Long priority;

	@Column(name = "PROJECT_ID")
	private Long projectId;

	@Column(name = "TASK_DESCRIPTION")
	private String taskDescription;

	@Column(name = "CURRENT_STATUS")
	private Long currentStatus;

	@Column(name = "SCRUM_MASTER")
	private Long scrumMaster;

	@Column(name = "ASSIGNEE")
	private Long assignee;

	@Column(name = "TESTER")
	private Long tester;

	@Column(name = "DEVELOPER")
	private Long developer;

	@Column(name = "ESTIMATED_EFFORTS")
	private Long estimatedEfforts;

	@Column(name = "EXPECTED_DEV_START_DATE")
	private Timestamp expectedDevStartDate;

	@Column(name = "ACTUAL_DEV_START_DATE")
	private Timestamp actualDevStartDate;

	@Column(name = "EXPECTED_DEV_COMPLETION_DATE")
	private Timestamp expectedDevCompletionDate;

	@Column(name = "ACTUAL_DEV_COMPLETION_DATE")
	private Timestamp actualDevCompletionDate;

	@Column(name = "EXPECTED_TESTING_START_DATE")
	private Timestamp expectedTestingStartDate;

	@Column(name = "ACTUAL_TESTING_START_DATE")
	private Timestamp actualTestingStartDate;

	@Column(name = "EXPECTED_TESTING_COMPLETION_DATE")
	private Timestamp expectedTestingCompletionDate;

	@Column(name = "ACTUAL_TESTING_COMPLETION_DATE")
	private Timestamp actualTestingCompletionDate;

	@Column(name = "EXPECTED_DELIVERY_DATE")
	private Timestamp expectedDeliveryDate;

	@Column(name = "ACTUAL_DELIVERY_DATE")
	private Timestamp actualDeliveryDate;

	@Column(name = "EXTERNAL_ASSIGNEE")
	private String externalAssignee;

	@Column(name = "EXTERNAL_STATUS")
	private String externalStatus;

	@Column(name = "SPRINT")
	private String sprint;

	@Column(name = "SCREEN_NAME")
	private String screenName;

	@Column(name = "REPORTER")
	private String reporter;

	@Column(name = "STORY_POINTS")
	private Integer storyPoints;

	@Column(name = "SPRINT_LINK")
	private String sprintLink;

	@Column(name = "ISSUE_CREATED_DATE")
	private Timestamp issueCreatedDate;

	@Column(name = "ISSUE_UPDATED_DATE")
	private Timestamp issueUpdatedDate;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@Column(name = "CREATED_DATE")
	private Timestamp createdDate;

	@Column(name = "UPDATED_DATE")
	private Timestamp updatedDate;

}
