package org.vrnda.hrms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vrnda.hrms.entity.CmnAddressesMstEntity;

@Repository
public interface CmnAddressesMstRepository extends PagingAndSortingRepository<CmnAddressesMstEntity, Long> {

	@Query("SELECT e FROM CmnAddressesMstEntity as e WHERE addressId = :addressId")
	public CmnAddressesMstEntity getAddressDetailsWithAddressId(@Param("addressId") Long addressId);

	@Query("SELECT e FROM CmnAddressesMstEntity as e WHERE addressId in(:presentAddressId,:permAddressId)")
	public List<CmnAddressesMstEntity> getAddressDetails(@Param("presentAddressId") Long presentAddressId,
			@Param("permAddressId") Long permAddressId);

}
