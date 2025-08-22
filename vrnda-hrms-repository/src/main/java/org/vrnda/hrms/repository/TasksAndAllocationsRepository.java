package org.vrnda.hrms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vrnda.hrms.entity.TasksAndAllocationsEntity;

@Repository
public interface TasksAndAllocationsRepository extends PagingAndSortingRepository<TasksAndAllocationsEntity, Long> {

	@Query("SELECT t, (select CONCAT(e.employeeLastName, ' ', e.employeeFirstName ) from EmployeeDetailsEntity e where t.tester = e.employeeId ) AS testerFullName, (select CONCAT(e.employeeLastName, ' ', e.employeeFirstName ) from EmployeeDetailsEntity e where t.developer = e.employeeId ) AS developerFullName, (select CONCAT(e.employeeLastName, ' ', e.employeeFirstName ) from EmployeeDetailsEntity e where t.assignee = e.employeeId ) AS employeeFullName,(select jiraURL from CmnProjectsMstEntity p where p.projectId = t.projectId) as jiraURL,(select jiraName from CmnProjectsMstEntity p where p.projectId = t.projectId) as jiraURL  from TasksAndAllocationsEntity as t where t.projectId in (:projectIdList) ")
	public List<Object[]> getAllTasksAndAllocationsBaseOnEmployeeId(@Param("projectIdList") List<Long> projectIdList);

	@Query("SELECT t  from TasksAndAllocationsEntity as t  ")
	public List<TasksAndAllocationsEntity> getAllTasksAndAllocations();

	@Query("select e from TasksAndAllocationsEntity as e where projectId in (:projectId)")
	public List<TasksAndAllocationsEntity> getAllTaskDetailsByProjectId(@Param("projectId") List<Long> projectId);

	@Query("select e from TasksAndAllocationsEntity as e where projectId = :projectId")
	public List<TasksAndAllocationsEntity> getAllTaskDetailsByProjectId(@Param("projectId") Long projectId);

	@Query("select e,(select jiraURL from CmnProjectsMstEntity p where p.projectId = e.projectId) as jiraURL  from TasksAndAllocationsEntity as e where taskId = :taskId")
	public List<Object[]> getTasksAndAllocationsByTaskId(@Param("taskId") String taskId);

	@Query("select e from TasksAndAllocationsEntity as e where projectId = :projectId and (assignee = :employeeId or scrumMaster = :employeeId or developer = :employeeId or tester = :employeeId ) and currentStatus not in (:currentStatusIds) ")
	public List<TasksAndAllocationsEntity> getTasksAllocationDetailsByEmp(@Param("projectId") Long projectId,
			@Param("employeeId") Long employeeId, @Param("currentStatusIds") List<Long> currentStatusIds);

	@Query("SELECT t, (select CONCAT(e.employeeLastName, ' ', e.employeeFirstName ) from EmployeeDetailsEntity e where t.tester = e.employeeId ) AS testerFullName, (select CONCAT(e.employeeLastName, ' ', e.employeeFirstName ) from EmployeeDetailsEntity e where t.developer = e.employeeId ) AS developerFullName, (select CONCAT(e.employeeLastName, ' ', e.employeeFirstName ) from EmployeeDetailsEntity e where t.assignee = e.employeeId ) AS employeeFullName,(select jiraURL from CmnProjectsMstEntity p where p.projectId = t.projectId) as jiraURL,(select jiraName from CmnProjectsMstEntity p where p.projectId = t.projectId) as jiraName  from TasksAndAllocationsEntity as t where t.assignee = :assignee")
	public List<Object[]> getAllTaskDetailsBasedOnAssigne(@Param("assignee") Long assignee);

	@Query("SELECT t.taskId from TasksAndAllocationsEntity as t where (t.assignee = :assignee or t.developer = :assignee or t.tester = :assignee)")
	public List<String> getAllActiveTaskDetailsBasedOnAssigne(@Param("assignee") Long assignee);
}
