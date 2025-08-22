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
import org.vrnda.hrms.repository.dto.EmployeeDetailsDTO;
import org.vrnda.hrms.service.EmployeeDetailsService;

@RestController
@CrossOrigin(origins = "*")
public class EmployeeDetailsController extends GenericController {

	@Autowired
	EmployeeDetailsService employeeDetailsService;

	@RequestMapping(value = "/createOrUpdateEmployeeDtls", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> createOrUpdateEmployeeDtls(@RequestBody EmployeeDetailsDTO employeeDetailsDTO) {
		ResponseEntity<Message> message = toResponse(
				employeeDetailsService.createOrUpdateEmployeeDtls(employeeDetailsDTO, getLoggedInUser()));
		return message;
	}

	@RequestMapping(value = "/getAllEmployeeDtls", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getAllEmployeeDtls() {
		ResponseEntity<Message> message = toResponse(employeeDetailsService.getAllEmployeeDtls());
		return message;
	}

	@RequestMapping(value = "/getAllLeadsAndManagers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getAllLeadsAndManagers() {
		ResponseEntity<Message> message = toResponse(employeeDetailsService.getAllLeadsAndManagers());
		return message;
	}

	@RequestMapping(value = "/getAllLeadsAndManagersByStatusLookUpId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getAllLeadsAndManagersByStatusLookUpId(@RequestParam Long statusLookUpId) {
		ResponseEntity<Message> message = toResponse(
				employeeDetailsService.getAllLeadsAndManagersByStatusLookUpId(statusLookUpId));
		return message;
	}

	@RequestMapping(value = "/getAllHrs", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getAllHrs() {
		ResponseEntity<Message> message = toResponse(employeeDetailsService.getAllHrs());
		return message;
	}

	@RequestMapping(value = "/getAllHrsByStatusLookUpId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getAllHrsByStatusLookUpId(@RequestParam Long statusLookUpId) {
		ResponseEntity<Message> message = toResponse(employeeDetailsService.getAllHrsByStatusLookUpId(statusLookUpId));
		return message;
	}

	@RequestMapping(value = "/getEmployeeCompleteDetailsByEmployeeId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getEmployeeCompleteDetailsByEmployeeId(@RequestParam Long employeeId) {
		ResponseEntity<Message> message = toResponse(
				employeeDetailsService.getEmployeeCompleteDetailsByEmployeeId(employeeId));
		return message;
	}

	@RequestMapping(value = "/getEmployeeDetailsBySearchParams", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getEmployeeSearchDetails(@RequestBody EmployeeDetailsDTO employeeDetailsDTO) {
		ResponseEntity<Message> message = toResponse(
				employeeDetailsService.getEmployeeDetailsBySearchParams(employeeDetailsDTO));
		return message;
	}

	@RequestMapping(value = "/getAllActiveEmployeesAndUserIsNull", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getAllActiveEmployeesAndUserIsNull(@RequestParam Long statusLookUpId) {
		ResponseEntity<Message> message = toResponse(
				employeeDetailsService.getAllActiveEmployeesAndUserIsNull(statusLookUpId));
		return message;
	}
	
	@RequestMapping(value = "/getEmployeeDetailsByManagerIdOrHrId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getEmployeeDetailsByManagerIdOrHrId(@RequestParam Long employeeId) {
		ResponseEntity<Message> message = toResponse(
				employeeDetailsService.getEmployeeDetailsByManagerIdOrHrId(employeeId));
		return message;
	}
	
	@RequestMapping(value = "/getAllLeadsAndManagersByStatusLookUpIdAndDepartMentId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getAllLeadsAndManagersByStatusLookUpIdAndDepartMentId(@RequestParam Long statusLookUpId,
			@RequestParam Long departMentId) {
		ResponseEntity<Message> message = toResponse(
				employeeDetailsService.getAllLeadsAndManagersByStatusLookUpIdAndDepartMentId(statusLookUpId,departMentId));
		return message;
	}
	
	@RequestMapping(value = "/getAllLeadsAndHRsByStatusLookUpIdAndDepartMentId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getAllLeadsAndHRsByStatusLookUpIdAndDepartMentId(@RequestParam Long statusLookUpId,
			@RequestParam Long departMentId) {
		ResponseEntity<Message> message = toResponse(
				employeeDetailsService.getAllLeadsAndHRsByStatusLookUpIdAndDepartMentId(statusLookUpId,departMentId));
		return message;
	}
	

}
