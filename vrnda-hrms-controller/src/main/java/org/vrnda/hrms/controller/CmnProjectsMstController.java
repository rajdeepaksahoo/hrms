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
import org.vrnda.hrms.repository.dto.CmnProjectsMstDTO;
import org.vrnda.hrms.service.CmnProjectsMstService;

@RestController
@CrossOrigin(origins = "*")
public class CmnProjectsMstController extends GenericController {

	@Autowired
	CmnProjectsMstService cmnProjectsMstService;

	@RequestMapping(value = "/saveOrUpdateProject", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> saveOrUpdateProject(@RequestBody CmnProjectsMstDTO cmnProjectsMstDTO) {
		ResponseEntity<Message> message = toResponse(
				cmnProjectsMstService.saveOrUpdateProject(cmnProjectsMstDTO, getLoggedInUser()));
		return message;
	}

	@RequestMapping(value = "/deleteProjectByProjectId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> deleteProjectByProjectId(@RequestParam Long projectId) {
		ResponseEntity<Message> message = toResponse(cmnProjectsMstService.deleteProjectById(projectId));
		return message;
	}

	@RequestMapping(value = "/deleteProjectDetailsList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> deleteProjectDetailsList(
			@RequestBody List<CmnProjectsMstDTO> cmnProjectsMstDTOList) {
		ResponseEntity<Message> message = toResponse(
				cmnProjectsMstService.deleteProjectDetailsList(cmnProjectsMstDTOList));
		return message;
	}

	@RequestMapping(value = "/getAllProjects", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getAllProjects() {
		ResponseEntity<Message> message = toResponse(cmnProjectsMstService.getAllProjects());
		return message;
	}

	@RequestMapping(value = "/getProjectByProjectId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getProjectByProjectId(@RequestParam Long projectId) {
		ResponseEntity<Message> message = toResponse(cmnProjectsMstService.getProjectByProjectId(projectId));
		return message;
	}

	@RequestMapping(value = "/getAllProjectsByStatusLookUpId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getAllProjectsByStatusLookUpId(@RequestParam Long statusLookUpId) {
		ResponseEntity<Message> message = toResponse(
				cmnProjectsMstService.getAllProjectsByStatusLookUpId(statusLookUpId));
		return message;
	}

	@RequestMapping(value = "/getAllProjectsDetails", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getAllProjectsDetails() {
		ResponseEntity<Message> message = toResponse(cmnProjectsMstService.getAllProjectsDetails());
		return message;
	}

}
