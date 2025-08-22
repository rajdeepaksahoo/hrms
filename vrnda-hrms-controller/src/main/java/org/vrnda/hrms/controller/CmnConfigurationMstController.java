package org.vrnda.hrms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.vrnda.hrms.repository.dto.CmnConfigurationsMstDTO;
import org.vrnda.hrms.service.CmnConfigurationsMstService;

@RestController
@CrossOrigin(origins = "*")
public class CmnConfigurationMstController extends GenericController {

	@Autowired
	CmnConfigurationsMstService cmnConfigurationsMstService;

	@RequestMapping(value = "/saveOrUpdateConfiguration", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> saveOrUpdateConfiguration(@RequestBody CmnConfigurationsMstDTO cmnConfiguration) {
		ResponseEntity<Message> message = toResponse(cmnConfigurationsMstService.saveOrUpdateConfiguration(cmnConfiguration, getLoggedInUser()));
		return message;
	}

	@RequestMapping(value = "/deleteConfigurationByConfigurationId", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> deleteConfigurationByConfigurationId(@RequestBody Long configurationId) {
		ResponseEntity<Message> message = toResponse(cmnConfigurationsMstService.deleteConfigurationByConfigurationId(configurationId));
		return message;
	}

	@RequestMapping(value = "/deleteConfigurationsList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> deleteConfigurationsList(@RequestBody List<CmnConfigurationsMstDTO> cmnConfigurationsMstDtoList) {
		ResponseEntity<Message> message = toResponse(cmnConfigurationsMstService.deleteConfigurationsList(cmnConfigurationsMstDtoList));
		return message;
	}

		@RequestMapping(value = "/getConfigurationByConfigurationId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Message> getConfigurationByConfigurationId(Long configurationId) {
			ResponseEntity<Message> message = toResponse(cmnConfigurationsMstService.getConfigurationByConfigurationId(configurationId));
			return message;
		}

	@RequestMapping(value = "/getConfigurationsByConfigTypeLookupIdAndYearId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getConfigurationsByConfigTypeLookupIdAndYearId(String configTypeLookupName, Long yearId) {
		ResponseEntity<Message> message = toResponse(cmnConfigurationsMstService.getConfigurationsByConfigTypeLookupIdAndYearId(configTypeLookupName, yearId));
		return message;
	}

	@RequestMapping(value = "/getConfigurationsByConfigurationTypeLookupIdAndYearIdAndStatusLookupId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getConfigurationsByConfigurationTypeLookupIdAndYearIdAndStatusLookupId(Long configTypeLookupId, Long yearId, Long statusLookupId) {
		ResponseEntity<Message> message = toResponse(cmnConfigurationsMstService.getConfigurationsByConfigurationTypeLookupIdAndYearIdAndStatusLookupId(configTypeLookupId, yearId, statusLookupId));
		return message;
	}
	
	@RequestMapping(value = "/getConfigurationsByYearId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getConfigurationsByYearId(Long yearId) {
		ResponseEntity<Message> message = toResponse(cmnConfigurationsMstService.getConfigurationsByYearId(yearId));
		return message;
	}
	
	
}
