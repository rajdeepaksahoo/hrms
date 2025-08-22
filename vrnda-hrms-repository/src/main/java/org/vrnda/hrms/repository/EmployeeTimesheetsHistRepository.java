package org.vrnda.hrms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vrnda.hrms.entity.EmployeeTimesheetsHistEntity;

@Repository
public interface EmployeeTimesheetsHistRepository
		extends PagingAndSortingRepository<EmployeeTimesheetsHistEntity, Long> {

	@Query("select t from EmployeeTimesheetsHistEntity as t where t.empCheckinId in (select e.empCheckinId from EmployeeCheckinCheckoutHistEntity e where e.employeeId = :employeeId )")
	List<EmployeeTimesheetsHistEntity> getAllEmployeeTimeSheetHistData(@Param("employeeId") Long employeeId);

}
