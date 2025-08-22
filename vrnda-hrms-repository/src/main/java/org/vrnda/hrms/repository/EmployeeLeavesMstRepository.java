package org.vrnda.hrms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vrnda.hrms.entity.EmployeeLeavesMstEntity;

@Repository
public interface EmployeeLeavesMstRepository extends PagingAndSortingRepository<EmployeeLeavesMstEntity, Long>{
	
	@Query("SELECT e FROM EmployeeLeavesMstEntity as e WHERE employeeId = :employeeId" )
	public List<EmployeeLeavesMstEntity> getAllLeaveBalanceById(@Param("employeeId") Long employeeId);
	
	@Query("SELECT e FROM EmployeeLeavesMstEntity as e WHERE employeeId = :employeeId and leaveTypeId = :leaveTypeId" )
	public EmployeeLeavesMstEntity getLeaveBalanceByIdandLvtype(@Param("employeeId") Long employeeId,@Param("leaveTypeId") Long leaveTypeId);
	
	@Query("SELECT e FROM EmployeeLeavesMstEntity as e WHERE employeeId = :employeeId and leaveTypeId = :leaveTypeId and statusLookupId = :statusLookupId" )
	public EmployeeLeavesMstEntity getLeaveBalById(@Param("employeeId") Long employeeId,@Param("leaveTypeId") Long leaveTypeId, @Param("statusLookupId") Long statusLookupId);
	
	@Modifying
	@Query("UPDATE EmployeeLeavesMstEntity SET totalLeaveBalance = :remainingLeaves  WHERE employeeId = :employeeId and lvTypeLookupId = :lvTypeLookupId" )
	public void updateNoofLeaves(@Param("employeeId") Long employeeId,@Param("lvTypeLookupId") Long lvTypeLookupId, @Param("remainingLeaves") int remainingLeaves);

	@Query("SELECT e FROM EmployeeLeavesMstEntity as e WHERE yearId = :yearId" )
	public List<EmployeeLeavesMstEntity> getEmployeeLeavesByYearId(@Param("yearId") Long yearId);
	
	@Query("SELECT e FROM EmployeeLeavesMstEntity as e WHERE leaveTypeId = :leaveTypeId" )
	public List<EmployeeLeavesMstEntity> getLeaveBalanceByLeaveTypeId(@Param("leaveTypeId") Long leaveTypeId);

	@Query("SELECT e FROM EmployeeLeavesMstEntity as e WHERE employeeId = :employeeId and leaveTypeId = :leaveTypeId and yearId = :yearId" )
	public EmployeeLeavesMstEntity getEmployeeLeaveMstByEmployeeIdAndLeaveTypeIdAndYearId(
			@Param("employeeId") Long employeeId,
			@Param("leaveTypeId") Long leaveTypeId, 
			@Param("yearId") Long yearId);
	
	@Query("select	e FROM EmployeeLeavesMstEntity as e WHERE	employeeId = :empId	and e.yearId = (SELECT yearId FROM	CmnYearsMstEntity where	"
		+" yearName = :yearName) and statusLookupId = :statusLookupId")
	public List<EmployeeLeavesMstEntity> getAllEmpLeaveBalance(@Param("yearName") String currentYear, @Param("statusLookupId") Long activeFlagId,@Param("empId") Long employeeId);
	
}
