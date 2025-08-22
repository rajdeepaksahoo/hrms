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
import org.vrnda.hrms.repository.dto.CmnGeneralLoanConfigDetailsDTO;
import org.vrnda.hrms.service.CmnGeneralLoanConfigDetailsService;

@RestController
@CrossOrigin(origins = "*")
public class CmnGeneralLoanConfigDetailsController extends GenericController {

	@Autowired
	CmnGeneralLoanConfigDetailsService cmnGeneralLoanConfigDetailsService;

	@RequestMapping(value = "/saveOrUpdateGeneralLoanConfigDetails", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> saveOrUpdateGeneralLoanConfigDetails(@RequestBody CmnGeneralLoanConfigDetailsDTO cmnLoanConfigDetailsDto) {
		ResponseEntity<Message> message = toResponse(cmnGeneralLoanConfigDetailsService.saveOrUpdateGeneralLoanConfigDetails(cmnLoanConfigDetailsDto, getLoggedInUser()));
		return message;
	}

	@RequestMapping(value = "/deleteGeneralLoanConfigDetailsByCmnGenLoanConfigDetlsId", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> deleteGeneralLoanConfigDetailsByCmnGenLoanConfigDetlsId(@RequestParam  Long cmnLoanConfigDetlsId) {
		ResponseEntity<Message> message = toResponse(cmnGeneralLoanConfigDetailsService.deleteGeneralLoanConfigDetailsByCmnGenLoanConfigDetlsId(cmnLoanConfigDetlsId));
		return message;
	}

	@RequestMapping(value = "/deleteGeneralLoanConfigDetailsList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> deleteGeneralLoanConfigDetailsList(@RequestBody List<CmnGeneralLoanConfigDetailsDTO> cmnLoanConfigDetailsDtoList) {
		ResponseEntity<Message> message = toResponse(cmnGeneralLoanConfigDetailsService.deleteGeneralLoanConfigDetailsList(cmnLoanConfigDetailsDtoList));
		return message;
	}

	@RequestMapping(value = "/getGeneralLoanConfigDetailsByConfigurationId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getGeneralLoanConfigDetailsByConfigurationId(@RequestParam  Long configurationId) {
		ResponseEntity<Message> message = toResponse(cmnGeneralLoanConfigDetailsService.getGeneralLoanConfigDetailsByConfigurationId(configurationId));
		return message;
	}

	@RequestMapping(value = "/getGeneralLoanConfigDetailsByCmnGenLoanConfigDetlsId", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getGeneralLoanConfigDetailsByCmnGenLoanConfigDetlsId(@RequestParam  Long cmnLoanConfigDetlsId) {
		ResponseEntity<Message> message = toResponse(cmnGeneralLoanConfigDetailsService.getGeneralLoanConfigDetailsByCmnGenLoanConfigDetlsId(cmnLoanConfigDetlsId));
		return message;
	}
	
	@RequestMapping(value = "/getGeneralLoanConfigDetailsByConfigurationIdAndStatusLookupId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getGeneralLoanConfigDetailsByConfigurationIdAndStatusLookupId(@RequestParam  Long configurationId, @RequestParam  Long statusLookupId) {
		ResponseEntity<Message> message = toResponse(cmnGeneralLoanConfigDetailsService.getGeneralLoanConfigDetailsByConfigurationIdAndStatusLookupId(configurationId, statusLookupId));
		return message;
	}
	
	@RequestMapping(value = "/getLoanConfigDetailsByLookUpId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getLoanConfigDetailsByLookUpId() {
		ResponseEntity<Message> message = toResponse(cmnGeneralLoanConfigDetailsService.getLoanConfigDetailsByLookUpId());
		return message;
	}
	
	

}
