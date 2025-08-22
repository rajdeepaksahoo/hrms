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
import org.vrnda.hrms.repository.dto.CmnAppRoleAccessPrivilegesDTO;
import org.vrnda.hrms.repository.dto.CmnAppRolesMstDTO;
import org.vrnda.hrms.service.CmnAppRoleAccessPrivilegesService;

@RestController
@CrossOrigin(origins = "*")
public class CmnAppRoleAccessPrivilegesController extends GenericController{
	
	@Autowired
	CmnAppRoleAccessPrivilegesService cmnapproleaccessprivilegesservice;
	
	@RequestMapping(value = "/getAppAccessPrivileges", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getAppRoleByAppRoleId(@RequestParam Long appRoleId) {
	ResponseEntity<Message> message = toResponse(cmnapproleaccessprivilegesservice.getRoleAccessPrivilegesRoels(appRoleId));
		return message;
	}
	
	@RequestMapping(value = "/saveOrUpdateAppAccessPrivileges", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> saveOrUpdateAppRole(@RequestBody List<CmnAppRoleAccessPrivilegesDTO> cmnapproleaccessprivilegesdto) {
		ResponseEntity<Message>message = toResponse(cmnapproleaccessprivilegesservice.saveOrUpdateRoleAccessAccessprivilegesChild(cmnapproleaccessprivilegesdto, getLoggedInUser()));
		return message;
	}

}
