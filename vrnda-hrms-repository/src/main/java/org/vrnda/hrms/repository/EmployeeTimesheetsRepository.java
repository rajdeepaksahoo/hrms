package org.vrnda.hrms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vrnda.hrms.entity.EmployeeTimesheetsEntity;

@Repository
public interface EmployeeTimesheetsRepository extends PagingAndSortingRepository<EmployeeTimesheetsEntity, Long> {

	@Query("select t from EmployeeTimesheetsEntity as t where t.empCheckinId in (select e.empCheckinId from EmployeeCheckinCheckoutEntity e where e.employeeId = :employeeId )")
	List<EmployeeTimesheetsEntity> getAllEmployeeTimeSheetData(@Param("employeeId") Long employeeId);

	@Query("select t from EmployeeTimesheetsEntity as t where t.empCheckinId in (:empCheckinIds)")
	List<EmployeeTimesheetsEntity> getAllEmployeeTimeSheetDataBasedOnCheckIns(
			@Param("empCheckinIds") List<Long> empCheckinIds);

}
