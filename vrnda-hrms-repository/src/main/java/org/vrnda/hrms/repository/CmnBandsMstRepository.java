package org.vrnda.hrms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vrnda.hrms.entity.CmnBandsMstEntity;

@Repository
public interface CmnBandsMstRepository extends PagingAndSortingRepository<CmnBandsMstEntity, Long>{

	@Query("SELECT e FROM CmnBandsMstEntity as e WHERE bandId = :bandId")
	public CmnBandsMstEntity getBandByBandId(
			@Param("bandId") Long bandId);	

	@Query("SELECT cmn FROM CmnBandsMstEntity as cmn WHERE bandName = :bandName ")
	public CmnBandsMstEntity getBandByBandName(
			@Param("bandName") String bandName);

	@Query("SELECT e  FROM  CmnBandsMstEntity as e")
	public List<CmnBandsMstEntity> getAllBands();

	@Query("SELECT e  FROM  CmnBandsMstEntity as e where statusLookupId = :statusLookupId ")
	public List<CmnBandsMstEntity> getBandsByStatusLookupId(
			@Param("statusLookupId") Long statusLookupId);

}
