package org.vrnda.hrms.repository.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class EmployeeTimesheetsDTO {

	private Long empTimesheetId;

	private Long employeeId;

	private Long empCheckinId;

	private String taskId;

	private LocalDateTime startTime;

	private LocalDateTime endTime;

	private Long statusLookupId;

	private String createdBy;

	private String updatedBy;

	private Timestamp createdDate;

	private Timestamp updatedDate;

}
