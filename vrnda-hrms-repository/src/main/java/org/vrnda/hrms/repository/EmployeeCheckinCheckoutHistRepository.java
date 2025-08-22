package org.vrnda.hrms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vrnda.hrms.entity.EmployeeCheckinCheckoutEntity;
import org.vrnda.hrms.entity.EmployeeCheckinCheckoutHistEntity;

@Repository
public interface EmployeeCheckinCheckoutHistRepository
		extends PagingAndSortingRepository<EmployeeCheckinCheckoutHistEntity, Long> {

	@Query("select e from EmployeeCheckinCheckoutHistEntity  as e where e.employeeId = :employeeId")
	public List<EmployeeCheckinCheckoutEntity> getEmployeeCheckinHistDetails(@Param("employeeId") Long employeeId);

}
