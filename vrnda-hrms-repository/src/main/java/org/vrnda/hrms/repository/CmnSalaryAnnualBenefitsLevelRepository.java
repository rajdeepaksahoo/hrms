package org.vrnda.hrms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vrnda.hrms.entity.CmnSalaryAnnualBenefitsConfigDetailsEntity;


@Repository
public interface CmnSalaryAnnualBenefitsLevelRepository extends PagingAndSortingRepository<CmnSalaryAnnualBenefitsConfigDetailsEntity, Long> {
	
	
	@Query("select e from CmnSalaryAnnualBenefitsConfigDetailsEntity as e where configurationId = :configurationId ")
	public List<CmnSalaryAnnualBenefitsConfigDetailsEntity> getSalaryAnnualTypeconfigDetails(
			@Param("configurationId") Long configurationId);
	
	
	@Query("SELECT e FROM CmnSalaryAnnualBenefitsConfigDetailsEntity as e WHERE cmnSalaryAnnBenConfigDtlsId = :cmnSalaryAnnBenConfigDtlsId")
	public CmnSalaryAnnualBenefitsConfigDetailsEntity getCmnSalaryAnnualRetriementBenfitConfigDetlsId(
			@Param("cmnSalaryAnnBenConfigDtlsId") Long cmnSalaryAnnBenConfigDtlsId );
}
