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
import org.vrnda.hrms.repository.dto.CmnProjectTeamMembersDTO;
import org.vrnda.hrms.service.CmnProjectTeamMembersService;

@RestController
@CrossOrigin(origins = "*")
public class CmnProjectTeamMembersController extends GenericController {

	@Autowired
	CmnProjectTeamMembersService cmnProjectTeamMembersService;

	@RequestMapping(value = "/saveOrUpdateCmnProjectTeamMembers", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> saveOrUpdate(@RequestBody CmnProjectTeamMembersDTO cmnProjectTeamMembersDTO) {
		ResponseEntity<Message> message = toResponse(
				cmnProjectTeamMembersService.saveOrUpdateCmnProjectTeamMembers(cmnProjectTeamMembersDTO, getLoggedInUser()));
		return message;
	}

	@RequestMapping(value = "/getAllCmnProjectTeamMembers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getActiveCmnProjectMembers() {
		ResponseEntity<Message> message = toResponse(cmnProjectTeamMembersService.getAllCmnProjectTeamMembers());
		return message;
	}

	@RequestMapping(value = "/getAllCmnProjectTeamMembersByStatusLookUpId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getAllCmnProjectMembersByStatusLookUpId(@RequestParam Long statusLookUpId) {
		ResponseEntity<Message> message = toResponse(
				cmnProjectTeamMembersService.getAllCmnProjectTeamMembersByStatusLookUpId(statusLookUpId));
		return message;
	}

	@RequestMapping(value = "/getAllCmnProjectTeamMembersByProjectId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getAllCmnProjectMembersByProjectId(@RequestParam Long projectId) {
		ResponseEntity<Message> message = toResponse(
				cmnProjectTeamMembersService.getAllCmnProjectTeamMembersByProjectId(projectId));
		return message;
	}

	@RequestMapping(value = "/getAllCmnProjectTeamMembersByTeamId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getAllCmnProjectTeamMembersByTeamId(@RequestParam Long teamId) {
		ResponseEntity<Message> message = toResponse(
				cmnProjectTeamMembersService.getAllCmnProjectTeamMembersByTeamId(teamId));
		return message;
	}

	@RequestMapping(value = "/getAllCmnProjectTeamMembersByScrumMaster", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getAllProjectTeamMembersByProjectIdAndScrumMaster(@RequestParam Long projectId) {
		ResponseEntity<Message> message = toResponse(
				cmnProjectTeamMembersService.getAllProjectTeamMembersByProjectIdAndScrumMaster(projectId));
		return message;
	}

	@RequestMapping(value = "/getAllCmnProjectTeamMembersByProjectIdTeamId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getAllCmnProjectTeamMembersByProjectIdModuleId(@RequestParam Long projectId,
			Long teamId) {
		ResponseEntity<Message> message = toResponse(
				cmnProjectTeamMembersService.getAllCmnProjectTeamMembersByProjectIdModuleId(projectId, teamId));
		return message;
	}

	@RequestMapping(value = "/getAllDetails", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getAllDetails() {
		ResponseEntity<Message> message = toResponse(cmnProjectTeamMembersService.getAllDetails());
		return message;
	}

	@RequestMapping(value = "/deleteByProjectId", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> deleteByProjectId(@RequestBody CmnProjectTeamMembersDTO projectmembers) {
		ResponseEntity<Message> message = toResponse(cmnProjectTeamMembersService.deleteByProjectId(projectmembers));
		return message;
	}

	@RequestMapping(value = "/deleteProjectTeamMembersDetailsList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> deleteProjectTeamMembersDetailsList(
			@RequestBody List<CmnProjectTeamMembersDTO> projectmembers) {
		ResponseEntity<Message> message = toResponse(
				cmnProjectTeamMembersService.deleteProjectTeamMembersDetailsList(projectmembers));
		return message;
	}

}
