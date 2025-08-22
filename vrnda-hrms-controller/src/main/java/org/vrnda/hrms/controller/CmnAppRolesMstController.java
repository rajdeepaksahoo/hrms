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
import org.vrnda.hrms.repository.dto.CmnAppRolesMstDTO;
import org.vrnda.hrms.service.CmnAppRolesMstService;

@RestController
@CrossOrigin(origins = "*")
public class CmnAppRolesMstController extends GenericController{

	@Autowired
	private CmnAppRolesMstService cmnAppRoleMstService;

	@RequestMapping(value = "/saveOrUpdateAppRole", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> saveOrUpdateAppRole(@RequestBody CmnAppRolesMstDTO cmnAppRolesMstDTO) {
		ResponseEntity<Message>message = toResponse(cmnAppRoleMstService.saveOrUpdateAppRole(cmnAppRolesMstDTO, getLoggedInUser()));
		return message;
	}

	@RequestMapping(value = "/deleteAppRoleByAppRoleId", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> deleteAppRoleByAppRoleId(@RequestBody Long appRoleId) {
		ResponseEntity<Message> message = toResponse(cmnAppRoleMstService.deleteAppRoleByAppRoleId(appRoleId));
		return message;
	}

	@RequestMapping(value = "/deleteAppRolesList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> deleteAppRolesList(@RequestBody List<CmnAppRolesMstDTO> cmnAppRoleMstDTOList) {
		ResponseEntity<Message> message = toResponse(cmnAppRoleMstService.deleteAppRolesList(cmnAppRoleMstDTOList));
		return message;
	}

	@RequestMapping(value = "/getAllAppRoles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getAllAppRoles() {
		ResponseEntity<Message> message = toResponse(cmnAppRoleMstService.getAllAppRoles());
		return message;
	}

	@RequestMapping(value = "/getAppRoleByAppRoleId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getAppRoleByAppRoleId(@RequestParam Long appRoleId) {
		ResponseEntity<Message> message = toResponse(cmnAppRoleMstService.getAppRoleByAppRoleId(appRoleId));
		return message;
	}

	@RequestMapping(value = "/getAppRoleByStatusLookupId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getAppRoleByStatusLookupId(@RequestParam Long statusLookupId) {
		ResponseEntity<Message> message = toResponse(cmnAppRoleMstService.getAppRoleByStatusLookupId(statusLookupId));
		return message;
	}

}
