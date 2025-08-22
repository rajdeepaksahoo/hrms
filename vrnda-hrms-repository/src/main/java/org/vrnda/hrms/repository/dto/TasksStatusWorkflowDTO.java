package org.vrnda.hrms.repository.dto;

import java.sql.Blob;
import java.sql.Timestamp;
import java.util.HashMap;

import lombok.Data;

@Data
public class TasksStatusWorkflowDTO {

	private Long taskType;

	private String taskTypeName;

	private Long taskStatus;

	private String taskStatusName;

	private Blob workFlow;

	private String workFlowBlobToString;

	private String createdBy;

	private String updatedBy;

	private Timestamp createdDate;

	private Timestamp updatedDate;

	private HashMap<String, Object> workFlowMap;
}
