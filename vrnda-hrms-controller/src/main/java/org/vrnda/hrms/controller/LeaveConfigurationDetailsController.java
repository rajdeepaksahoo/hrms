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
import org.vrnda.hrms.repository.dto.CmnLeavePlanConfigDetailsDTO;
import org.vrnda.hrms.service.CmnConfigurationsMstService;
import org.vrnda.hrms.service.CmnLeavePlanConfigDetailsService;


@RestController
@CrossOrigin(origins = "*")
public class LeaveConfigurationDetailsController extends GenericController {


	@Autowired
	CmnConfigurationsMstService cmnConfigurationsMstService;
	
	@Autowired
	CmnLeavePlanConfigDetailsService cmnLeavePlanConfigDetailsService;

             
	@RequestMapping(value = "/saveOrUpdateLeavePlans", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> saveOrUpdateLeavePlans(
			@RequestBody CmnLeavePlanConfigDetailsDTO cmnConfigDetailsMstDto) {
		ResponseEntity<Message> message = toResponse(
				cmnLeavePlanConfigDetailsService.saveOrUpdateLeavePlans(cmnConfigDetailsMstDto,getLoggedInUser()));
		return message;
	}

	@RequestMapping(value = "/getLeavePlanConfigDetailsByConfigurationIdAndleaveTypeId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getLeavePlanConfigDetailsByConfigurationIdAndleaveTypeId(@RequestParam Long configurationId,
			@RequestParam Long leaveTypeId) {
		ResponseEntity<Message> message = toResponse(
				cmnLeavePlanConfigDetailsService.getLeavePlanConfigDetailsByConfigurationIdAndleaveTypeId(configurationId, leaveTypeId));
		return message;
	}
	@RequestMapping(value = "/deleteLeavePlanByCmnlvplnConfigdeltsId", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> deleteLeavePlanByCmnlvplnConfigdeltsId(@RequestBody Long CmnlvplnConfigdeltsId) {
		ResponseEntity<Message> message = toResponse(
				cmnLeavePlanConfigDetailsService.deleteLeavePlanByCmnlvplnConfigdeltsId(CmnlvplnConfigdeltsId));
		return message;
	}

	@RequestMapping(value = "/deleteLeavePlans", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> deleteLeavePlans(@RequestBody List<CmnLeavePlanConfigDetailsDTO> cmnLeavePlanConfigDetail) {
		ResponseEntity<Message> message = toResponse(
				cmnLeavePlanConfigDetailsService.deleteLeavePlans(cmnLeavePlanConfigDetail));
		return message;
	}


}
