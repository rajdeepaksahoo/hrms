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
import org.vrnda.hrms.repository.dto.CmnSalaryAllowanceConfigDetailsDTO;
import org.vrnda.hrms.repository.dto.CmnSalaryAnnualBenefitsConfigDetailsDTO;
import org.vrnda.hrms.service.CmnSalaryAnnualBenefitsConfigDetailsService;

@RestController
@CrossOrigin(origins = "*")
public class CmnSalaryAnnualBenefitsConfigDetailsController extends GenericController {
	
	@Autowired
	private CmnSalaryAnnualBenefitsConfigDetailsService cmnSalaryAnnualBenefitsConfigDetailsService;
	
	
	@RequestMapping(value = "/getSalaryAnnualTypeconfigDetails", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getSalaryAnnualTypeconfigDetails(@RequestParam Long configurationId) {
		ResponseEntity<Message> message = toResponse(
				cmnSalaryAnnualBenefitsConfigDetailsService.getSalaryAnnualTypeconfigDetails(configurationId));
		return message;
	}
	
	@RequestMapping(value = "/saveOrUpdateAnnualTypes", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> saveOrUpdateAnnualTypes(@RequestBody  List<CmnSalaryAnnualBenefitsConfigDetailsDTO>  cmnSalaryAnnualTypesDto) {
		ResponseEntity<Message> message = toResponse(cmnSalaryAnnualBenefitsConfigDetailsService
				.saveOrUpdateAnnualTypes(cmnSalaryAnnualTypesDto, getLoggedInUser()));
		return message;
	
	}
	
}
