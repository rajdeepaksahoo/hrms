package org.vrnda.hrms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vrnda.hrms.entity.CmnGeneralLoanConfigDetailsEntity;
import org.vrnda.hrms.entity.CmnLeaveConfigDetailsEntity;
import org.vrnda.hrms.entity.CmnYearsMstEntity;
import org.vrnda.hrms.entity.EmployeeAllConfigDetailsEntity;

@Repository
public interface CmnGeneralLoanConfigDetailsRepository extends PagingAndSortingRepository<CmnGeneralLoanConfigDetailsEntity, Long> {

	@Query("select e from CmnGeneralLoanConfigDetailsEntity as e where cmnGenLoanConfigDetlsId = :cmnGenLoanConfigDetlsId ")
	public CmnGeneralLoanConfigDetailsEntity getGeneralLoanConfigDetailsByCmnGenLoanConfigDetlsId(
			@Param("cmnGenLoanConfigDetlsId") Long cmnGenLoanConfigDetlsId);

	@Query("select e from CmnGeneralLoanConfigDetailsEntity as e where configurationId = :configurationId")
	public List<CmnGeneralLoanConfigDetailsEntity> getGeneralLoanConfigDetailsByConfigurationId(
			@Param("configurationId") Long configurationId);

	@Query("select e from CmnGeneralLoanConfigDetailsEntity as e "
			+ "where configurationId = :configurationId and statusLookupId = :statusLookupId")
	public List<CmnGeneralLoanConfigDetailsEntity> getGeneralLoanConfigDetailsByConfigurationIdAndStatusLookupId(
			@Param("configurationId") Long configurationId,
			@Param("statusLookupId") Long statusLookupId);

	@Query("select e from EmployeeAllConfigDetailsEntity as e where employeeId = :employeeId and yearId = ( select yearId from CmnYearsMstEntity cym where yearName = :year and yearTypeLookupId = (select lookupId from CmnLookupMstEntity clm where lookupName = 'CALENDAR_YEAR'))")
	public EmployeeAllConfigDetailsEntity getEmployeeLoanConfigDetails(
			@Param("employeeId") Long employeeId ,
			@Param("year") String year);
	
	
	@Query("SELECT e FROM CmnGeneralLoanConfigDetailsEntity as e WHERE configurationId = :configurationId")
	public List<CmnGeneralLoanConfigDetailsEntity> getLoanConfigDetailsByConfigurationId(
			@Param("configurationId") long configurationId);

}
