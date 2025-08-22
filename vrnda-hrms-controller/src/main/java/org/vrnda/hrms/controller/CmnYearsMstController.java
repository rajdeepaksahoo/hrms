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
import org.vrnda.hrms.repository.dto.CmnYearsMstDTO;
import org.vrnda.hrms.service.CmnYearsMstService;

@RestController
@CrossOrigin(origins = "*")
public class CmnYearsMstController extends GenericController {

	@Autowired
	CmnYearsMstService cmnYearsMstService;

	@RequestMapping(value = "/saveOrUpdateYears", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> saveOrUpdateYears(@RequestBody CmnYearsMstDTO cmnYearsMstDto) {
		ResponseEntity<Message> message = toResponse(cmnYearsMstService.saveOrUpdateYears(cmnYearsMstDto, getLoggedInUser()));
		return message;
	}

	@RequestMapping(value = "/deleteYearByYearId", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> deleteYearByYearId(@RequestBody Long yearId) {
		ResponseEntity<Message> message = toResponse(cmnYearsMstService.deleteYearByYearId(yearId));
		return message;
	}

	@RequestMapping(value = "/deleteYearsList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> deleteYearsList(@RequestBody List<CmnYearsMstDTO> cmnYearsMstDtoList) {
		ResponseEntity<Message> message = toResponse(cmnYearsMstService.deleteYearsList(cmnYearsMstDtoList));
		return message;
	}

	@RequestMapping(value = "/getYearsByYearType", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getYearsByYearType(@RequestParam String yearType) {
		ResponseEntity<Message> message = toResponse(cmnYearsMstService.getYearsByYearType(yearType));
		return message;
	}

	@RequestMapping(value = "/getDefaultYearsByYearType", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getDefaultYearsByYearType(@RequestParam String yearType) {
		ResponseEntity<Message> message = toResponse(cmnYearsMstService.getDefaultYearsByYearType(yearType));
		return message;
	}

	@RequestMapping(value = "/getYearIdForCurrentYear", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getYearIdForCurrentYear(@RequestParam String yearName) {
		ResponseEntity<Message> message = toResponse(cmnYearsMstService.getYearIdForCurrentYear(yearName));
		return message;
	}

	@RequestMapping(value = "/getYearNameByYearId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getYearNameByYearId(@RequestParam Long yearId) {
		ResponseEntity<Message> message = toResponse(cmnYearsMstService.getCmnYearsMstByYearId(yearId));
		return message;
	}	
	
	@RequestMapping(value = "/deleteAllYearsList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> deleteAllYearsList(@RequestBody List<CmnYearsMstDTO> cmnYearsMstDtoList) {
		ResponseEntity<Message> message = toResponse(cmnYearsMstService.deleteAllYearsList(cmnYearsMstDtoList));
		return message;
	}

}
