package org.vrnda.hrms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vrnda.hrms.entity.CmnLeavePlanConfigDetailsEntity;

@Repository
public interface CmnLeavePlanConfigDetailsRepository extends PagingAndSortingRepository<CmnLeavePlanConfigDetailsEntity, String>{
	
	@Query(value ="SELECT e FROM CmnLeavePlanConfigDetailsEntity as e  WHERE configurationId  =:configurationId and leaveTypeId = :leaveTypeId")
	public List<CmnLeavePlanConfigDetailsEntity> getLeavePlanConfigDetailsByConfigurationIdAndLvTypeLookUpId(@Param("configurationId") Long configurationId,@Param("leaveTypeId") Long leaveTypeId);

	@Query(value ="SELECT e FROM CmnLeavePlanConfigDetailsEntity as e  WHERE configurationId  =:configId and leaveTypeId = :leaveTypeId")
	public CmnLeavePlanConfigDetailsEntity getLeavePlanConfigByConfigutrationIdAndLvTypeLookupIdEntity(@Param("configId") Long configurationId,@Param("leaveTypeId") Long leaveTypeId);
	
	@Query(value ="SELECT e FROM CmnLeavePlanConfigDetailsEntity as e  WHERE cmnLvPlnConfigDetlsId  =:lvplnconfigId")
	public CmnLeavePlanConfigDetailsEntity getLeavePlanConfigBycmnlvdetId(@Param("lvplnconfigId") Long cmnLvPlnConfigDetlsId);
	
	@Query(value ="SELECT e FROM CmnLeavePlanConfigDetailsEntity as e  WHERE configurationId  =:configurationId")
	public List<CmnLeavePlanConfigDetailsEntity> getLeavePlanConfigByConfigurationId(@Param("configurationId") Long configurationId);
	
	@Query(value ="SELECT e FROM CmnLeavePlanConfigDetailsEntity as e  WHERE minValue  =:minValue and maxValue  =:maxValue and value  =:value and configurationId  =:configurationId and leaveTypeId  =:leaveTypeId")
	public CmnLeavePlanConfigDetailsEntity getCmnleavePlansByCmnLeavePlanConfigDetails(@Param("minValue") String minValue,@Param("maxValue") String maxValue,@Param("value") String value,@Param("configurationId") Long configurationId,@Param("leaveTypeId") Long leaveTypeId);
	
	@Query(value ="SELECT e FROM CmnLeavePlanConfigDetailsEntity as e  WHERE leaveTypeId = :leaveTypeId")
	public List<CmnLeavePlanConfigDetailsEntity> getLeavePlanConfigDetailsByleaveTypeId(@Param("leaveTypeId") Long leaveTypeId);
	
	

}
