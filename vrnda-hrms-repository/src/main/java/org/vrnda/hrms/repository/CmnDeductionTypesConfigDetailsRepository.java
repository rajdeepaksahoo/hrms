package org.vrnda.hrms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vrnda.hrms.entity.CmnDeductionTypeConfigDetailsEntity;

@Repository
public interface CmnDeductionTypesConfigDetailsRepository extends PagingAndSortingRepository<CmnDeductionTypeConfigDetailsEntity, Long>{
	
	@Query("SELECT e FROM CmnDeductionTypeConfigDetailsEntity as e WHERE configurationId = :configurationId")
	List<CmnDeductionTypeConfigDetailsEntity> getDeductionTypesConfigDetailsByConfigurationId(
			@Param("configurationId") long configurationId);

	@Query("SELECT e FROM CmnDeductionTypeConfigDetailsEntity as e WHERE cmnDtConfigDetlsId = :cmnDtConfigDetlsId")
	public CmnDeductionTypeConfigDetailsEntity getDeductionTypesConfigDetailsBycmnDtConfigDetlsId(
			@Param("cmnDtConfigDetlsId") long cmnDtConfigDetlsId);
	
}
