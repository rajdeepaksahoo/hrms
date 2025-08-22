package org.vrnda.hrms.repository.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CalendarEvents implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	private String title;

	private LocalDateTime start;

	private LocalDateTime end;

	private String className;

	private String details;

	private Long checkInId;
}
