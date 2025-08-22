package org.vrnda.hrms.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "EMPLOYEE_TIMESHEETS")
public class EmployeeTimesheetsEntity extends EntityBaseModel {

	@Id
	@Column(name = "EMP_TIMESHEET_ID")
	private Long empTimesheetId;

	@Column(name = "EMP_CHECKIN_ID")
	private Long empCheckinId;

	@Column(name = "TASK_ID")
	private String taskId;

	@Column(name = "START_TIME")
	private LocalDateTime startTime;

	@Column(name = "END_TIME")
	private LocalDateTime endTime;

}
