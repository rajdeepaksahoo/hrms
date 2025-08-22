package org.vrnda.hrms.service.resultset;

import java.util.List;

import org.vrnda.hrms.repository.dto.TasksStatusWorkflowDTO;

import lombok.Data;

@Data
public class TasksStatusWorkflowResultSet extends GenericResultSet {

	List<TasksStatusWorkflowDTO> tasksStatusWorkflowList;

}
