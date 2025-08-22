//package org.vrnda.hrms.repository;
//
//import java.util.List;
//
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.PagingAndSortingRepository;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//import org.vrnda.hrms.entity.LeaveConfigurationDetailsEntity;
//
//@Repository
//public interface LeaveConfigurationDetailsRepository extends PagingAndSortingRepository<LeaveConfigurationDetailsEntity, Long>{
//	
//	@Query("SELECT e FROM LeaveConfigurationDetailsEntity as e WHERE configurationId = :lvConfLookupId" )
//	public List<LeaveConfigurationDetailsEntity> getDataForConfMst(@Param("lvConfLookupId") long lvConfLookupId);
//	
//	@Query("SELECT e FROM LeaveConfigurationDetailsEntity as e WHERE leaveConfigDetlsId = :leaveConfigDetlsId" )
//	public LeaveConfigurationDetailsEntity getDataById(@Param("leaveConfigDetlsId") long leaveConfigDetlsId);
//	
//	@Modifying
//	@Query("DELETE FROM LeaveConfigurationDetailsEntity WHERE lvConfLookupId = :lvConfLookupId" )
//	public void deletebyConfMstId(@Param("lvConfLookupId") long lvConfLookupId);
//	
//	@Query("SELECT e FROM LeaveConfigurationDetailsEntity as e WHERE configurationId = :configurationId and monthId= :monthId and lvTypeLookupId = :lvTypeLookupId" )
//	public LeaveConfigurationDetailsEntity getLeaveConfigurationDetailsByConfidIdAndMonthIdAndLeaveTypeId( @Param("configurationId")long configurationId, @Param("monthId") long monthId, @Param("lvTypeLookupId")long lvTypeLookupId);
//
//	@Query("SELECT e FROM LeaveConfigurationDetailsEntity as e WHERE lvTypeLookupId = :lvTypeLookupId" )
//	public List<LeaveConfigurationDetailsEntity> getDataLeaveType(@Param("lvTypeLookupId") long lvTypeLookupId);
//	
//	@Query("SELECT e FROM LeaveConfigurationDetailsEntity as e WHERE lvConfLookupId = :lvConfLookupId and monthId= :monthId and statusLookupId = :statusLookupId" )
//	public List<LeaveConfigurationDetailsEntity> getLeaveConfigurationDetailsByConfidIdAndMonthIdAndStatusLookupId( @Param("lvConfLookupId")long lvConfLookupId, @Param("monthId") long monthId, @Param("statusLookupId")long statusLookupId);
//
//
//}
