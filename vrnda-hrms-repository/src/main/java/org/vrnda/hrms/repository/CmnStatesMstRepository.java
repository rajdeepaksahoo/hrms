package org.vrnda.hrms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.vrnda.hrms.entity.CmnStatesMstEntity;

@Repository
public interface CmnStatesMstRepository extends PagingAndSortingRepository<CmnStatesMstEntity, Long> {

	@Query("SELECT e FROM CmnStatesMstEntity as e order by e.stateName")
	public List<CmnStatesMstEntity> getAllCmnStateMstList();
	
}