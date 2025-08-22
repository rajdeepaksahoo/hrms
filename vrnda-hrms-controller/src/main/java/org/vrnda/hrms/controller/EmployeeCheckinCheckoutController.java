package org.vrnda.hrms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.vrnda.hrms.repository.dto.EmployeeCheckinCheckoutDTO;
import org.vrnda.hrms.repository.dto.EmployeeTimesheetsDTO;
import org.vrnda.hrms.service.EmployeeCheckinCheckoutService;

@RestController
@CrossOrigin(origins = "*")
public class EmployeeCheckinCheckoutController extends GenericController {

	@Autowired
	EmployeeCheckinCheckoutService employeeCheckinCheckoutService;

	@RequestMapping(value = "/getEmployeeCheckinDetails", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getEmployeeCheckinDetails(@RequestParam Long employeeId) {
		ResponseEntity<Message> message = toResponse(
				employeeCheckinCheckoutService.getEmployeeCheckinDetails(employeeId));
		return message;
	}

	@RequestMapping(value = "/employeeCheckIn", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> employeeCheckIn(@RequestBody EmployeeCheckinCheckoutDTO employeeCheckinCheckoutDTO) {
		ResponseEntity<Message> message = toResponse(
				employeeCheckinCheckoutService.employeeCheckIn(employeeCheckinCheckoutDTO, getLoggedInUser()));
		return message;
	}

	@RequestMapping(value = "/employeeCheckOut", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> employeeCheckOut(@RequestBody List<EmployeeTimesheetsDTO> employeeTimesheetsDTO) {
		ResponseEntity<Message> message = toResponse(
				employeeCheckinCheckoutService.employeeCheckOut(employeeTimesheetsDTO, getLoggedInUser()));
		return message;
	}

//	@RequestMapping(value="/saveCheckinCheckout",method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<Message> saveCheckinCheckout(@RequestBody EmployeeCheckinCheckoutCommitBean empCheckinCheckout){		 
//		ResponseEntity<Message> message = toResponse(employeeCheckinCheckoutService.saveCheckinCheckout(empCheckinCheckout));
//		return message;
//	}
//	
//	@RequestMapping(value="/saveEmployeeTimesheets",method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<Message> saveEmployeeTimesheets(@RequestBody EmployeeTimesheetsCommitBean empTimesheetsCommitBean){		 
//		ResponseEntity<Message> message = toResponse(employeeCheckinCheckoutService.saveEmployeeTimesheets(empTimesheetsCommitBean));
//		return message;
//	}

}
