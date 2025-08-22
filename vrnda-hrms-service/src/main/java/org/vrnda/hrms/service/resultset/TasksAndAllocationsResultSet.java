package org.vrnda.hrms.service.resultset;

import java.util.List;

import org.vrnda.hrms.entity.TasksAndAllocationsEntity;
import org.vrnda.hrms.repository.dto.EmployeeDetailsDTO;
import org.vrnda.hrms.repository.dto.TasksAndAllocationsDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_EMPTY)
public class TasksAndAllocationsResultSet extends GenericResultSet {

	TasksAndAllocationsDTO tasksAndAllocationsDto;

	List<TasksAndAllocationsDTO> tasksAndAllocationsDtoList;

	List<TasksAndAllocationsEntity> tasksAndAllocationsEntityList;

	List<TasksAndAllocationsDTO> openList;

	List<TasksAndAllocationsDTO> devInProgressList;

	List<TasksAndAllocationsDTO> onHoldList;

	List<TasksAndAllocationsDTO> reOpenList;

	List<TasksAndAllocationsDTO> backLogList;

	List<TasksAndAllocationsDTO> readyForTestingList;

	List<TasksAndAllocationsDTO> testingInProgressList;

	List<TasksAndAllocationsDTO> readyForDeliveryList;

	List<TasksAndAllocationsDTO> moveToRegression;

	List<TasksAndAllocationsDTO> regressionInProgress;

	List<TasksAndAllocationsDTO> readyForRegression;

	TasksAndAllocationsEntity tasksAndAllocationsEntity;

	List<EmployeeDetailsDTO> scrumMastersList;

	List<EmployeeDetailsDTO> developersList;

	List<EmployeeDetailsDTO> testersList;

	List<String> taskIdsList;

}
