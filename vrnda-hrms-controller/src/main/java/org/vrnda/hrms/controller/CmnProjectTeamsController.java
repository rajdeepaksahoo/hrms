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
import org.vrnda.hrms.repository.dto.CmnProjectTeamsDTO;
import org.vrnda.hrms.service.CmnProjectTeamsService;

@RestController
@CrossOrigin(origins = "*")
public class CmnProjectTeamsController extends GenericController {

	@Autowired
	private CmnProjectTeamsService cmnProjectTeamsService;

	@RequestMapping(value = "/saveOrUpdateTeams", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> saveorUpdateTeams(@RequestBody CmnProjectTeamsDTO cmnProjectTeamsDTO) {
		ResponseEntity<Message> message = toResponse(
				cmnProjectTeamsService.saveorUpdateTeams(cmnProjectTeamsDTO, getLoggedInUser()));
		return message;
	}

	@RequestMapping(value = "/getAllProjectTeams", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getAllProjectTeams() {
		ResponseEntity<Message> message = toResponse(cmnProjectTeamsService.getAllProjectTeams());
		return message;
	}

	@RequestMapping(value = "/getProjectTeamByTeamId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getProjectTeamByTeamId(@RequestParam Long teamId) {
		ResponseEntity<Message> message = toResponse(cmnProjectTeamsService.getProjectTeamsByTeamId(teamId));
		return message;
	}

	@RequestMapping(value = "/getAllProjectTeamsByStatusLookUpId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getAllProjectTeamsByStatusLookUpId(@RequestParam Long statusLookUpId) {
		ResponseEntity<Message> message = toResponse(
				cmnProjectTeamsService.getAllProjectTeamsByStatusLookUpId(statusLookUpId));
		return message;
	}

	@RequestMapping(value = "/getAllProjectTeamsByProjectId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getAllProjectTeamsByProjectId(Long projectId) {
		ResponseEntity<Message> message = toResponse(cmnProjectTeamsService.getAllProjectTeamsByProjectId(projectId));
		return message;
	}

	@RequestMapping(value = "/getAllProjectTeamsByProjectIdAndStatusLookupId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getAllProjectTeamsByProjectIdAndStatusLookupId(Long projectId,
			Long statusLookupId) {
		ResponseEntity<Message> message = toResponse(
				cmnProjectTeamsService.getAllProjectTeamsByProjectIdAndStatusLookupId(projectId, statusLookupId));
		return message;
	}

	@RequestMapping(value = "/deleteProjectTeamsList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> deleteProjectTeams(@RequestBody List<CmnProjectTeamsDTO> cmnProjectTeamsDTO) {
		ResponseEntity<Message> message = toResponse(cmnProjectTeamsService.deleteProjectTeams(cmnProjectTeamsDTO));
		return message;
	}

	@RequestMapping(value = "/deleteProjectTeamByTeamId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> deleteProjectTeamByTeamId(@RequestParam Long teamId) {
		ResponseEntity<Message> message = toResponse(cmnProjectTeamsService.deleteProjectTeamByTeamId(teamId));
		return message;
	}

}
