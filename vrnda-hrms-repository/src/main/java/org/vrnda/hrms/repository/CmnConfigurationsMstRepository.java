package org.vrnda.hrms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vrnda.hrms.entity.CmnConfigurationsMstEntity;

@Repository
public interface CmnConfigurationsMstRepository extends PagingAndSortingRepository<CmnConfigurationsMstEntity, String>{

	@Query("SELECT e FROM CmnConfigurationsMstEntity as e WHERE configurationId = :configurationId")
	public CmnConfigurationsMstEntity getConfigurationByConfigurationId(
			@Param("configurationId") long configurationId);

	@Query("SELECT e FROM CmnConfigurationsMstEntity as e WHERE configTypeLookupId = :configTypeLookupId and yearId =:yearId")
	public List<CmnConfigurationsMstEntity> getConfigurationsByConfigTypeLookupIdAndYearId(
			@Param("configTypeLookupId") long configTypeLookupId, 
			@Param("yearId") long yearId);

	@Query("SELECT e FROM CmnConfigurationsMstEntity as e WHERE configTypeLookupId = :configTypeLookupId "
			+ "and yearId =:yearId and statusLookupId = :statusLookupId")
	public List<CmnConfigurationsMstEntity> getConfigurationsByConfigurationTypeLookupIdAndYearIdAndStatusLookupId(
			@Param("configTypeLookupId") long configTypeLookupId, 
			@Param("yearId") long yearId,
			@Param("statusLookupId") long statusLookupId);

	@Query("SELECT e FROM CmnConfigurationsMstEntity as e WHERE configurationName = :configurationName and "
			+ "configTypeLookupId = :configTypeLookupId and yearId = :yearId")
	public CmnConfigurationsMstEntity getConfigurationByconfigurationNameAndConfigTypeLookupIdAndYearId(
			@Param("configurationName") String configurationName,
			@Param("configTypeLookupId") long configTypeLookupId,
			@Param("yearId") long yearId);

	@Query("SELECT e.configurationId FROM CmnConfigurationsMstEntity as e WHERE configurationName = :configurationName")
	public long getConfigurationIdByConfigurationName(
			@Param("configurationName") String configurationName);
	
	@Query("SELECT e FROM CmnConfigurationsMstEntity as e WHERE yearId = :yearId")
	public List<CmnConfigurationsMstEntity> getConfigurationsByYearId(
			@Param("yearId") long yearId);
	
	@Query("SELECT e FROM CmnConfigurationsMstEntity as e WHERE configTypeLookupId = :configTypeLookupId and yearId =:yearId "
			+ "and defaultConfig = 'Y'")
	public List<CmnConfigurationsMstEntity> getDefaultConfigurationsByConfigTypeLookupIdAndYearId(
			@Param("configTypeLookupId") long configTypeLookupId, 
			@Param("yearId") long yearId);


}
