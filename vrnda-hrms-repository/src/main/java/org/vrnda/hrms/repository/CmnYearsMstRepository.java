package org.vrnda.hrms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vrnda.hrms.entity.CmnYearsMstEntity;
import org.vrnda.hrms.repository.dto.CmnYearsMstDTO;

@Repository
public interface CmnYearsMstRepository extends PagingAndSortingRepository<CmnYearsMstEntity, String>{

	@Query(" select new org.vrnda.hrms.repository.dto.CmnYearsMstDTO("
			+ "yearId, yearName, yearStartDate, yearEndDate, applicableFrom, yearTypeLookupId, "
			+ "statusLookupId, systemConfigFlag, createdDate) "
			+ "from CmnYearsMstEntity as e where yearTypeLookupId = :yearTypeLookupId order by yearName") 
	public List<CmnYearsMstDTO> getYearsByYearTypeLookupId(@Param("yearTypeLookupId") Long yearTypeLookupId);
	
	@Query(" select e from CmnYearsMstEntity as e where yearId = :yearId")
	public CmnYearsMstEntity getCmnYearsMstByYearId(@Param("yearId") Long yearId);
	
	@Query("select e from CmnYearsMstEntity as e where yearName = :yearName")
	public CmnYearsMstEntity getYearByYearName(@Param("yearName") String yearName);
	
	@Query("select e from CmnYearsMstEntity as e where yearName = (select max(yearName) from CmnYearsMstEntity)")
	public CmnYearsMstEntity getYearForMaxYear();
	
	@Query("select e from CmnYearsMstEntity as e where yearName = :yearName and yearTypeLookupId = :yearTypeLookupId")
	public CmnYearsMstEntity getYearsByYearNameAndYearType(
			@Param("yearName") String yearName,
			@Param("yearTypeLookupId") Long yearTypeLookupId);
	
	@Query("select e from CmnYearsMstEntity as e where yearName = :yearName and yearTypeLookupId = (select lookupId from CmnLookupMstEntity where lookupName = :yearTypeLookupName)")
	public CmnYearsMstEntity getYearByYearNameAndYearTypeLookupId(@Param("yearName") String yearName,@Param("yearTypeLookupName") String yearTypeLookupName);
	
}
