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
import org.vrnda.hrms.repository.dto.CmnGeneralLoanConfigDetailsDTO;
import org.vrnda.hrms.repository.dto.EmployeeLoanDetailsDTO;
import org.vrnda.hrms.service.EmployeeLoanDetailsService;

@RestController
@CrossOrigin(origins = "*")
public class EmployeeLoanDetailsController extends GenericController {
	@Autowired
	EmployeeLoanDetailsService employeeService;

	@RequestMapping(value = "/getEmployeeLoanDtlsById", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getEmployeeLoanDtlsByID(@RequestParam Long employeeId) {
		ResponseEntity<Message> message = toResponse(employeeService.getEmployeeLoanDetailsById(employeeId));

		return message;

	}

	@RequestMapping(value = "/saveEmployeeLoanDtls", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> saveEmployeeLoan(@RequestBody EmployeeLoanDetailsDTO employeeLoanDto)
			throws Throwable {
		ResponseEntity<Message> message = toResponse(employeeService.saveEmployeeLoanDetails(employeeLoanDto));
		return message;
	}

	@RequestMapping(value = "/getEmployeeLoanConfigDetails", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getEmployeeLoanConfigDetails(@RequestParam Long employeeId,
			@RequestParam String year) {
		ResponseEntity<Message> message = toResponse(employeeService.getEmployeeLoanConfigDetails(employeeId, year));

		return message;

	}

	@RequestMapping(value = "/getAllLoanRequests", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getAllLoanRequests() {
		ResponseEntity<Message> message = toResponse(employeeService.getAllLoanRequests());
		return message;
	}
	
	@RequestMapping(value = "/approveEmployeeLoan", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> approveEmployeeLoan(@RequestBody EmployeeLoanDetailsDTO employeeLoanDto) {
		ResponseEntity<Message> message = toResponse(employeeService.approveEmployeeLoan(employeeLoanDto));
		return message;
	}

}
