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
import org.vrnda.hrms.service.CmnConfigurationsMstService;
import org.vrnda.hrms.service.CmnSalaryAllowanceConfigDetailsService;

@RestController
@CrossOrigin(origins = "*")
public class CmnSalaryAllowanceConfigDetailsController extends GenericController {

	@Autowired
	private CmnSalaryAllowanceConfigDetailsService cmnSalaryAllowanceConfigDetailsService;

	@Autowired
	CmnConfigurationsMstService cmnConfigurationsMstService;

	@RequestMapping(value = "/getAllsalaryAllowanceDetails", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getAllsalaryAllowanceDetails(@RequestParam Long configurationId) {
		ResponseEntity<Message> message = toResponse(
				cmnSalaryAllowanceConfigDetailsService.getAllsalaryAllowanceDetails(configurationId));
		return message;
	}
	
	@RequestMapping(value = "/saveOrUpdatesalaryAllowances", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> saveOrUpdatesalaryAllowances(
			@RequestBody CmnSalaryAllowanceConfigDetailsDTO cmnSalaryAllowancDto) {
		ResponseEntity<Message> message = toResponse(cmnSalaryAllowanceConfigDetailsService
				.saveOrUpdatesalaryAllowances(cmnSalaryAllowancDto, getLoggedInUser()));
		return message;
	}

	@RequestMapping(value = "/deleteSalarySlabBycmnSalaryAllowanceConfigDetlsId", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> deleteSalarySlabAllowanceById(@RequestBody Long cmnSalaryAllowanceConfigDetlsId) {
		ResponseEntity<Message> message = toResponse(cmnSalaryAllowanceConfigDetailsService
				.deleteSalarySlabBycmnSalaryAllowanceConfigDetlsId(cmnSalaryAllowanceConfigDetlsId));
		return message;
	}

	@RequestMapping(value = "/deleteSalarySlabsAllowance", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> deleteSalarySlabsAllowance(
			@RequestBody List<CmnSalaryAllowanceConfigDetailsDTO> cmnSalaryAllowancDto) {
		ResponseEntity<Message> message = toResponse(
				cmnSalaryAllowanceConfigDetailsService.deleteSalarySlabsAllowance(cmnSalaryAllowancDto));
		return message;
	}
	
	@RequestMapping(value = "/saveOrUpdateAllowances", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> saveOrUpdateAllowances(@RequestBody  List<CmnSalaryAllowanceConfigDetailsDTO>  cmnSalaryAllowancDto) {
		ResponseEntity<Message> message = toResponse(cmnSalaryAllowanceConfigDetailsService
				.saveOrUpdateAllowances(cmnSalaryAllowancDto, getLoggedInUser()));
		return message;
	
	}
	
	
	@RequestMapping(value = "/getAllsalaryAllowanceDetailsBycnfgIdAndLevelId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getAllsalaryAllowanceDetailsBycnfgIdAndLevelId(@RequestParam Long configurationId,@RequestParam Long cmnSalarySlabLevelConfigDetlsId) {
		ResponseEntity<Message> message = toResponse(
				cmnSalaryAllowanceConfigDetailsService.getAllsalaryAllowanceDetailsBycnfgIdAndLevelId(configurationId,cmnSalarySlabLevelConfigDetlsId));
		return message;
	}
	
}
