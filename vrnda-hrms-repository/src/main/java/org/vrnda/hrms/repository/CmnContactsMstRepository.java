package org.vrnda.hrms.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vrnda.hrms.entity.CmnContactsMstEntity;

@Repository
public interface CmnContactsMstRepository extends PagingAndSortingRepository<CmnContactsMstEntity, Long> {

	@Query("SELECT e FROM CmnContactsMstEntity as e WHERE contactId = :contactId")
	public CmnContactsMstEntity getCmnContactMstByContactId(@Param("contactId") Long contactId);

}
