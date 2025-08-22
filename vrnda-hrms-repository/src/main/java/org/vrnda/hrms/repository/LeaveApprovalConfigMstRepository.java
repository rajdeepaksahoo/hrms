//package org.vrnda.hrms.repository;
//
//import java.util.List;
//
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.PagingAndSortingRepository;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//import org.vrnda.hrms.entity.LeaveApprovalConfigMstEntity;
//
//@Repository
//public interface LeaveApprovalConfigMstRepository extends PagingAndSortingRepository<LeaveApprovalConfigMstEntity, Long>{
//
//	@Query("SELECT e FROM  LeaveApprovalConfigMstEntity as e WHERE lvApprovalConfigId = :lvApprovalConfigId" )
//	public LeaveApprovalConfigMstEntity getLeaveApprovalConfigMstByLvApprovalConfigId(@Param("lvApprovalConfigId") Long lvApprovalConfigId);
//	
//	@Query("SELECT e FROM  LeaveApprovalConfigMstEntity as e WHERE configurationId = :configurationId" )
//	public List<LeaveApprovalConfigMstEntity> getLeaveApprovalConfigMstByConfigutrationId(@Param("configurationId") Long configurationId);
//
//
//	@Query("SELECT e FROM  LeaveApprovalConfigMstEntity as e WHERE lvApprovalConfigId = :lvApprovalConfigId" )
//	public List<LeaveApprovalConfigMstEntity> getLeaveApprovalConfigMstByLvAppConfigId(@Param("lvApprovalConfigId") Long lvApprovalConfigId);
//
//	@Query(" select  e from  LeaveApprovalConfigMstEntity as e where  configurationId=:configurationId AND lvTypeLookupId=:lvTypeLookupId order by level" )
//	public List<LeaveApprovalConfigMstEntity> getLeaveApprovalConfigMstByConfigutrationIdAndLvTypeLookupId(
//			@Param("configurationId") Long configurationId,
//			@Param("lvTypeLookupId") Long lvTypeLookupId);
//
//	@Query(" select e from  LeaveApprovalConfigMstEntity as e where configurationId=:configurationId")
//	public List<LeaveApprovalConfigMstEntity> getRecords(@Param("configurationId") Long configurationId );
//	
//	
//	@Query("select e from  LeaveApprovalConfigMstEntity as e where configurationId=:configurationId and lvTypeLookupId=:lvTypeLookupId")
//	public List<LeaveApprovalConfigMstEntity> getRecordsByIdLevelLvType(@Param("configurationId") Long configurationId,@Param("lvTypeLookupId") Long lvTypeLookupId);
//
//
//}
