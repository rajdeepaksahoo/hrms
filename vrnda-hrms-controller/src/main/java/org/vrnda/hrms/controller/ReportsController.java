//package org.vrnda.hrms.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.vrnda.hrms.service.EmployeeDetailsService;
//import org.vrnda.hrms.service.EmployeeLeavesMstService;
//
//@RestController
//@CrossOrigin(origins = "*")
//public class ReportsController extends GenericController {
//	
//	@Autowired
//	EmployeeDetailsService employeeDetailsService;
//	
//	@Autowired
//	EmployeeLeavesMstService empLeaveMstService;
//	
//	@RequestMapping(value = "/getAllEmployeeByDOJ", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<Message> getAllEmployeeByDOJ(@RequestParam Long year, String month) {
//		ResponseEntity<Message> message = toResponse(employeeDetailsService.getEmployeeByDOJ(year,month));
//		return message;
//	}
//
//	@RequestMapping(value = "/getAllActiveEmployees", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<Message> getAllActiveEmployees() {
//		ResponseEntity<Message> message = toResponse(employeeDetailsService.getAllActiveEmployees());
//		return message;
//	}
//	
//
//	@RequestMapping(value = "/getEmployeeLvBalance", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<Message> getEmployeeLvBalance() {
//		ResponseEntity<Message> message = toResponse(empLeaveMstService.getEmployeeLvBalance());
//		return message;
//	}
//}
//
