package org.vrnda.hrms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.vrnda.hrms.service.EmployeeTimesheetsService;

@RestController
@CrossOrigin(origins = "*")
public class EmployeeTimesheetsController extends GenericController {

	@Autowired
	private EmployeeTimesheetsService employeeTimesheetsService;

	@RequestMapping(value = "/getAllEmployeeTimeSheetData", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getAllEmployeeTimeSheetData(@RequestParam Long employeeId) {
		ResponseEntity<Message> message = toResponse(employeeTimesheetsService.getAllEmployeeTimeSheetData(employeeId));
		return message;
	}

}
