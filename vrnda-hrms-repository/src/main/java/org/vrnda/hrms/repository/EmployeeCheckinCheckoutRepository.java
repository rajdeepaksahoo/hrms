package org.vrnda.hrms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vrnda.hrms.entity.EmployeeCheckinCheckoutEntity;

@Repository
public interface EmployeeCheckinCheckoutRepository
		extends PagingAndSortingRepository<EmployeeCheckinCheckoutEntity, Long> {

	@Query("select e from EmployeeCheckinCheckoutEntity  as e where e.employeeId = :employeeId and DATE(e.checkinTime) = DATE(CURRENT_DATE()) order by empCheckinId desc")
	public List<EmployeeCheckinCheckoutEntity> getEmployeeCheckinDetails(@Param("employeeId") Long employeeId);

	@Query(value = "select * from EmployeeCheckinCheckoutEntity where employeeId = :employeeId and  checkoutTime is NULL", nativeQuery = true)
	public List<EmployeeCheckinCheckoutEntity> verifyEmpliyeeCheckout(@Param("employeeId") Long employeeId);

	@Query("SELECT e FROM EmployeeCheckinCheckoutEntity as e WHERE empCheckinId = :empCheckinId ")
	public EmployeeCheckinCheckoutEntity getEmployeeCheckinDetailsByCheckinId(@Param("empCheckinId") Long empCheckinId);

}
