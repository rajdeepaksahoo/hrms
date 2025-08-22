package org.vrnda.hrms.repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vrnda.hrms.entity.EmployeeLeaveDetailsEntity;

@Repository
public interface EmployeeLeaveDetailsRepository extends PagingAndSortingRepository<EmployeeLeaveDetailsEntity, Long> {

	@Query("SELECT e,(select totalLeaveBalance from EmployeeLeavesMstEntity as mst where mst.leaveTypeId = e.leaveTypeId AND mst.employeeId =e.employeeId) as availableLeaves FROM EmployeeLeaveDetailsEntity as e WHERE employeeId = :employeeId and statusLookupId = :statusLookupId and leaveStatusLookupId in(:req,:updReq,:cancReq,:approved) ")
	public List<Object[]> getAllLeaveRequestsById(@Param("employeeId") Long employeeId,
			@Param("statusLookupId") Long statusLookupId,@Param("req") Long req,@Param("updReq") Long updReq,@Param("cancReq") Long cancReq,@Param("approved") Long approved);

	@Query("SELECT e,(select totalLeaveBalance from EmployeeLeavesMstEntity as mst where mst.leaveTypeId = e.leaveTypeId AND mst.employeeId =e.employeeId) as availableLeaves  FROM EmployeeLeaveDetailsEntity as e WHERE statusLookupId = :statusLookupId and approverId=:approverId and leaveStatusLookupId in(:req,:updReq,:cancReq) ")
	public List<Object[]> getAllActiveRequests(@Param("statusLookupId") Long statusLookupId,
			@Param("approverId") Long approverId ,@Param("req") Long req,@Param("updReq") Long updReq,@Param("cancReq") Long cancReq);
	
	@Query("SELECT e FROM EmployeeLeaveDetailsEntity as e WHERE employeeId = :employeeId and statusLookupId = :statusLookupId and leaveStatusLookupId in(:req,:updReq,:cancReq,:approved) and ((leaveFromDate <= :fromDate and leaveToDate >= :fromDate) or (leaveFromDate <= :toDate and leaveToDate >= :toDate)) or ((:fromDate<leaveFromDate and :toDate>leaveFromDate) and (:fromDate<leaveToDate and :toDate>leaveToDate))")
	public List<EmployeeLeaveDetailsEntity> verificationAppliedRequest(@Param("employeeId") Long employeeId,
			@Param("statusLookupId") Long statusLookupId,@Param("req") Long req,@Param("updReq") Long updReq,@Param("cancReq") Long cancReq,@Param("approved") Long approved,@Param("fromDate") Date fromDate,@Param("toDate") Date toDate);

	@Query("SELECT e FROM EmployeeLeaveDetailsEntity as e WHERE employeeLeaveId = :employeeLeaveId")
	public EmployeeLeaveDetailsEntity getById(@Param("employeeLeaveId") Long employeeLeaveId);

	@Query("select e from EmployeeLeaveDetailsEntity as e where leaveFromDate = :leaveFromDate and leaveToDate = :leaveToDate and employeeId =:employeeId")
	public List<EmployeeLeaveDetailsEntity> getLeavesData(@Param("leaveFromDate") Date leaveFromDate,
			@Param("leaveToDate") Date leaveToDate, @Param("employeeId") Long employeeId);
	
	@Query(value = "select * from EMPLOYEE_LEAVE_DETAILS where UPDATED_DATE > now() - interval 24 hour and STATUS_LOOKUP_ID = :statusLookupId", nativeQuery = true)
	public List<EmployeeLeaveDetailsEntity> getLeavesForMail(@Param("statusLookupId") Long statusLookupId);
	
	@Query("SELECT e FROM EmployeeLeaveDetailsEntity as e WHERE leaveTypeId = :leaveTypeId")
	public List<EmployeeLeaveDetailsEntity> getEmployeeLeaveDetailsByleaveTypeId(@Param("leaveTypeId") Long leaveTypeId);
	
	@Query("select ( select CONCAT(ed.employeeLastName, ' ', ed.employeeFirstName) FROM EmployeeDetailsEntity ed where ed.employeeId = eld.employeeId) as fullName , eld.leaveFromDate , eld.leaveToDate from EmployeeLeaveDetailsEntity eld where eld.employeeId in ( select DISTINCT cptm1.employeeId from CmnProjectTeamMembersEntity cptm1 join CmnProjectTeamMembersEntity cptm2 on ((cptm1.projectId = cptm2.projectId and cptm1.teamId = cptm2.teamId) or (cptm1.projectId in ( select projectId from CmnProjectsMstEntity where projectHead =:employeeId))) where cptm2.employeeId =:employeeId) and eld.leaveFromDate <= CURRENT_DATE and eld.leaveToDate >= CURRENT_DATE")
	public List<Object[]> getDashBoadOnLeaveDetails(@Param("employeeId") Long employeeId);
	
	@Query(	"select eld.leaveFromDate, eld.totalLeaveDays, clm.lookupDesc FROM EmployeeLeaveDetailsEntity eld INNER JOIN CmnLookupMstEntity clm ON eld.leaveStatusLookupId = clm.lookupId WHERE eld.employeeId = :employeeId AND eld.leaveFromDate >= CURRENT_DATE")
	public List<Object[]> getDashBoadLeaveRequestsDetails(@Param("employeeId") Long employeeId);
}
