package org.vrnda.hrms.service.resultset;

import java.util.List;

import org.vrnda.hrms.repository.dto.CmnHolidaysMstDTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_EMPTY)
public class CmnHolidaysMstResultSet extends GenericResultSet {

	List<CmnHolidaysMstDTO> cmnHolidaysMstDTOList;

	Integer weekDayHolidays;

	Integer weekEndHolidays;

	Integer totalHolidays;

}
