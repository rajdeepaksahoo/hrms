package org.vrnda.hrms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.vrnda.hrms.repository.dto.CmnDeductionTypesConfigDetailsDTO;
import org.vrnda.hrms.service.CmnDeductionTypesConfigDetailsService;

@RestController
@CrossOrigin(origins = "*")
public class CmnDeductionTypesConfigDetailsController extends GenericController {

	@Autowired
	CmnDeductionTypesConfigDetailsService cmnDeductionTypesConfigDetailsService;  
	
	@RequestMapping(value = "/getDeductionTypesConfigDetailsByConfigurationId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getDeductionTypesConfigDetailsByConfigurationId(Long configurationId) {
		ResponseEntity<Message> message = toResponse(
				cmnDeductionTypesConfigDetailsService.getDeductionTypesConfigDetailsByConfigurationId(configurationId));
		return message;
	}
	
	@RequestMapping(value = "/saveAndUpdateDeductionTypesConfigDetails", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> saveAndUpdateDeductionTypesConfigDetails(@RequestBody List<CmnDeductionTypesConfigDetailsDTO> deductiontypesconfigdetailsList) {
		ResponseEntity<Message> message = toResponse(
				cmnDeductionTypesConfigDetailsService.saveAndUpdateDeductionTypesConfigDetails(deductiontypesconfigdetailsList,getLoggedInUser()));
		return message;
	}
}
