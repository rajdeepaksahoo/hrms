package org.vrnda.hrms.service;

import java.util.List;

import org.vrnda.hrms.entity.CmnHolidaysMstEntity;
import org.vrnda.hrms.repository.dto.CmnHolidaysMstDTO;
import org.vrnda.hrms.service.resultset.CmnHolidaysMstResultSet;

public interface CmnHolidaysMstService extends GenericService<CmnHolidaysMstEntity, Long> {

	public CmnHolidaysMstResultSet saveOrUpdateHoliday(CmnHolidaysMstDTO cmnHolidaysMstDTO, String loggedInUser);

	public CmnHolidaysMstResultSet deleteHolidayByHolidayId(Long holidayId);

	public CmnHolidaysMstResultSet deleteHolidaysList(List<CmnHolidaysMstDTO> cmnHolidaysMstDTOList);

	public CmnHolidaysMstResultSet getHolidaysByYearId(Long yearId);

	public CmnHolidaysMstResultSet getHolidaysByYearIdAndStatusLookupId(Long yearId, Long statusLookupId);

	public CmnHolidaysMstResultSet getHolidaysCountByYearId(Long yearId);

	public CmnHolidaysMstResultSet getHolidaysListToCopy(Long yearId, Long holidayTypeLookupId);

	public CmnHolidaysMstResultSet saveCopiedHolidays(Long yearId, Long holidayTypeLookupId, String loggedInUser);

	public boolean verifyDuplicateHolidayName(CmnHolidaysMstDTO cmnHolidaysMstDTO);

	public boolean verifyDuplicateHolidayDate(CmnHolidaysMstDTO cmnHolidaysMstDTO);

}
