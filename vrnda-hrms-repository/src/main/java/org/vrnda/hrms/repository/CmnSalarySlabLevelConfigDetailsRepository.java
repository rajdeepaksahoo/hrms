package org.vrnda.hrms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vrnda.hrms.entity.CmnSalarySlabLevelConfigDetailsEntity;

@Repository
public interface CmnSalarySlabLevelConfigDetailsRepository extends PagingAndSortingRepository<CmnSalarySlabLevelConfigDetailsEntity, Long> {

	@Query("select e from CmnSalarySlabLevelConfigDetailsEntity as e where configurationId = :configurationId ")
	public List<CmnSalarySlabLevelConfigDetailsEntity> getSlabLevelDetailsByConfigurationId(
			@Param("configurationId") Long configurationId);

	@Query("SELECT e FROM CmnSalarySlabLevelConfigDetailsEntity as e WHERE cmnSalarySlabLevelConfigDetlsId = :cmnSalarySlabLevelConfigDetlsId")
	public CmnSalarySlabLevelConfigDetailsEntity getSalarySlabLevelByCmnSalarySlabLevelConfigDetlsId(
			@Param("cmnSalarySlabLevelConfigDetlsId") Long cmnSalarySlabLevelConfigDetlsId );

	@Query(value ="SELECT e FROM CmnSalarySlabLevelConfigDetailsEntity as e  WHERE slabLevel  =:slabLevel and configurationId  =:configurationId")
	public CmnSalarySlabLevelConfigDetailsEntity getCmnsalarySlabsBycmnsalaryslabdetails(@Param("slabLevel") Long slabLevel,@Param("configurationId") Long configurationId);
	

}
