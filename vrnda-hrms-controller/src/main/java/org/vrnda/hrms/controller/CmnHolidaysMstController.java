package org.vrnda.hrms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.vrnda.hrms.repository.dto.CmnHolidaysMstDTO;
import org.vrnda.hrms.service.CmnHolidaysMstService;

@RestController
@CrossOrigin(origins = "*")
public class CmnHolidaysMstController extends GenericController {

	@Autowired
	private CmnHolidaysMstService cmnHolidaysMstService;

	@RequestMapping(value="/saveOrUpdateHoliday",method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> saveOrUpdateHoliday(@RequestBody CmnHolidaysMstDTO cmnHolidaysMstDto){	
		ResponseEntity<Message> message = toResponse(cmnHolidaysMstService.saveOrUpdateHoliday(cmnHolidaysMstDto, getLoggedInUser()));
		return message;
	}

	@RequestMapping(value = "/deleteHolidayByHolidayId", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> deleteHolidayByHolidayId(@RequestBody Long holidayId) {
		ResponseEntity<Message> message = toResponse(cmnHolidaysMstService.deleteHolidayByHolidayId(holidayId));
		return message;
	}

	@RequestMapping(value = "/deleteHolidaysList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> deleteHolidaysList(@RequestBody List<CmnHolidaysMstDTO> cmnHolidaysMstDtoList) {
		ResponseEntity<Message> message = toResponse(cmnHolidaysMstService.deleteHolidaysList(cmnHolidaysMstDtoList));
		return message;
	}

	@RequestMapping(value = "/getHolidaysByYearId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getHolidaysByYearId(@RequestParam Long yearId) {
		ResponseEntity<Message> message = toResponse(cmnHolidaysMstService.getHolidaysByYearId(yearId));
		return message;
	}

	@RequestMapping(value = "/getHolidaysByYearIdAndStatusLookupId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getHolidaysByYearIdAndStatusLookupId(@RequestParam Long yearId, @RequestParam Long statusLookupId) {
		ResponseEntity<Message> message = toResponse(cmnHolidaysMstService.getHolidaysByYearIdAndStatusLookupId(yearId, statusLookupId));
		return message;
	}

	@RequestMapping(value = "/getHolidaysCountByYearId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getHolidaysCountByYearId(@RequestParam Long yearId) {
		ResponseEntity<Message> message = toResponse(cmnHolidaysMstService.getHolidaysCountByYearId(yearId));
		return message;
	}

	@RequestMapping(value = "/getHolidaysListToCopy", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getHolidaysListToCopy(@RequestParam Long yearId, @RequestParam Long holidayTypeLookupId) {
		ResponseEntity<Message> message = toResponse(cmnHolidaysMstService.getHolidaysListToCopy(yearId, holidayTypeLookupId));
		return message;
	}

	@RequestMapping(value = "/saveCopiedHolidays", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> saveCopiedHolidays(@RequestParam Long yearId, @RequestParam Long holidayTypeLookupId) {
		ResponseEntity<Message> message = toResponse(cmnHolidaysMstService.saveCopiedHolidays(yearId, holidayTypeLookupId, getLoggedInUser()));
		return message;
	}
}
