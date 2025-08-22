package org.vrnda.hrms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vrnda.hrms.entity.CmnAppRolesMstEntity;
import org.vrnda.hrms.entity.CmnCompanyRolesMstEntity;
import org.vrnda.hrms.entity.CmnConfigurationsMstEntity;

@Repository
public interface CmnLoanConfigurationsRepository extends PagingAndSortingRepository<CmnConfigurationsMstEntity, Long> {

	@Query("SELECT e FROM CmnConfigurationsMstEntity as e WHERE configurationId = :configurationId")
	public CmnConfigurationsMstEntity retriveLoanConfigByConfigId(@Param("configurationId") Long configurationId);

	@Query("SELECT cmn FROM CmnConfigurationsMstEntity as cmn WHERE   configurationName = :configurationName")
	public CmnConfigurationsMstEntity getConfigurationInfo(
			@Param("configurationName") String configurationName);
	
	@Query("SELECT e FROM CmnConfigurationsMstEntity as e WHERE configurationId = :configurationId")
	public CmnConfigurationsMstEntity getConfigurationByConfigId(@Param("configurationId") Long configurationId);

}
