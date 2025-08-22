package org.vrnda.hrms.service;

import java.util.List;

import org.vrnda.hrms.entity.TasksAndAllocationsEntity;
import org.vrnda.hrms.repository.dto.TasksAndAllocationsDTO;
import org.vrnda.hrms.service.resultset.TasksAndAllocationsResultSet;

public interface TasksAndAllocationsService extends GenericService<TasksAndAllocationsEntity, Long> {

	TasksAndAllocationsResultSet loadIssuesFromJiraByProjectName(String projectName);

	TasksAndAllocationsResultSet getAllTasksAndAllocations(Long employeeId, Long statusLookUpId);

	TasksAndAllocationsResultSet getAllTaskDetailsBasedOnTaskId(String taskId);

	TasksAndAllocationsResultSet updateTaskAndAllocations(TasksAndAllocationsDTO tasksAndAllocationsDTO,
			String loggedInUser);

	TasksAndAllocationsResultSet getAllTaskDetailsBasedOnAssigne(Long assignee, Long statusLookUpId, String viewType);

	TasksAndAllocationsResultSet getAllTaskDetailsByProjectId(List<Long> projectId);

	TasksAndAllocationsResultSet getAllTaskDetailsByProjectId(Long projectId);

	TasksAndAllocationsResultSet getAllMembersByProjectIdAndStatusLookUpId(Long projectId, Long statusLookUpId);

	TasksAndAllocationsResultSet getAllActiveTaskDetailsBasedOnAssigne(Long assignee);

}
