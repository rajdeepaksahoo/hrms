package org.vrnda.hrms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vrnda.hrms.entity.CmnLeaveTypesMstEntity;

@Repository
public interface CmnLeaveTypesMstRepository extends PagingAndSortingRepository<CmnLeaveTypesMstEntity, String> {

	@Query("select e from CmnLeaveTypesMstEntity as e")
	public List<CmnLeaveTypesMstEntity> getAllLeaveTypes();

	@Query("select leaveTypeName, leaveTypeId, leaveTypeDescription from CmnLeaveTypesMstEntity as e")
	public List<Object[]> getLeaveTypeIdAndLeaveTypeNameAndLeaveTypeDescription();

	@Query("select leaveTypeId from CmnLeaveTypesMstEntity as e where leaveTypeName=:leaveTypeName")
	public Long getLeaveTypeIdByLeaveTypeName(
			@Param("leaveTypeName") String leaveTypeName);

	@Query("select e from CmnLeaveTypesMstEntity as e where leaveTypeName=:leaveTypeName")
	public CmnLeaveTypesMstEntity getLeaveTypeByLeaveTypeName(
			@Param("leaveTypeName") String leaveTypeName);

	@Query("select e from CmnLeaveTypesMstEntity as e where leaveTypeId=:leaveTypeId")
	public CmnLeaveTypesMstEntity getLeaveTypeByLeaveTypeId(
			@Param("leaveTypeId") Long leaveTypeId);

	@Query("select	e from CmnLeaveTypesMstEntity as e where statusLookupId = :statusLookupId")
	public List<CmnLeaveTypesMstEntity> getAllActiveLeaveTypes(
			@Param("statusLookupId") Long activeFlagId);
	@Query(value = "select * from CMN_LEAVE_TYPES_MST cltm JOIN CMN_YEARS_MST cym on cltm.YEAR_ID = cym.YEAR_ID where cltm.STATUS_LOOKUP_ID =:activeFlagId and cym.YEAR_NAME = :currentYear",nativeQuery = true)
	List<CmnLeaveTypesMstEntity> getLeaveType(String currentYear, Long activeFlagId);
}
