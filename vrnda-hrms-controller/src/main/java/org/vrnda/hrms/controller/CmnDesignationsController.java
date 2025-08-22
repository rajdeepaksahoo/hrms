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
import org.vrnda.hrms.repository.dto.CmnDesignationsMstDTO;
import org.vrnda.hrms.service.CmnDesignationsMstService;

@RestController
@CrossOrigin(origins = "*")
public class CmnDesignationsController extends GenericController{

	@Autowired
	private CmnDesignationsMstService cmnDesignationsMstService;

	@RequestMapping(value = "/saveOrUpdateDesignation", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<Message> saveOrUpdateDesignation(@RequestBody CmnDesignationsMstDTO cmnDesignationsMstDto)  {
		ResponseEntity<Message> message = toResponse(cmnDesignationsMstService.saveOrUpdateDesignation(cmnDesignationsMstDto, getLoggedInUser()));
		return message;
	}

	@RequestMapping(value = "/deleteDesignationByDesignationId", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<Message> deleteDesignationByDesignationId(@RequestBody Long designationId)  {
		ResponseEntity<Message> message = toResponse(cmnDesignationsMstService.deleteDesignationByDesignationId(designationId));
		return message;
	}

	@RequestMapping(value = "/deleteDesignationsList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> deleteDesignationsList(@RequestBody List<CmnDesignationsMstDTO> cmnDesignationMstDtoList) {
		ResponseEntity<Message> message = toResponse(cmnDesignationsMstService.deleteDesignationsList(cmnDesignationMstDtoList));
		return message;
	}

	@RequestMapping(value = "/getAllDesignations", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<Message> getAllDesignations()  {
		ResponseEntity<Message> message = toResponse(cmnDesignationsMstService.getAllDesignations());
		return message;
	}

	@RequestMapping(value = "/getDesignationByDesignationId", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<Message> getDesignationByDesignationId(@RequestParam Long designationId)  {
		ResponseEntity<Message> message = toResponse(cmnDesignationsMstService.getDesignationByDesignationId(designationId));
		return message;
	}

	@RequestMapping(value = "/getDesignationsByStatusLookupId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<Message> getDesignationsByStatusLookupId(Long statusLookupId)  {
		ResponseEntity<Message> message = toResponse(cmnDesignationsMstService.getDesignationsByStatusLookupId(statusLookupId));
		return message;
	}

}
