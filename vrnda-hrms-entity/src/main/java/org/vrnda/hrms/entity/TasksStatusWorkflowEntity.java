package org.vrnda.hrms.entity;

import java.sql.Blob;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@IdClass(TasksStatusWorkflowEntityPk.class)
@Table(name = "TASKS_STATUS_WORKFLOW")
public class TasksStatusWorkflowEntity {

	@Id
	@Column(name = "TASK_TYPE")
	private Long taskType;

	@Id
	@Column(name = "TASK_STATUS")
	private Long taskStatus;

	@Column(name = "WORKFLOW")
	private Blob workFlow;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@Column(name = "CREATED_DATE")
	private Timestamp createdDate;

	@Column(name = "UPDATED_DATE")
	private Timestamp updatedDate;

}
