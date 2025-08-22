package org.vrnda.hrms.service.impl;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.vrnda.hrms.entity.AppUsersMstEntity;
import org.vrnda.hrms.entity.EmployeeLeavesMstEntity;
import org.vrnda.hrms.repository.AppUserMstRepository;
import org.vrnda.hrms.repository.CmnLeaveTypesMstRepository;
import org.vrnda.hrms.repository.CmnLookupMstRepository;
import org.vrnda.hrms.repository.EmployeeLeavesMstRepository;
import org.vrnda.hrms.repository.dto.EmployeeDetailsDTO;
import org.vrnda.hrms.repository.dto.EmployeeLeavesDto;
import org.vrnda.hrms.repository.dto.EmployeeLeavesMstDTO;
import org.vrnda.hrms.repository.dto.LeaveDetailsDTO;
import org.vrnda.hrms.service.CmnLookupMstService;
import org.vrnda.hrms.service.EmployeeDetailsService;
import org.vrnda.hrms.service.EmployeeLeavesMstService;
import org.vrnda.hrms.service.resultset.CmnLookupMstResultSet;
import org.vrnda.hrms.service.resultset.EmployeeDtlsResultSet;
import org.vrnda.hrms.service.resultset.EmployeeLeavesMstResultSet;
import org.vrnda.hrms.utils.ApplicationConstants;
import org.vrnda.hrms.utils.BaseUtils;

@Service
public class EmployeeLeavesMstServiceImpl extends GenericServiceImpl<EmployeeLeavesMstEntity, Long>
implements EmployeeLeavesMstService {

	public EmployeeLeavesMstServiceImpl(PagingAndSortingRepository<EmployeeLeavesMstEntity, Long> typeRepository) {
		super(typeRepository);
		// TODO Auto-generated constructor stub
	}

	@Autowired
	EmployeeLeavesMstRepository employeeLeaveMstRepository;

	@Autowired
	CmnLookupMstService cmnLookupMstService;

	@Autowired
	EmployeeDetailsService employeeDetailsService;

	@Autowired
	CmnLookupMstRepository 	cmnLookupMstRepository;
	
	@Autowired
	CmnLeaveTypesMstRepository 	cmnLeaveTypesMstRepository;

	@Autowired 
	AppUserMstRepository aumRepository;

	@Override
	public EmployeeLeavesMstResultSet getLeaveBalance(Long employeeId) {
		EmployeeLeavesMstResultSet employeeLeavesMstResultSet = new EmployeeLeavesMstResultSet();
		try {
			List<EmployeeLeavesMstEntity> empLeaveMstEntity = employeeLeaveMstRepository
					.getAllLeaveBalanceById(employeeId);
			if (empLeaveMstEntity.size() > 0) {
				employeeLeavesMstResultSet.setEmployeeLeavesMstEntityList(empLeaveMstEntity);
			} else {
				employeeLeavesMstResultSet.setStatus(false);
				employeeLeavesMstResultSet.setMessage("Failed");
				employeeLeavesMstResultSet.setMessageDescription("ConfigLeave list not available");
			}

		} catch (Exception e) {
			employeeLeavesMstResultSet.setStatus(false);
			employeeLeavesMstResultSet.setMessage("Exception");
			employeeLeavesMstResultSet.setMessageDescription(e.getMessage());
		}
		return employeeLeavesMstResultSet;
	}

	@Override
	public EmployeeLeavesMstResultSet updateLeaveBalance(EmployeeLeavesMstDTO employeeLeavesMstDto,String loggedInUser) {
		EmployeeLeavesMstResultSet employeeLeavesMstResultSet = new EmployeeLeavesMstResultSet();
		try {
			EmployeeLeavesMstEntity empLeaveMstEntity = employeeLeaveMstRepository.getLeaveBalanceByIdandLvtype(employeeLeavesMstDto.getEmployeeId(), employeeLeavesMstDto.getLeaveTypeId());
			Long lopLookupId = cmnLeaveTypesMstRepository.getLeaveTypeIdByLeaveTypeName(ApplicationConstants.LOP);
			if (employeeLeavesMstDto.getLvBalChange()) {//employeeLeavesMstDto.isLvBalChange()
				Double remainingLeaves;
				if( employeeLeavesMstDto.getLeaveTypeId() == lopLookupId) {
					remainingLeaves= empLeaveMstEntity.getTotalLeaveBalance() + employeeLeavesMstDto.getNoOfDays();
				} else {
					remainingLeaves = empLeaveMstEntity.getTotalLeaveBalance() - employeeLeavesMstDto.getNoOfDays();
				}
				if(remainingLeaves<0) {
					empLeaveMstEntity.setTotalLeaveBalance(0.0);
				}
				else {
					empLeaveMstEntity.setTotalLeaveBalance(remainingLeaves);
				}
				BaseUtils.modifyBaseData(empLeaveMstEntity,loggedInUser);
				update(empLeaveMstEntity);

			} else {
				Double remainingLeaves =0.0;
				if(empLeaveMstEntity.getLeaveTypeId() == lopLookupId) {
					remainingLeaves = empLeaveMstEntity.getTotalLeaveBalance() - employeeLeavesMstDto.getNoOfDays();	
				} else {
					remainingLeaves = empLeaveMstEntity.getTotalLeaveBalance() + employeeLeavesMstDto.getNoOfDays();
				}
				empLeaveMstEntity.setTotalLeaveBalance(remainingLeaves);
				BaseUtils.modifyBaseData(empLeaveMstEntity,"");
				update(empLeaveMstEntity);
			}
		} catch (Exception e) {
			employeeLeavesMstResultSet.setStatus(false);
			employeeLeavesMstResultSet.setMessage("Exception");
			employeeLeavesMstResultSet.setMessageDescription(e.getMessage());
		}

		return employeeLeavesMstResultSet;

	}

	@Override
	public EmployeeLeavesMstResultSet getEmployeeLvBalance() {


		EmployeeLeavesMstResultSet employeeLeavesMstResultSet = new EmployeeLeavesMstResultSet();
		try {
			Map<Long, String> lvTypeMap1 = cmnLookupMstService.getLookupIdAndLookupNameMapingsByParentLookupName(ApplicationConstants.LEAVE_TYPE);
			CmnLookupMstResultSet lvTypeMap =  cmnLookupMstService.getLookupByLookupName(ApplicationConstants.LEAVE_TYPE);
			//employeeLeavesMstResultSet.setLvTypeMap(lvTypeMap.);
			List<EmployeeLeavesDto> empLeaveIdListDto = new ArrayList<EmployeeLeavesDto>();
			EmployeeDtlsResultSet empDtlsResultset = employeeDetailsService.getAllActiveEmployees();
			for (EmployeeDetailsDTO employeeDetailsDto : empDtlsResultset.getEmployeeDetailsDTOList()) {
				List<LeaveDetailsDTO> leaveDetailsList = new ArrayList<LeaveDetailsDTO>();
				EmployeeLeavesDto empLeaveIdObj = new EmployeeLeavesDto();
				List<EmployeeLeavesMstEntity> empById = employeeLeaveMstRepository.getAllLeaveBalanceById(employeeDetailsDto.getEmployeeId());
				//EmployeeLeavesMstResultSet empById	= getLeaveBalance(employeeDetailsDto.getEmployeeId());
				if(empById != null && !empById.isEmpty() && empById.size() > 0) {
					empLeaveIdObj.setEmployeeFullName(employeeDetailsDto.getEmployeeFullName());
					empLeaveIdObj.setEmployeeId(employeeDetailsDto.getEmployeeId());
					for (EmployeeLeavesMstEntity employeeLeavesMstEntity : empById) {
						LeaveDetailsDTO  leaveDetails = new LeaveDetailsDTO();
						leaveDetails.setLeaveBalance(employeeLeavesMstEntity.getTotalLeaveBalance());
						leaveDetails.setLvTypeId(employeeLeavesMstEntity.getLeaveTypeId());

						leaveDetails.setLvTypeName(lvTypeMap1.get(employeeLeavesMstEntity.getLeaveTypeId()));
						//leaveDetails.setLeaveBalance(employeeLeavesMstEntity.getTotalLeaveBalance());
						leaveDetailsList.add(leaveDetails);

					}
					empLeaveIdObj.setLeaveDetailsList(leaveDetailsList);
					empLeaveIdObj.setEmployeeFullName(employeeDetailsDto.getEmployeeFullName());
					empLeaveIdObj.setEmployeeId(employeeDetailsDto.getEmployeeId());
					empLeaveIdListDto.add(empLeaveIdObj);
				}
			}
			employeeLeavesMstResultSet.setEmployeeLeavesMstDtoList(empLeaveIdListDto);//setEmployeeLeavesMstDtoList
			//			employeeLeavesMstResultSet.setLookupIdAndNameDTOList(lvTypeMap.getLookupIdAndLookupNameMapings());
			employeeLeavesMstResultSet.setEmployeeLeavesMstDtoList(empLeaveIdListDto);
		} catch (Exception e) {
			employeeLeavesMstResultSet.setStatus(false);
			employeeLeavesMstResultSet.setMessage("Exception");
			employeeLeavesMstResultSet.setMessageDescription(e.getMessage());
		}

		return employeeLeavesMstResultSet;
	}

	@Override
	public EmployeeLeavesMstResultSet getLeaveBalById(Long empId, Long lvTypeLookupId,Long statusLookupId) {

		EmployeeLeavesMstResultSet employeeLeavesMstResultSet = new EmployeeLeavesMstResultSet();
		EmployeeLeavesMstEntity empLvMstEntity = new EmployeeLeavesMstEntity();
		try {
			empLvMstEntity = employeeLeaveMstRepository.getLeaveBalById(empId, lvTypeLookupId, statusLookupId);
			employeeLeavesMstResultSet.setEmployeeLeavesMstEntity(empLvMstEntity);
		} catch (Exception e) {
			employeeLeavesMstResultSet.setStatus(false);
			employeeLeavesMstResultSet.setMessage("Exception");
			employeeLeavesMstResultSet.setMessageDescription(e.getMessage());
		}
		return employeeLeavesMstResultSet;
	}

	@Override
	public EmployeeLeavesMstResultSet getLeaveBalanceByUserId(String username) {
		EmployeeLeavesMstResultSet employeeLeavesMstResultSet = new EmployeeLeavesMstResultSet();
		AppUsersMstEntity appUserMst = aumRepository.getAppUserByUserName(username);
		if(appUserMst != null) {
			employeeLeavesMstResultSet=getLeaveBalance(appUserMst.getEmployeeId());
		} else {
			employeeLeavesMstResultSet.setStatus(false);
			employeeLeavesMstResultSet.setMessage("No data exists.");
		}
		return employeeLeavesMstResultSet;
	}

	@Override
	public EmployeeLeavesMstResultSet getEmployeeLeavesByYearId(Long yearId) {
		EmployeeLeavesMstResultSet employeeLeavesMstResultSet=new EmployeeLeavesMstResultSet();
		if(yearId != null) {
			List<EmployeeLeavesMstEntity> employeeLeavesMstEntityList = employeeLeaveMstRepository.getEmployeeLeavesByYearId(yearId);
			if(employeeLeavesMstEntityList != null && employeeLeavesMstEntityList.size() > 0) {
				employeeLeavesMstResultSet.setEmployeeLeavesMstEntityList(employeeLeavesMstEntityList);
			} else {
				employeeLeavesMstResultSet.setStatus(false);
				employeeLeavesMstResultSet.setMessage("No data exists.");
			}
		} else {
			employeeLeavesMstResultSet.setStatus(false);
			employeeLeavesMstResultSet.setMessage("No data exists.");
		}
		return employeeLeavesMstResultSet;
	}

}
