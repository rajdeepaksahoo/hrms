package org.vrnda.hrms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vrnda.hrms.entity.CmnSalaryEarningsConfigDetailsEntity;

@Repository
public interface CmnSalaryEarningsConfigDetailsRepository
		extends PagingAndSortingRepository<CmnSalaryEarningsConfigDetailsEntity, Long> {

	@Query("SELECT e FROM CmnSalaryEarningsConfigDetailsEntity as e WHERE configurationId = :configurationId")
	public List<CmnSalaryEarningsConfigDetailsEntity> getSalaryEarningsConfigDetailsByConfigId(
			@Param("configurationId") Long configurationId);

	@Query("SELECT e FROM CmnSalaryEarningsConfigDetailsEntity as e WHERE cmnSalaryEarningsConfigDetailsId = :cmnSalaryEarningsConfigDetailsId")
	public CmnSalaryEarningsConfigDetailsEntity getSalaryEarningsConfigDetailsByCmnSalaryEarningsConfigDetailsId(
			@Param("cmnSalaryEarningsConfigDetailsId") Long cmnSalaryEarningsConfigDetailsId);
}
