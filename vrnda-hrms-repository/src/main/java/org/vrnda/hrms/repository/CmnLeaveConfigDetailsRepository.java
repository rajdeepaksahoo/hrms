package org.vrnda.hrms.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vrnda.hrms.entity.CmnLeaveConfigDetailsEntity;

import java.util.List;

@Repository
public interface CmnLeaveConfigDetailsRepository extends PagingAndSortingRepository<CmnLeaveConfigDetailsEntity, Long> {
	
	@Query("SELECT e FROM CmnLeaveConfigDetailsEntity as e WHERE configurationId = :configurationId")
	public CmnLeaveConfigDetailsEntity getLeaveConfigDetailsByConfigurationId(
			@Param("configurationId") long configurationId);

	@Query("SELECT e FROM CmnLeaveConfigDetailsEntity as e WHERE configurationId = :configurationId")
	public List<CmnLeaveConfigDetailsEntity> getLeaveConfigDetailsListByConfigurationId(
			@Param("configurationId") long configurationId);
	
}
