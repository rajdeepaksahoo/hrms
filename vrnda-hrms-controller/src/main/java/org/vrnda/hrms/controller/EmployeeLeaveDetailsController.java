package org.vrnda.hrms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.vrnda.hrms.repository.dto.EmployeeLeaveDetailsDTO;
import org.vrnda.hrms.service.EmployeeLeaveDetailsService;

@RestController
@CrossOrigin(origins = "*")
public class EmployeeLeaveDetailsController extends GenericController {
	
	@Autowired
	EmployeeLeaveDetailsService employeeLeaveDetailsService;
	
	
	@RequestMapping(value = "/getEmployeeLeaveRequests", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getEmployeeLeaveRequests(@RequestParam Long employeeId) {
		ResponseEntity<Message> message = toResponse(employeeLeaveDetailsService.getEmployeeLeaveRequests(employeeId));
		return message;
	}
	
//	@RequestMapping(value = "/getEmployeeLeaveRequestsByUserId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<Message> getEmployeeLeaveRequestsByUserId(@RequestParam String username) {
//		ResponseEntity<Message> message = toResponse(employeeLeaveDetailsService.getEmployeeLeaveRequestsByUserId(username));
//		return message;
//	}
	@RequestMapping(value = "/getAllLeaveRequests", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getAllLeaveRequests(@RequestParam Long approverId) {
		ResponseEntity<Message> message = toResponse(employeeLeaveDetailsService.getAllLeaveRequests(approverId));
		return message;
	}
	
	
	@RequestMapping(value = "/saveOrUpdateLeaves", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> saveOrUpdateLeaves(@RequestBody EmployeeLeaveDetailsDTO employeeLeaveDtlsDto) {
		ResponseEntity<Message> message = toResponse(employeeLeaveDetailsService.saveOrUpdate(employeeLeaveDtlsDto,getLoggedInUser()));
		return message;
	}
	
//	@RequestMapping(value = "/cancelLeaveRequest", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<Message> cancelLeaveRequest(@RequestBody EmployeeLeaveDetailsDTO employeeLeaveDtlsDto) {
//		ResponseEntity<Message> message = toResponse(employeeLeaveDetailsService.cancelLeaveRequest(employeeLeaveDtlsDto));
//		return message;
//	}
//	
	@RequestMapping(value = "/approveRequest", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> approveRequest(@RequestBody EmployeeLeaveDetailsDTO employeeLeaveDtlsDto) {
		ResponseEntity<Message> message = toResponse(employeeLeaveDetailsService.approveRequest(employeeLeaveDtlsDto,getLoggedInUser()));
		return message;
	}
}
