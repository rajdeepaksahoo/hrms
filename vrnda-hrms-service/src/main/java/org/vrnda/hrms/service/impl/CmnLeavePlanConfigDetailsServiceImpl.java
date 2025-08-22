package org.vrnda.hrms.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.vrnda.hrms.entity.CmnLeavePlanConfigDetailsEntity;
import org.vrnda.hrms.repository.CmnLeavePlanConfigDetailsRepository;
import org.vrnda.hrms.repository.dto.CmnLeavePlanConfigDetailsDTO;
import org.vrnda.hrms.service.CmnLeavePlanConfigDetailsService;
import org.vrnda.hrms.service.CmnTableSeqService;
import org.vrnda.hrms.service.resultset.CmnLeavePlanConfigDetailsResultSet;
import org.vrnda.hrms.utils.ApplicationConstants;
import org.vrnda.hrms.utils.BaseUtils;

@Service
public class CmnLeavePlanConfigDetailsServiceImpl extends GenericServiceImpl<CmnLeavePlanConfigDetailsEntity, String>
implements CmnLeavePlanConfigDetailsService {
	@Autowired
	CmnLeavePlanConfigDetailsRepository cmnLeavePlanConfigDetailsRepository;
	
	@Autowired
	CmnTableSeqService cmnTableSeqService;
	public CmnLeavePlanConfigDetailsServiceImpl(
			PagingAndSortingRepository<CmnLeavePlanConfigDetailsEntity, String> typeRepository) {
		super(typeRepository);
		// TODO Auto-generated constructor stub
	}

	@Override
	public CmnLeavePlanConfigDetailsResultSet getLeavePlanConfigDetailsByConfigurationIdAndleaveTypeId(Long configurationId,
			Long leaveTypeId) {
		CmnLeavePlanConfigDetailsResultSet cmnleaveplanResultSet = new CmnLeavePlanConfigDetailsResultSet();

		try {

			if (configurationId != null && leaveTypeId != null) {
				List<CmnLeavePlanConfigDetailsEntity> cmnleaveplanEntity = cmnLeavePlanConfigDetailsRepository
						.getLeavePlanConfigDetailsByConfigurationIdAndLvTypeLookUpId(configurationId,leaveTypeId);
				if (cmnleaveplanEntity.size() > 0) {
					cmnleaveplanResultSet.setCmnLeavePlanConfigDetailsEntityList(cmnleaveplanEntity);
				} else {
					cmnleaveplanResultSet.setStatus(false);
					cmnleaveplanResultSet.setMessage("Failed");
					cmnleaveplanResultSet.setMessageDescription("Config Leave Plan list not available");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			cmnleaveplanResultSet.setStatus(false);
			cmnleaveplanResultSet.setMessage("Exception");
			cmnleaveplanResultSet.setMessageDescription(e.getMessage());
		}
		return cmnleaveplanResultSet;
	}

	@Override
	public CmnLeavePlanConfigDetailsResultSet saveOrUpdateLeavePlans(CmnLeavePlanConfigDetailsDTO cmnLeavePlanConfigDetailsDto,String loggedInUser) {
		CmnLeavePlanConfigDetailsResultSet leaveplanconfigResultSet = new CmnLeavePlanConfigDetailsResultSet();
		try {
           if(cmnLeavePlanConfigDetailsDto!=null) {
				if(verifyDuplicateInLeavePlans(cmnLeavePlanConfigDetailsDto)) {
					leaveplanconfigResultSet.setStatus(false);
					leaveplanconfigResultSet.setMessage("Failed");
					leaveplanconfigResultSet.setMessageDescription("Leave Plan already exists with same configuration.");
					return leaveplanconfigResultSet;
				}
				if(cmnLeavePlanConfigDetailsDto.getCmnLvPlnConfigDetlsId() == 0) {
					CmnLeavePlanConfigDetailsEntity leaveplanEntity = new CmnLeavePlanConfigDetailsEntity();
					BeanUtils.copyProperties(cmnLeavePlanConfigDetailsDto, leaveplanEntity);
					leaveplanEntity.setCmnLvPlnConfigDetlsId(cmnTableSeqService.getNextSequence(ApplicationConstants.CMN_LEAVE_PLAN_CONFIG_DETAILS, ApplicationConstants.CMN_LV_PLN_CONFIG_DETLS_ID));
					BaseUtils.setBaseData(leaveplanEntity,loggedInUser);
					save(leaveplanEntity);
					leaveplanconfigResultSet.setMessageDescription("Record saved successfully.");
				} 
				else if(cmnLeavePlanConfigDetailsDto != null && cmnLeavePlanConfigDetailsDto.getCmnLvPlnConfigDetlsId() > 0) {
					CmnLeavePlanConfigDetailsEntity leaveplanEntity = cmnLeavePlanConfigDetailsRepository.getLeavePlanConfigBycmnlvdetId(cmnLeavePlanConfigDetailsDto.getCmnLvPlnConfigDetlsId());
					if(leaveplanEntity != null) {
						BeanUtils.copyProperties(cmnLeavePlanConfigDetailsDto, leaveplanEntity);
						BaseUtils.modifyBaseData(leaveplanEntity,loggedInUser);
						save(leaveplanEntity);
						leaveplanconfigResultSet.setMessageDescription("Record updated successfully.");
					}
                 }
				else {
					leaveplanconfigResultSet.setStatus(false);
					leaveplanconfigResultSet.setMessage("Error");
					leaveplanconfigResultSet.setMessageDescription("Invalid Inputs.");
				}
           }

		} catch (Exception e) {
			leaveplanconfigResultSet.setStatus(false);
			leaveplanconfigResultSet.setMessage("Exception");
			leaveplanconfigResultSet.setMessageDescription(e.getMessage());
		}
 		return leaveplanconfigResultSet;

	}

	public boolean verifyDuplicateInLeavePlans(CmnLeavePlanConfigDetailsDTO cmnLeavePlanConfigDetailsDto) {
		if(cmnLeavePlanConfigDetailsDto != null) {
			CmnLeavePlanConfigDetailsEntity tempCmnConfig = cmnLeavePlanConfigDetailsRepository.getCmnleavePlansByCmnLeavePlanConfigDetails(cmnLeavePlanConfigDetailsDto.getMinValue(),cmnLeavePlanConfigDetailsDto.getMaxValue(), cmnLeavePlanConfigDetailsDto.getValue(),cmnLeavePlanConfigDetailsDto.getConfigurationId(), cmnLeavePlanConfigDetailsDto.getLeaveTypeId());
			if(tempCmnConfig != null && cmnLeavePlanConfigDetailsDto.getCmnLvPlnConfigDetlsId() != null && cmnLeavePlanConfigDetailsDto.getCmnLvPlnConfigDetlsId() == 0)
				return true;
			else if(tempCmnConfig != null && cmnLeavePlanConfigDetailsDto.getCmnLvPlnConfigDetlsId() != null && cmnLeavePlanConfigDetailsDto.getCmnLvPlnConfigDetlsId() > 0 && tempCmnConfig.getCmnLvPlnConfigDetlsId().longValue() != cmnLeavePlanConfigDetailsDto.getCmnLvPlnConfigDetlsId().longValue())
				return true;
			else 
				return false;
		} else {
			return true;
		}
	}

	@Override
	public CmnLeavePlanConfigDetailsResultSet deleteLeavePlanByCmnlvplnConfigdeltsId(Long CmnlvplnConfigdeltsId) {
		CmnLeavePlanConfigDetailsResultSet cmnleaveplandetResultSet = new CmnLeavePlanConfigDetailsResultSet();
		try {
			if(CmnlvplnConfigdeltsId != null) {
				CmnLeavePlanConfigDetailsEntity leaveplanEntity = cmnLeavePlanConfigDetailsRepository.getLeavePlanConfigBycmnlvdetId(CmnlvplnConfigdeltsId);
				delete(leaveplanEntity);
				cmnleaveplandetResultSet.setMessageDescription("Record deleted successfully.");
			} else {
				cmnleaveplandetResultSet.setStatus(false);
				cmnleaveplandetResultSet.setMessage("Failed");
				cmnleaveplandetResultSet.setMessageDescription("Invalid Inputs.");
			}
		} catch (Exception e) {
			cmnleaveplandetResultSet.setStatus(false);
			cmnleaveplandetResultSet.setMessage("Exception");
			cmnleaveplandetResultSet.setMessageDescription(e.getMessage());
		}
		return cmnleaveplandetResultSet;
	}

	@Override
	public CmnLeavePlanConfigDetailsResultSet deleteLeavePlans(List<CmnLeavePlanConfigDetailsDTO> cmnLeavePlanConfigDetailsDto) {
		CmnLeavePlanConfigDetailsResultSet cmlvplandetRS = new CmnLeavePlanConfigDetailsResultSet();
		int successCount = 0;
		int failureCount = 0;
		try {
			if(cmnLeavePlanConfigDetailsDto != null && cmnLeavePlanConfigDetailsDto.size() > 0) {
				for(CmnLeavePlanConfigDetailsDTO cmleaveplanconfig : cmnLeavePlanConfigDetailsDto) {
					CmnLeavePlanConfigDetailsEntity leaveplanEntity = cmnLeavePlanConfigDetailsRepository.getLeavePlanConfigBycmnlvdetId(cmleaveplanconfig.getCmnLvPlnConfigDetlsId());
					if(leaveplanEntity != null) {
							delete(leaveplanEntity);
							successCount++;
					} else {
						failureCount++;
					}
				}
				if(failureCount > 0) {
					cmlvplandetRS.setMessageDescription(successCount + " rows deleted and failed to delete " + failureCount + " rows.");
				} else {
					cmlvplandetRS.setMessageDescription(successCount + " row deleted successfully.");
				}
				cmlvplandetRS.setSuccessCount(successCount);
				cmlvplandetRS.setFailureCount(failureCount);
			} else {
				cmlvplandetRS.setStatus(false);
				cmlvplandetRS.setMessage("Failed");
				cmlvplandetRS.setMessageDescription("Invalid Inputs");
			}
		} catch(Exception e) {
			cmlvplandetRS.setStatus(false);
			cmlvplandetRS.setMessage("Exception");
			cmlvplandetRS.setMessageDescription("Excepton occured while deleting the Leave plans. Please contact Administrator");
		}
		return cmlvplandetRS;
	}

}
