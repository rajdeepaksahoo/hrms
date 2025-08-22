package org.vrnda.hrms.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.vrnda.hrms.entity.CmnMenusMstEntity;
import org.vrnda.hrms.entity.EmployeeLeaveDetailsEntity;
import org.vrnda.hrms.entity.EmployeeLoanDetailsEntity;
import org.vrnda.hrms.repository.EmployeeLeaveDetailsRepository;
import org.vrnda.hrms.repository.dto.CmnAppRoleAccessPrivilegesDTO;
import org.vrnda.hrms.repository.dto.EmployeeLeaveDetailsDTO;
import org.vrnda.hrms.repository.dto.EmployeeLoanDetailsDTO;
import org.vrnda.hrms.service.DashboadService;
import org.vrnda.hrms.service.EmployeeLeaveDetailsService;
import org.vrnda.hrms.service.resultset.EmployeeLeaveDetailsResultSet;
import org.vrnda.hrms.utils.ApplicationConstants;

@Service
@EnableScheduling
public class DashBoadServiceImpl extends GenericServiceImpl<EmployeeLeaveDetailsEntity, Long>
		implements DashboadService {

	public DashBoadServiceImpl(PagingAndSortingRepository<EmployeeLeaveDetailsEntity, Long> typeRepository) {
		super(typeRepository);
		// TODO Auto-generated constructor stub
	}

	@Autowired
	EmployeeLeaveDetailsRepository employeeLeaveDetailsRepository;
	
	

	@Override
	public  EmployeeLeaveDetailsResultSet getEmployeeOnLeaveDatails(Long employeeId) {
		EmployeeLeaveDetailsResultSet employeeLeaveDetailsResultSet=new EmployeeLeaveDetailsResultSet();
		EmployeeLeaveDetailsResultSet empLeaveDtlsResultSet = new EmployeeLeaveDetailsResultSet();
		List<EmployeeLeaveDetailsDTO> empLeaveDetailsDtoList = new ArrayList<EmployeeLeaveDetailsDTO>();
		 List<Object[]> resultObject =
				 employeeLeaveDetailsRepository.getDashBoadOnLeaveDetails(employeeId);
		 try {
		 for(Object[] obj:resultObject) {
			 EmployeeLeaveDetailsDTO employeeLeaveDetailsDTO = new EmployeeLeaveDetailsDTO();
			 employeeLeaveDetailsDTO.setEmployeeFullName(obj[0].toString());
			 employeeLeaveDetailsDTO.setLeaveFromDate((Date) obj[1]);
			 employeeLeaveDetailsDTO.setLeaveToDate((Date) obj[2]);
			 empLeaveDetailsDtoList.add(employeeLeaveDetailsDTO);
		 }
		 empLeaveDtlsResultSet.setEmployeeLeaveDetailsDtoList(empLeaveDetailsDtoList);
		 } catch (Exception e) {
				empLeaveDtlsResultSet.setStatus(false);
				empLeaveDtlsResultSet.setMessageDescription(e.getMessage());
			}
			return empLeaveDtlsResultSet;
	}



	@Override
	public EmployeeLeaveDetailsResultSet getDashBoadLeaveRequestsDetails(Long employeeId) {
		EmployeeLeaveDetailsResultSet employeeLeaveDetailsResultSet=new EmployeeLeaveDetailsResultSet();
		EmployeeLeaveDetailsResultSet empLeaveDtlsResultSet = new EmployeeLeaveDetailsResultSet();
		List<EmployeeLeaveDetailsDTO> empLeaveDetailsDtoList = new ArrayList<EmployeeLeaveDetailsDTO>();
		 List<Object[]> resultObject =
				 employeeLeaveDetailsRepository.getDashBoadLeaveRequestsDetails(employeeId);
		 try {
		 for(Object[] obj:resultObject) {
			 EmployeeLeaveDetailsDTO employeeLeaveDetailsDTO = new EmployeeLeaveDetailsDTO();
			 employeeLeaveDetailsDTO.setLeaveFromDate((Date) obj[0]);
			 employeeLeaveDetailsDTO.setTotalLeaveDays((Double) obj[1]);
			 employeeLeaveDetailsDTO.setDesignation((String) obj[2]);
			 empLeaveDetailsDtoList.add(employeeLeaveDetailsDTO);
		 }
		 empLeaveDtlsResultSet.setEmployeeLeaveDetailsDtoList(empLeaveDetailsDtoList);
		 } catch (Exception e) {
				empLeaveDtlsResultSet.setStatus(false);
				empLeaveDtlsResultSet.setMessageDescription(e.getMessage());
			}
			return empLeaveDtlsResultSet;
	}

}
