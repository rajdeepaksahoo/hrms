package org.vrnda.hrms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.vrnda.hrms.entity.CmnDistrictMstEntity;

@Repository
public interface CmnDistrictMstRepository extends PagingAndSortingRepository<CmnDistrictMstEntity, Long> {

	@Query("SELECT e FROM CmnDistrictMstEntity as e order by districtName")
	public List<CmnDistrictMstEntity> getAllCmnDistrictMstList();
	
}
