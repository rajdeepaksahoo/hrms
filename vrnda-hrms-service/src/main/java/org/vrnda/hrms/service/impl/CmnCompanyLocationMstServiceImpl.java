package org.vrnda.hrms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.vrnda.hrms.entity.CmnCompanyLocationsMstEntity;
import org.vrnda.hrms.entity.EmployeeDetailsEntity;
import org.vrnda.hrms.repository.CmnCompanyLocationsMstRepository;
import org.vrnda.hrms.repository.EmployeeDetailsRepository;
import org.vrnda.hrms.repository.dto.CmnAddressesMstDTO;
import org.vrnda.hrms.repository.dto.CmnCompanyLocationsMstDTO;
import org.vrnda.hrms.service.CmnAddressesMstService;
import org.vrnda.hrms.service.CmnCompanyLocationMstService;
import org.vrnda.hrms.service.CmnLookupMstService;
import org.vrnda.hrms.service.CmnTableSeqService;
import org.vrnda.hrms.service.EmployeeDetailsService;
import org.vrnda.hrms.service.resultset.CmnAddressesMstResultset;
import org.vrnda.hrms.service.resultset.CompanyLocationResultSet;
import org.vrnda.hrms.utils.ApplicationConstants;
import org.vrnda.hrms.utils.BaseUtils;

@Service
public class CmnCompanyLocationMstServiceImpl extends GenericServiceImpl<CmnCompanyLocationsMstEntity, Long> implements CmnCompanyLocationMstService {

	public CmnCompanyLocationMstServiceImpl(PagingAndSortingRepository<CmnCompanyLocationsMstEntity, Long> typeRepository) {
		super(typeRepository);
	}

	@Autowired
	CmnAddressesMstService cmnAddressesMstService;
	
	@Autowired
	CmnCompanyLocationsMstRepository cmnCompanyLocationsMstRepository;

	@Autowired
	CmnCompanyLocationMstService cmnCompanyLocationMstService;

	@Autowired
	CmnLookupMstService cmnLookupMstService;

	@Autowired
	CmnTableSeqService cmnTableSeqService;
	
	@Autowired
	EmployeeDetailsRepository detailsRepository;
	


	@Override
	public CompanyLocationResultSet saveOrUpdateLocation(CmnCompanyLocationsMstDTO cmnCompanyLocationsMstDto, String loggedInUser) {
		CompanyLocationResultSet cmnCompanyLocationsMstDTOResultSet = new CompanyLocationResultSet();
		CmnAddressesMstResultset cmnAddressesMstResultset=new CmnAddressesMstResultset();
		Long AddresstypelookupId=cmnLookupMstService.getLookupByLookupName(ApplicationConstants.PERMANENT_ADDRESS).getCmnLookupMstEntity().getLookupId();
		try {
			if(cmnCompanyLocationsMstDto!=null) {
				if(verifyDuplicateLocationName(cmnCompanyLocationsMstDto)) {
					cmnCompanyLocationsMstDTOResultSet.setStatus(false);
					cmnCompanyLocationsMstDTOResultSet.setMessage("Failed");
					cmnCompanyLocationsMstDTOResultSet.setMessageDescription("Records already exists with Same Location Name .");
					return cmnCompanyLocationsMstDTOResultSet;
				}
				if(cmnCompanyLocationsMstDto.getLocationId() == 0) {
					
					if(cmnCompanyLocationsMstDto.getAddress()!=null) {
					
						CmnAddressesMstDTO cmnAddressesMstDTO=new CmnAddressesMstDTO();
						
						BeanUtils.copyProperties( cmnCompanyLocationsMstDto,cmnAddressesMstDTO);
						cmnAddressesMstDTO.setAddressLine1(cmnCompanyLocationsMstDto.getAddress());
						cmnAddressesMstDTO.setAddressTypeLookupId(AddresstypelookupId);
//						cmnAddressesMstDTO.setCountryId(cmnCompanyLocationsMstDto.getCountryId());
//						cmnAddressesMstDTO.setStateId(cmnCompanyLocationsMstDto.getStateId());
//						cmnAddressesMstDTO.setDistrictId(cmnCompanyLocationsMstDto.getDistrictId());
//						cmnAddressesMstDTO.setCityName(cmnCompanyLocationsMstDto.getCityName());
//						cmnAddressesMstDTO.setPincode(cmnCompanyLocationsMstDto.getPincode());
//						cmnAddressesMstDTO.setStatusLookupId(cmnCompanyLocationsMstDto.getStatusLookupId());
						cmnAddressesMstResultset=cmnAddressesMstService.saveOrUpdateAddress(cmnAddressesMstDTO,loggedInUser);
					}
					CmnCompanyLocationsMstEntity locationEntity = new CmnCompanyLocationsMstEntity();
					BeanUtils.copyProperties(cmnCompanyLocationsMstDto, locationEntity);
					locationEntity.setAddressId(cmnAddressesMstResultset.getCmnAddressesMstEntity().getAddressId());
					locationEntity.setLocationId(cmnTableSeqService.getNextSequence(ApplicationConstants.CMN_COMPANY_LOCATIONS_MST, ApplicationConstants.LOCATION_ID));
					BaseUtils.setBaseData(locationEntity, loggedInUser);
					save(locationEntity);
					cmnCompanyLocationsMstDTOResultSet.setMessageDescription("Location saved successfully.");
				} 
				else if(cmnCompanyLocationsMstDto != null && cmnCompanyLocationsMstDto.getLocationId() > 0) {
					CmnCompanyLocationsMstEntity locationEntity = cmnCompanyLocationsMstRepository.getCompanyLocationByLocationId(cmnCompanyLocationsMstDto.getLocationId());
					if(locationEntity != null) {
						if(cmnCompanyLocationsMstDto.getAddress()!=null) {
							CmnAddressesMstDTO cmnAddressesMstDTO=new CmnAddressesMstDTO();
							BeanUtils.copyProperties( cmnCompanyLocationsMstDto,cmnAddressesMstDTO);
							cmnAddressesMstDTO.setAddressLine1(cmnCompanyLocationsMstDto.getAddress());
							cmnAddressesMstDTO.setAddressTypeLookupId(AddresstypelookupId);
							cmnAddressesMstResultset=cmnAddressesMstService.saveOrUpdateAddress(cmnAddressesMstDTO,loggedInUser);
						}
						BeanUtils.copyProperties(cmnCompanyLocationsMstDto, locationEntity);
						BaseUtils.modifyBaseData(locationEntity, loggedInUser);
						save(locationEntity);
						cmnCompanyLocationsMstDTOResultSet.setMessageDescription("Location updated successfully.");
					}
				}
				else {
					cmnCompanyLocationsMstDTOResultSet.setStatus(false);
					cmnCompanyLocationsMstDTOResultSet.setMessage("Error");
					cmnCompanyLocationsMstDTOResultSet.setMessageDescription("Invalid Inputs.");
				}
			}
		} catch (Exception e) {
			cmnCompanyLocationsMstDTOResultSet.setStatus(false);
			cmnCompanyLocationsMstDTOResultSet.setMessage("Exception");
			cmnCompanyLocationsMstDTOResultSet.setMessageDescription(e.getMessage());
		}
		return cmnCompanyLocationsMstDTOResultSet;
	}

	@Override
	public CompanyLocationResultSet deleteLocationByLocationId(Long locationId) {
		CompanyLocationResultSet cmnCompanyLocationsMstDTOResultSet = new CompanyLocationResultSet();
		try {
			if(locationId != null) {
				List<EmployeeDetailsEntity> detailsEntity = detailsRepository.getEmployeeDetailsLocationIds(locationId);
				if (detailsEntity != null && detailsEntity.size() > 0) {
					cmnCompanyLocationsMstDTOResultSet.setStatus(false);
					cmnCompanyLocationsMstDTOResultSet.setMessage(ApplicationConstants.FAILED);
					cmnCompanyLocationsMstDTOResultSet.setMessageDescription("Record cannot be deleted child records exists in employee details...");
					;
				} else {
                	CmnCompanyLocationsMstEntity locationEntity = cmnCompanyLocationsMstRepository.getCompanyLocationByLocationId(locationId);
    				delete(locationEntity);
    				if(locationEntity.getAddressId()!=null) {
    					cmnAddressesMstService.deleteAddressByAddressId(locationEntity.getAddressId());   //this method will delete the cmnaddressmst based on addressid
    				}
    				cmnCompanyLocationsMstDTOResultSet.setMessageDescription("Location deleted successfully.");
                }
			} else {
				cmnCompanyLocationsMstDTOResultSet.setStatus(false);
				cmnCompanyLocationsMstDTOResultSet.setMessage("Failed");
				cmnCompanyLocationsMstDTOResultSet.setMessageDescription("Invalid Inputs.");
			}
		} catch (Exception e) {
			cmnCompanyLocationsMstDTOResultSet.setStatus(false);
			cmnCompanyLocationsMstDTOResultSet.setMessage("Exception");
			cmnCompanyLocationsMstDTOResultSet.setMessageDescription(e.getMessage());
		}
		return cmnCompanyLocationsMstDTOResultSet;
	}

	@Override
	public CompanyLocationResultSet deleteLocationsList(List<CmnCompanyLocationsMstDTO> cmnCompanyLocationsMstDtoList) {
		CompanyLocationResultSet cmpLocationMstRS = new CompanyLocationResultSet();
		int successCount = 0;
		int failureCount = 0;
		try {
			if(cmnCompanyLocationsMstDtoList != null && cmnCompanyLocationsMstDtoList.size() > 0) {
				for(CmnCompanyLocationsMstDTO cmnCompanyLocationsMstDto : cmnCompanyLocationsMstDtoList) {
					List<EmployeeDetailsEntity> detailsEntity = detailsRepository.getEmployeeDetailsLocationIds(cmnCompanyLocationsMstDto.getLocationId());
					if (detailsEntity != null && detailsEntity.size() > 0) {
						failureCount++;
					} else {
						CmnCompanyLocationsMstEntity locationEntity = cmnCompanyLocationsMstRepository.getCompanyLocationByLocationId(cmnCompanyLocationsMstDto.getLocationId());
						if(locationEntity != null) {
							delete(locationEntity);
							if(locationEntity.getAddressId()!=null) {
								cmnAddressesMstService.deleteAddressByAddressId(locationEntity.getAddressId());   //this method will delete the cmnaddressmst based on addressid
								successCount++;
							}	
						} 
					}
				}
				if(failureCount > 0) {
					cmpLocationMstRS.setMessageDescription(successCount + " rows deleted and failed to delete " + failureCount + " rows.");
				} else {
					cmpLocationMstRS.setMessageDescription(successCount + " row deleted successfully.");
				}
				cmpLocationMstRS.setSuccessCount(successCount);
				cmpLocationMstRS.setFailureCount(failureCount);
			} else {
				cmpLocationMstRS.setStatus(false);
				cmpLocationMstRS.setMessage("Failed");
				cmpLocationMstRS.setMessageDescription("Invalid Inputs");
			}
		} catch(Exception e) {
			cmpLocationMstRS.setStatus(false);
			cmpLocationMstRS.setMessage("Exception");
			cmpLocationMstRS.setMessageDescription("Excepton occured while deleting the Locations. Please contact Administrator");
		}
		return cmpLocationMstRS;
	}

	@Override
	public CompanyLocationResultSet getAllCompanyLocations() {
		CompanyLocationResultSet cmnCompanyLocationsMstDTOResultSet = new CompanyLocationResultSet();
		try {
			List<CmnCompanyLocationsMstDTO> cmnCompanyLocationsMstDTO = cmnCompanyLocationsMstRepository.getAllCompanyLocationsFromAddressMst();
			cmnCompanyLocationsMstDTOResultSet.setCmnCompanyLocationsMstDTOList(cmnCompanyLocationsMstDTO);
		} catch (Exception e) {
			e.getStackTrace();
			cmnCompanyLocationsMstDTOResultSet.setStatus(false);
			cmnCompanyLocationsMstDTOResultSet.setMessage("Exception");
			cmnCompanyLocationsMstDTOResultSet.setMessageDescription(e.getMessage());
		}
		return cmnCompanyLocationsMstDTOResultSet;
	}

	@Override
	public CompanyLocationResultSet getCompanyLocationByLocationId(Long locationId) {
		CmnCompanyLocationsMstEntity cmnCompanyLocationsMstDTOEntity = null;
		CompanyLocationResultSet cmnCompanyLocationsMstDTOResultSet = new CompanyLocationResultSet();
		try {
			cmnCompanyLocationsMstDTOEntity = cmnCompanyLocationsMstRepository.getCompanyLocationByLocationId(locationId);
			if (cmnCompanyLocationsMstDTOEntity != null) {
				List<CmnCompanyLocationsMstEntity> list = new ArrayList<CmnCompanyLocationsMstEntity>();
				list.add(cmnCompanyLocationsMstDTOEntity);
				cmnCompanyLocationsMstDTOResultSet.setCmnCompanyLocationsMstEntityList(list);
			} else {
				cmnCompanyLocationsMstDTOResultSet.setStatus(false);
				cmnCompanyLocationsMstDTOResultSet.setMessage("Exception");
				cmnCompanyLocationsMstDTOResultSet.setMessageDescription("Employee Id does not exist");
			}
		} catch (Exception e) {
			e.getStackTrace();
			cmnCompanyLocationsMstDTOResultSet.setStatus(false);
			cmnCompanyLocationsMstDTOResultSet.setMessage("Exception");
			cmnCompanyLocationsMstDTOResultSet.setMessageDescription(e.getMessage());
		}
		return cmnCompanyLocationsMstDTOResultSet;
	}

	@Override
	public CompanyLocationResultSet getCompanyLocationsByStatusLookupId(Long statusLookupId) {
		List<CmnCompanyLocationsMstEntity> cmnCompanyLocationsMstDTOEntity = null;
		CompanyLocationResultSet cmnCompanyLocationsMstDTOResultSet = new CompanyLocationResultSet();
		try {
			cmnCompanyLocationsMstDTOEntity = cmnCompanyLocationsMstRepository.getCompanyLocationsByStatusLookupId(statusLookupId);
			if (cmnCompanyLocationsMstDTOEntity != null) {
				cmnCompanyLocationsMstDTOResultSet.setCmnCompanyLocationsMstEntityList(cmnCompanyLocationsMstDTOEntity);
			} else {
				cmnCompanyLocationsMstDTOResultSet.setStatus(false);
				cmnCompanyLocationsMstDTOResultSet.setMessage("Exception");
				cmnCompanyLocationsMstDTOResultSet.setMessageDescription("Employee Id does not exist");
			}
		} catch (Exception e) {
			e.getStackTrace();
			cmnCompanyLocationsMstDTOResultSet.setStatus(false);
			cmnCompanyLocationsMstDTOResultSet.setMessage("Exception");
			cmnCompanyLocationsMstDTOResultSet.setMessageDescription(e.getMessage());
		}
		return cmnCompanyLocationsMstDTOResultSet;
	}

	@Override
	public boolean verifyDuplicateLocationName(CmnCompanyLocationsMstDTO cmnCompanyLocationsMstDto) {
		if(cmnCompanyLocationsMstDto != null) {
			CmnCompanyLocationsMstEntity tempCmnloc = cmnCompanyLocationsMstRepository.getLocationbyLocationName(cmnCompanyLocationsMstDto.getLocationName());
			if(tempCmnloc != null && cmnCompanyLocationsMstDto.getLocationId() != null && cmnCompanyLocationsMstDto.getLocationId() == 0)
				return true;
			else if(tempCmnloc != null && cmnCompanyLocationsMstDto.getLocationId() != null && cmnCompanyLocationsMstDto.getLocationId() > 0 && tempCmnloc.getLocationId() != cmnCompanyLocationsMstDto.getLocationId())
				return true;
			else 
				return false;
		} else {
			return true;
		}
	}

}
