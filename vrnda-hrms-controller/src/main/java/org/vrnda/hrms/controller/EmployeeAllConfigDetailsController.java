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
import org.vrnda.hrms.repository.dto.EmployeeAllConfigDetailsDTO;
import org.vrnda.hrms.service.EmployeeAllConfigDetailsService;

@RestController
@CrossOrigin(origins = "*")
public class EmployeeAllConfigDetailsController extends GenericController{

	@Autowired
	EmployeeAllConfigDetailsService employeeAllConfigDetailsService;
	
	@RequestMapping(value = "/getEmployeeAllConfigDetailsByEmployeeId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getEmployeeAllConfigDetailsByEmployeeId(@RequestParam Long employeeId) {
		ResponseEntity<Message> message = toResponse(employeeAllConfigDetailsService.getEmployeeAllConfigDetailsByEmployeeId(employeeId));
		return message;
	}
	
	@RequestMapping(value = "/getEmployeeAllConfigDetailsByYearIdandEmployeeId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getEmployeeAllConfigDetailsByYearIdandEmployeeId(@RequestParam Long yearId,@RequestParam Long employeeId) {
		ResponseEntity<Message> message = toResponse(employeeAllConfigDetailsService.getEmployeeAllConfigDetailsByYearIdandEmployeeId(yearId,employeeId));
		return message;
	}
	
	@RequestMapping(value = "/saveOrUpdateEmployeeAllConfigDetails", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> saveOrUpdateEmployeeAllConfigDetails(@RequestBody EmployeeAllConfigDetailsDTO employeeLoanDto)   {
		ResponseEntity<Message> message = toResponse(employeeAllConfigDetailsService.saveOrUpdateEmployeeAllConfigDetails(employeeLoanDto,getLoggedInUser()));
		return message;
	}
}
