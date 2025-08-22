package org.vrnda.hrms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.vrnda.hrms.entity.CmnCountrysMstEntity;

@Repository
public interface CmnCountrysMstRepository extends PagingAndSortingRepository<CmnCountrysMstEntity, Long> {

	@Query("SELECT e FROM CmnCountrysMstEntity as e order by countryName")
	public List<CmnCountrysMstEntity> getAllCmnCountryMstList();
	
}
