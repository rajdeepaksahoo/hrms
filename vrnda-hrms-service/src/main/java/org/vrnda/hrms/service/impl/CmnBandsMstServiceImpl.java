package org.vrnda.hrms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.vrnda.hrms.entity.CmnBandsMstEntity;
import org.vrnda.hrms.repository.CmnBandsMstRepository;
import org.vrnda.hrms.repository.dto.CmnBandsMstDTO;
import org.vrnda.hrms.service.CmnBandsMstService;
import org.vrnda.hrms.service.CmnTableSeqService;
import org.vrnda.hrms.service.resultset.BandsResultset;
import org.vrnda.hrms.utils.ApplicationConstants;
import org.vrnda.hrms.utils.BaseUtils;

@Service
public class CmnBandsMstServiceImpl extends GenericServiceImpl<CmnBandsMstEntity, Long> implements CmnBandsMstService  {

	@Autowired
	CmnBandsMstRepository bandsRepository;

	@Autowired
	CmnTableSeqService cmnTableSeqService;
	

	public CmnBandsMstServiceImpl(PagingAndSortingRepository<CmnBandsMstEntity, Long> typeRepository) {
		super(typeRepository);
	}

	@Override
	public BandsResultset saveOrUpdateBand(CmnBandsMstDTO cmnBandsMstDTO, String loggedInUser) {
		BandsResultset bandsResultset = new BandsResultset();
		try {
			if (cmnBandsMstDTO != null) {
				if(verifyDuplicateBandName(cmnBandsMstDTO.getBandName(), cmnBandsMstDTO.getBandId())) {
					bandsResultset.setStatus(false);
					bandsResultset.setMessage("Error");
					bandsResultset.setMessageDescription("Band already exists with this name.");
					return bandsResultset;
				}
				if(cmnBandsMstDTO.getBandId() == 0) {
					CmnBandsMstEntity cmnBandsMstEntity = new CmnBandsMstEntity();
					BeanUtils.copyProperties(cmnBandsMstDTO, cmnBandsMstEntity);
					cmnBandsMstEntity.setBandId(cmnTableSeqService.getNextSequence(ApplicationConstants.CMN_BANDS_MST, ApplicationConstants.BAND_ID));
					BaseUtils.setBaseData(cmnBandsMstEntity, loggedInUser);
					save(cmnBandsMstEntity);
					bandsResultset.setMessageDescription("Band saved successfully.");
				} else if(cmnBandsMstDTO != null && cmnBandsMstDTO.getBandId() > 0) {
					CmnBandsMstEntity cmnBandsMstEntity = bandsRepository.getBandByBandId(cmnBandsMstDTO.getBandId());
					if(cmnBandsMstEntity != null) {
						BeanUtils.copyProperties(cmnBandsMstDTO, cmnBandsMstEntity);
						BaseUtils.modifyBaseData(cmnBandsMstEntity, loggedInUser);
						save(cmnBandsMstEntity);
						bandsResultset.setMessageDescription("Band updated successfully.");
					}
				} else {
					bandsResultset.setStatus(false);
					bandsResultset.setMessage("Error");
					bandsResultset.setMessageDescription("Invalid Inputs.");
				}
			} else {
				bandsResultset.setStatus(false);
				bandsResultset.setMessage("Failed");
				bandsResultset.setMessageDescription("Invalid Inputs.");
			}
		} catch (Exception e) {
			bandsResultset.setStatus(false);
			bandsResultset.setMessage("Error");
			bandsResultset.setMessageDescription("Unable to save or update. Please contact Administrator.");
		}
		return bandsResultset;
	}

	@Override
	public BandsResultset deleteBandById(Long bandId) {
		BandsResultset bandsResultset = new BandsResultset();
		CmnBandsMstEntity cmnBandsMstEntity=null;
//		List<EmployeeDetailsEntity> employeeDetailsEntityList=null;
		try {
			if(bandId != null) {
				cmnBandsMstEntity  = bandsRepository.getBandByBandId(bandId);
				if(cmnBandsMstEntity != null) {
//					employeeDetailsEntityList = employeeDetailsRepository.getEmployeeDetailsByBandId(bandId);
//					if(employeeDetailsEntityList.size()>0) {
//						bandsResultset.setStatus(false);
//						bandsResultset.setMessage("Failed");
//						bandsResultset.setMessageDescription("Band assigned to a user cannot be deleted.");
//					}else {
//					}
					delete(cmnBandsMstEntity);
					bandsResultset.setMessageDescription("Band deleted successfully.");
				}
			} else {
				bandsResultset.setStatus(false);
				bandsResultset.setMessage("Failed");
				bandsResultset.setMessageDescription("Invalid Inputs.");
			}
		} catch (Exception e) {
			bandsResultset.setStatus(false);
			bandsResultset.setMessage("Exception");
			bandsResultset.setMessageDescription(e.getMessage());
		}
		return bandsResultset;
	}

	@Override
	public BandsResultset deleteBandsList(List<CmnBandsMstDTO> cmnBandsMstDTOList) {
		BandsResultset bandsResultset = new BandsResultset();
		int successCount = 0;
		int failureCount = 0;
		try {
			if(cmnBandsMstDTOList != null && cmnBandsMstDTOList.size() > 0) {
				for(CmnBandsMstDTO cmnBandsMstDTO : cmnBandsMstDTOList) {
					CmnBandsMstEntity cmnBandsMstEntity = bandsRepository.getBandByBandId(cmnBandsMstDTO.getBandId());
					if(cmnBandsMstEntity != null) {
						delete(cmnBandsMstEntity);
						successCount++;
					} else {
						failureCount++;
					}
				}
				if(failureCount > 0) {
					bandsResultset.setMessageDescription(successCount + " rows deleted and failed to delete " + failureCount + " rows.");
				} else {
					bandsResultset.setMessageDescription(successCount + " row deleted successfully.");
				}
				bandsResultset.setSuccessCount(successCount);
				bandsResultset.setFailureCount(failureCount);
			} else {
				bandsResultset.setStatus(false);
				bandsResultset.setMessage("Failed");
				bandsResultset.setMessageDescription("Invalid Inputs");
			}
		} catch(Exception e) {
			bandsResultset.setStatus(false);
			bandsResultset.setMessage("Exception");
			bandsResultset.setMessageDescription("Excepton occured while deleting the Bands. Please contact Administrator");
		}
		return bandsResultset;
	}

	@Override
	public BandsResultset getAllBands() {
		BandsResultset obj = new BandsResultset();
		try {
			obj.setCmnBandsMstEntityList(bandsRepository.getAllBands());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	@Override
	public BandsResultset getBandById(Long bandId) {
		CmnBandsMstEntity bandsEntity = null;
		BandsResultset bandsResultset = new BandsResultset();
		try {
			bandsEntity = bandsRepository.getBandByBandId(bandId);
			if (bandsEntity != null) {
				List<CmnBandsMstEntity> list = new ArrayList<CmnBandsMstEntity>();
				list.add(bandsEntity);
				bandsResultset.setCmnBandsMstEntityList(list);
			} else {
				bandsResultset.setStatus(false);
				bandsResultset.setMessage("Failed");
				bandsResultset.setMessageDescription("Band Id does not exist");
			}
		} catch (Exception e) {
			bandsResultset.setStatus(false);
			bandsResultset.setMessage("Exception");
			bandsResultset.setMessageDescription(e.getMessage());
		}
		return bandsResultset;
	}

	@Override
	public BandsResultset getBandsByStatusLookupId(Long statusLookupId) {
		BandsResultset bandsResultset = new BandsResultset();
		try {
			List<CmnBandsMstEntity> bandsEntity = bandsRepository.getBandsByStatusLookupId(statusLookupId);
			if (bandsEntity.size() > 0) {
				bandsResultset.setCmnBandsMstEntityList(bandsEntity);

			} else {
				bandsResultset.setStatus(false);
				bandsResultset.setMessage("Failed");
				bandsResultset.setMessageDescription("Band list not available");
			} 
		}catch (Exception e) {
			e.printStackTrace();
			bandsResultset.setStatus(false);
			bandsResultset.setMessage("Exception");
			bandsResultset.setMessageDescription(e.getMessage());
		}
		return  bandsResultset;
	}

	@Override
	public boolean verifyDuplicateBandName(String bandName, Long bandId) {
		if(bandName != null) {
			CmnBandsMstEntity tempBand = bandsRepository.getBandByBandName(bandName);
			if(tempBand != null && bandId != null && bandId == 0)
				return true;
			else if(tempBand != null && bandId != null && bandId > 0 && tempBand.getBandId() != bandId)
				return true;
			else 
				return false;
		} else {
			return true;
		}
	}

}
