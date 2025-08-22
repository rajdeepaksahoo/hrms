package org.vrnda.hrms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.vrnda.hrms.entity.TasksStatusWorkflowEntity;
import org.vrnda.hrms.entity.TasksStatusWorkflowEntityPk;

@Repository
public interface TasksStatusWorkflowRepository
		extends PagingAndSortingRepository<TasksStatusWorkflowEntity, TasksStatusWorkflowEntityPk> {

	@Query("select t,(select lookupName from CmnLookupMstEntity c where c.lookupId = t.taskType) as taskTypeName,(select lookupName from CmnLookupMstEntity c where c.lookupId = t.taskStatus) as taskStatusName from TasksStatusWorkflowEntity t")
	List<Object[]> getAllTasksStatusWorkFlow();

}
