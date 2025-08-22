package org.vrnda.hrms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.vrnda.hrms.service.EmployeeLeavesMstService;


@RestController
@CrossOrigin(origins = "*")
public class EmployeeLeaveMstController extends GenericController {
	@Autowired
	EmployeeLeavesMstService employeeLeavesMstService;
	
	@RequestMapping(value = "/getLeaveBalance", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getLeaveBalance(Long employeeId) {
		ResponseEntity<Message> message = toResponse(employeeLeavesMstService.getLeaveBalance(employeeId));
		return message;
	}
//	@RequestMapping(value = "/getLeaveBalanceByUserId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<Message> getLeaveBalanceByUserId(@RequestParam String username) {
//		ResponseEntity<Message> message = toResponse(employeeLeavesMstService.getLeaveBalanceByUserId(username));
//		return message;
//	}
//	
//	@RequestMapping(value = "/updateLeaveBalance", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<Message> updateLeaveBalance(@RequestBody EmployeeLeavesMstDTO employeeLeavesMstDto) {
//		ResponseEntity<Message> message = toResponse(employeeLeavesMstService.updateLeaveBalance(employeeLeavesMstDto));
//		return message;
//	}

}
