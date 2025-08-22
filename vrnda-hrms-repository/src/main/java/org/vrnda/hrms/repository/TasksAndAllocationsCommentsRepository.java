package org.vrnda.hrms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vrnda.hrms.entity.TasksAndAllocationsCommentsEntity;

@Repository
public interface TasksAndAllocationsCommentsRepository
		extends PagingAndSortingRepository<TasksAndAllocationsCommentsEntity, Long> {

	@Query("SELECT t  from TasksAndAllocationsCommentsEntity as t where t.taskId = :taskId order by tasksAndAllocCommentId desc")
	public List<TasksAndAllocationsCommentsEntity> getCommentsByTaskId(@Param("taskId") String taskId);

}
