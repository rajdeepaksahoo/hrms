package org.vrnda.hrms.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.vrnda.hrms.entity.EmployeeCheckinCheckoutEntity;
import org.vrnda.hrms.repository.EmployeeCheckinCheckoutRepository;
import org.vrnda.hrms.repository.dto.EmployeeCheckinCheckoutDTO;
import org.vrnda.hrms.repository.dto.EmployeeTimesheetsDTO;
import org.vrnda.hrms.service.CmnTableSeqService;
import org.vrnda.hrms.service.EmployeeCheckinCheckoutService;
import org.vrnda.hrms.service.EmployeeTimesheetsService;
import org.vrnda.hrms.service.resultset.EmployeeCheckinCheckoutResultSet;
import org.vrnda.hrms.service.resultset.EmployeeTimesheetsResultSet;
import org.vrnda.hrms.utils.ApplicationConstants;
import org.vrnda.hrms.utils.BaseUtils;

@Service
public class EmployeeCheckinCheckoutServiceImpl extends GenericServiceImpl<EmployeeCheckinCheckoutEntity, Long>
		implements EmployeeCheckinCheckoutService {

	public EmployeeCheckinCheckoutServiceImpl(
			PagingAndSortingRepository<EmployeeCheckinCheckoutEntity, Long> typeRepository) {
		super(typeRepository);
		// TODO Auto-generated constructor stub
	}

	@Autowired
	EmployeeCheckinCheckoutRepository employeeCheckinCheckoutRepository;

	@Autowired
	EmployeeTimesheetsService empTimesheetsService;

	@Autowired
	CmnTableSeqService cmnTableSeqService;

	@Override
	public EmployeeCheckinCheckoutResultSet getEmployeeCheckinDetails(Long employeeId) {
		EmployeeCheckinCheckoutResultSet empCheckinCheckoutDetails = new EmployeeCheckinCheckoutResultSet();
		List<EmployeeCheckinCheckoutDTO> employeeCheckinDtoList = new ArrayList<EmployeeCheckinCheckoutDTO>();
		try {
			List<EmployeeCheckinCheckoutEntity> empCheckinEntity = new ArrayList<EmployeeCheckinCheckoutEntity>();
			empCheckinEntity = employeeCheckinCheckoutRepository.getEmployeeCheckinDetails(employeeId);
			if (empCheckinEntity != null && !empCheckinEntity.isEmpty()) {
				List<Long> empCheckIns = new ArrayList<Long>();
				for (EmployeeCheckinCheckoutEntity obj : empCheckinEntity) {
					EmployeeCheckinCheckoutDTO empDtoObj = new EmployeeCheckinCheckoutDTO();
					BeanUtils.copyProperties(obj, empDtoObj);
					employeeCheckinDtoList.add(empDtoObj);
					empCheckIns.add(empDtoObj.getEmpCheckinId());
				}
				if (empCheckIns.size() > 1) { // Previous Check-In TimeSheet details of same day to validate Time
												// OverLap in Client Side
					EmployeeTimesheetsResultSet employeeTimesheetsResultSet = empTimesheetsService
							.getAllEmployeeTimeSheetDataBasedOnCheckIns(empCheckIns);
					if (employeeTimesheetsResultSet != null) {
						empCheckinCheckoutDetails.setEmployeeTimesheetsDtoList(
								employeeTimesheetsResultSet.getEmployeeTimesheetsDtoList());
					}
				}
				empCheckinCheckoutDetails.setEmployeeCheckinCheckoutDtoList(employeeCheckinDtoList);
			}
		} catch (Exception e) {
			empCheckinCheckoutDetails.setStatus(false);
			empCheckinCheckoutDetails.setMessage(ApplicationConstants.FAILED);
			empCheckinCheckoutDetails.setMessageDescription("Unable to Load Employee Check-In Details");
		}
		return empCheckinCheckoutDetails;
	}

	@Override
	public EmployeeCheckinCheckoutResultSet employeeCheckIn(EmployeeCheckinCheckoutDTO employeeCheckinCheckoutDTO,
			String loggedInUser) {
		EmployeeCheckinCheckoutResultSet employeeCheckinCheckoutResultSet = new EmployeeCheckinCheckoutResultSet();
		try {

			if (employeeCheckinCheckoutDTO != null) {
				EmployeeCheckinCheckoutEntity employeeCheckinCheckoutEntity = new EmployeeCheckinCheckoutEntity();
				BeanUtils.copyProperties(employeeCheckinCheckoutDTO, employeeCheckinCheckoutEntity);
				employeeCheckinCheckoutEntity.setEmpCheckinId(cmnTableSeqService.getNextSequence(
						ApplicationConstants.EMPLOYEE_CHECKIN_CHECKOUT, ApplicationConstants.EMP_CHECKIN_ID));
				employeeCheckinCheckoutEntity.setCheckinTime(BaseUtils.getCurrentTime());
				BaseUtils.setBaseData(employeeCheckinCheckoutEntity, loggedInUser);
				save(employeeCheckinCheckoutEntity);
			} else {
				employeeCheckinCheckoutResultSet.setStatus(false);
				employeeCheckinCheckoutResultSet.setMessage(ApplicationConstants.FAILED);
				employeeCheckinCheckoutResultSet.setMessageDescription("Unable to Check-In");
			}

		} catch (Exception e) {
			employeeCheckinCheckoutResultSet.setStatus(false);
			employeeCheckinCheckoutResultSet.setMessage(ApplicationConstants.FAILED);
			employeeCheckinCheckoutResultSet.setMessageDescription("Unable to Check-In");
		}
		return employeeCheckinCheckoutResultSet;
	}

	@Transactional(rollbackOn = Exception.class)
	@Override
	public EmployeeCheckinCheckoutResultSet employeeCheckOut(List<EmployeeTimesheetsDTO> employeeTimesheetsDTO,
			String loggedInUser) {
		EmployeeCheckinCheckoutResultSet employeeCheckinCheckoutResultSet = new EmployeeCheckinCheckoutResultSet();
		try {
			if (employeeTimesheetsDTO != null) {

				EmployeeTimesheetsResultSet employeeTimesheetsResultSet = empTimesheetsService
						.saveTimesheets(employeeTimesheetsDTO, loggedInUser);
				if (employeeTimesheetsResultSet != null && employeeTimesheetsResultSet.getStatus()) {
					List<EmployeeCheckinCheckoutEntity> empCheckinEntity = employeeCheckinCheckoutRepository
							.getEmployeeCheckinDetails(employeeTimesheetsDTO.get(0).getEmployeeId());
					if (empCheckinEntity != null && empCheckinEntity.size() > 0) {
						EmployeeCheckinCheckoutEntity employeeCheckinCheckoutEntity = empCheckinEntity.get(0);
						BaseUtils.modifyBaseData(employeeCheckinCheckoutEntity, loggedInUser);
						employeeCheckinCheckoutEntity.setCheckoutTime(BaseUtils.getCurrentTime());
						save(employeeCheckinCheckoutEntity);
					} else {
						employeeCheckinCheckoutResultSet.setStatus(false);
						employeeCheckinCheckoutResultSet.setMessage(ApplicationConstants.FAILED);
						employeeCheckinCheckoutResultSet.setMessageDescription("Unable to Check-out");
						throw new Exception("Unable to Check-out");
					}
				}
			}
		} catch (Exception e) {
			employeeCheckinCheckoutResultSet.setStatus(false);
			employeeCheckinCheckoutResultSet.setMessage(ApplicationConstants.FAILED);
			employeeCheckinCheckoutResultSet.setMessageDescription(e.getMessage());
		}
		return employeeCheckinCheckoutResultSet;
	}

}
