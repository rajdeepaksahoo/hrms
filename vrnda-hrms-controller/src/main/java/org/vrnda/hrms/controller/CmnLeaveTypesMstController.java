package org.vrnda.hrms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.vrnda.hrms.repository.dto.CmnLeaveTypesMstDTO;
import org.vrnda.hrms.service.CmnLeaveTypesMstService;

@RestController
@CrossOrigin(origins = "*")
public class CmnLeaveTypesMstController extends GenericController{

	@Autowired
	CmnLeaveTypesMstService cmnLeaveTypesMstService;

	@RequestMapping(value = "/getAllLeaveTypes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getAllLeaveTypes() {
		ResponseEntity<Message> message = toResponse(cmnLeaveTypesMstService.getAllLeaveTypes());
		return message;
	}
	
	@RequestMapping(value = "/saveOrUpdateLeaveTypes", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> saveOrUpdateLookup(@RequestBody CmnLeaveTypesMstDTO cmnLeaveTypesMststo) {
		ResponseEntity<Message> message = toResponse(cmnLeaveTypesMstService.saveOrUpdateLookup(cmnLeaveTypesMststo, getLoggedInUser()));
		return message;
	}
	
}
