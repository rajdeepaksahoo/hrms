package org.vrnda.hrms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vrnda.hrms.entity.CmnCompanyLocationsMstEntity;
import org.vrnda.hrms.repository.dto.CmnCompanyLocationsMstDTO;

@Repository
public interface CmnCompanyLocationsMstRepository extends PagingAndSortingRepository<CmnCompanyLocationsMstEntity, Long> {

	@Query("select e from CmnCompanyLocationsMstEntity as e where locationId = :locationId")
	public CmnCompanyLocationsMstEntity getCompanyLocationByLocationId(
			@Param("locationId") Long locationId);

	@Query("select e from CmnCompanyLocationsMstEntity as e where locationName = :locationName")
	public CmnCompanyLocationsMstEntity getCompanyLocationByLocationName(
			@Param("locationName") Long locationName);

	@Query("select e from CmnCompanyLocationsMstEntity as e order by locationName")
	public List<CmnCompanyLocationsMstEntity> getAllComapnyLocations();
	
	@Query("SELECT new org.vrnda.hrms.repository.dto.CmnCompanyLocationsMstDTO(loc.locationId, loc.locationName, loc.locationDescription, loc.addressId,addr.addressLine1, loc.latitude, loc.longitude, loc.statusLookupId, loc.systemConfigFlag, addr.countryId, addr.stateId, addr.districtId, addr.cityName, addr.pincode) "
			+ "FROM CmnCompanyLocationsMstEntity loc INNER JOIN CmnAddressesMstEntity addr on loc.addressId = addr.addressId")
	public List<CmnCompanyLocationsMstDTO> getAllCompanyLocationsFromAddressMst();

	@Query("select e from CmnCompanyLocationsMstEntity as e where statusLookupId = :statusLookupId")
	public List<CmnCompanyLocationsMstEntity> getCompanyLocationsByStatusLookupId(
			@Param("statusLookupId") Long statusLookupId);

	@Query("SELECT e FROM CmnCompanyLocationsMstEntity as e WHERE locationName = :locationName "
			+ "and countryId = :countryId and stateId = :stateId and districtId = :districtId")
	public CmnCompanyLocationsMstEntity getLocationbyNameCountryIdStateIdDistrictId(
			@Param ("locationName") String locationName,
			@Param ("countryId") Long countryId,
			@Param ("stateId") Long stateId,
			@Param ("districtId") Long districtId);
	
	@Query("SELECT e FROM CmnCompanyLocationsMstEntity as e WHERE locationName = :locationName ")
	public CmnCompanyLocationsMstEntity getLocationbyLocationName(
			@Param ("locationName") String locationName);

}


