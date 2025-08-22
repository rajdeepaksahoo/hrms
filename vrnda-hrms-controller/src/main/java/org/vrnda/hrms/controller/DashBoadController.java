package org.vrnda.hrms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.vrnda.hrms.service.DashboadService;

@RestController
@CrossOrigin(origins = "*")
public class DashBoadController extends GenericController {
	@Autowired
	DashboadService dashboadService;

	@RequestMapping(value = "/getEmployeOnLeaveDetails", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getEmployeOnLeaveDetails(@RequestParam Long employeeId) {
	ResponseEntity<Message> message = toResponse(dashboadService.getEmployeeOnLeaveDatails(employeeId));
		return message;
	}
	
	@RequestMapping(value = "/getDashBoadLeaveRequests", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getDashBoadLeaveRequestsDetails(@RequestParam Long employeeId) {
	ResponseEntity<Message> message = toResponse(dashboadService.getDashBoadLeaveRequestsDetails(employeeId));
		return message;
	}
	
	
	
}
