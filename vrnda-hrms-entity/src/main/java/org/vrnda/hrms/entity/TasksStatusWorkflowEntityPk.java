package org.vrnda.hrms.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class TasksStatusWorkflowEntityPk implements Serializable {

	private Long taskType;

	private Long taskStatus;

}
