package org.vrnda.hrms.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vrnda.hrms.entity.EmployeeCompOffLeaveMstEntity;

@Repository
public interface EmployeeCompOffLeaveMstRepository extends PagingAndSortingRepository<EmployeeCompOffLeaveMstEntity, Long>{

	@Query("select e from EmployeeCompOffLeaveMstEntity as e where employeeId = :employeeId ")
	public List<EmployeeCompOffLeaveMstEntity> getCompOffLeavesByEmployeeId(@Param("employeeId") Long employeeId );
	
	@Query("select e from EmployeeCompOffLeaveMstEntity as e where employeeId = :employeeId and ((fromDate <= :fromDate and toDate >= :fromDate) or (fromDate <= :toDate and toDate >= :toDate)) or ((:fromDate<fromDate and :toDate>fromDate) and (:fromDate<toDate and :toDate>toDate))")
	public EmployeeCompOffLeaveMstEntity getCompOffLeavesByEmployeeIdFromandToDate(@Param("employeeId") Long employeeId ,@Param("fromDate") Timestamp fromDate ,@Param("toDate") Timestamp toDate );
	
	@Query(value = "select LOOKUP_ID  from CMN_LOOKUP_MST  where LOOKUP_NAME = 'COMP OFF'", nativeQuery = true)
	public Long getLookupId();
	
	
	@Query("select e from EmployeeCompOffLeaveMstEntity as e ")
	public List<EmployeeCompOffLeaveMstEntity> getAllLeaveApproval( );
	
	@Query("select e from EmployeeCompOffLeaveMstEntity as e where leaveExpiryDate < CURDATE()")
	public List<EmployeeCompOffLeaveMstEntity> getCompoffLeaveHistory();
	
	@Query("select e from EmployeeCompOffLeaveMstEntity as e ")
	public List<EmployeeCompOffLeaveMstEntity> getCompoffLeave();

	
}
