package org.vrnda.hrms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.vrnda.hrms.entity.CmnLeaveTypesMstEntity;
import org.vrnda.hrms.entity.EmployeeLeavesMstEntity;
import org.vrnda.hrms.repository.CmnLeaveTypesMstRepository;
import org.vrnda.hrms.repository.EmployeeLeavesMstRepository;
import org.vrnda.hrms.repository.dto.EmployeeAllLeaveBalanceDto;
import org.vrnda.hrms.service.EmployeeLeaveBalanceService;
import org.vrnda.hrms.service.resultset.EmployeeLeaveBalanceResultset;

@Service
public class EmployeeLeaveBalanceServiceImpl extends GenericServiceImpl<CmnLeaveTypesMstEntity, String>
		implements EmployeeLeaveBalanceService {

	@Autowired
	CmnLeaveTypesMstRepository cmnLeaveTypesMstRepository;

	@Autowired
	EmployeeLeavesMstRepository employeeLeaveMstRepository;

	public EmployeeLeaveBalanceServiceImpl(PagingAndSortingRepository<CmnLeaveTypesMstEntity, String> typeRepository) {
		super(typeRepository);
		// TODO Auto-generated constructor stub
	}

	public EmployeeLeaveBalanceResultset getAllEmpLeaveBalance(String currentYear, Long activeFlagId, Long employeeId) {
		EmployeeLeaveBalanceResultset employeeLeaveBalanceResultset = new EmployeeLeaveBalanceResultset();
		List<EmployeeAllLeaveBalanceDto> employeeAllLeaveBalanceDtoList = new ArrayList<EmployeeAllLeaveBalanceDto>();
		try {
			List<CmnLeaveTypesMstEntity> leaveTypesEntityList = cmnLeaveTypesMstRepository.getLeaveType(currentYear, activeFlagId);
			if (leaveTypesEntityList != null && leaveTypesEntityList.size() > 0) {
				List<EmployeeLeavesMstEntity> employeeLeaveBalanceEntityList = employeeLeaveMstRepository
						.getAllEmpLeaveBalance(currentYear, activeFlagId, employeeId);
				if (employeeLeaveBalanceEntityList != null && employeeLeaveBalanceEntityList.size() > 0) {
					leaveTypesEntityList.forEach(obj -> {
						EmployeeAllLeaveBalanceDto allLeaveBalanceDto = new EmployeeAllLeaveBalanceDto();
						allLeaveBalanceDto.setLeaveTypeId(obj.getLeaveTypeId());
						allLeaveBalanceDto.setLeaveDescription(obj.getLeaveTypeDescription());
						allLeaveBalanceDto.setLeaveName(obj.getLeaveTypeName());
//						allLeaveBalanceDto.setYearId(obj.getYearId());

						EmployeeLeavesMstEntity employeeLeavesMstEntity = employeeLeaveBalanceEntityList
								.stream()
								.filter(a -> a.getLeaveTypeId().equals(allLeaveBalanceDto.getLeaveTypeId()))
								.findAny()
								.orElse(null);
						allLeaveBalanceDto.setTotalLeaveBalance(employeeLeavesMstEntity != null && employeeLeavesMstEntity.getTotalLeaveBalance()!=null ? employeeLeavesMstEntity.getTotalLeaveBalance() : 0L);
						employeeAllLeaveBalanceDtoList.add(allLeaveBalanceDto);
					});
					employeeLeaveBalanceResultset.setEmployeeAllLeaveBalanceDtoList(employeeAllLeaveBalanceDtoList);
				}else {
					leaveTypesEntityList.forEach(obj -> {
						EmployeeAllLeaveBalanceDto allLeaveBalanceDto = new EmployeeAllLeaveBalanceDto();
						allLeaveBalanceDto.setLeaveTypeId(obj.getLeaveTypeId());
						allLeaveBalanceDto.setLeaveDescription(obj.getLeaveTypeDescription());
						allLeaveBalanceDto.setLeaveName(obj.getLeaveTypeName());
//						allLeaveBalanceDto.setYearId(obj.getYearId());
						allLeaveBalanceDto.setTotalLeaveBalance(new Integer(0) .doubleValue());
						employeeAllLeaveBalanceDtoList.add(allLeaveBalanceDto);
					});
					employeeLeaveBalanceResultset.setEmployeeAllLeaveBalanceDtoList(employeeAllLeaveBalanceDtoList);
				}
			}
			else {
				employeeLeaveBalanceResultset.setStatus(false);
				employeeLeaveBalanceResultset.setMessage("Failed");
				employeeLeaveBalanceResultset.setMessageDescription("Leave Types data is empty");
			}
		} catch (Exception e) {
			employeeLeaveBalanceResultset.setStatus(false);
			employeeLeaveBalanceResultset.setMessage("Error");
			employeeLeaveBalanceResultset.setMessageDescription(e.getMessage());
		}
		return employeeLeaveBalanceResultset;
	}
}
