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
import org.vrnda.hrms.repository.dto.CmnLeaveConfigDetailsDTO;
import org.vrnda.hrms.service.CmnLeaveConfigDetailsService;

@RestController
@CrossOrigin(origins = "*")
public class CmnLeaveConfigDetailsController extends GenericController {

	@Autowired
	CmnLeaveConfigDetailsService cmnLeaveConfigDetailsService;

	@RequestMapping(value = "/getLeaveConfigDetailsByConfigurationId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getLeaveConfigDetailsByConfigurationId(Long configurationId) {
		ResponseEntity<Message> message = toResponse(
				cmnLeaveConfigDetailsService.getLeaveConfigDetailsByConfigurationId(configurationId));
		return message;
	}

	@RequestMapping(value = "/saveAndUpdateLeaveConfigDetails", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> saveAndUpdateLeaveConfigDetails(@RequestBody List<CmnLeaveConfigDetailsDTO> leaveconfigdetailsList) {
		ResponseEntity<Message> message = toResponse(
				cmnLeaveConfigDetailsService.saveAndUpdateLeaveConfigDetails(leaveconfigdetailsList,getLoggedInUser()));
		return message;
	}

}
