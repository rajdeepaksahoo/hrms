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
import org.vrnda.hrms.repository.dto.CmnCompanyLocationsMstDTO;
import org.vrnda.hrms.service.CmnCompanyLocationMstService;

@RestController
@CrossOrigin(origins = "*")
public class CmnCompanyLocationMstController extends GenericController {

	@Autowired
	CmnCompanyLocationMstService cmnCompanyLocationMstService;

	@RequestMapping(value = "/saveOrUpdateLocation", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> saveOrUpdateLocation(@RequestBody CmnCompanyLocationsMstDTO cmnCompanyLocationsMstDTO) {
		ResponseEntity<Message> message = toResponse(cmnCompanyLocationMstService.saveOrUpdateLocation(cmnCompanyLocationsMstDTO, getLoggedInUser()));
		return message;
	}

	@RequestMapping(value = "/deleteLocationByLocationId", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> deleteLocationByLocationId(@RequestBody Long locationId){
		ResponseEntity<Message> message = toResponse(cmnCompanyLocationMstService.deleteLocationByLocationId(locationId));
		return message;
	}

	@RequestMapping(value = "/deleteLocationsList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> deleteLocationsList(@RequestBody List<CmnCompanyLocationsMstDTO> cmnCompanyLocationsMstDTOList) {
		ResponseEntity<Message> message = toResponse(cmnCompanyLocationMstService.deleteLocationsList(cmnCompanyLocationsMstDTOList));
		return message;
	}

	@RequestMapping(value = "/getAllCompanyLocations", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getAllCompanyLocations(){
		ResponseEntity<Message> message = toResponse(cmnCompanyLocationMstService.getAllCompanyLocations());
		return message;
	}

	@RequestMapping(value = "/getCompanyLocationByLocationId", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getCompanyLocationByLocationId(@RequestParam Long locationId){
		ResponseEntity<Message> message = toResponse(cmnCompanyLocationMstService.getCompanyLocationByLocationId(locationId));
		return message;
	}

	@RequestMapping(value = "/getCompanyLocationsByStatusLookupId", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getCompanyLocationsByStatusLookupId(@RequestParam Long statusLookupId){
		ResponseEntity<Message> message = toResponse(cmnCompanyLocationMstService.getCompanyLocationsByStatusLookupId(statusLookupId));
		return message;
	}

}
