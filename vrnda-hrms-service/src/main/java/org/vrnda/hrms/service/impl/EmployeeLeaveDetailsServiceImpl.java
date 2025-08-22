package org.vrnda.hrms.service.impl;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.vrnda.hrms.entity.EmployeeDetailsEntity;
import org.vrnda.hrms.entity.EmployeeLeaveDetailsEntity;
import org.vrnda.hrms.entity.EmployeeLeavesMstEntity;
import org.vrnda.hrms.repository.AppUserMstRepository;
import org.vrnda.hrms.repository.CmnYearsMstRepository;
import org.vrnda.hrms.repository.EmployeeDetailsRepository;
import org.vrnda.hrms.repository.EmployeeLeaveDetailsRepository;
import org.vrnda.hrms.repository.dto.EmployeeLeaveDetailsDTO;
import org.vrnda.hrms.repository.dto.EmployeeLeavesMstDTO;
import org.vrnda.hrms.service.CmnLeaveTypesMstService;
import org.vrnda.hrms.service.CmnLookupMstService;
import org.vrnda.hrms.service.CmnTableSeqService;
import org.vrnda.hrms.service.EmployeeDetailsService;
import org.vrnda.hrms.service.EmployeeLeaveDetailsService;
import org.vrnda.hrms.service.EmployeeLeavesMstService;
import org.vrnda.hrms.service.resultset.EmployeeDtlsResultSet;
import org.vrnda.hrms.service.resultset.EmployeeLeaveDetailsResultSet;
import org.vrnda.hrms.service.resultset.EmployeeLeavesMstResultSet;
import org.vrnda.hrms.utils.ApplicationConstants;
import org.vrnda.hrms.utils.BaseUtils;

@Service
@EnableScheduling
public class EmployeeLeaveDetailsServiceImpl extends GenericServiceImpl<EmployeeLeaveDetailsEntity, Long>
implements EmployeeLeaveDetailsService {

	public EmployeeLeaveDetailsServiceImpl(
			PagingAndSortingRepository<EmployeeLeaveDetailsEntity, Long> typeRepository) {
		super(typeRepository);
		// TODO Auto-generated constructor stub
	}

	@Autowired
	EmployeeDetailsRepository employeeDetailsRepository;

	@Autowired
	EmployeeLeaveDetailsRepository employeeLeaveDetailsRepository;

	@Autowired
	CmnLookupMstService cmnLookupMstService;
	
	@Autowired
	CmnLeaveTypesMstService cmnLeaveTypesMstService;

	@Autowired
	EmployeeLeavesMstService employeeLeavesMstService;

//	@Autowired
//	EmployeeLeavePlanApprovalsMappingService empLvPlanApprMapService;
//
//	@Autowired
//	LeaveApprovalConfigMstService leaveApprovalCOnfigService;

	@Autowired
	EmployeeDetailsService employeeDetailsService;

	@Autowired 
	AppUserMstRepository aumRepository;
	
	@Autowired
	CmnTableSeqService cmnTableSeqService;
	
	@Autowired
	CmnYearsMstRepository cmnYearsMstRepository;
	
	int year = Year.now().getValue();
	String yearname=Integer.toString(year);
	
	@Override
	public EmployeeLeaveDetailsResultSet getAllLeaveRequests(Long approverId) {
		EmployeeLeaveDetailsResultSet empLeaveDtlsResultset = new EmployeeLeaveDetailsResultSet();
//		List<EmployeeLeaveDetailsEntity> activeLeaveRequests = new ArrayList<EmployeeLeaveDetailsEntity>();
		List<EmployeeLeaveDetailsDTO> empLeaveDetailsDtoList = new ArrayList<EmployeeLeaveDetailsDTO>();
		List<EmployeeLeaveDetailsDTO> activeLeaveRequests = new ArrayList<EmployeeLeaveDetailsDTO>();
		try {
			Map<String, Long> status = cmnLookupMstService.getLookupNameAndLookupIdMapingsByParentLookupName(ApplicationConstants.STATUS);
			Map<String, Long> leaveStatus =cmnLookupMstService.getLookupNameAndLookupIdMapingsByParentLookupName(ApplicationConstants.LEAVE_STATUS);
			List<Object[]> LeaveRequest=employeeLeaveDetailsRepository.getAllActiveRequests(status.get(ApplicationConstants.ACTIVE), approverId,
					leaveStatus.get(ApplicationConstants.REQUESTED), leaveStatus.get(ApplicationConstants.CANCEL_REQUESTED),
					leaveStatus.get(ApplicationConstants.UPDATE_REQUESTED));
			for (Object[] leave : LeaveRequest) {
				if (leave != null) {
					EmployeeLeaveDetailsDTO employeeLeaveDetailsDTO = new EmployeeLeaveDetailsDTO();
					EmployeeLeaveDetailsEntity cmnProjectsMstEntity1 = (EmployeeLeaveDetailsEntity) leave[0];
					BeanUtils.copyProperties(cmnProjectsMstEntity1, employeeLeaveDetailsDTO);
					employeeLeaveDetailsDTO.setAvailableLeaves(Double.parseDouble(leave[1].toString()));
					activeLeaveRequests.add(employeeLeaveDetailsDTO);
				}
			}
			List<Long> empId = new ArrayList<Long>();
			for (EmployeeLeaveDetailsDTO primary : activeLeaveRequests) {
				List<EmployeeLeaveDetailsDTO> empIDWiseList = new ArrayList<EmployeeLeaveDetailsDTO>();
				empIDWiseList = activeLeaveRequests.stream().filter(i -> i.getEmployeeId() == primary.getEmployeeId())
						.collect(Collectors.toList());
				List<Long> retEmpList = empId.stream().filter(i -> i == primary.getEmployeeId())
						.collect(Collectors.toList());
				if (retEmpList == null || retEmpList.isEmpty() || !(retEmpList.size() > 0)) {
					empLeaveDetailsDtoList.addAll(leaveHistory(empIDWiseList));
				}
				empId.add(primary.getEmployeeId());
			}
			if (empLeaveDetailsDtoList.size() > 0) {
				empLeaveDtlsResultset.setEmployeeLeaveDetailsDtoList(empLeaveDetailsDtoList);
			}
		} catch (Exception e) {
			empLeaveDtlsResultset.setStatus(false);
			empLeaveDtlsResultset.setMessage(ApplicationConstants.EXCEPTION);
			empLeaveDtlsResultset.setMessageDescription(e.getMessage());
		}
		return empLeaveDtlsResultset;
	}

	@Override
	public EmployeeLeaveDetailsResultSet getEmployeeLeaveRequests(Long employeeId) {
		EmployeeLeaveDetailsResultSet empLeaveDtlsResultSet = new EmployeeLeaveDetailsResultSet();
		List<EmployeeLeaveDetailsDTO> empLeaveDetailsDtoList = new ArrayList<EmployeeLeaveDetailsDTO>();

		try {
			Map<String, Long> leaveStatus =cmnLookupMstService.getLookupNameAndLookupIdMapingsByParentLookupName(ApplicationConstants.LEAVE_STATUS);
			Map<String, Long> status = cmnLookupMstService.getLookupNameAndLookupIdMapingsByParentLookupName(ApplicationConstants.STATUS);
			List<Object[]> empLeaveDetailsEntity = employeeLeaveDetailsRepository
					.getAllLeaveRequestsById(employeeId, status.get(ApplicationConstants.ACTIVE),leaveStatus.get(ApplicationConstants.REQUESTED), leaveStatus.get(ApplicationConstants.CANCEL_REQUESTED),
							leaveStatus.get(ApplicationConstants.UPDATE_REQUESTED),leaveStatus.get(ApplicationConstants.APPROVED));
			for (Object[] leave : empLeaveDetailsEntity) {
				if (leave != null) {
					EmployeeLeaveDetailsDTO employeeLeaveDetailsDTO = new EmployeeLeaveDetailsDTO();
					EmployeeLeaveDetailsEntity cmnProjectsMstEntity1 = (EmployeeLeaveDetailsEntity) leave[0];
					BeanUtils.copyProperties(cmnProjectsMstEntity1, employeeLeaveDetailsDTO);
					employeeLeaveDetailsDTO.setAvailableLeaves(Double.parseDouble(leave[1].toString()));
					empLeaveDetailsDtoList.add(employeeLeaveDetailsDTO);
				}
			}
			List<EmployeeLeaveDetailsDTO> empLeaveDtoList = leaveHistory(empLeaveDetailsDtoList);

			if (empLeaveDtoList.size() > 0) {
				empLeaveDtlsResultSet.setEmployeeLeaveDetailsDtoList(empLeaveDtoList);
			} else {
				empLeaveDtlsResultSet.setStatus(false);
				empLeaveDtlsResultSet.setMessage(ApplicationConstants.FAILED);
				empLeaveDtlsResultSet.setMessageDescription("ConfigLeave list not available");
			}
		} catch (Exception e) {
			empLeaveDtlsResultSet.setStatus(false);
			empLeaveDtlsResultSet.setMessage(ApplicationConstants.EXCEPTION);
			empLeaveDtlsResultSet.setMessageDescription(e.getMessage());
		}
		return empLeaveDtlsResultSet;
	}

	public List<EmployeeLeaveDetailsDTO> leaveHistory(List<EmployeeLeaveDetailsDTO> empLeaveDetailsEntity) {
		EmployeeDtlsResultSet empName = new EmployeeDtlsResultSet();
		List<EmployeeLeaveDetailsDTO> empLeaveDetailsDtoList = new ArrayList<EmployeeLeaveDetailsDTO>();
		Long yearId=cmnYearsMstRepository.getYearByYearNameAndYearTypeLookupId(yearname,ApplicationConstants.CALENDAR_YEAR).getYearId();
		Map<Long, String> leaveStatus = cmnLookupMstService.getLookupIdAndLookupNameMapingsByParentLookupName(ApplicationConstants.LEAVE_STATUS);
		Map<String, Long> statusIdMap = cmnLookupMstService.getLookupNameAndLookupIdMapingsByParentLookupName(ApplicationConstants.STATUS);
		Map<Long, String> leaveTypes = cmnLeaveTypesMstService.getIdAndLeaveTypeNameLeaveTypes(yearId);
		empName = employeeDetailsService.getAllActiveEmployees();
		for (EmployeeLeaveDetailsDTO obj : empLeaveDetailsEntity) {
			int a = 0;
			if (obj.getChildEmpLvId() == null) {
				a = 1;
				for (EmployeeLeaveDetailsDTO obj1 : empLeaveDetailsEntity) {
					EmployeeLeaveDetailsDTO o = new EmployeeLeaveDetailsDTO();
					if (obj1.getEmployeeLeaveId() != obj.getEmployeeLeaveId() && obj.getEmployeeLeaveId().equals(obj1.getChildEmpLvId() != null ? obj1.getChildEmpLvId() : 0)) {
						BeanUtils.copyProperties(obj1, o);
						o.setTotalLeaveDays(obj.getTotalLeaveDays() + obj1.getTotalLeaveDays());
						if(obj1.getTotalLeaveDays()>0) {
							o.setLopLeaves(obj.getTotalLeaveDays());
							o.setIsLop("Y");
						}
						else {
							o.setIsLop("N");
						}
//						o.setUsedleaves(obj1.getTotalLeaveDays());
						o.setAvailableLeaves(obj1.getAvailableLeaves());
						o.setApprovalStatus(leaveStatus.get(obj.getLeaveStatusLookupId()));
						o.setLvTypeName(leaveTypes.get(obj1.getLeaveTypeId()));
						o.setStatusLookupId(obj.getStatusLookupId());
						empName.getEmployeeDtlsMst().forEach(data->
						{
							if(data.getEmployeeId() == obj.getEmployeeId()) {
								o.setEmployeeFirstName(data.getEmployeeFirstName());
								o.setEmployeeLastName(data.getEmployeeLastName());
							}
						});
						empLeaveDetailsDtoList.add(o);
						a = 2;
					}
				}
			}
			if(obj.getChildEmpLvId() != null){
				EmployeeLeaveDetailsEntity lopRecord = employeeLeaveDetailsRepository.getById(obj.getChildEmpLvId());
				if(lopRecord.getStatusLookupId().equals(statusIdMap.get(ApplicationConstants.INACTIVE))) {
					EmployeeLeaveDetailsDTO o = new EmployeeLeaveDetailsDTO();
					BeanUtils.copyProperties(obj, o);
					o.setTotalLeaveDays(obj.getTotalLeaveDays());
					o.setUsedleaves(obj.getTotalLeaveDays());
					o.setApprovalStatus(leaveStatus.get(obj.getLeaveStatusLookupId()));
					o.setLvTypeName(leaveTypes.get(obj.getLeaveTypeId()));
					o.setStatusLookupId(obj.getStatusLookupId());
					o.setLopLeaves(0.00);
					o.setIsLop("N");
					o.setAvailableLeaves(obj.getAvailableLeaves());
					empName.getEmployeeDtlsMst().forEach(data->
					{
						if(data.getEmployeeId() == obj.getEmployeeId()) {
							o.setEmployeeFirstName(data.getEmployeeFirstName());
							o.setEmployeeLastName(data.getEmployeeLastName());
						}
					});
					empLeaveDetailsDtoList.add(o);
				}
			}
			if (a == 1) {
				EmployeeLeaveDetailsDTO o = new EmployeeLeaveDetailsDTO();
				BeanUtils.copyProperties(obj, o);
				o.setTotalLeaveDays(obj.getTotalLeaveDays());
				o.setUsedleaves(obj.getTotalLeaveDays());
				o.setApprovalStatus(leaveStatus.get(obj.getLeaveStatusLookupId()));
				o.setLvTypeName(leaveTypes.get(obj.getLeaveTypeId()));
				o.setStatusLookupId(obj.getStatusLookupId());
				o.setLopLeaves(0.00);
				o.setIsLop("N");
				o.setAvailableLeaves(obj.getAvailableLeaves());
				empName.getEmployeeDtlsMst().forEach(data->
				{
					if(data.getEmployeeId() == obj.getEmployeeId()) {
						o.setEmployeeFirstName(data.getEmployeeFirstName());
						o.setEmployeeLastName(data.getEmployeeLastName());
					}
				});
				empLeaveDetailsDtoList.add(o);
			}
		}
		return empLeaveDetailsDtoList;
	}

	// User Request
	@Override
	public EmployeeLeaveDetailsResultSet saveOrUpdate(EmployeeLeaveDetailsDTO employeeLeaveDtlsDto,String loginUser) {
		EmployeeLeaveDetailsResultSet empLeaveDtlsResultSet = new EmployeeLeaveDetailsResultSet();
		EmployeeLeaveDetailsEntity selectLvData = new EmployeeLeaveDetailsEntity();
		EmployeeLeavesMstDTO employeeLeavesMstDto = new EmployeeLeavesMstDTO();
		EmployeeDetailsEntity employeeDetails = null;
		Long firstlevelApproverId = null;
		Long secondlevelApproverId = null;
		Long hrApproverId = null;
		Long yearId=cmnYearsMstRepository.getYearByYearName(yearname).getYearId();
		try {
			Map<String, Long> leaveStatusMap = cmnLookupMstService.getLookupNameAndLookupIdMapingsByParentLookupName(ApplicationConstants.LEAVE_STATUS);
			Map<String, Long> statusLookupIdMap = cmnLookupMstService.getLookupNameAndLookupIdMapingsByParentLookupName(ApplicationConstants.STATUS);
			Map<String, Long> leaveTypesMap = cmnLeaveTypesMstService.getLeaveTypeNameAndIdLeaveTypes(yearId);
			List<EmployeeLeaveDetailsEntity> EmpLeaveDetailsList =employeeLeaveDetailsRepository.verificationAppliedRequest(employeeLeaveDtlsDto.getEmployeeId(), statusLookupIdMap.get(ApplicationConstants.ACTIVE), leaveStatusMap.get(ApplicationConstants.REQUESTED), leaveStatusMap.get(ApplicationConstants.CANCEL_REQUESTED),
					leaveStatusMap.get(ApplicationConstants.UPDATE_REQUESTED),leaveStatusMap.get(ApplicationConstants.APPROVED), employeeLeaveDtlsDto.getLeaveFromDate(), employeeLeaveDtlsDto.getLeaveToDate());
			
			
			if(employeeLeaveDtlsDto !=null && employeeLeaveDtlsDto.getEmployeeId() != null) {
				employeeDetails = employeeDetailsRepository.getEmployeeDetailsByEmployeeId(employeeLeaveDtlsDto.getEmployeeId());
				if(employeeDetails != null) {
					firstlevelApproverId = employeeDetails.getManagerId();
					hrApproverId = employeeDetails.getHrId();
					if(hrApproverId!=null) {
						employeeLeaveDtlsDto.setHrId(hrApproverId);
					}
					if (firstlevelApproverId != null) {
						employeeLeaveDtlsDto.setApproverId(firstlevelApproverId);
					} 
					else{
						empLeaveDtlsResultSet.setStatus(false);
						empLeaveDtlsResultSet.setMessage(ApplicationConstants.ERROR);
						empLeaveDtlsResultSet.setMessageDescription(
								"Your Approver/HR Details are incorrect. Contact your Administrator or HR or Manager.");
						return empLeaveDtlsResultSet;
					}
				} else {
					empLeaveDtlsResultSet.setStatus(false);
					empLeaveDtlsResultSet.setMessage(ApplicationConstants.ERROR);
					empLeaveDtlsResultSet.setMessageDescription("Your details not found. Contact your Administrator or HR or Manager.");
					return empLeaveDtlsResultSet;
				}
			} else {
				empLeaveDtlsResultSet.setStatus(false);
				empLeaveDtlsResultSet.setMessage(ApplicationConstants.ERROR);
				empLeaveDtlsResultSet.setMessageDescription("Invalid request details. Contact your Administrator or HR or Manager.");
				return empLeaveDtlsResultSet;
			}
			if (!(leaveStatusMap != null && leaveStatusMap.size() > 0)) {
				empLeaveDtlsResultSet.setStatus(false);
				empLeaveDtlsResultSet.setMessage(ApplicationConstants.ERROR);
				empLeaveDtlsResultSet.setMessageDescription("Invalid leave status mappings. Contact your Administrator or HR or Manager.");
			}

			// Updating already existing request
			if (employeeLeaveDtlsDto.getEmployeeLeaveId() != 0 && employeeLeaveDtlsDto.getEmployeeLeaveId() != null) {
				EmployeeLeaveDetailsEntity existingLeaveEntity = new EmployeeLeaveDetailsEntity();
				existingLeaveEntity = employeeLeaveDetailsRepository.getById(employeeLeaveDtlsDto.getEmployeeLeaveId());
				
				if (employeeLeaveDtlsDto.getChildEmpLvId() != null) {
					
					EmployeeLeaveDetailsEntity lopRecord = new EmployeeLeaveDetailsEntity();
					lopRecord = employeeLeaveDetailsRepository.getById(employeeLeaveDtlsDto.getChildEmpLvId());
					
					Double preLopLeave = lopRecord.getTotalLeaveDays();

					/*
					 * Total leaves checking whether leaves updated are need to be increased or
					 * Decreased in database
					 */
					Double totalLeaves = lopRecord.getTotalLeaveDays() + existingLeaveEntity.getTotalLeaveDays();
					/*
					 * To get the no of days added or cancelled. If negative added else cancelled
					 */
					Double newLeaves = totalLeaves - employeeLeaveDtlsDto.getTotalLeaveDays();
					/*
					 * Checking the leave status.
					 */
					if (existingLeaveEntity.getLeaveStatusLookupId().equals(leaveStatusMap.get(ApplicationConstants.REQUESTED))
							|| existingLeaveEntity.getLeaveStatusLookupId().equals(leaveStatusMap.get(ApplicationConstants.UPDATE_REQUESTED))) {

						/*
						 * If Applied leave is Extended newLeaves will be in negative numbers
						 */
						if (newLeaves < 0) {
							EmployeeLeavesMstResultSet empLvMstRs = employeeLeavesMstService.getLeaveBalById(
									employeeLeaveDtlsDto.getEmployeeId(), existingLeaveEntity.getLeaveTypeId(),
									statusLookupIdMap.get(ApplicationConstants.ACTIVE));
							EmployeeLeavesMstEntity empLvMstEntity = empLvMstRs.getEmployeeLeavesMstEntity();
							Double balLv = empLvMstEntity.getTotalLeaveBalance();
							if (balLv > 0) {
								existingLeaveEntity.setLeaveFromDate(employeeLeaveDtlsDto.getLeaveFromDate());
								existingLeaveEntity.setLeaveToDate(employeeLeaveDtlsDto.getLeaveToDate());
								existingLeaveEntity.setTotalLeaveDays(balLv + existingLeaveEntity.getTotalLeaveDays());
								existingLeaveEntity.setReason(employeeLeaveDtlsDto.getReason());
								update(existingLeaveEntity);

								employeeLeavesMstDto.setLvBalChange(true);
								employeeLeavesMstDto.setEmployeeId(employeeLeaveDtlsDto.getEmployeeId());
								employeeLeavesMstDto.setLeaveTypeId(existingLeaveEntity.getLeaveTypeId());
								employeeLeavesMstDto.setNoOfDays(Math.abs(balLv));
								employeeLeavesMstDto.setYearId(yearId);
								employeeLeavesMstService.updateLeaveBalance(employeeLeavesMstDto,loginUser);

								newLeaves = newLeaves + balLv;
							}
							
							if(lopRecord.getStatusLookupId().equals(statusLookupIdMap.get(ApplicationConstants.ACTIVE))) {
								lopRecord.setTotalLeaveDays(lopRecord.getTotalLeaveDays() - newLeaves);
								lopRecord.setLeaveFromDate(employeeLeaveDtlsDto.getLeaveFromDate());
								lopRecord.setLeaveToDate(employeeLeaveDtlsDto.getLeaveToDate());
								lopRecord.setReason(employeeLeaveDtlsDto.getReason());
								BaseUtils.modifyBaseData(lopRecord,loginUser);
								lopRecord.setStatusLookupId(statusLookupIdMap.get(ApplicationConstants.ACTIVE));
								update(lopRecord);

								employeeLeavesMstDto.setLvBalChange(true);
								employeeLeavesMstDto.setEmployeeId(employeeLeaveDtlsDto.getEmployeeId());
								employeeLeavesMstDto.setLeaveTypeId(lopRecord.getLeaveTypeId());
								employeeLeavesMstDto.setNoOfDays(Math.abs(newLeaves));
								employeeLeavesMstDto.setYearId(yearId);
								employeeLeavesMstService.updateLeaveBalance(employeeLeavesMstDto,loginUser);	
							}
							existingLeaveEntity.setLeaveFromDate(employeeLeaveDtlsDto.getLeaveFromDate());
							existingLeaveEntity.setLeaveToDate(employeeLeaveDtlsDto.getLeaveToDate());
							existingLeaveEntity.setReason(employeeLeaveDtlsDto.getReason());
							BaseUtils.modifyBaseData(existingLeaveEntity,loginUser);
							update(existingLeaveEntity);
						}

						if (newLeaves > 0) {
							Double reducedLeaves = lopRecord.getTotalLeaveDays() - newLeaves;
							/*
							 * reducedLeaves is to check how many days has been reduced. If it is in
							 * negative numbers Lop leaves are not there for particular user. Else we can
							 * deduct leaves form Lop leaves
							 */
							if (reducedLeaves < 1) {
								lopRecord.setTotalLeaveDays(0.0);
								lopRecord.setLeaveFromDate(employeeLeaveDtlsDto.getLeaveFromDate());
								lopRecord.setLeaveToDate(employeeLeaveDtlsDto.getLeaveToDate());
								BaseUtils.modifyBaseData(lopRecord,loginUser);
								lopRecord.setStatusLookupId(statusLookupIdMap.get(ApplicationConstants.INACTIVE));
								update(lopRecord);

								employeeLeavesMstDto.setLvBalChange(true);
								employeeLeavesMstDto.setEmployeeId(employeeLeaveDtlsDto.getEmployeeId());
								employeeLeavesMstDto.setLeaveTypeId(lopRecord.getLeaveTypeId());
								employeeLeavesMstDto.setNoOfDays(-preLopLeave);
								employeeLeavesMstDto.setYearId(yearId);
								employeeLeavesMstService.updateLeaveBalance(employeeLeavesMstDto,loginUser);

								if (reducedLeaves < 0) {
									existingLeaveEntity.setTotalLeaveDays(existingLeaveEntity.getTotalLeaveDays() + reducedLeaves);
									existingLeaveEntity.setLeaveFromDate(employeeLeaveDtlsDto.getLeaveFromDate());
									existingLeaveEntity.setLeaveToDate(employeeLeaveDtlsDto.getLeaveToDate());
									existingLeaveEntity.setStatusLookupId(statusLookupIdMap.get(ApplicationConstants.ACTIVE));
									existingLeaveEntity.setReason(employeeLeaveDtlsDto.getReason());
									BaseUtils.modifyBaseData(existingLeaveEntity,loginUser);
									update(existingLeaveEntity);
									employeeLeavesMstDto.setLvBalChange(true);
									employeeLeavesMstDto.setEmployeeId(employeeLeaveDtlsDto.getEmployeeId());
									employeeLeavesMstDto.setLeaveTypeId(existingLeaveEntity.getLeaveTypeId());
									employeeLeavesMstDto.setNoOfDays(reducedLeaves);
									employeeLeavesMstDto.setYearId(yearId);
									employeeLeavesMstService.updateLeaveBalance(employeeLeavesMstDto,loginUser);
								}
							} else {
								lopRecord.setTotalLeaveDays(reducedLeaves);
								lopRecord.setLeaveFromDate(employeeLeaveDtlsDto.getLeaveFromDate());
								lopRecord.setLeaveToDate(employeeLeaveDtlsDto.getLeaveToDate());
								BaseUtils.modifyBaseData(lopRecord,loginUser);
								lopRecord.setStatusLookupId(statusLookupIdMap.get(ApplicationConstants.ACTIVE));
								lopRecord.setReason(employeeLeaveDtlsDto.getReason());
								update(lopRecord);

								employeeLeavesMstDto.setLvBalChange(true);
								employeeLeavesMstDto.setEmployeeId(employeeLeaveDtlsDto.getEmployeeId());
								employeeLeavesMstDto.setLeaveTypeId(lopRecord.getLeaveTypeId());
								employeeLeavesMstDto.setNoOfDays(-newLeaves);
								employeeLeavesMstDto.setYearId(yearId);
								employeeLeavesMstService.updateLeaveBalance(employeeLeavesMstDto,loginUser);

								existingLeaveEntity.setLeaveFromDate(employeeLeaveDtlsDto.getLeaveFromDate());
								existingLeaveEntity.setLeaveToDate(employeeLeaveDtlsDto.getLeaveToDate());
								existingLeaveEntity.setStatusLookupId(statusLookupIdMap.get(ApplicationConstants.ACTIVE));
								existingLeaveEntity.setReason(employeeLeaveDtlsDto.getReason());
								BaseUtils.modifyBaseData(existingLeaveEntity,loginUser);
								update(existingLeaveEntity);
							}
						}
						else {
							existingLeaveEntity.setReason(employeeLeaveDtlsDto.getReason());
							BaseUtils.modifyBaseData(existingLeaveEntity,loginUser);
							update(existingLeaveEntity);
						}
						empLeaveDtlsResultSet.setStatus(true);
						empLeaveDtlsResultSet.setMessage(ApplicationConstants.SUCCESS);
						empLeaveDtlsResultSet.setMessageDescription("Leave Request Updated Successfully.");
					}

					if (existingLeaveEntity.getLeaveStatusLookupId().equals(leaveStatusMap.get(ApplicationConstants.APPROVED))) {
						/*
						 * Setting Status Inactive for the First Request for both the Records. And
						 * Adding the previously approved leaves which are deducted from
						 * Emmployee_leave_Mst table
						 */
						lopRecord.setStatusLookupId((statusLookupIdMap.get(ApplicationConstants.INACTIVE)));
						update(lopRecord);

						employeeLeavesMstDto.setLvBalChange(true);
						employeeLeavesMstDto.setEmployeeId(employeeLeaveDtlsDto.getEmployeeId());
						employeeLeavesMstDto.setLeaveTypeId(lopRecord.getLeaveTypeId());
						employeeLeavesMstDto.setNoOfDays(-lopRecord.getTotalLeaveDays());
						employeeLeavesMstDto.setYearId(yearId);
						employeeLeavesMstService.updateLeaveBalance(employeeLeavesMstDto,loginUser);

						existingLeaveEntity.setStatusLookupId((statusLookupIdMap.get(ApplicationConstants.INACTIVE)));
						update(existingLeaveEntity);

						employeeLeavesMstDto.setLvBalChange(true);
						employeeLeavesMstDto.setEmployeeId(employeeLeaveDtlsDto.getEmployeeId());
						employeeLeavesMstDto.setLeaveTypeId(existingLeaveEntity.getLeaveTypeId());
						employeeLeavesMstDto.setNoOfDays(-existingLeaveEntity.getTotalLeaveDays());
						employeeLeavesMstDto.setYearId(yearId);
						employeeLeavesMstService.updateLeaveBalance(employeeLeavesMstDto,loginUser);

						EmployeeLeavesMstResultSet empLvMstRs = employeeLeavesMstService.getLeaveBalById(
								employeeLeaveDtlsDto.getEmployeeId(), employeeLeaveDtlsDto.getLeaveTypeId(),
								statusLookupIdMap.get(ApplicationConstants.ACTIVE));
						EmployeeLeavesMstEntity empLvMstEntity = empLvMstRs.getEmployeeLeavesMstEntity();
						// Creating new Leave request
						Double remainingLeaves = empLvMstEntity.getTotalLeaveBalance()
								- employeeLeaveDtlsDto.getTotalLeaveDays();
						Long updChildLeaveRecord = 0L;
						if (remainingLeaves < 0) {
							// Creating new record record for loss of pay
							EmployeeLeaveDetailsEntity empLopIns = new EmployeeLeaveDetailsEntity();
							BeanUtils.copyProperties(existingLeaveEntity, empLopIns);
							BeanUtils.copyProperties(employeeLeaveDtlsDto, empLopIns);

							empLopIns.setEmployeeLeaveId(cmnTableSeqService.getNextSequence(ApplicationConstants.EMPLOYEE_LEAVE_DETAILS, ApplicationConstants.EMPLOYEE_LEAVE_ID));
							empLopIns.setParentEmpLeaveId(existingLeaveEntity.getEmployeeLeaveId());
							empLopIns.setTotalLeaveDays(Math.abs(remainingLeaves));
							empLopIns.setLeaveTypeId(leaveTypesMap.get(ApplicationConstants.LOP));
							empLopIns.setLeaveStatusLookupId(leaveStatusMap.get(ApplicationConstants.UPDATE_REQUESTED));
							empLopIns.setApproverId(lopRecord.getApproverId());
							empLopIns.setReason(employeeLeaveDtlsDto.getReason());
							empLopIns.setChildEmpLvId(null);

//							BaseUtils.setBaseData(empLopIns,statusLookupIdMap.get(employeeLeaveDtlsDto.getStatusLookupId()));
							BaseUtils.setBaseData(empLopIns,loginUser);
							save(empLopIns);
							updChildLeaveRecord = empLopIns.getEmployeeLeaveId();
							// Updating Leave balance in EmployeeLeaveMst table
							employeeLeavesMstDto.setLvBalChange(true);
							employeeLeavesMstDto.setEmployeeId(empLopIns.getEmployeeId());
							employeeLeavesMstDto.setLeaveTypeId(empLopIns.getLeaveTypeId());
							employeeLeavesMstDto.setNoOfDays(empLopIns.getTotalLeaveDays());
							employeeLeavesMstDto.setYearId(yearId);
							employeeLeavesMstService.updateLeaveBalance(employeeLeavesMstDto,loginUser);
						}
						// Creating new Leave request with the leave type
						if (empLvMstEntity.getTotalLeaveBalance() > 0) {
							EmployeeLeaveDetailsEntity updNewLvTypeReq = new EmployeeLeaveDetailsEntity();
							employeeLeaveDtlsDto.setEmployeeLeaveId(cmnTableSeqService.getNextSequence(ApplicationConstants.EMPLOYEE_LEAVE_DETAILS, ApplicationConstants.EMPLOYEE_LEAVE_ID));
							employeeLeaveDtlsDto.setParentEmpLeaveId(existingLeaveEntity.getEmployeeLeaveId());
							BeanUtils.copyProperties(employeeLeaveDtlsDto, updNewLvTypeReq);
							updNewLvTypeReq.setLeaveStatusLookupId(leaveStatusMap.get(ApplicationConstants.UPDATE_REQUESTED));
							if (updChildLeaveRecord != 0) {
								updNewLvTypeReq.setChildEmpLvId(updChildLeaveRecord);
							}
							updNewLvTypeReq.setApproverId(lopRecord.getApproverId());
							updNewLvTypeReq.setReason(employeeLeaveDtlsDto.getReason());
							updNewLvTypeReq.setTotalLeaveDays(
									remainingLeaves < 0 ? employeeLeaveDtlsDto.getTotalLeaveDays() + remainingLeaves
											: employeeLeaveDtlsDto.getTotalLeaveDays());
							updNewLvTypeReq.setYearId(yearId);
//							BaseUtils.setBaseData(updNewLvTypeReq,statusLookupIdMap.get(employeeLeaveDtlsDto.getStatusLookupId()));
							BaseUtils.setBaseData(updNewLvTypeReq,loginUser);
							save(updNewLvTypeReq);
							// Updating Leave balance in EmployeeLeaveMst table
							employeeLeavesMstDto.setLvBalChange(true);
							employeeLeavesMstDto.setEmployeeId(employeeLeaveDtlsDto.getEmployeeId());
							employeeLeavesMstDto.setLeaveTypeId(employeeLeaveDtlsDto.getLeaveTypeId());
							employeeLeavesMstDto.setNoOfDays(
									existingLeaveEntity.getTotalLeaveDays() != null ? existingLeaveEntity.getTotalLeaveDays()
											: 0);
							employeeLeavesMstDto.setYearId(yearId);
							employeeLeavesMstService.updateLeaveBalance(employeeLeavesMstDto,loginUser);
						}
						empLeaveDtlsResultSet.setStatus(true);
						empLeaveDtlsResultSet.setMessage(ApplicationConstants.SUCCESS);
						empLeaveDtlsResultSet.setMessageDescription("Leave Request Updated Successfully.");
					}
				} else {
					/*
					 * If it is not Lop or else Only Lop || If we have only single report for that
					 * leave only.
					 */

					EmployeeLeavesMstResultSet empLvMstRs1 = employeeLeavesMstService.getLeaveBalById(employeeLeaveDtlsDto.getEmployeeId(), employeeLeaveDtlsDto.getLeaveTypeId(), statusLookupIdMap.get(ApplicationConstants.ACTIVE));
					EmployeeLeavesMstEntity empLvMstEntity1 = empLvMstRs1.getEmployeeLeavesMstEntity();

					Double noOfLeaveDays = employeeLeaveDtlsDto.getTotalLeaveDays() - existingLeaveEntity.getTotalLeaveDays();
					Double remainingLeaves2 = empLvMstEntity1.getTotalLeaveBalance() - noOfLeaveDays;
					if (existingLeaveEntity.getLeaveStatusLookupId().equals(leaveStatusMap.get(ApplicationConstants.REQUESTED)) || existingLeaveEntity.getLeaveStatusLookupId().equals(leaveStatusMap.get(ApplicationConstants.UPDATE_REQUESTED))) {
						if (remainingLeaves2 < 0) {
							EmployeeLeaveDetailsEntity empLeaveDetailsEntity = new EmployeeLeaveDetailsEntity();
							BeanUtils.copyProperties(employeeLeaveDtlsDto, empLeaveDetailsEntity);
							empLeaveDetailsEntity.setEmployeeLeaveId(cmnTableSeqService.getNextSequence(ApplicationConstants.EMPLOYEE_LEAVE_DETAILS, ApplicationConstants.EMPLOYEE_LEAVE_ID));
							empLeaveDetailsEntity.setLeaveTypeId(leaveTypesMap.get(ApplicationConstants.LOP));
							empLeaveDetailsEntity.setTotalLeaveDays(Math.abs(remainingLeaves2));
							empLeaveDetailsEntity.setYearId(yearId);
							BaseUtils.setBaseData(empLeaveDetailsEntity,loginUser);
							empLeaveDetailsEntity.setReason(employeeLeaveDtlsDto.getReason());
							save(empLeaveDetailsEntity);
							employeeLeaveDtlsDto.setChildEmpLvId(empLeaveDetailsEntity.getEmployeeLeaveId());

							employeeLeavesMstDto.setLvBalChange(true);
							employeeLeavesMstDto.setEmployeeId(empLeaveDetailsEntity.getEmployeeId());
							employeeLeavesMstDto.setLeaveTypeId(empLeaveDetailsEntity.getLeaveTypeId());
							employeeLeavesMstDto.setNoOfDays(empLeaveDetailsEntity.getTotalLeaveDays());
							employeeLeavesMstDto.setYearId(yearId);
							employeeLeavesMstService.updateLeaveBalance(employeeLeavesMstDto,loginUser);

						}
						BeanUtils.copyProperties(existingLeaveEntity, selectLvData);
						BeanUtils.copyProperties(employeeLeaveDtlsDto, selectLvData);
						BaseUtils.modifyBaseData(selectLvData,loginUser);
						selectLvData.setReason(employeeLeaveDtlsDto.getReason());
						selectLvData.setTotalLeaveDays(
								remainingLeaves2 < 0 ? employeeLeaveDtlsDto.getTotalLeaveDays() + remainingLeaves2
										: employeeLeaveDtlsDto.getTotalLeaveDays());
						save(selectLvData);
					}
					if (existingLeaveEntity.getLeaveStatusLookupId().equals(leaveStatusMap.get(ApplicationConstants.APPROVED))) {
						existingLeaveEntity.setStatusLookupId((statusLookupIdMap.get(ApplicationConstants.INACTIVE)));
						save(existingLeaveEntity); // status inactive for the previous request

						employeeLeavesMstDto.setLvBalChange(true);
						employeeLeavesMstDto.setEmployeeId(employeeLeaveDtlsDto.getEmployeeId());
						employeeLeavesMstDto.setLeaveTypeId(existingLeaveEntity.getLeaveTypeId());
						employeeLeavesMstDto.setNoOfDays(-existingLeaveEntity.getTotalLeaveDays());
						employeeLeavesMstDto.setYearId(yearId);
						employeeLeavesMstService.updateLeaveBalance(employeeLeavesMstDto,loginUser);

						Long updatedNewChildLopId = 0L;
						if (remainingLeaves2 < 0) {
							EmployeeLeaveDetailsEntity newUpdLopRecord = new EmployeeLeaveDetailsEntity();
							BeanUtils.copyProperties(existingLeaveEntity, newUpdLopRecord);
							BeanUtils.copyProperties(employeeLeaveDtlsDto, newUpdLopRecord);
							newUpdLopRecord.setEmployeeLeaveId(cmnTableSeqService.getNextSequence(ApplicationConstants.EMPLOYEE_LEAVE_DETAILS, ApplicationConstants.EMPLOYEE_LEAVE_ID));
							newUpdLopRecord.setParentEmpLeaveId(existingLeaveEntity.getEmployeeLeaveId());
							newUpdLopRecord.setLeaveStatusLookupId(leaveStatusMap.get(ApplicationConstants.UPDATE_REQUESTED));
							newUpdLopRecord.setTotalLeaveDays(Math.abs(remainingLeaves2));
							newUpdLopRecord.setLeaveTypeId(leaveTypesMap.get(ApplicationConstants.LOP));
							newUpdLopRecord.setYearId(yearId);
//							BaseUtils.setBaseData(newUpdLopRecord,statusLookupIdMap.get(employeeLeaveDtlsDto.getStatusLookupId()));
							BaseUtils.setBaseData(newUpdLopRecord,loginUser);
							save(newUpdLopRecord);
							updatedNewChildLopId = newUpdLopRecord.getEmployeeLeaveId();

							employeeLeavesMstDto.setLvBalChange(true);
							employeeLeavesMstDto.setEmployeeId(newUpdLopRecord.getEmployeeId());
							employeeLeavesMstDto.setLeaveTypeId(newUpdLopRecord.getLeaveTypeId());
							employeeLeavesMstDto.setNoOfDays(newUpdLopRecord.getTotalLeaveDays());
							employeeLeavesMstDto.setYearId(yearId);
							employeeLeavesMstService.updateLeaveBalance(employeeLeavesMstDto,loginUser);
						}
						EmployeeLeaveDetailsEntity updateEmpLeaveDtlsEntity = new EmployeeLeaveDetailsEntity();
						BeanUtils.copyProperties(employeeLeaveDtlsDto, updateEmpLeaveDtlsEntity);
						updateEmpLeaveDtlsEntity.setEmployeeLeaveId(cmnTableSeqService.getNextSequence(ApplicationConstants.EMPLOYEE_LEAVE_DETAILS, ApplicationConstants.EMPLOYEE_LEAVE_ID));
						updateEmpLeaveDtlsEntity.setReason(employeeLeaveDtlsDto.getReason());
						updateEmpLeaveDtlsEntity.setParentEmpLeaveId(employeeLeaveDtlsDto.getEmployeeLeaveId());
						updateEmpLeaveDtlsEntity.setLeaveStatusLookupId(leaveStatusMap.get(ApplicationConstants.UPDATE_REQUESTED));
						updateEmpLeaveDtlsEntity.setYearId(yearId);
						updateEmpLeaveDtlsEntity.setTotalLeaveDays(
								remainingLeaves2 < 0 ? employeeLeaveDtlsDto.getTotalLeaveDays() + remainingLeaves2
										: employeeLeaveDtlsDto.getTotalLeaveDays());
//						BaseUtils.setBaseData(updateEmpLeaveDtlsEntity,statusLookupIdMap.get(employeeLeaveDtlsDto.getStatusLookupId()));
						BaseUtils.setBaseData(updateEmpLeaveDtlsEntity,loginUser);
						if (updatedNewChildLopId != 0L) {
							updateEmpLeaveDtlsEntity.setChildEmpLvId(updatedNewChildLopId);
						}
						// Creating new record with the updated date
						save(updateEmpLeaveDtlsEntity);
					}
					// Updating Leave balance in EmployeeLeaveMst table
//					if (remainingLeaves2 < 0) {
						remainingLeaves2 = remainingLeaves2 < 0
								? employeeLeaveDtlsDto.getTotalLeaveDays() + remainingLeaves2
										: employeeLeaveDtlsDto.getTotalLeaveDays();
//					}
					employeeLeavesMstDto.setLvBalChange(true);
					employeeLeavesMstDto.setEmployeeId(employeeLeaveDtlsDto.getEmployeeId());
					employeeLeavesMstDto.setLeaveTypeId(employeeLeaveDtlsDto.getLeaveTypeId());
					employeeLeavesMstDto.setNoOfDays(remainingLeaves2);
					employeeLeavesMstDto.setYearId(yearId);
					employeeLeavesMstService.updateLeaveBalance(employeeLeavesMstDto,loginUser);
					empLeaveDtlsResultSet.setStatus(true);
					empLeaveDtlsResultSet.setMessage(ApplicationConstants.STATUS);
					empLeaveDtlsResultSet.setMessageDescription("Leave Request Updated Successfully.");
				}

			} else {
				/*
				 * Creating new leave request. Checking for Loss of pay or not by comparing
				 * available leaves in employee_leaves_mst table
				 */
				if(EmpLeaveDetailsList.size()>0) {
					empLeaveDtlsResultSet.setStatus(false);
					empLeaveDtlsResultSet.setMessage("Failed");
					empLeaveDtlsResultSet.setMessageDescription("Leave Request Applied already exist with same Date.");
					return empLeaveDtlsResultSet;
				}
				EmployeeLeavesMstResultSet empLvMstRs = employeeLeavesMstService.getLeaveBalById(employeeLeaveDtlsDto.getEmployeeId(), employeeLeaveDtlsDto.getLeaveTypeId(), statusLookupIdMap.get(ApplicationConstants.ACTIVE));
				EmployeeLeavesMstEntity empLvMstEntity = empLvMstRs.getEmployeeLeavesMstEntity();
				// Creating new Leave request
				Double remainingLeaves = empLvMstEntity.getTotalLeaveBalance()
						- employeeLeaveDtlsDto.getTotalLeaveDays();
				Long childLvId = 0L;
				if (remainingLeaves < 0) {
					// Creating new record record for loss of pay
					EmployeeLeaveDetailsEntity empLopIns = new EmployeeLeaveDetailsEntity();
					BeanUtils.copyProperties(employeeLeaveDtlsDto, empLopIns);
					empLopIns.setEmployeeLeaveId(cmnTableSeqService.getNextSequence(ApplicationConstants.EMPLOYEE_LEAVE_DETAILS, ApplicationConstants.EMPLOYEE_LEAVE_ID));
					empLopIns.setTotalLeaveDays(Math.abs(remainingLeaves));
					empLopIns.setLeaveTypeId(leaveTypesMap.get(ApplicationConstants.LOP));
//					empLopIns.setApproverId(empLvPlanApprMapResultset.getEmpLeavePlApprMapEntity().getApprover1Id());
			        empLopIns.setYearId(yearId);
					empLopIns.setStatusLookupId(statusLookupIdMap.get(ApplicationConstants.ACTIVE));
					empLopIns.setLeaveStatusLookupId(leaveStatusMap.get(ApplicationConstants.REQUESTED));
					
//					BaseUtils.setBaseData(empLopIns, statusLookupIdMap.get(ApplicationConstants.ACTIVE));
					BaseUtils.setBaseData(empLopIns,loginUser);
					save(empLopIns);
					childLvId = empLopIns.getEmployeeLeaveId();
					// Updating Leave balance in EmployeeLeaveMst table
					employeeLeavesMstDto.setLvBalChange(true);
					employeeLeavesMstDto.setEmployeeId(empLopIns.getEmployeeId());
					employeeLeavesMstDto.setLeaveTypeId(empLopIns.getLeaveTypeId());
					employeeLeavesMstDto.setNoOfDays(empLopIns.getTotalLeaveDays());
					employeeLeavesMstDto.setYearId(yearId);
					employeeLeavesMstService.updateLeaveBalance(employeeLeavesMstDto,loginUser);
				}
				// Creating new Leave request with the leave type
				if (empLvMstEntity.getTotalLeaveBalance() > 0) {

					employeeLeaveDtlsDto.setEmployeeLeaveId(cmnTableSeqService.getNextSequence(ApplicationConstants.EMPLOYEE_LEAVE_DETAILS, ApplicationConstants.EMPLOYEE_LEAVE_ID));
					BeanUtils.copyProperties(employeeLeaveDtlsDto, selectLvData);
					selectLvData.setStatusLookupId(statusLookupIdMap.get(ApplicationConstants.ACTIVE));
					if (childLvId != 0 && childLvId > 0) {
						selectLvData.setChildEmpLvId(childLvId);
					}
					selectLvData.setYearId(yearId);
					selectLvData.setLeaveStatusLookupId(leaveStatusMap.get(ApplicationConstants.REQUESTED));
					selectLvData.setTotalLeaveDays(
							remainingLeaves < 0 ? employeeLeaveDtlsDto.getTotalLeaveDays() + remainingLeaves
									: employeeLeaveDtlsDto.getTotalLeaveDays());
//					BaseUtils.setBaseData(selectLvData, statusLookupIdMap.get(ApplicationConstants.ACTIVE));
					BaseUtils.setBaseData(selectLvData,loginUser);
					save(selectLvData);

					// Updating Leave balance in EmployeeLeaveMst table
					employeeLeavesMstDto.setLvBalChange(true);
					employeeLeavesMstDto.setEmployeeId(employeeLeaveDtlsDto.getEmployeeId());
					employeeLeavesMstDto.setLeaveTypeId(employeeLeaveDtlsDto.getLeaveTypeId());
					employeeLeavesMstDto.setNoOfDays(selectLvData.getTotalLeaveDays());
					employeeLeavesMstDto.setYearId(yearId);
					employeeLeavesMstService.updateLeaveBalance(employeeLeavesMstDto,loginUser);
					empLeaveDtlsResultSet.setStatus(true);
					empLeaveDtlsResultSet.setMessage(ApplicationConstants.SUCCESS);
					empLeaveDtlsResultSet.setMessageDescription("Leave Request Applied Successfully.");
				}
			}
		} catch (Exception e) {
		empLeaveDtlsResultSet.setStatus(false);
		empLeaveDtlsResultSet.setMessage(ApplicationConstants.EXCEPTION);
		empLeaveDtlsResultSet.setMessageDescription(e.getMessage());
	}
	return empLeaveDtlsResultSet;
}

// User Cancel Request
//@Override
//public EmployeeLeaveDetailsResultSet cancelLeaveRequest(EmployeeLeaveDetailsDTO employeeLeaveDtlsDto) {
//	EmployeeLeaveDetailsResultSet empLeaveDtlsResultSet = new EmployeeLeaveDetailsResultSet();
//	EmployeeLeaveDetailsEntity empLeaveDtlsEntity = new EmployeeLeaveDetailsEntity();
//	EmployeeLeaveDetailsEntity lopLeaveRecord = null;
//	EmployeeLeavesMstDTO employeeLeavesMstDto = new EmployeeLeavesMstDTO();
//	Double leavesUsed = 0.0;
//
//	try {
//		Long childEmpLvId = 0L;
//		if (employeeLeaveDtlsDto.getChildEmpLvId() != null) {
//			childEmpLvId = employeeLeaveDtlsDto.getChildEmpLvId();
//			lopLeaveRecord = new EmployeeLeaveDetailsEntity();
//			lopLeaveRecord = employeeLeaveDetailsRepository.getById(childEmpLvId);
//			leavesUsed = lopLeaveRecord.getTotalLeaveDays();
//		}
//		empLeaveDtlsEntity = employeeLeaveDetailsRepository.getById(employeeLeaveDtlsDto.getEmployeeLeaveId());
//		Map<String, Long> leaveStatusMap = cmnLookupMstService.getLookupNameAndLookupIdMapings(ApplicationConstants.LEAVE_STATUS);
//		Map<String, Long> statusLookupIdMap = cmnLookupMstService.getLookupNameAndLookupIdMapings(ApplicationConstants.STATUS);
//
//		// When Leave is not approved
//		if (empLeaveDtlsEntity.getLeaveStatusLookupId() == leaveStatusMap.get(ApplicationConstants.REQUESTED)) {
//			/*
//			 * If Lop record is present It is modified to inactive
//			 */
//			if (lopLeaveRecord != null) {
//				lopLeaveRecord.setLeaveStatusLookupId(leaveStatusMap.get(ApplicationConstants.CANCELLED));
//				lopLeaveRecord.setStatusLookupId(statusLookupIdMap.get(ApplicationConstants.INACTIVE));
//				BaseUtils.modifyBaseData(lopLeaveRecord);
//				save(lopLeaveRecord);
//				// updating LOP usage in EmployeeLeaveMst table
//				employeeLeavesMstDto.setLvBalChange(false);
//				employeeLeavesMstDto.setEmployeeId(lopLeaveRecord.getEmployeeId());
//				employeeLeavesMstDto.setLvTypeLookupId(lopLeaveRecord.getLvTypeLookupId());
//				employeeLeavesMstDto.setNoOfDays(lopLeaveRecord.getTotalLeaveDays());
//				employeeLeavesMstService.updateLeaveBalance(employeeLeavesMstDto);
//			}
//			empLeaveDtlsEntity.setLeaveStatusLookupId(leaveStatusMap.get(ApplicationConstants.CANCELLED));
//			//				BeanUtils.copyProperties(employeeLeaveDtlsDto, empLeaveDtlsEntity);
//			empLeaveDtlsEntity.setStatusLookupId(statusLookupIdMap.get(ApplicationConstants.INACTIVE));
//			BaseUtils.modifyBaseData(empLeaveDtlsEntity);
//			save(empLeaveDtlsEntity);
//			// updating Leave balance in EmployeeLeaveMst table
//			leavesUsed = leavesUsed == 0 ? empLeaveDtlsEntity.getTotalLeaveDays()
//					: empLeaveDtlsEntity.getTotalLeaveDays() - leavesUsed;
//			employeeLeavesMstDto.setLvBalChange(false);
//			employeeLeavesMstDto.setEmployeeId(employeeLeaveDtlsDto.getEmployeeId());
//			employeeLeavesMstDto.setLvTypeLookupId(employeeLeaveDtlsDto.getLvTypeLookupId());
//			employeeLeavesMstDto.setNoOfDays(leavesUsed);
//			employeeLeavesMstService.updateLeaveBalance(employeeLeavesMstDto);
//		}
//		// if Leave Approved or Leave Update Requested or Leave Update Approved
//		if (empLeaveDtlsEntity.getLeaveStatusLookupId() == leaveStatusMap.get(ApplicationConstants.APPROVED)
//				|| empLeaveDtlsEntity.getLeaveStatusLookupId() == leaveStatusMap.get(ApplicationConstants.UPDATE_REJECTED)
//				|| empLeaveDtlsEntity.getLeaveStatusLookupId() == leaveStatusMap.get(ApplicationConstants.UPDATE_APPROVED)) {
//			empLeaveDtlsEntity.setLeaveStatusLookupId(leaveStatusMap.get(ApplicationConstants.CANCEL_REQUESTED));
//			if (lopLeaveRecord != null) {
//				lopLeaveRecord.setLeaveStatusLookupId(leaveStatusMap.get(ApplicationConstants.CANCEL_REQUESTED));
//				BaseUtils.modifyBaseData(lopLeaveRecord);
//				save(lopLeaveRecord);
//			}
//			BaseUtils.modifyBaseData(empLeaveDtlsEntity);
//			save(empLeaveDtlsEntity);
//		}
//
//	} catch (Exception e) {
//		empLeaveDtlsResultSet.setStatus(false);
//		empLeaveDtlsResultSet.setMessage(ApplicationConstants.EXCEPTION);
//		empLeaveDtlsResultSet.setMessageDescription(e.getMessage());
//	}
//	return empLeaveDtlsResultSet;
//}

@Override
public EmployeeLeaveDetailsResultSet approveRequest(EmployeeLeaveDetailsDTO employeeLeaveDtlsDto,String logginUser) {
	EmployeeLeaveDetailsResultSet empLeaveDtlsResultSet = new EmployeeLeaveDetailsResultSet();
	EmployeeLeaveDetailsEntity empLeaveDtlsEntity = new EmployeeLeaveDetailsEntity();
	EmployeeLeaveDetailsEntity lopLeavesDtlsEntity = null;
	EmployeeLeavesMstDTO employeeLeavesMstDto = null;

	try {
		Map<String, Long> leaveStatusMap = cmnLookupMstService.getLookupNameAndLookupIdMapingsByParentLookupName(ApplicationConstants.LEAVE_STATUS);
		Map<String, Long> statusLookupIdMap = cmnLookupMstService.getLookupNameAndLookupIdMapingsByParentLookupName(ApplicationConstants.STATUS);


		empLeaveDtlsEntity = employeeLeaveDetailsRepository.getById(employeeLeaveDtlsDto.getEmployeeLeaveId());

		if (employeeLeaveDtlsDto.getChildEmpLvId() != null) {
			lopLeavesDtlsEntity = new EmployeeLeaveDetailsEntity();
			lopLeavesDtlsEntity = employeeLeaveDetailsRepository.getById(employeeLeaveDtlsDto.getChildEmpLvId());
		}
		if (empLeaveDtlsEntity != null) {
			// new Request
			if (empLeaveDtlsEntity != null && employeeLeaveDtlsDto.getLeaveStatusLookupId().equals(leaveStatusMap.get(ApplicationConstants.REQUESTED))) {
				if (ApplicationConstants.APPROVED.equalsIgnoreCase(employeeLeaveDtlsDto.getApprovalStatus())) {
					empLeaveDtlsEntity.setLeaveStatusLookupId(leaveStatusMap.get(ApplicationConstants.APPROVED));
					if (lopLeavesDtlsEntity != null) {
						lopLeavesDtlsEntity.setLeaveStatusLookupId(leaveStatusMap.get(ApplicationConstants.APPROVED));
					}
				} 
				else if (ApplicationConstants.REJECTED.equalsIgnoreCase(employeeLeaveDtlsDto.getApprovalStatus())) {
					empLeaveDtlsEntity.setLeaveStatusLookupId(leaveStatusMap.get(ApplicationConstants.REJECTED));
					if (lopLeavesDtlsEntity != null) {
						lopLeavesDtlsEntity.setLeaveStatusLookupId(leaveStatusMap.get(ApplicationConstants.REJECTED));
						lopLeavesDtlsEntity.setStatusLookupId(statusLookupIdMap.get(ApplicationConstants.INACTIVE));
						employeeLeavesMstDto = new EmployeeLeavesMstDTO();
						employeeLeavesMstDto.setEmployeeId(lopLeavesDtlsEntity.getEmployeeId());
						employeeLeavesMstDto.setLvBalChange(false);
						employeeLeavesMstDto.setLeaveTypeId(lopLeavesDtlsEntity.getLeaveTypeId());
						// Adding Leaves to EmpployeeLeaveMst table if request is Rejected.
						employeeLeavesMstDto.setNoOfDays(lopLeavesDtlsEntity.getTotalLeaveDays());
						employeeLeavesMstService.updateLeaveBalance(employeeLeavesMstDto,logginUser);
					}
					empLeaveDtlsEntity.setStatusLookupId(statusLookupIdMap.get(ApplicationConstants.INACTIVE));
					employeeLeavesMstDto = new EmployeeLeavesMstDTO();
					employeeLeavesMstDto.setEmployeeId(empLeaveDtlsEntity.getEmployeeId());
					employeeLeavesMstDto.setLvBalChange(false);
					employeeLeavesMstDto.setLeaveTypeId(employeeLeaveDtlsDto.getLeaveTypeId());
					// Adding Leaves to EmpployeeLeaveMst table if request is Rejected.
					employeeLeavesMstDto.setNoOfDays(empLeaveDtlsEntity.getTotalLeaveDays());
					employeeLeavesMstService.updateLeaveBalance(employeeLeavesMstDto,logginUser);
				}
				else {
					empLeaveDtlsEntity.setLeaveStatusLookupId(leaveStatusMap.get(ApplicationConstants.APPROVED));
				}
				BaseUtils.modifyBaseData(empLeaveDtlsEntity, logginUser);
				save(empLeaveDtlsEntity);
				if (lopLeavesDtlsEntity != null) {
					BaseUtils.modifyBaseData(lopLeavesDtlsEntity,logginUser);
					save(lopLeavesDtlsEntity);
				}
			}
			// Updated request
			if (employeeLeaveDtlsDto.getLeaveStatusLookupId() == leaveStatusMap.get(ApplicationConstants.UPDATE_REQUESTED)) {
				// Approving updated request
				if (ApplicationConstants.APPROVED.equalsIgnoreCase(employeeLeaveDtlsDto.getApprovalStatus())) {
					empLeaveDtlsEntity.setLeaveStatusLookupId(leaveStatusMap.get(ApplicationConstants.UPDATE_APPROVED));
					empLeaveDtlsEntity.setParentEmpLeaveId(null);
					save(empLeaveDtlsEntity);
					if (lopLeavesDtlsEntity != null) {
						lopLeavesDtlsEntity.setLeaveStatusLookupId(leaveStatusMap.get(ApplicationConstants.UPDATE_APPROVED));
						lopLeavesDtlsEntity.setParentEmpLeaveId(null);
						save(lopLeavesDtlsEntity);
					}
					// Removing previously Approved request
					EmployeeLeaveDetailsEntity previousRecord = employeeLeaveDetailsRepository.getById(employeeLeaveDtlsDto.getParentEmpLeaveId());
					delete(previousRecord);
					if (previousRecord.getChildEmpLvId() != null) {
						EmployeeLeaveDetailsEntity previousLopRecord = employeeLeaveDetailsRepository.getById(previousRecord.getChildEmpLvId());
						previousRecord.setChildEmpLvId(null);
						delete(previousLopRecord);
						previousRecord = null;
					}
				} else {
					EmployeeLeaveDetailsEntity previousLeaveRecord = employeeLeaveDetailsRepository.getById(empLeaveDtlsEntity.getParentEmpLeaveId());
					if (previousLeaveRecord.getChildEmpLvId() != null) {
						EmployeeLeaveDetailsEntity previousLopLeaveRecord = employeeLeaveDetailsRepository.getById(previousLeaveRecord.getChildEmpLvId());
						previousLopLeaveRecord.setStatusLookupId(statusLookupIdMap.get(employeeLeaveDtlsDto.getStatusLookupId()));
						BaseUtils.modifyBaseData(previousLopLeaveRecord,logginUser);
						save(previousLopLeaveRecord);
						if (lopLeavesDtlsEntity != null) {
							empLeaveDtlsEntity.setChildEmpLvId(null);
							lopLeavesDtlsEntity.setParentEmpLeaveId(null);
							delete(lopLeavesDtlsEntity);
							Double remainingLopLeaves = employeeLeaveDtlsDto.getLopLeaves() - previousLopLeaveRecord.getTotalLeaveDays();
							employeeLeavesMstDto = new EmployeeLeavesMstDTO();
							employeeLeavesMstDto.setEmployeeId(lopLeavesDtlsEntity.getEmployeeId());
							employeeLeavesMstDto.setLvBalChange(false);
							employeeLeavesMstDto.setLeaveTypeId(lopLeavesDtlsEntity.getLeaveTypeId());
							employeeLeavesMstDto.setNoOfDays(remainingLopLeaves);
							employeeLeavesMstService.updateLeaveBalance(employeeLeavesMstDto,logginUser);
						}
					}
					previousLeaveRecord.setStatusLookupId(statusLookupIdMap.get(employeeLeaveDtlsDto.getStatusLookupId()));
					BaseUtils.modifyBaseData(previousLeaveRecord,logginUser);
					save(previousLeaveRecord);
					delete(empLeaveDtlsEntity);

					Double remainingLeaves = employeeLeaveDtlsDto.getUsedleaves() - previousLeaveRecord.getTotalLeaveDays();
					employeeLeavesMstDto = new EmployeeLeavesMstDTO();
					employeeLeavesMstDto.setEmployeeId(empLeaveDtlsEntity.getEmployeeId());
					employeeLeavesMstDto.setLvBalChange(false);
					employeeLeavesMstDto.setLeaveTypeId(employeeLeaveDtlsDto.getLeaveTypeId());
					employeeLeavesMstDto.setNoOfDays(remainingLeaves);
					employeeLeavesMstService.updateLeaveBalance(employeeLeavesMstDto,logginUser);
				}
			}

			// Cancel Requested
			if (employeeLeaveDtlsDto.getLeaveStatusLookupId().equals(leaveStatusMap.get(ApplicationConstants.CANCEL_REQUESTED))) {
				// Cancel request approved

				if (ApplicationConstants.APPROVED.equalsIgnoreCase(employeeLeaveDtlsDto.getApprovalStatus())
						|| ApplicationConstants.UPDATE_APPROVED.equalsIgnoreCase(employeeLeaveDtlsDto.getApprovalStatus())) {
					empLeaveDtlsEntity.setLeaveStatusLookupId(leaveStatusMap.get(ApplicationConstants.CANCEL_APPROVED));
					BaseUtils.modifyBaseData(empLeaveDtlsEntity,logginUser);
					save(empLeaveDtlsEntity);
					// updateLeaveBalance
					employeeLeavesMstDto = new EmployeeLeavesMstDTO();
					employeeLeavesMstDto.setLvBalChange(false);
					employeeLeavesMstDto.setEmployeeId(employeeLeaveDtlsDto.getEmployeeId());
					employeeLeavesMstDto.setLeaveTypeId(employeeLeaveDtlsDto.getLeaveTypeId());
					employeeLeavesMstDto.setNoOfDays(employeeLeaveDtlsDto.getTotalLeaveDays());
					employeeLeavesMstService.updateLeaveBalance(employeeLeavesMstDto,logginUser);
					if( lopLeavesDtlsEntity != null) {
						lopLeavesDtlsEntity.setLeaveStatusLookupId(leaveStatusMap.get(ApplicationConstants.CANCEL_APPROVED));
						BaseUtils.modifyBaseData(lopLeavesDtlsEntity,logginUser);
						save(lopLeavesDtlsEntity);
						// updateLeaveBalance
						employeeLeavesMstDto.setLvBalChange(false);
						employeeLeavesMstDto.setEmployeeId(employeeLeaveDtlsDto.getEmployeeId());
						employeeLeavesMstDto.setLeaveTypeId(lopLeavesDtlsEntity.getLeaveTypeId());
						employeeLeavesMstDto.setNoOfDays(lopLeavesDtlsEntity.getTotalLeaveDays());
						employeeLeavesMstService.updateLeaveBalance(employeeLeavesMstDto,logginUser);
					}
				} else {
					// If Cancel Request Rejected
					empLeaveDtlsEntity.setLeaveStatusLookupId(leaveStatusMap.get(ApplicationConstants.CANCEL_REJECTED));
					save(empLeaveDtlsEntity);
					if(lopLeavesDtlsEntity!= null) {
						lopLeavesDtlsEntity.setLeaveStatusLookupId(leaveStatusMap.get(ApplicationConstants.CANCEL_REJECTED));
						save(lopLeavesDtlsEntity);
					}
				}
			}
		} else {
			empLeaveDtlsResultSet.setStatus(false);
			empLeaveDtlsResultSet.setMessage(ApplicationConstants.ERROR);
			empLeaveDtlsResultSet.setMessageDescription("Leave details with given LeaveId do not exists.");
		}

	} catch (Exception e) {
		empLeaveDtlsResultSet.setStatus(false);
		empLeaveDtlsResultSet.setMessage(ApplicationConstants.EXCEPTION);
		empLeaveDtlsResultSet.setMessageDescription(e.getMessage());
	}
	return empLeaveDtlsResultSet;
}

//@Override
//public EmployeeLeaveDetailsResultSet getEmployeeLeaveRequestsByUserId(String username) {
//	AppUserMstEntity appUserMst = aumRepository.getAppUserByUserName(username);
//	EmployeeLeaveDetailsResultSet employeeLeaveDetailsResultSet=new EmployeeLeaveDetailsResultSet();
//	Long empid=appUserMst.getEmployeeId();
//	employeeLeaveDetailsResultSet=getEmployeeLeaveRequests(empid);
//	return employeeLeaveDetailsResultSet;
//}

}
