package org.vrnda.hrms.repository.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class TasksAndAllocationsDTO {

	String taskId;

	Long taskType;

	Long priority;

	Long projectId;

	String taskDescription;

	Long currentStatus;

	Long scrumMaster;

	Long assignee;

	Long tester;

	Long developer;

	Long estimatedEfforts;

	Timestamp expectedDevStartDate;

	Timestamp actualDevStartDate;

	Timestamp expectedDevCompletionDate;

	Timestamp actualDevCompletionDate;

	Timestamp expectedTestingStartDate;

	Timestamp actualTestingStartDate;

	Timestamp expectedTestingCompletionDate;

	Timestamp actualTestingCompletionDate;

	Timestamp expectedDeliveryDate;

	Timestamp actualDeliveryDate;

	String externalAssignee;

	String externalStatus;

	String sprint;

	String screenName;

	String reporter;

	Integer storyPoints;

	String sprintLink;

	Timestamp issueCreatedDate;

	Timestamp issueUpdatedDate;

	String taskTypeDescription;

	String priorityDescription;

	String currentStatusDescription;

	String taskTypeString;

	String priorityString;

	Long statusLookupId;

	Object issueCompleteDetails;

	String employeeFullName;

	private String createdBy;

	private String updatedBy;

	private Timestamp createdDate;

	private Timestamp updatedDate;

	private String jiraURL;

	private String jiraName;

	private String developerFullName;

	private String testerFullName;

}
