package org.vrnda.hrms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vrnda.hrms.entity.CmnDesignationsMstEntity;

@Repository
public interface CmnDesignationsMstRepository extends JpaRepository<CmnDesignationsMstEntity, Long> {

	@Query("SELECT e FROM CmnDesignationsMstEntity as e WHERE designationId = :designationId")
	public CmnDesignationsMstEntity getDesignationByDesignationId(
			@Param("designationId") Long designationId);

	@Query("SELECT e FROM CmnDesignationsMstEntity as e WHERE designationName = :designationName")
	public CmnDesignationsMstEntity getDesignationByDesignationName(
			@Param("designationName") String designationName);

	@Query("select e from CmnDesignationsMstEntity as e order by designationName")
	public List<CmnDesignationsMstEntity> getAllDesignations();

	@Query("SELECT e FROM CmnDesignationsMstEntity as e WHERE statusLookupId = :statusLookupId")
	public List<CmnDesignationsMstEntity> getDesignationsByStatusLookupId(
			@Param("statusLookupId") Long statusLookupId);

	@Query("select cdme  from CmnDesignationsMstEntity cdme")
	public List<CmnDesignationsMstEntity> getAllDesignationIdsAndDesignationNames();

}
