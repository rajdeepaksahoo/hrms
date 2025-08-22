package org.vrnda.hrms.service;

import org.springframework.web.multipart.MultipartFile;
import org.vrnda.hrms.entity.TasksAndAllocationsCommentsEntity;
import org.vrnda.hrms.repository.dto.TasksAndAllocationsCommentsDTO;
import org.vrnda.hrms.service.resultset.TasksAndAllocationsCommentsResultSet;

public interface TasksAndAllocationsCommentsService extends GenericService<TasksAndAllocationsCommentsEntity, Long> {

	TasksAndAllocationsCommentsResultSet createComment(MultipartFile commentBlob, String taskID, String loggedInUser);

	TasksAndAllocationsCommentsResultSet getCommentsByTaskId(String taskId);

}
