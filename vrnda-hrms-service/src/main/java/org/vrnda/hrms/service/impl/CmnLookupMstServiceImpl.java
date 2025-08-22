package org.vrnda.hrms.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.vrnda.hrms.entity.CmnLookupMstEntity;
import org.vrnda.hrms.repository.CmnLookupMstRepository;
import org.vrnda.hrms.repository.dto.CmnLookupMstDTO;
import org.vrnda.hrms.service.CmnLookupMstService;
import org.vrnda.hrms.service.CmnTableSeqService;
import org.vrnda.hrms.service.resultset.CmnLookupMstResultSet;
import org.vrnda.hrms.utils.ApplicationConstants;
import org.vrnda.hrms.utils.BaseUtils;

@Service
public class CmnLookupMstServiceImpl extends GenericServiceImpl<CmnLookupMstEntity, String> implements CmnLookupMstService {

	public CmnLookupMstServiceImpl(PagingAndSortingRepository<CmnLookupMstEntity, String> typeRepository) {
		super(typeRepository);
	}

	@Autowired
	CmnLookupMstRepository cmnLookupMstRepositoy;

	@Autowired
	CmnTableSeqService cmnTableSeqService;

	@Override	
	public CmnLookupMstResultSet saveOrUpdateLookup(CmnLookupMstDTO cmnLookupMstDto, String loggedInUser) {
		CmnLookupMstResultSet cmnLookupMstResultset = new CmnLookupMstResultSet();
		try {
			if(cmnLookupMstDto !=null) {
				if(verifyDuplicateLookup(cmnLookupMstDto)) {
					cmnLookupMstResultset.setStatus(false);
					cmnLookupMstResultset.setMessage("Warning");
					cmnLookupMstResultset.setMessageDescription("Records already exists in ");
					return cmnLookupMstResultset;
				}
				if(cmnLookupMstDto.getLookupId()==0) {
					CmnLookupMstEntity cmnLookupMstEntity = new CmnLookupMstEntity();
					if(cmnLookupMstDto.getParentLookupType()!=null) {
						Long parentLookupId=cmnLookupMstRepositoy.getLookupByLookupName(cmnLookupMstDto.getParentLookupType()).getLookupId();
						cmnLookupMstDto.setParentLookupId(parentLookupId);
					}
					BeanUtils.copyProperties(cmnLookupMstDto, cmnLookupMstEntity);
					Integer orderNum = cmnLookupMstRepositoy.getMaxOrderNoByParentLookupId(cmnLookupMstDto.getParentLookupId());
					if(orderNum!=null && orderNum > 0) {
						orderNum++;
					} else {
						orderNum = 1;
					}
					
					cmnLookupMstEntity.setOrderNo(orderNum);
					cmnLookupMstEntity.setLookupId(cmnTableSeqService.getNextSequence(ApplicationConstants.CMN_LOOKUP_MST, ApplicationConstants.LOOKUP_ID));
					BaseUtils.setBaseData(cmnLookupMstEntity, loggedInUser);
					save(cmnLookupMstEntity);
					cmnLookupMstResultset.setMessageDescription("Record Saved Successfully."); 
				} else if(cmnLookupMstDto != null && cmnLookupMstDto.getLookupId() > 0) {
					CmnLookupMstEntity cmnLookupMstEntity = cmnLookupMstRepositoy.getLookupByLookupId(cmnLookupMstDto.getLookupId());
					if(cmnLookupMstEntity != null) {
						BeanUtils.copyProperties(cmnLookupMstDto, cmnLookupMstEntity);
						BaseUtils.modifyBaseData(cmnLookupMstEntity, loggedInUser);
						save(cmnLookupMstEntity);
						cmnLookupMstResultset.setMessageDescription("Record updated successfully.");
					}
				} else {
					cmnLookupMstResultset.setStatus(false);
					cmnLookupMstResultset.setMessage("Error");
					cmnLookupMstResultset.setMessageDescription("Invalid Inputs.");
				}
			}
		} catch (Exception e) {
			cmnLookupMstResultset.setStatus(false);
			cmnLookupMstResultset.setMessage("Error");
			cmnLookupMstResultset.setMessageDescription(e.getMessage());
		}
		return cmnLookupMstResultset;
	}

	@Override
	public CmnLookupMstResultSet getLookupIdAndNameListByParentLookupName(String parentLookupName) {
		CmnLookupMstResultSet cmnLookupMstResultset = new CmnLookupMstResultSet();
		try {
			if(parentLookupName != null) {
				List<CmnLookupMstDTO> cmnLookupMstDtoList = new ArrayList<CmnLookupMstDTO>();
				List<Object[]> lookupIdAndNameList = cmnLookupMstRepositoy.getLookupIdAndNameListByParentLookupName(parentLookupName);
				for (Object[] lookupIdAndName : lookupIdAndNameList) {
					if (lookupIdAndName != null) {
						CmnLookupMstDTO cmnLookupMstDto = new CmnLookupMstDTO();
						cmnLookupMstDto.setLookupId(Long.parseLong(lookupIdAndName[0].toString()));
						cmnLookupMstDto.setLookupName(lookupIdAndName[1].toString());
						if(lookupIdAndName.length>2 && lookupIdAndName[2] != null) {
							cmnLookupMstDto.setLookupDesc(lookupIdAndName[2].toString());
						}else{
							cmnLookupMstDto.setLookupDesc(lookupIdAndName[1].toString());
						}
						cmnLookupMstDtoList.add(cmnLookupMstDto);
					}
					cmnLookupMstResultset.setCmnLookupMstDtoList(cmnLookupMstDtoList);
				}
			} else {
				cmnLookupMstResultset.setStatus(false);
				cmnLookupMstResultset.setMessage("Failed");
				cmnLookupMstResultset.setMessageDescription("Invalid Inputs");
			}
		} catch (Exception e) {
			cmnLookupMstResultset.setStatus(false);
			cmnLookupMstResultset.setMessage("Error");
			cmnLookupMstResultset.setMessageDescription(e.getMessage());
		}
		return cmnLookupMstResultset;
	}

	@Override
	public CmnLookupMstResultSet getLookupIdAndNameListByParentLookupNameAndParentLookupId(String parentLookupName, Long parentLookupId) {
		CmnLookupMstResultSet cmnLookupMstResultset = new CmnLookupMstResultSet();
		try {
			List<CmnLookupMstDTO> cmnLookupMstDtoList = new ArrayList<CmnLookupMstDTO>();
			List<Object[]> lookupIdAndNameList = cmnLookupMstRepositoy.getLookupIdAndNameListByParentLookupNameAndParentLookupId(parentLookupName,parentLookupId);
			for (Object[] lookupIdAndName : lookupIdAndNameList) {
				if (lookupIdAndName != null) {
					CmnLookupMstDTO cmnLookupMstDto = new CmnLookupMstDTO();
					cmnLookupMstDto.setLookupId(Long.parseLong(lookupIdAndName[0].toString()));
					cmnLookupMstDto.setLookupName(lookupIdAndName[1].toString());
					if(lookupIdAndName.length>2 && lookupIdAndName[2] != null) {
						cmnLookupMstDto.setLookupDesc(lookupIdAndName[2].toString());
					}else{
						cmnLookupMstDto.setLookupDesc(lookupIdAndName[1].toString());
					}
					cmnLookupMstDtoList.add(cmnLookupMstDto);
				}
				cmnLookupMstResultset.setCmnLookupMstDtoList(cmnLookupMstDtoList);
			}
		} catch (Exception e) {
			cmnLookupMstResultset.setStatus(false);
			cmnLookupMstResultset.setMessage("Error");
			cmnLookupMstResultset.setMessageDescription(e.getMessage());
		}

		return cmnLookupMstResultset;
	}

	@Override
	public Long getLookupIdByLookupNameAndParentLookupName(String lookupName, String parentLookupName) {
		Long lookupId = null;
		try {
			lookupId = cmnLookupMstRepositoy.getLookupIdByLookupNameAndParentLookupName(lookupName, parentLookupName);
		} catch(Exception e) {
			lookupId = 0l;
			e.printStackTrace();
		}
		return lookupId;
	}

	@Override
	public CmnLookupMstResultSet getLookupByLookupName(String lookupName) {
		CmnLookupMstResultSet cmnLookupMstRS = new CmnLookupMstResultSet();
		try {
			if(lookupName != null) {
				CmnLookupMstEntity cmnLookMstEntity = cmnLookupMstRepositoy.getLookupByLookupName(lookupName);
				cmnLookupMstRS.setCmnLookupMstEntity(cmnLookMstEntity);
			} else {
				cmnLookupMstRS.setStatus(false);
				cmnLookupMstRS.setMessage("Failed");
				cmnLookupMstRS.setMessageDescription("Invalid Inputs");
			}
		} catch (Exception e) {
			cmnLookupMstRS.setStatus(false);
			cmnLookupMstRS.setMessage("Error");
			cmnLookupMstRS.setMessageDescription(e.getMessage());
		}
		return cmnLookupMstRS;
	}

	@Override
	public CmnLookupMstResultSet getLookupByParentLookupName(String parentLookupName){
		CmnLookupMstResultSet cmnLookupMstResultset = new CmnLookupMstResultSet();
		List<CmnLookupMstEntity> CmnLookupMstList = cmnLookupMstRepositoy.getLookupByParentLookupName(parentLookupName);
		if(CmnLookupMstList != null) {
			cmnLookupMstResultset.setCmnLookupMstEntityList(CmnLookupMstList);
		}
		return cmnLookupMstResultset;
	}

	@Override
	public CmnLookupMstResultSet deleteLookupByLookupId(Long lookupId) {
		CmnLookupMstResultSet cmnLookupMstResultSet = new CmnLookupMstResultSet();
		try {
			if(lookupId != null) {
				CmnLookupMstEntity cmnLookupMstEntity = cmnLookupMstRepositoy.getLookupByLookupId(lookupId);
				delete(cmnLookupMstEntity);
				cmnLookupMstResultSet.setMessageDescription("Record deleted successfully.");
			} else {
				cmnLookupMstResultSet.setStatus(false);
				cmnLookupMstResultSet.setMessage("Failed");
				cmnLookupMstResultSet.setMessageDescription("Invalid Inputs.");
			}
		} catch (Exception e) {
			cmnLookupMstResultSet.setStatus(false);
			cmnLookupMstResultSet.setMessage("Exception");
			cmnLookupMstResultSet.setMessageDescription(e.getMessage());
		}
		return cmnLookupMstResultSet;
	}

	@Override
	public CmnLookupMstResultSet deleteLookupByLookup(CmnLookupMstDTO cmnLookupMstDto) {
		CmnLookupMstResultSet cmnLookupMstResultSet = new CmnLookupMstResultSet();
		try {
			if(cmnLookupMstDto != null) {
				CmnLookupMstEntity cmnLookupMstEntity = cmnLookupMstRepositoy.getLookupByLookupId(cmnLookupMstDto.getLookupId());
				if (cmnLookupMstEntity != null) {
					delete(cmnLookupMstEntity);
					cmnLookupMstResultSet.setMessageDescription("Record deleted successfully.");
				} else {
					cmnLookupMstResultSet.setStatus(false);
					cmnLookupMstResultSet.setMessage("Failed");
					cmnLookupMstResultSet.setMessageDescription("Invalid Inputs.");
				}
			} else {
				cmnLookupMstResultSet.setStatus(false);
				cmnLookupMstResultSet.setMessage("Failed");
				cmnLookupMstResultSet.setMessageDescription("Invalid Inputs.");
			}
		} catch (Exception e) {
			cmnLookupMstResultSet.setStatus(false);
			cmnLookupMstResultSet.setMessage("Exception");
			cmnLookupMstResultSet.setMessageDescription(e.getMessage());
		}
		return cmnLookupMstResultSet;
	}

	@Override
	public CmnLookupMstResultSet deleteLookupByLookupList(List<CmnLookupMstDTO> cmnLookupMstDtoList) {
		CmnLookupMstResultSet cmnLookupMstResultSet = new CmnLookupMstResultSet();
		int successCount = 0;
		int failureCount = 0;
		try {
			if(cmnLookupMstDtoList != null && cmnLookupMstDtoList.size() > 0) {
				for(CmnLookupMstDTO cmnLookupMstDto : cmnLookupMstDtoList) {
					CmnLookupMstEntity cmnLookupMstEntity = cmnLookupMstRepositoy.getLookupByLookupId(cmnLookupMstDto.getLookupId());
					if(cmnLookupMstEntity != null) {
						delete(cmnLookupMstEntity);
						successCount++;
					} else {
						failureCount++;
					}
				}
				if(failureCount > 0) {
					cmnLookupMstResultSet.setMessageDescription(successCount + " rows deleted and failed to delete " + failureCount + " rows.");
				} else {
					cmnLookupMstResultSet.setMessageDescription(successCount + " row deleted successfully.");
				}
				cmnLookupMstResultSet.setSuccessCount(successCount);
			} else {
				cmnLookupMstResultSet.setStatus(false);
				cmnLookupMstResultSet.setMessage("Failed");
				cmnLookupMstResultSet.setMessageDescription("Invalid Inputs.");
			}
		} catch (Exception e) {
			cmnLookupMstResultSet.setStatus(false);
			cmnLookupMstResultSet.setMessage("Error");
			cmnLookupMstResultSet.setMessageDescription("Exception while deleting records. Contact Administrator.");
		}
		return cmnLookupMstResultSet;
	}

	@Override
	public Map<String, Long> getLookupNameAndLookupIdMapingsByParentLookupName(String parentLookupName) {
		CmnLookupMstResultSet cmnLookupMstResultset = getLookupIdAndNameListByParentLookupName(parentLookupName);
		Map<String, Long> lookupNameIdMap = null;
		if (cmnLookupMstResultset != null && cmnLookupMstResultset.getCmnLookupMstDtoList() != null && cmnLookupMstResultset.getCmnLookupMstDtoList().size() > 0) {
			lookupNameIdMap = new HashMap<String, Long>();
			for (CmnLookupMstDTO cmnLookupMstDto : cmnLookupMstResultset.getCmnLookupMstDtoList()) {
				lookupNameIdMap.put(cmnLookupMstDto.getLookupName(), cmnLookupMstDto.getLookupId());
			}
		}
		return lookupNameIdMap;
	}

	@Override
	public Map<Long, String> getLookupIdAndLookupNameMapingsByParentLookupName(String parentLookupName) {
		CmnLookupMstResultSet cmnLookupMstResultset = getLookupIdAndNameListByParentLookupName(parentLookupName);
		Map<Long, String> lookupYearMonthMap = null;
		if (cmnLookupMstResultset != null && cmnLookupMstResultset.getCmnLookupMstDtoList() != null && cmnLookupMstResultset.getCmnLookupMstDtoList().size() > 0) {
			lookupYearMonthMap = new HashMap<Long, String>();
			for (CmnLookupMstDTO cmnLookupMstDto : cmnLookupMstResultset.getCmnLookupMstDtoList()) {
				lookupYearMonthMap.put(cmnLookupMstDto.getLookupId(), cmnLookupMstDto.getLookupName());
			}
		}
		return lookupYearMonthMap;
	}

	@Override
	public boolean verifyDuplicateLookup(CmnLookupMstDTO cmnLookupMstDto) {
		if(cmnLookupMstDto != null) {
			CmnLookupMstEntity tempCmnLookup = cmnLookupMstRepositoy.getLookupByLookupNameandParentLookupId(cmnLookupMstDto.getLookupName(), cmnLookupMstDto.getParentLookupType());
			if(tempCmnLookup != null && cmnLookupMstDto.getLookupId() != null && cmnLookupMstDto.getLookupId() == 0)
				return true;
			else if(tempCmnLookup != null && cmnLookupMstDto.getLookupId() != null && cmnLookupMstDto.getLookupId() > 0 && tempCmnLookup.getLookupId() != cmnLookupMstDto.getLookupId().longValue())
				return true;
			else 
				return false;
		} else {
			return true;
		}
	}

	@Override
	public CmnLookupMstResultSet getLookupDetailsByLookupName(String lookupName) {
		CmnLookupMstResultSet cmnLookupMstResultset = new CmnLookupMstResultSet();
		List<CmnLookupMstEntity> cmnLookupMstEntityList = cmnLookupMstRepositoy
				.getLookupDetailsByLookupName(lookupName);
		if (cmnLookupMstEntityList != null) {
			cmnLookupMstResultset.setCmnLookupMstEntityList(cmnLookupMstEntityList);
		}
		return cmnLookupMstResultset;
	}

}
