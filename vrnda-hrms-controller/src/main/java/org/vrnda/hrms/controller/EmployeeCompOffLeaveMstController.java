package org.vrnda.hrms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.vrnda.hrms.repository.dto.EmployeeCompOffLeaveMstDTO;
import org.vrnda.hrms.service.EmployeeCompOffLeaveMstService;

@RestController
@CrossOrigin(origins = "*")
public class EmployeeCompOffLeaveMstController extends GenericController {

	@Autowired
	EmployeeCompOffLeaveMstService employeeCompOffLeaveMstService;

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@RequestMapping(value = "/getEmployeeCompOffLeavesDetails", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getEmployeeCompOffLeavesDetails(@RequestParam Long employeeId) {
		ResponseEntity<Message> message = toResponse(employeeCompOffLeaveMstService.getEmployeeCompOffLeavesDetails(employeeId));
		return message;
	}
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@RequestMapping(value = "/saveOrUpdateCompoffLeaves", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> saveOrUpdateCompoffLeaves(@RequestBody EmployeeCompOffLeaveMstDTO compoffLeavesdto) {
		ResponseEntity<Message> message = toResponse(employeeCompOffLeaveMstService.saveOrUpdateCompoffLeaves(compoffLeavesdto,getLoggedInUser()));
		return message;
	}
	
	
//	@ExceptionHandler(HttpMessageNotReadableException.class)
//	@RequestMapping(value = "/getLeaveApprovalsData", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<Message> getLeaveApprovalsData() {
//		ResponseEntity<Message> message = toResponse(employeeCompOffLeaveMstService.getLeaveApprovalsData());
//		return message;
//	}
	
	@RequestMapping(value = "/getCompoffLeave", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getCompoffLeave() {
		ResponseEntity<Message> message = toResponse(employeeCompOffLeaveMstService.getCompoffLeave());
		return message;
	}
	
	@RequestMapping(value = "/getCompoffLeaveHistory", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getCompoffLeaveHistory() {
		ResponseEntity<Message> message = toResponse(employeeCompOffLeaveMstService.getCompoffLeaveHistory());
		return message;
	}
	
}
