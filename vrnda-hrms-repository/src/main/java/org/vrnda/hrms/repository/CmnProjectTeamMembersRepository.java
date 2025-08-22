package org.vrnda.hrms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vrnda.hrms.entity.CmnProjectTeamMembersEntity;
import org.vrnda.hrms.entity.CmnProjectTeamMembersEntityPk;

@Repository
public interface CmnProjectTeamMembersRepository
		extends PagingAndSortingRepository<CmnProjectTeamMembersEntity, CmnProjectTeamMembersEntityPk> {

	@Query("Select c from CmnProjectTeamMembersEntity as c ")
	public List<CmnProjectTeamMembersEntity> getAllCmnProjectTeamMembers();

	@Query("select c from  CmnProjectTeamMembersEntity as c where teamId =:teamId and projectId =:projectId and employeeId =:employeeId")
	public CmnProjectTeamMembersEntity getCmnProjectTeamMembersByProjIdModIdEmpId(@Param("projectId") Long projectId,
			@Param("teamId") Long teamId, @Param("employeeId") Long employeeId);

	@Query("Select c from CmnProjectTeamMembersEntity as c where statusLookUpId =:statusLookUpId")
	public List<CmnProjectTeamMembersEntity> getAllCmnProjectTeamMembersByStatusLookUpId(
			@Param("statusLookUpId") Long statusLookUpId);

	@Query("Select p,(select CONCAT(ed.employeeLastName,' ',ed.employeeFirstName)from EmployeeDetailsEntity ed where ed.employeeId  = p.employeeId) as employeeFullName from CmnProjectTeamMembersEntity as p where p.projectId =:projectId")
	public List<Object[]> getAllCmnProjectTeamMembersByProjectId(@Param("projectId") Long projectId);

	@Query("Select p,(select teamName from CmnProjectTeamsEntity cmn1 where cmn1.teamId= p.teamId)as teamName from CmnProjectTeamMembersEntity as p where p.teamId =:teamId")
	public List<Object[]> getAllCmnProjectTeamMembersByTeamId(@Param("teamId") Long teamId);

	@Query("Select c from CmnProjectTeamMembersEntity as c where teamId in ( :teamId)")
	public List<CmnProjectTeamMembersEntity> getAllCmnProjectTeamMembersByTeamIds(@Param("teamId") List<Long> teamId);

	@Query("Select p,(select CONCAT(ed.employeeLastName,' ',ed.employeeFirstName)from EmployeeDetailsEntity ed where ed.employeeId  = p.employeeId) as employeeFullName from CmnProjectTeamMembersEntity as p where p.projectId =:projectId and p.scrumMaster ='Y'")
	public List<Object[]> getAllProjectTeamMembersByProjectIdAndScrumMaster(@Param("projectId") Long projectId);

	@Query("select p,(select CONCAT(ed.employeeLastName,' ',ed.employeeFirstName)   from EmployeeDetailsEntity ed "
			+ "where ed.employeeId  = p.employeeId) as employeeFullName,(select projectName from CmnProjectsMstEntity cmn "
			+ "where cmn.projectId = p.projectId) as projectName,(select teamName from CmnProjectTeamsEntity cmn1 "
			+ "where cmn1.teamId= p.teamId)as teamName,(select e.companyRoleName from CmnCompanyRolesMstEntity as e WHERE e.companyRoleId=p.roleId) as roleName from CmnProjectTeamMembersEntity as p where projectId =:projectId and teamId =:teamId")
	public List<Object[]> getAllCmnProjectTeamMembersByTeamId(@Param("projectId") Long projectId,
			@Param("teamId") Long teamId);

	@Query("select p,(select CONCAT(ed.employeeLastName,' ',ed.employeeFirstName)  from EmployeeDetailsEntity ed "
			+ "where ed.employeeId  = p.employeeId) as employeeFullName,(select projectName from CmnProjectsMstEntity cmn "
			+ "where cmn.projectId = p.projectId) as projectName,(select teamName from CmnProjectTeamsEntity cmn1 "
			+ "where cmn1.teamId= p.teamId)as teamName from CmnProjectTeamMembersEntity as p ")
	public List<Object[]> getAllDetails();

	@Query("SELECT p  FROM  CmnProjectTeamMembersEntity as p WHERE  employeeId = :employeeId")
	public List<CmnProjectTeamMembersEntity> getProjectTeamMembersByEmployeeId(@Param("employeeId") Long employeeId);

}
