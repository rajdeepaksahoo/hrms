//package org.vrnda.hrms.service.impl;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.repository.PagingAndSortingRepository;
//import org.springframework.stereotype.Service;
//import org.vrnda.hrms.entity.AppUserMstEntity;
//import org.vrnda.hrms.entity.EmployeeLeavesMstEntity;
//import org.vrnda.hrms.repository.AppUserMstRepository;
//import org.vrnda.hrms.repository.CmnLookupMstRepository;
//import org.vrnda.hrms.repository.EmployeeLeavesMstRepository;
//import org.vrnda.hrms.repository.dto.EmployeeDetailsDTO;
//import org.vrnda.hrms.repository.dto.EmployeeLeavesMstDTO;
//import org.vrnda.hrms.repository.dto.EmployeeLeavesDto;
//import org.vrnda.hrms.repository.dto.LeaveDetailsDto;
//import org.vrnda.hrms.service.CmnLookupMstService;
//import org.vrnda.hrms.service.EmployeeDetailsService;
//import org.vrnda.hrms.service.EmployeeLeavesMstService;
//import org.vrnda.hrms.service.resultset.CmnLookupMstResultSet;
//import org.vrnda.hrms.service.resultset.EmployeeDtlsResultSet;
//import org.vrnda.hrms.service.resultset.EmployeeLeavesMstResultSet;
//import org.vrnda.hrms.service.resultset.TasksAndAllocationsResultSet;
//import org.vrnda.hrms.utils.ApplicationConstants;
//import org.vrnda.hrms.utils.BaseUtils;
//
//@Service
//public class EmployeeLeavesMstServiceImpl extends GenericServiceImpl<EmployeeLeavesMstEntity, Long>
//implements EmployeeLeavesMstService {
//
//	public EmployeeLeavesMstServiceImpl(PagingAndSortingRepository<EmployeeLeavesMstEntity, Long> typeRepository) {
//		super(typeRepository);
//		// TODO Auto-generated constructor stub
//	}
//
//	@Autowired
//	EmployeeLeavesMstRepository employeeLeaveMstRepository;
//	
//	@Autowired
//	CmnLookupMstService cmnLookupMstService;
//	
//	@Autowired
//	EmployeeDetailsService employeeDetailsService;
//	
//	@Autowired
//	CmnLookupMstRepository 	cmnLookupMstRepository;
//	
//	@Autowired 
//	AppUserMstRepository aumRepository;
//
//	@Override
//	public EmployeeLeavesMstResultSet getLeaveBalance(Long employeeId) {
//		EmployeeLeavesMstResultSet employeeLeavesMstResultSet = new EmployeeLeavesMstResultSet();
//		try {
//			List<EmployeeLeavesMstEntity> empLeaveMstEntity = employeeLeaveMstRepository
//					.getAllLeaveBalanceById(employeeId);
//			if (empLeaveMstEntity.size() > 0) {
//				employeeLeavesMstResultSet.setEmployeeLeaveMstEntityList(empLeaveMstEntity);
//			} else {
//				employeeLeavesMstResultSet.setStatus(false);
//				employeeLeavesMstResultSet.setMessage("Failed");
//				employeeLeavesMstResultSet.setMessageDescription("ConfigLeave list not available");
//			}
//			
//		} catch (Exception e) {
//			employeeLeavesMstResultSet.setStatus(false);
//			employeeLeavesMstResultSet.setMessage("Exception");
//			employeeLeavesMstResultSet.setMessageDescription(e.getMessage());
//		}
//		return employeeLeavesMstResultSet;
//	}
//
//	@Override
//	public EmployeeLeavesMstResultSet updateLeaveBalance(EmployeeLeavesMstDTO employeeLeavesMstDto) {
//		EmployeeLeavesMstResultSet employeeLeavesMstResultSet = new EmployeeLeavesMstResultSet();
//		try {
//			EmployeeLeavesMstEntity empLeaveMstEntity = employeeLeaveMstRepository.getLeaveBalanceByIdandLvtype(
//					employeeLeavesMstDto.getEmployeeId(), employeeLeavesMstDto.getLvTypeLookupId());
//			Long lopLookupId = cmnLookupMstRepository.getLookupIdFromCmnLookupMstByLookupNameAndParent(ApplicationConstants.LOP, ApplicationConstants.LEAVE_TYPE);
//			if (employeeLeavesMstDto.isLvBalChange()) {
//				Double remainingLeaves;
//				if( employeeLeavesMstDto.getLvTypeLookupId() == lopLookupId) {
//					remainingLeaves= empLeaveMstEntity.getTotalLeaveBalance() + employeeLeavesMstDto.getNoOfDays();
//				} else {
//					 remainingLeaves = empLeaveMstEntity.getTotalLeaveBalance() - employeeLeavesMstDto.getNoOfDays();
//				}
//				empLeaveMstEntity.setTotalLeaveBalance(remainingLeaves);
//				BaseUtils.modifyBaseData(empLeaveMstEntity);
//				update(empLeaveMstEntity);
//
//			} else {
//				Double remainingLeaves =0.0;
//				if(empLeaveMstEntity.getLvTypeLookupId() == lopLookupId) {
//					remainingLeaves = empLeaveMstEntity.getTotalLeaveBalance() - employeeLeavesMstDto.getNoOfDays();	
//				} else {
//					remainingLeaves = empLeaveMstEntity.getTotalLeaveBalance() + employeeLeavesMstDto.getNoOfDays();
//				}
//				empLeaveMstEntity.setTotalLeaveBalance(remainingLeaves);
//				BaseUtils.modifyBaseData(empLeaveMstEntity);
//				update(empLeaveMstEntity);
//			}
//		} catch (Exception e) {
//			employeeLeavesMstResultSet.setStatus(false);
//			employeeLeavesMstResultSet.setMessage("Exception");
//			employeeLeavesMstResultSet.setMessageDescription(e.getMessage());
//		}
//
//		return employeeLeavesMstResultSet;
//
//	}
//
//	@Override
//	public EmployeeLeavesMstResultSet getEmployeeLvBalance() {
//
//
//		EmployeeLeavesMstResultSet employeeLeavesMstResultSet = new EmployeeLeavesMstResultSet();
//		try {
//			Map<Long, String> lvTypeMap1 = cmnLookupMstService.getLookupIdAndLookupNameMapings(ApplicationConstants.LEAVE_TYPE);
//			CmnLookupMstResultSet lvTypeMap =  cmnLookupMstService.getLookupDetailsWithLookupName(ApplicationConstants.LEAVE_TYPE);
//			//employeeLeavesMstResultSet.setLvTypeMap(lvTypeMap.);
//			List<EmployeeLeavesDto> empLeaveIdListDto = new ArrayList<EmployeeLeavesDto>();
//			EmployeeDtlsResultSet empDtlsResultset = employeeDetailsService.getAllActiveEmployees();
// 			for (EmployeeDetailsDTO employeeDetailsDto : empDtlsResultset.getEmployeeDetailsDTOList()) {
// 				List<LeaveDetailsDto> leaveDetailsList = new ArrayList<LeaveDetailsDto>();
//				EmployeeLeavesDto empLeaveIdObj = new EmployeeLeavesDto();
//				List<EmployeeLeavesMstEntity> empById = employeeLeaveMstRepository.getAllLeaveBalanceById(employeeDetailsDto.getEmployeeId());
//				//EmployeeLeavesMstResultSet empById	= getLeaveBalance(employeeDetailsDto.getEmployeeId());
//				if(empById != null && !empById.isEmpty() && empById.size() > 0) {
//					empLeaveIdObj.setEmployeeFullName(employeeDetailsDto.getEmployeeFullName());
//					empLeaveIdObj.setEmployeeId(employeeDetailsDto.getEmployeeId());
//					for (EmployeeLeavesMstEntity employeeLeavesMstEntity : empById) {
//						LeaveDetailsDto  leaveDetails = new LeaveDetailsDto();
//						leaveDetails.setLeaveBalance(employeeLeavesMstEntity.getTotalLeaveBalance());
//						leaveDetails.setLvTypeId(employeeLeavesMstEntity.getLvTypeLookupId());
//					
//								leaveDetails.setLvTypeName(lvTypeMap1.get(employeeLeavesMstEntity.getLvTypeLookupId()));
//								//leaveDetails.setLeaveBalance(employeeLeavesMstEntity.getTotalLeaveBalance());
//								leaveDetailsList.add(leaveDetails);
//						
//					}
//					empLeaveIdObj.setLeaveDetailsList(leaveDetailsList);
//					empLeaveIdObj.setEmployeeFullName(employeeDetailsDto.getEmployeeFullName());
//					empLeaveIdObj.setEmployeeId(employeeDetailsDto.getEmployeeId());
//					empLeaveIdListDto.add(empLeaveIdObj);
//				}
//			}
//			employeeLeavesMstResultSet.setEmployeeLeavesDtoList(empLeaveIdListDto);
//			employeeLeavesMstResultSet.setLookupIdAndNameDTOList(lvTypeMap.getLookupIdAndNameDTOList());
//			//employeeLeavesMstResultSet.setEmployeeLeaveIdDto(empLeaveIdListDto);
//		} catch (Exception e) {
//			employeeLeavesMstResultSet.setStatus(false);
//			employeeLeavesMstResultSet.setMessage("Exception");
//			employeeLeavesMstResultSet.setMessageDescription(e.getMessage());
//		}
//
//		return employeeLeavesMstResultSet;
//	}
//	
//	@Override
//	public EmployeeLeavesMstResultSet getLeaveBalById(Long empId, Long lvTypeLookupId,Long statusLookupId) {
//		
//		EmployeeLeavesMstResultSet employeeLeavesMstResultSet = new EmployeeLeavesMstResultSet();
//		EmployeeLeavesMstEntity empLvMstEntity = new EmployeeLeavesMstEntity();
//		try {
//			empLvMstEntity = employeeLeaveMstRepository.getLeaveBalById(empId, lvTypeLookupId, statusLookupId);
//			employeeLeavesMstResultSet.setEmployeeLeaveMstEntity(empLvMstEntity);
//		} catch (Exception e) {
//			employeeLeavesMstResultSet.setStatus(false);
//			employeeLeavesMstResultSet.setMessage("Exception");
//			employeeLeavesMstResultSet.setMessageDescription(e.getMessage());
//		}
//		return employeeLeavesMstResultSet;
//	}
//	@Override
//	public EmployeeLeavesMstResultSet getLeaveBalanceByUserId(String username) {
//		AppUserMstEntity appUserMst = aumRepository.getAppUserByUserName(username);
//		EmployeeLeavesMstResultSet employeeLeavesMstResultSet=new EmployeeLeavesMstResultSet();
//		Long empid=appUserMst.getEmployeeId();
//		employeeLeavesMstResultSet=getLeaveBalance(empid);
//		return employeeLeavesMstResultSet;
//	}
//
//}
