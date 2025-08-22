package org.vrnda.hrms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.vrnda.hrms.service.EmployeeLeaveBalanceService;

@RestController
@CrossOrigin(origins = "*")
public class EmployeeLeaveBalanceController extends GenericController{
	
	@Autowired
	EmployeeLeaveBalanceService employeeLeaveBalanceService;
	
	
	@RequestMapping(value = "/getAllEmpLeaveBalance", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getAllEmpLeaveBalance(@RequestParam String currentYear, Long activeFlagId, Long employeeId ) {
		ResponseEntity<Message> message = toResponse(employeeLeaveBalanceService.getAllEmpLeaveBalance(currentYear, activeFlagId, employeeId));
		return message;
	}
	
	

}
