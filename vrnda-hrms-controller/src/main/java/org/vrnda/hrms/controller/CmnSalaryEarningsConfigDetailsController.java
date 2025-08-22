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
import org.vrnda.hrms.repository.dto.CmnSalaryEarningsConfigDetailsDTO;
import org.vrnda.hrms.service.CmnSalaryEarningsConfigDetailsService;

@RestController
@CrossOrigin(origins = "*")
public class CmnSalaryEarningsConfigDetailsController extends GenericController {

	@Autowired
	private CmnSalaryEarningsConfigDetailsService cmnSalaryEarningsConfigDetailsService;

	@RequestMapping(value = "/getSalaryEarningsConfigDetailsByConfigId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getSalaryEarningsConfigDetailsByConfigId(@RequestParam Long configurationId) {
		ResponseEntity<Message> message = toResponse(
				cmnSalaryEarningsConfigDetailsService.getSalaryEarningsConfigDetailsByConfigId(configurationId));
		return message;
	}

	@RequestMapping(value = "/saveOrUpdateSalaryEarningsTypeConfigDetails", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> saveOrUpdateEarningsTypeConfigDetails(
			@RequestBody List<CmnSalaryEarningsConfigDetailsDTO> cmnSalaryEarningsConfigDetailsDTOList) {
		ResponseEntity<Message> message = toResponse(cmnSalaryEarningsConfigDetailsService
				.saveOrUpdateSalaryEarningsConfigDetails(cmnSalaryEarningsConfigDetailsDTOList, getLoggedInUser()));
		return message;
	}
}
