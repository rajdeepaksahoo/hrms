package org.vrnda.hrms.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vrnda.hrms.entity.CmnHolidaysMstEntity;
import org.vrnda.hrms.repository.dto.CmnHolidaysMstDTO;

@Repository
public interface CmnHolidaysMstRepository extends PagingAndSortingRepository<CmnHolidaysMstEntity, Long>{
	
	
	@Query("SELECT e FROM CmnHolidaysMstEntity as e WHERE holidayId = :holidayId")
	public CmnHolidaysMstEntity getHolidaybyHolidayId(@Param("holidayId") Long holidayId);
	
	@Query("SELECT e FROM CmnHolidaysMstEntity as e WHERE holidayName = :holidayName and yearId = :yearId and holidayTypeLookupId = :holidayTypeLookupId")
	public CmnHolidaysMstEntity getHolidaysByHolidayIdAndNameAndYearIdAndHolidayType(
			@Param ("holidayName") String holidayName,
			@Param ("yearId") Long yearId,
			@Param ("holidayTypeLookupId") Long holidayTypeLookupId);
	
	@Query("SELECT e FROM CmnHolidaysMstEntity as e WHERE holidayDate = :holidayDate and yearId = :yearId and holidayTypeLookupId = :holidayTypeLookupId")
	public CmnHolidaysMstEntity getHolidaysByHolidayIdAndDateAndYearIdAndHolidayType(
			@Param ("holidayDate") Date holidayDate,
			@Param ("yearId") Long yearId,
			@Param ("holidayTypeLookupId") Long holidayTypeLookupId);
	
	@Query("SELECT new org.vrnda.hrms.repository.dto.CmnHolidaysMstDTO("
			+ "holidayId, yearId, holidayDate, holidayName, description, statusLookupId,systemConfigFlag, holidayTypeLookupId, createdDate) "
			+ "FROM CmnHolidaysMstEntity as e WHERE yearId = :yearId order by holidayDate")
	public List<CmnHolidaysMstDTO> getHolidaysByYearId(
			@Param("yearId") Long yearId);
	
	@Query("SELECT new org.vrnda.hrms.repository.dto.CmnHolidaysMstDTO("
			+ "holidayId, yearId, holidayDate, holidayName, description, statusLookupId,systemConfigFlag, holidayTypeLookupId, createdDate) "
			+ "FROM CmnHolidaysMstEntity as e WHERE yearId = :yearId and holidayTypeLookupId = :holidayTypeLookupId and statusLookupId = :statusLookupId")
	public List<CmnHolidaysMstDTO> getHolidaysByYearIdAndHolidayTypeLookupId(
			@Param ("yearId") Long yearId,
			@Param ("holidayTypeLookupId") Long holidayTypeLookupId,
			@Param ("statusLookupId") Long statusLookupId);
	
	@Query("SELECT new org.vrnda.hrms.repository.dto.CmnHolidaysMstDTO("
			+ "holidayId, yearId, holidayDate, holidayName, description, statusLookupId, systemConfigFlag, holidayTypeLookupId, createdDate) "
			+ "FROM CmnHolidaysMstEntity as e WHERE yearId = :yearId and statusLookupId = :statusLookupId order by holidayDate")
	public List<CmnHolidaysMstDTO> getHolidaysByYearIdAndStatusLookupId(
			@Param("yearId") Long yearId,
			@Param("statusLookupId") Long statusLookupId);
	
}
