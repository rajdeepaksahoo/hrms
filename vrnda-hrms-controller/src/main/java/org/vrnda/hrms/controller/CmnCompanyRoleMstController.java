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
import org.vrnda.hrms.repository.dto.CmnCompanyRolesMstDTO;
import org.vrnda.hrms.service.CmnCompanyRolesMstService;

@RestController
@CrossOrigin(origins = "*")
public class CmnCompanyRoleMstController extends GenericController {

	@Autowired
	private CmnCompanyRolesMstService CmnCompanyRolesMstService;

	@RequestMapping(value = "/saveOrUpdateCompanyRole", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> saveOrUpdateCompanyRole(@RequestBody CmnCompanyRolesMstDTO cmnCompanyRoleMstDto) {
		ResponseEntity<Message> message = toResponse(CmnCompanyRolesMstService.saveOrUpdateCompanyRole(cmnCompanyRoleMstDto, getLoggedInUser()));
		return message;
	}

	@RequestMapping(value = "/deleteCompanyRoleByRoleId", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> deleteCompanyRoleByRoleId(@RequestBody Long companyRoleId) {
		ResponseEntity<Message> message = toResponse(CmnCompanyRolesMstService.deleteCompanyRoleByRoleId(companyRoleId));
		return message;
	}

	@RequestMapping(value = "/deleteCompanyRolesList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> deleteCompanyRolesList(@RequestBody List<CmnCompanyRolesMstDTO> cmnCompanyRoleMstDtoList) {
		ResponseEntity<Message> message = toResponse(CmnCompanyRolesMstService.deleteCompanyRolesList(cmnCompanyRoleMstDtoList));
		return message;
	}

	@RequestMapping(value = "/getAllCompanyRoles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getAllCompanyRoles() {
		ResponseEntity<Message> message = toResponse(CmnCompanyRolesMstService.getAllCompanyRoles());
		return message;
	}

	@RequestMapping(value = "/getAllCompanyRolesByStatusLookupId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getAllCompanyRolesByStatusLookupId(@RequestParam Long statusLookupId) {
		ResponseEntity<Message> message = toResponse(CmnCompanyRolesMstService.getAllCompanyRolesByStatusLookupId(statusLookupId));
		return message;
	}

	@RequestMapping(value = "/getCompanyRoleByCompanyRoleId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getCompanyRoleByCompanyRoleId(@RequestParam Long companyRoleId) {
		ResponseEntity<Message> message = toResponse(CmnCompanyRolesMstService.getCompanyRoleByCompanyRoleId(companyRoleId));
		return message;
	}

	@RequestMapping(value = "/getCompanyRolesByStatusLookupId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getCompanyRolesByStatusLookupId(@RequestParam Long statusLookupId) {
		ResponseEntity<Message> message = toResponse(CmnCompanyRolesMstService.getCompanyRolesByStatusLookupId(statusLookupId));
		return message;
	}
	
	@RequestMapping(value = "/getCompanyRolesByManager", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getCompanyRolesByIsManager() {
		ResponseEntity<Message> message = toResponse(CmnCompanyRolesMstService.getCompanyRolesByLeadOrManager());
		return message;
	}
	
	@RequestMapping(value = "/getCompanyRolesByHr", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getCompanyRolesByHr() {
		ResponseEntity<Message> message = toResponse(CmnCompanyRolesMstService.getCompanyRolesByHr());
		return message;
	}
	
	@RequestMapping(value = "/getCompanyRolesByStatusLookupIdAndManager", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getCompanyRolesByStatusLookupIdAndManager(@RequestParam Long statusLookupId) {
		ResponseEntity<Message> message = toResponse(CmnCompanyRolesMstService.getCompanyRolesByStatusLookupIdAndManager(statusLookupId));
		return message;
	}
	
	@RequestMapping(value = "/getCompanyRolesByStatusLookupIdAndHr", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getCompanyRolesByStatusLookupIdAndHr(@RequestParam Long statusLookupId) {
		ResponseEntity<Message> message = toResponse(CmnCompanyRolesMstService.getCompanyRolesByStatusLookupIdAndHr(statusLookupId));
		return message;
	}
	
	@RequestMapping(value = "/getCompanyRoleIdByRoleName", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getCompanyRoleIdByRoleName(@RequestParam String companyRoleName){
		ResponseEntity<Message> message = toResponse(CmnCompanyRolesMstService);
		return message;
	}
	
	@RequestMapping(value = "/getCompanyRolesByStatusLookupIdAndDepartmentId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getCompanyRolesByStatusLookupId(@RequestParam Long statusLookupId , @RequestParam Long departMentID) {
		ResponseEntity<Message> message = toResponse(CmnCompanyRolesMstService.getCompanyRolesByStatusLookupIdAndDepartmentId(statusLookupId,departMentID));
		return message;
	}

}
