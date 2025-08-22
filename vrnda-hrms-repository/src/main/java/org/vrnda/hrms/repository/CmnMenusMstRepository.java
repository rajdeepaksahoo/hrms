package org.vrnda.hrms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vrnda.hrms.entity.CmnMenusMstEntity;

@Repository
public interface CmnMenusMstRepository extends PagingAndSortingRepository<CmnMenusMstEntity, Long> {

	@Query("SELECT e FROM CmnMenusMstEntity as e WHERE menuId = :menuId")
	public CmnMenusMstEntity getCmnMenuMstEntityByMenuId(
			@Param("menuId") long menuId);

	@Query("SELECT e FROM CmnMenusMstEntity as e")
	public List<CmnMenusMstEntity> getAllMenus();

	@Query("SELECT s FROM CmnMenusMstEntity as s WHERE s.menuType = :menuTypeId order by menuSeq")
	public List<CmnMenusMstEntity> getMenusByMenuTypeId(
			@Param("menuTypeId") Long menuTypeId);

	@Query("SELECT s FROM CmnMenusMstEntity as s WHERE s.parentMenuId = :parentMenuId order by menuSeq")
	public List<CmnMenusMstEntity> getMenusByParentMenuId(
			@Param("parentMenuId") Long parentMenuId);

	@Query("SELECT s FROM CmnMenusMstEntity as s WHERE s.menuId = :menuId and s.menuType = :menuTypeId")
	public CmnMenusMstEntity getMenuByMenuIdAndMenuTypeId(
			@Param("menuId") Long menuId,
			@Param("menuTypeId") Long menuTypeId);

	@Query("SELECT s FROM CmnMenusMstEntity as s WHERE s.menuCode = :menuCode")
	public CmnMenusMstEntity getMenuByMenuCode(
			@Param("menuCode") String menuCode);

	@Query("SELECT s FROM CmnMenusMstEntity as s WHERE s.menuName = :menuName")
	public CmnMenusMstEntity getMenuByMenuName(
			@Param("menuName") String menuName);

	@Query("SELECT s FROM CmnMenusMstEntity as s WHERE s.menuPath = :menuPath")
	public CmnMenusMstEntity getMenuByMenuPath(
			@Param("menuPath") String menuPath);

}
