package org.vrnda.hrms.service.resultset;

import java.util.List;

import org.vrnda.hrms.entity.TasksAndAllocationsCommentsEntity;
import org.vrnda.hrms.repository.dto.TasksAndAllocationsCommentsDTO;

import lombok.Data;

@Data
public class TasksAndAllocationsCommentsResultSet extends GenericResultSet {
	
	List<TasksAndAllocationsCommentsEntity> tasksAndAllocationsCommentsEntityList;
	
	List<TasksAndAllocationsCommentsDTO> tasksAndAllocationsCommentsDTOList;
	
	TasksAndAllocationsCommentsEntity tasksAndAllocationsCommentsEntity;
	
	TasksAndAllocationsCommentsDTO tasksAndAllocationsCommentsDTO;

}
