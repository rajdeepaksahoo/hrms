package org.vrnda.hrms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.vrnda.hrms.entity.CmnConfigurationsMstEntity;
import org.vrnda.hrms.entity.CmnLeaveTypesMstEntity;
import org.vrnda.hrms.entity.EmployeeCompOffLeaveMstEntity;
import org.vrnda.hrms.repository.EmployeeCompOffLeaveMstRepository;
import org.vrnda.hrms.repository.dto.CmnLeaveTypesMstDTO;
import org.vrnda.hrms.repository.dto.EmployeeCompOffLeaveMstDTO;
import org.vrnda.hrms.service.CmnTableSeqService;
import org.vrnda.hrms.service.EmployeeCompOffLeaveMstService;
import org.vrnda.hrms.service.resultset.EmployeeCompOffLeaveMstResultSet;
import org.vrnda.hrms.utils.ApplicationConstants;
import org.vrnda.hrms.utils.BaseUtils;

@Service
public class EmployeeCompOffLeaveMstServiceImpl extends GenericServiceImpl<EmployeeCompOffLeaveMstEntity, Long>
		implements EmployeeCompOffLeaveMstService {

	@Autowired
	EmployeeCompOffLeaveMstRepository employeeCompOffLeaveMstRepository;
	
	@Autowired
	CmnTableSeqService cmnTableSeqService;

	public EmployeeCompOffLeaveMstServiceImpl(
			PagingAndSortingRepository<EmployeeCompOffLeaveMstEntity, Long> typeRepository) {
		super(typeRepository);
		// TODO Auto-generated constructor stub
	}

	public EmployeeCompOffLeaveMstResultSet getEmployeeCompOffLeavesDetails(Long employeeId) {
		EmployeeCompOffLeaveMstResultSet employeeCompOffLeaveMstRS = new EmployeeCompOffLeaveMstResultSet();

		try {
			List<EmployeeCompOffLeaveMstDTO> compoffDtoList = new ArrayList<EmployeeCompOffLeaveMstDTO>();
            List<EmployeeCompOffLeaveMstEntity> compoffEntityList=employeeCompOffLeaveMstRepository.getCompOffLeavesByEmployeeId(employeeId);
			if(compoffEntityList!=null&&compoffEntityList.size()>0) {
				compoffEntityList.forEach(obj->
				{
					EmployeeCompOffLeaveMstDTO compoffdto=new EmployeeCompOffLeaveMstDTO();
					BeanUtils.copyProperties(obj,compoffdto);
					compoffDtoList.add(compoffdto);
				});
				
				employeeCompOffLeaveMstRS.setEmployeeCompOffLeaveMstDtoList(compoffDtoList);	
			}
			else {
				employeeCompOffLeaveMstRS.setStatus(false);
				employeeCompOffLeaveMstRS.setMessage("Failed");
				employeeCompOffLeaveMstRS.setMessageDescription("No Comp off leaves available.");
			}
		} catch (Exception e) {
			employeeCompOffLeaveMstRS.setStatus(false);
			employeeCompOffLeaveMstRS.setMessage("Error");
			employeeCompOffLeaveMstRS.setMessageDescription(e.getMessage());
		}
		return employeeCompOffLeaveMstRS;
	}

	@Override
	public EmployeeCompOffLeaveMstResultSet saveOrUpdateCompoffLeaves(EmployeeCompOffLeaveMstDTO compoffleavesdto,String logginUser) {
		EmployeeCompOffLeaveMstResultSet employeeCompOffLeaveMstResultSet = new EmployeeCompOffLeaveMstResultSet();
		try {
			if (compoffleavesdto.getEmpCoLvMstId() == null) {
				EmployeeCompOffLeaveMstEntity compoffEntity2 = employeeCompOffLeaveMstRepository
						.getCompOffLeavesByEmployeeIdFromandToDate(compoffleavesdto.getEmployeeId(),
								compoffleavesdto.getFromDate(), compoffleavesdto.getToDate());
				if (compoffEntity2 != null) {
					employeeCompOffLeaveMstResultSet.setStatus(false);
					employeeCompOffLeaveMstResultSet.setMessage("Failed");
					employeeCompOffLeaveMstResultSet
							.setMessageDescription("CompOff Leaves Already Exist with same Data");
					return employeeCompOffLeaveMstResultSet;
				}
				EmployeeCompOffLeaveMstEntity employeecompoffEntity = new EmployeeCompOffLeaveMstEntity();
				BeanUtils.copyProperties(compoffleavesdto, employeecompoffEntity);
				
				employeecompoffEntity.setEmpCoLvMstId(cmnTableSeqService.getNextSequence(
						ApplicationConstants.EMPLOYEE_COMPOFF_LEAVE_MST, ApplicationConstants.EMP_CO_LV_MST_ID));
				employeecompoffEntity.setCompoffUsed("N");
				BaseUtils.setBaseData(employeecompoffEntity, logginUser);
				save(employeecompoffEntity);
				employeeCompOffLeaveMstResultSet.setMessageDescription("Record Saved Successfully.");
			}
//			if (commitBean != null && commitBean.getEmpCompOffInsertList() != null
//					&& !commitBean.getEmpCompOffInsertList().isEmpty()) {
//				Long lookupId = employeeCompOffLeaveMstRepository.getLookupId();
//				for (EmployeeCompOffLeaveMstDTO obj : commitBean.getEmpCompOffInsertList()) {
//					obj.setEmpCoLvMstId(cmnTableSeqService.getNextSequence(ApplicationConstants.EMPLOYEE_COMPOFF_LEAVE_MST, ApplicationConstants.EMP_CO_LV_MST_ID));
//					obj.setLeaveTypeLookupId(lookupId);
//					EmployeeCompOffLeaveMstEntity insEntity = new EmployeeCompOffLeaveMstEntity();
//					BeanUtils.copyProperties(obj, insEntity);
//					BaseUtils.setBaseData(insEntity, obj.getStatusLookupId().longValue());
//					entityList.add(insEntity);
//				}
//				saveAll(entityList);
//			}
//
//			if (commitBean != null && commitBean.getEmpCompOffUpdatetList() != null
//					&& !commitBean.getEmpCompOffUpdatetList().isEmpty()) {
//				entityList = new ArrayList<EmployeeCompOffLeaveMstEntity>();
//				for (EmployeeCompOffLeaveMstDTO obj : commitBean.getEmpCompOffUpdatetList()) {
//					EmployeeCompOffLeaveMstEntity insEntity = new EmployeeCompOffLeaveMstEntity();
//					BeanUtils.copyProperties(obj, insEntity);
//					BaseUtils.setBaseData(insEntity, obj.getStatusLookupId().longValue());
//					entityList.add(insEntity);
//				}
//				saveAll(entityList);
//			}
//			
//			if (commitBean != null && commitBean.getEmpCompOffDeleteList() != null
//					&& !commitBean.getEmpCompOffDeleteList().isEmpty()) {
//				entityList = new ArrayList<EmployeeCompOffLeaveMstEntity>();
//				for (EmployeeCompOffLeaveMstDTO obj : commitBean.getEmpCompOffDeleteList()) {
//					EmployeeCompOffLeaveMstEntity insEntity = new EmployeeCompOffLeaveMstEntity();
//					BeanUtils.copyProperties(obj, insEntity);
//					BaseUtils.setBaseData(insEntity, obj.getStatusLookupId().longValue());
//					entityList.add(insEntity);
//				}
//				deleteAll(entityList);
//			}

		} catch (Exception e) {
			employeeCompOffLeaveMstResultSet.setStatus(false);
			employeeCompOffLeaveMstResultSet.setMessage("Unable to save.");
			employeeCompOffLeaveMstResultSet.setMessageDescription(e.getMessage());
		}
		return employeeCompOffLeaveMstResultSet;
	}

//	@Override
//	public List<EmployeeCompOffLeaveMstEntity> getLeaveApprovalsData() {
//		List<EmployeeCompOffLeaveMstEntity> leaveApprvEntity = new ArrayList<EmployeeCompOffLeaveMstEntity>();
//		try {
//			leaveApprvEntity = employeeCompOffLeaveMstRepository.getAllLeaveApproval();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return leaveApprvEntity;
//	}
	
	@Override
	public EmployeeCompOffLeaveMstResultSet getCompoffLeave() {
		EmployeeCompOffLeaveMstResultSet employeeCompOffLeaveMstResultSet = new EmployeeCompOffLeaveMstResultSet();
		try {
			List<EmployeeCompOffLeaveMstDTO> compoffLeaveDtoList = new ArrayList<EmployeeCompOffLeaveMstDTO>();
			 List<EmployeeCompOffLeaveMstEntity> compoffLeaveEntityList=employeeCompOffLeaveMstRepository.getCompoffLeave();
			 if(compoffLeaveEntityList!=null&&compoffLeaveEntityList.size()>0) {
				 compoffLeaveEntityList.forEach(obj->
					{
						EmployeeCompOffLeaveMstDTO compoffLeavedto=new EmployeeCompOffLeaveMstDTO();
						BeanUtils.copyProperties(obj,compoffLeavedto);
						compoffLeaveDtoList.add(compoffLeavedto);
					});
					
				 employeeCompOffLeaveMstResultSet.setEmployeeCompOffLeaveMstDtoList(compoffLeaveDtoList);	 
			 }
			 else {
				 employeeCompOffLeaveMstResultSet.setStatus(false);
				 employeeCompOffLeaveMstResultSet.setMessage("Failed");
				 employeeCompOffLeaveMstResultSet.setMessageDescription("No Comp off leaves available.");
			 }
		}
		catch(Exception e) {
			employeeCompOffLeaveMstResultSet.setStatus(false);
			employeeCompOffLeaveMstResultSet.setMessage("Error");
			employeeCompOffLeaveMstResultSet.setMessageDescription(e.getMessage());
		}
		return employeeCompOffLeaveMstResultSet;
	}
	
	@Override
	public EmployeeCompOffLeaveMstResultSet getCompoffLeaveHistory() {
		EmployeeCompOffLeaveMstResultSet employeeCompOffLeaveHistoryMstResultSet = new EmployeeCompOffLeaveMstResultSet();
		try {
			List<EmployeeCompOffLeaveMstDTO> compoffLeaveHistoryDtoList = new ArrayList<EmployeeCompOffLeaveMstDTO>();
			 List<EmployeeCompOffLeaveMstEntity> compoffLeaveHistoryEntityList=employeeCompOffLeaveMstRepository.getCompoffLeaveHistory();
			 if(compoffLeaveHistoryEntityList!=null&&compoffLeaveHistoryEntityList.size()>0) {
				 compoffLeaveHistoryEntityList.forEach(obj->
					{
						EmployeeCompOffLeaveMstDTO compoffLeaveHistorydto=new EmployeeCompOffLeaveMstDTO();
						BeanUtils.copyProperties(obj,compoffLeaveHistorydto);
						compoffLeaveHistoryDtoList.add(compoffLeaveHistorydto);
					});
					
				 employeeCompOffLeaveHistoryMstResultSet.setEmployeeCompOffLeaveMstDtoList(compoffLeaveHistoryDtoList);	 
			 }
			 else {
				 employeeCompOffLeaveHistoryMstResultSet.setStatus(false);
				 employeeCompOffLeaveHistoryMstResultSet.setMessage("Failed");
				 employeeCompOffLeaveHistoryMstResultSet.setMessageDescription("No Comp off leaves available.");
			 }
		}
		catch(Exception e) {
			employeeCompOffLeaveHistoryMstResultSet.setStatus(false);
			employeeCompOffLeaveHistoryMstResultSet.setMessage("Error");
			employeeCompOffLeaveHistoryMstResultSet.setMessageDescription(e.getMessage());
		}
		return employeeCompOffLeaveHistoryMstResultSet;
	}

}




















