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
import org.vrnda.hrms.repository.dto.CmnDepartmentsMstDTO;
import org.vrnda.hrms.service.CmnDepartmentsMstService;

@RestController
@CrossOrigin(origins = "*")
public class CmnDepartmentsMstController extends GenericController {
	@Autowired
	CmnDepartmentsMstService cmnDepartmentsMstService;

	@RequestMapping(value = "/saveOrUpdateDepartments", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> saveOrUpdateDepartments(@RequestBody CmnDepartmentsMstDTO cmnDepartmentsMstDto) {
		ResponseEntity<Message> message = toResponse(cmnDepartmentsMstService.saveOrUpdateDepartments(cmnDepartmentsMstDto, getLoggedInUser()));
		return message;
	}

	@RequestMapping(value = "/deleteDepartmentByDepartmentId", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> deleteDepartmentByDepartmentId(@RequestBody Long departmentId) {
		ResponseEntity<Message> message = toResponse(cmnDepartmentsMstService.deleteDepartmentByDepartmentId(departmentId));
		return message;
	}

	@RequestMapping(value = "/deleteDepartmentsList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> deleteDepartmentsList(@RequestBody List<CmnDepartmentsMstDTO> cmnDepartmentsMstDtoList) {
		ResponseEntity<Message> message = toResponse(cmnDepartmentsMstService.deleteDepartmentsList(cmnDepartmentsMstDtoList));
		return message;
	}

	@RequestMapping(value = "/getAllDepartments", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getAllDepartments() {
		ResponseEntity<Message> message = toResponse(cmnDepartmentsMstService.getAllDepartments());
		return message;
	}

	@RequestMapping(value = "/getDepartmentByDepartmentId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getDepartmentByDepartmentId(@RequestParam Long departmentId) {
		ResponseEntity<Message> message = toResponse(cmnDepartmentsMstService.getDepartmentByDepartmentId(departmentId));
		return message;
	}

	@RequestMapping(value = "/getDepartmentsByStatusLookupId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getDepartmentsByStatusLookupId(@RequestParam Long statusLookupId) {
		ResponseEntity<Message> message = toResponse(cmnDepartmentsMstService.getDepartmentsByStatusLookupId(statusLookupId));
		return message;
	}

}
