package org.vrnda.hrms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vrnda.hrms.entity.CmnLookupMstEntity;

@Repository
public interface CmnLookupMstRepository  extends JpaRepository<CmnLookupMstEntity, String> {

	@Query(value="select e from CmnLookupMstEntity as e where lookupName = :lookupName and parentLookupId = :parentLookupId")
	public CmnLookupMstEntity getLookupByLookupNameAndParentLookupId(
			@Param("lookupName") String lookupName,
			@Param("parentLookupId") Long parentLookupId);

	@Query(value="call PROC_CMN_DROP_DOWN(:parentLookupName,NULL)", nativeQuery = true)
	public List<Object[]> getLookupIdAndNameListByParentLookupName(
			@Param("parentLookupName") String parentLookupName);

	@Query(value="call PROC_CMN_DROP_DOWN(:parentLookupName,:parentLookupId)", nativeQuery = true)
	public List<Object[]> getLookupIdAndNameListByParentLookupNameAndParentLookupId(
			@Param("parentLookupName") String parentLookupName,
			@Param("parentLookupId") Long parentLookupId);

	@Query("select lookupId from CmnLookupMstEntity c  where c.lookupName = :lookupName and c.parentLookupId = "
			+ "(select lookupId from CmnLookupMstEntity cl  where cl.lookupName = :parentLookupName)")
	public Long getLookupIdByLookupNameAndParentLookupName(
			@Param("lookupName") String lookupName, 
			@Param("parentLookupName") String parentLookupName);

	@Query("select e from CmnLookupMstEntity as e where lookupName = :lookupName") 
	public CmnLookupMstEntity getLookupByLookupName(
			@Param("lookupName") String lookupName);

	@Query("select e from CmnLookupMstEntity as e where "
			+ "parentLookupId = (select lookupId from CmnLookupMstEntity cl  where cl.lookupName = :parentLookupName)")
	public List<CmnLookupMstEntity> getLookupByParentLookupName(
			@Param("parentLookupName") String parentLookupName);

	@Query("select e from CmnLookupMstEntity e where lookupId = :lookupId")
	public   CmnLookupMstEntity getLookupByLookupId(
			@Param("lookupId") Long lookupId);

	@Query(" select max(orderNo) from CmnLookupMstEntity as e where parentLookupId = :parentLookupId")
	public Integer getMaxOrderNoByParentLookupId(@Param("parentLookupId") Long parentLookupId);

	@Query(value = "select e from CmnLookupMstEntity as e where parentLookupId in (select t.lookupId from CmnLookupMstEntity as t where t.lookupName = :lookupName)")
	public List<CmnLookupMstEntity> getLookupDetailsByLookupName(@Param("lookupName") String lookupName);
	
	@Query("select e from CmnLookupMstEntity as e where lookupName = :lookupName and "
			+ " parentLookupId = (select lookupId from CmnLookupMstEntity cl  where cl.lookupName = :parentLookupName)")
	public CmnLookupMstEntity getLookupByLookupNameandParentLookupId(@Param("lookupName") String lookupName,
			@Param("parentLookupName") String parentLookupName);

}
