package org.vrnda.hrms.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.vrnda.hrms.entity.CmnLeaveTypesMstEntity;
import org.vrnda.hrms.repository.CmnLeaveConfigDetailsRepository;
import org.vrnda.hrms.repository.CmnLeavePlanConfigDetailsRepository;
import org.vrnda.hrms.repository.CmnLeaveTypesMstRepository;
import org.vrnda.hrms.repository.EmployeeLeaveDetailsRepository;
import org.vrnda.hrms.repository.EmployeeLeavesMstRepository;
import org.vrnda.hrms.repository.dto.CmnLeaveTypesMstDTO;
import org.vrnda.hrms.service.CmnLeaveTypesMstService;
import org.vrnda.hrms.service.CmnTableSeqService;
import org.vrnda.hrms.service.CmnYearsMstService;
import org.vrnda.hrms.service.resultset.CmnLeaveTypesMstResultSet;
import org.vrnda.hrms.utils.ApplicationConstants;
import org.vrnda.hrms.utils.BaseUtils;

@Service
public class CmnLeaveTypesMstServiceImpl extends GenericServiceImpl<CmnLeaveTypesMstEntity, String> implements CmnLeaveTypesMstService {

	public CmnLeaveTypesMstServiceImpl(PagingAndSortingRepository<CmnLeaveTypesMstEntity, String> typeRepository) {
		super(typeRepository);
	}
	
	@Autowired
	CmnLeaveTypesMstRepository cmnLeaveTypesMstRepository;
	
	@Autowired
	CmnLeaveConfigDetailsRepository cmnLeaveConfigDetailsRepository;
	
	@Autowired
	CmnLeavePlanConfigDetailsRepository cmnLeavePlanConfigDetailsRepository;
	
	@Autowired
	EmployeeLeavesMstRepository employeeLeavesMstRepository;

	@Autowired
	EmployeeLeaveDetailsRepository employeeLeaveDetailsRepository;
	
	@Autowired
	CmnTableSeqService cmnTableSeqService;
	
	@Autowired
	CmnYearsMstService cmnYearsMstService;

	@Override
	public CmnLeaveTypesMstResultSet getAllLeaveTypes() {
		CmnLeaveTypesMstResultSet cmnLeaveTypesMstResultSet = new CmnLeaveTypesMstResultSet();
		try {
			List<CmnLeaveTypesMstDTO> cmnLookupMstDtoList = new ArrayList<CmnLeaveTypesMstDTO>();
			List<CmnLeaveTypesMstEntity> leaveTypesEntityList=cmnLeaveTypesMstRepository.getAllLeaveTypes();
			if(leaveTypesEntityList!=null&&leaveTypesEntityList.size()>0) {
				leaveTypesEntityList.forEach(obj->
				{
					CmnLeaveTypesMstDTO leavetypedto=new CmnLeaveTypesMstDTO();
					BeanUtils.copyProperties(obj,leavetypedto);
					cmnLookupMstDtoList.add(leavetypedto);
				});
				
				cmnLeaveTypesMstResultSet.setCmnLeaveTypesMstDtoList(cmnLookupMstDtoList);	
			}
			else {
				cmnLeaveTypesMstResultSet.setStatus(false);
				cmnLeaveTypesMstResultSet.setMessage("Failed");
				cmnLeaveTypesMstResultSet.setMessageDescription("Leave Types data is empty");
			}
		} catch (Exception e) {
			cmnLeaveTypesMstResultSet.setStatus(false);
			cmnLeaveTypesMstResultSet.setMessage("Error");
			cmnLeaveTypesMstResultSet.setMessageDescription(e.getMessage());
		}

		return cmnLeaveTypesMstResultSet;
	}

	@Override
	public CmnLeaveTypesMstResultSet saveOrUpdateLookup(CmnLeaveTypesMstDTO cmnLeaveTypesMstdto, String loggedInUser) {
		CmnLeaveTypesMstResultSet cmnLeaveTypesMstResultSet = new CmnLeaveTypesMstResultSet();
		try {
			if(cmnLeaveTypesMstdto !=null) {
				if(verifyDuplicateLeaveTypes(cmnLeaveTypesMstdto)) {
					cmnLeaveTypesMstResultSet.setStatus(false);
					cmnLeaveTypesMstResultSet.setMessage("Failed");
					cmnLeaveTypesMstResultSet.setMessageDescription("Records already exists with Same Name .");
					return cmnLeaveTypesMstResultSet;
				}
				if(cmnLeaveTypesMstdto.getLeaveTypeId()==0) {
					CmnLeaveTypesMstEntity cmnLeaveTypesMstEntity = new CmnLeaveTypesMstEntity();
					BeanUtils.copyProperties(cmnLeaveTypesMstdto, cmnLeaveTypesMstEntity);
					cmnLeaveTypesMstEntity.setLeaveTypeId(cmnTableSeqService.getNextSequence(ApplicationConstants.CMN_LEAVE_TYPES_MST, ApplicationConstants.LEAVE_TYPE_ID));
					BaseUtils.setBaseData(cmnLeaveTypesMstEntity, loggedInUser);
					save(cmnLeaveTypesMstEntity);
					cmnLeaveTypesMstResultSet.setMessageDescription("Record Saved Successfully."); 
				} else if(cmnLeaveTypesMstdto != null && cmnLeaveTypesMstdto.getLeaveTypeId() > 0) {
					CmnLeaveTypesMstEntity cmnLeaveTypesMstEntity = cmnLeaveTypesMstRepository.getLeaveTypeByLeaveTypeId(cmnLeaveTypesMstdto.getLeaveTypeId());
					if(cmnLeaveTypesMstEntity != null) {
						BeanUtils.copyProperties(cmnLeaveTypesMstdto, cmnLeaveTypesMstEntity);
						BaseUtils.modifyBaseData(cmnLeaveTypesMstEntity, loggedInUser);
						save(cmnLeaveTypesMstEntity);
						cmnLeaveTypesMstResultSet.setMessageDescription("Leave Type updated successfully.");
					}
				} else {
					cmnLeaveTypesMstResultSet.setStatus(false);
					cmnLeaveTypesMstResultSet.setMessage("Error");
					cmnLeaveTypesMstResultSet.setMessageDescription("Invalid Inputs.");
				}
			}
		} catch (Exception e) {
			cmnLeaveTypesMstResultSet.setStatus(false);
			cmnLeaveTypesMstResultSet.setMessage("Error");
			cmnLeaveTypesMstResultSet.setMessageDescription(e.getMessage());
		}
		return cmnLeaveTypesMstResultSet;
	}
	
	public boolean verifyDuplicateLeaveTypes(CmnLeaveTypesMstDTO cmnLeaveTypesMstDto) {
		if(cmnLeaveTypesMstDto != null) {
			CmnLeaveTypesMstEntity tempCmnLookup = cmnLeaveTypesMstRepository.getLeaveTypeByLeaveTypeName(cmnLeaveTypesMstDto.getLeaveTypeName());
			if(tempCmnLookup != null && cmnLeaveTypesMstDto.getLeaveTypeId() != null && cmnLeaveTypesMstDto.getLeaveTypeId() == 0)
				return true;
			else if(tempCmnLookup != null && cmnLeaveTypesMstDto.getLeaveTypeId() != null && cmnLeaveTypesMstDto.getLeaveTypeId() > 0 && tempCmnLookup.getLeaveTypeId() != cmnLeaveTypesMstDto.getLeaveTypeId().longValue())
				return true;
			else 
				return false;
		} else {
			return true;
		}
	}
	
	@Override
	public Map<String, Long> getLeaveTypeNameAndIdLeaveTypes(Long YearId) {
		CmnLeaveTypesMstResultSet cmnLeaveTypesMstResultSet = getLeaveIdIdAndLeaveTypeNameLeaveTypes(YearId);
		Map<String, Long> leavetypeMap = null;
		if (cmnLeaveTypesMstResultSet != null && cmnLeaveTypesMstResultSet.getCmnLeaveTypesMstDtoList() != null && cmnLeaveTypesMstResultSet.getCmnLeaveTypesMstDtoList().size() > 0) {
			leavetypeMap = new HashMap<String, Long>();
			for (CmnLeaveTypesMstDTO cmnLeaveTypesDTO : cmnLeaveTypesMstResultSet.getCmnLeaveTypesMstDtoList()) {
				leavetypeMap.put(cmnLeaveTypesDTO.getLeaveTypeName(), cmnLeaveTypesDTO.getLeaveTypeId());
			}
		}
		return leavetypeMap;
	}
	
	@Override
	public Map<Long, String> getIdAndLeaveTypeNameLeaveTypes(Long YearId) {
		CmnLeaveTypesMstResultSet cmnLeaveTypesMstResultSet = getLeaveIdIdAndLeaveTypeNameLeaveTypes(YearId);
		Map<Long, String> leavetypeMap = null;
		if (cmnLeaveTypesMstResultSet != null && cmnLeaveTypesMstResultSet.getCmnLeaveTypesMstDtoList() != null && cmnLeaveTypesMstResultSet.getCmnLeaveTypesMstDtoList().size() > 0) {
			leavetypeMap = new HashMap<Long, String>();
			for (CmnLeaveTypesMstDTO cmnLeaveTypesDTO : cmnLeaveTypesMstResultSet.getCmnLeaveTypesMstDtoList()) {
				leavetypeMap.put(cmnLeaveTypesDTO.getLeaveTypeId(),cmnLeaveTypesDTO.getLeaveTypeDescription());
			}
		}
		return leavetypeMap;
	}
	
	
	public CmnLeaveTypesMstResultSet getLeaveIdIdAndLeaveTypeNameLeaveTypes(Long YearId) {
		CmnLeaveTypesMstResultSet cmnLeaveTypesMstResultSet = new CmnLeaveTypesMstResultSet();
		try {
			if(YearId != null) {
				List<CmnLeaveTypesMstDTO> cmnLeaveTypesDtoList = new ArrayList<CmnLeaveTypesMstDTO>();
				List<Object[]> leaveIdAndNameList = cmnLeaveTypesMstRepository.getLeaveTypeIdAndLeaveTypeNameAndLeaveTypeDescription();
				for (Object[] leaveIdAndName : leaveIdAndNameList) {
					if (leaveIdAndName != null) {
						CmnLeaveTypesMstDTO cmnLeaveTypesMstDTO = new CmnLeaveTypesMstDTO();
						cmnLeaveTypesMstDTO.setLeaveTypeName(leaveIdAndName[0].toString());
						cmnLeaveTypesMstDTO.setLeaveTypeId(Long.parseLong(leaveIdAndName[1].toString()));
						cmnLeaveTypesMstDTO.setLeaveTypeDescription(leaveIdAndName[2].toString());
						cmnLeaveTypesDtoList.add(cmnLeaveTypesMstDTO);
					}
					cmnLeaveTypesMstResultSet.setCmnLeaveTypesMstDtoList(cmnLeaveTypesDtoList);
				}
			} else {
				cmnLeaveTypesMstResultSet.setStatus(false);
				cmnLeaveTypesMstResultSet.setMessage("Failed");
				cmnLeaveTypesMstResultSet.setMessageDescription("Invalid Inputs");
			}
		} catch (Exception e) {
			cmnLeaveTypesMstResultSet.setStatus(false);
			cmnLeaveTypesMstResultSet.setMessage("Error");
			cmnLeaveTypesMstResultSet.setMessageDescription(e.getMessage());
		}
		return cmnLeaveTypesMstResultSet;
	}

}
