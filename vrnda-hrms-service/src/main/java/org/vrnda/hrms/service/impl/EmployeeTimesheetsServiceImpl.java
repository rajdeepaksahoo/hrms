package org.vrnda.hrms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.vrnda.hrms.entity.EmployeeTimesheetsEntity;
import org.vrnda.hrms.entity.EmployeeTimesheetsHistEntity;
import org.vrnda.hrms.repository.EmployeeTimesheetsHistRepository;
import org.vrnda.hrms.repository.EmployeeTimesheetsRepository;
import org.vrnda.hrms.repository.dto.CalendarEvents;
import org.vrnda.hrms.repository.dto.EmployeeTimesheetsDTO;
import org.vrnda.hrms.service.CmnTableSeqService;
import org.vrnda.hrms.service.EmployeeTimesheetsService;
import org.vrnda.hrms.service.resultset.EmployeeTimesheetsResultSet;
import org.vrnda.hrms.utils.ApplicationConstants;
import org.vrnda.hrms.utils.BaseUtils;

@Service
public class EmployeeTimesheetsServiceImpl extends GenericServiceImpl<EmployeeTimesheetsEntity, Long>
		implements EmployeeTimesheetsService {

	@Autowired
	private CmnTableSeqService cmnTableSeqService;

	@Autowired
	private EmployeeTimesheetsRepository employeeTimesheetsRepository;

	@Autowired
	private EmployeeTimesheetsHistRepository employeeTimesheetsHistRepository;

	public EmployeeTimesheetsServiceImpl(PagingAndSortingRepository<EmployeeTimesheetsEntity, Long> typeRepository) {
		super(typeRepository);
		// TODO Auto-generated constructor stub
	}

	private final static String CLASSNAME = "fc-event-success";

	@Override
	public EmployeeTimesheetsResultSet saveTimesheets(List<EmployeeTimesheetsDTO> employeeTimesheetsDTO,
			String loggedInUser) {
		EmployeeTimesheetsResultSet employeeTimesheetsResultSet = new EmployeeTimesheetsResultSet();
		try {
			List<EmployeeTimesheetsEntity> employeeTimesheetsEntityList = new ArrayList<EmployeeTimesheetsEntity>();
			employeeTimesheetsDTO.forEach(e -> {
				EmployeeTimesheetsEntity employeeTimesheetsEntity = new EmployeeTimesheetsEntity();
				BeanUtils.copyProperties(e, employeeTimesheetsEntity);
				if (employeeTimesheetsEntity.getEmpTimesheetId() != null
						&& employeeTimesheetsEntity.getEmpTimesheetId() != 0) {
					BaseUtils.modifyBaseData(employeeTimesheetsEntity, loggedInUser);

				} else {
					employeeTimesheetsEntity.setEmpTimesheetId(cmnTableSeqService.getNextSequence(
							ApplicationConstants.EMPLOYEE_TIMESHEETS, ApplicationConstants.EMP_TIMESHEET_ID));
					BaseUtils.setBaseData(employeeTimesheetsEntity, loggedInUser);

				}
				employeeTimesheetsEntityList.add(employeeTimesheetsEntity);
			});
			saveAll(employeeTimesheetsEntityList);
		} catch (Exception e) {
			employeeTimesheetsResultSet.setStatus(false);
			employeeTimesheetsResultSet.setMessage(ApplicationConstants.FAILED);
			employeeTimesheetsResultSet.setMessageDescription("Unable to Save Time sheets");
		}

		return employeeTimesheetsResultSet;
	}

	@Override
	public EmployeeTimesheetsResultSet getAllEmployeeTimeSheetData(Long employeeId) {
		EmployeeTimesheetsResultSet employeeTimesheetsResultSet = new EmployeeTimesheetsResultSet();
		try {
			List<EmployeeTimesheetsEntity> employeeTimesheetsEntityList = employeeTimesheetsRepository
					.getAllEmployeeTimeSheetData(employeeId);
			List<CalendarEvents> calendarEventsList = new ArrayList<CalendarEvents>();
			if (employeeTimesheetsEntityList != null) {
				employeeTimesheetsEntityList.forEach(e -> {
					CalendarEvents calendarEventsObj = new CalendarEvents();
					calendarEventsObj.setId(e.getEmpTimesheetId());
					calendarEventsObj.setCheckInId(e.getEmpCheckinId());
					calendarEventsObj.setStart(e.getStartTime());
					calendarEventsObj.setEnd(e.getEndTime());
					calendarEventsObj.setTitle(e.getTaskId());
					calendarEventsObj.setClassName(CLASSNAME);
					calendarEventsList.add(calendarEventsObj);
				});

			}

			List<EmployeeTimesheetsHistEntity> employeeTimesheetsHistEntityList = employeeTimesheetsHistRepository
					.getAllEmployeeTimeSheetHistData(employeeId);

			if (employeeTimesheetsHistEntityList != null) {
				employeeTimesheetsHistEntityList.forEach(e -> {
					CalendarEvents calendarEventsObj = new CalendarEvents();
					calendarEventsObj.setId(e.getEmpTimesheetId());
					calendarEventsObj.setCheckInId(e.getEmpCheckinId());
					calendarEventsObj.setStart(e.getStartTime());
					calendarEventsObj.setEnd(e.getEndTime());
					calendarEventsObj.setTitle(e.getTaskId());
					calendarEventsObj.setClassName(CLASSNAME);
					calendarEventsList.add(calendarEventsObj);
				});

			}

			employeeTimesheetsResultSet.setCalendarEventsList(calendarEventsList);
		} catch (Exception e) {
			employeeTimesheetsResultSet.setStatus(false);
			employeeTimesheetsResultSet.setMessage(ApplicationConstants.FAILED);
			employeeTimesheetsResultSet.setMessageDescription("Unable to Load Time sheets");
		}

		return employeeTimesheetsResultSet;
	}

	@Override
	public EmployeeTimesheetsResultSet getAllEmployeeTimeSheetDataBasedOnCheckIns(List<Long> empCheckinIds) {
		EmployeeTimesheetsResultSet employeeTimesheetsResultSet = new EmployeeTimesheetsResultSet();
		try {
			List<EmployeeTimesheetsEntity> employeeTimesheetsEntityList = employeeTimesheetsRepository
					.getAllEmployeeTimeSheetDataBasedOnCheckIns(empCheckinIds);
			if (employeeTimesheetsEntityList != null) {
				List<EmployeeTimesheetsDTO> employeeTimesheetsDtoList = new ArrayList<EmployeeTimesheetsDTO>();
				employeeTimesheetsEntityList.forEach(e -> {
					EmployeeTimesheetsDTO employeeTimesheetsDTO = new EmployeeTimesheetsDTO();
					BeanUtils.copyProperties(e, employeeTimesheetsDTO);
					employeeTimesheetsDtoList.add(employeeTimesheetsDTO);
				});
				employeeTimesheetsResultSet.setEmployeeTimesheetsDtoList(employeeTimesheetsDtoList);
			}

		} catch (Exception e) {
			employeeTimesheetsResultSet.setStatus(false);
			employeeTimesheetsResultSet.setMessage(ApplicationConstants.FAILED);
			employeeTimesheetsResultSet.setMessageDescription("Unable to Load Time sheets");
		}

		return employeeTimesheetsResultSet;
	}

}
