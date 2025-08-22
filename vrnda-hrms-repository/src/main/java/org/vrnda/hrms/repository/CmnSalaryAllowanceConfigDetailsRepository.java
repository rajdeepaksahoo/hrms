package org.vrnda.hrms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vrnda.hrms.entity.CmnSalaryAllowanceConfigDetailsEntity;
import org.vrnda.hrms.entity.CmnSalarySlabLevelConfigDetailsEntity;

@Repository
public interface CmnSalaryAllowanceConfigDetailsRepository
		extends PagingAndSortingRepository<CmnSalaryAllowanceConfigDetailsEntity, Long> {

	@Query("select e from CmnSalaryAllowanceConfigDetailsEntity as e where configurationId = :configurationId")
	public List<CmnSalaryAllowanceConfigDetailsEntity> getAllowanceLevelDetailsById(
			@Param("configurationId") Long configurationId);

	@Query(value = "SELECT e FROM CmnSalaryAllowanceConfigDetailsEntity as e  WHERE cmnSalaryAllowanceConfigDetlsId  =:cmnSalaryAllowanceConfigDetlsId ")
	public CmnSalaryAllowanceConfigDetailsEntity getCmnsalaryAllowanceBycmnsalarydetails(
			@Param("cmnSalaryAllowanceConfigDetlsId") Long cmnSalaryAllowanceConfigDetlsId);

	@Query(value = "SELECT e FROM CmnSalaryAllowanceConfigDetailsEntity as e  WHERE salaryAllowanceTypeLookupId  =:salaryAllowanceTypeLookupId and salarySlabLevelId  =:salarySlabLevelId and value  =:value and configurationId = :configurationId")
	public CmnSalaryAllowanceConfigDetailsEntity getCmnsalaryAllowance(
			@Param("salaryAllowanceTypeLookupId") Long salaryAllowanceTypeLookupId,
			@Param("salarySlabLevelId") Long salarySlabLevelId, @Param("value") Long value,
			@Param("configurationId") Long configurationId);

	@Query("SELECT e FROM CmnSalaryAllowanceConfigDetailsEntity as e WHERE cmnSalaryAllowanceConfigDetlsId = :cmnSalaryAllowanceConfigDetlsId")
	public CmnSalaryAllowanceConfigDetailsEntity getSalarySlabLevelByCmnSalarySlabAllowanceConfigDetlsId(
			@Param("cmnSalaryAllowanceConfigDetlsId") Long cmnSalaryAllowanceConfigDetlsId);

	@Query("select e from CmnSalaryAllowanceConfigDetailsEntity as e where configurationId = :configurationId and salarySlabLevelId =:cmnSalarySlabLevelConfigDetlsId")
	public List<CmnSalaryAllowanceConfigDetailsEntity> getAllsalaryAllowanceDetailsBycnfgIdAndLevelId(
			@Param("configurationId") Long configurationId,
			@Param("cmnSalarySlabLevelConfigDetlsId") Long cmnSalarySlabLevelConfigDetlsId);

	@Query("select e from CmnSalaryAllowanceConfigDetailsEntity as e where salarySlabLevelId = :salarySlabLevelId")
	public List<CmnSalaryAllowanceConfigDetailsEntity> getAllowanceLevelDetailsBySalaryLevelId(
			@Param("salarySlabLevelId") Long salarySlabLevelId);

}
