package org.vrnda.hrms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.vrnda.hrms.repository.dto.TasksAndAllocationsDTO;
import org.vrnda.hrms.service.TasksAndAllocationsService;

@RestController
@CrossOrigin(origins = "*")
public class TasksAndAllocationsController extends GenericController {

	@Autowired
	private TasksAndAllocationsService tasksAndAllocationsService;

	@RequestMapping(value = "/loadIssuesFromJiraByProjectName", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> loadIssuesFromJiraByProjectName(@RequestParam String projectName) {
		ResponseEntity<Message> message = toResponse(
				tasksAndAllocationsService.loadIssuesFromJiraByProjectName(projectName));
		return message;

	}

	@RequestMapping(value = "/getAllTasksAndAllocations", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getAllTasksAndAllocations(@RequestParam Long employeeId,
			@RequestParam Long statusLookUpId) {
		ResponseEntity<Message> message = toResponse(
				tasksAndAllocationsService.getAllTasksAndAllocations(employeeId, statusLookUpId));
		return message;

	}

	@RequestMapping(value = "/getAllTaskDetailsBasedOnTaskId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getAllTaskDetailsBasedOnTaskId(@RequestParam String taskId) {
		ResponseEntity<Message> message = toResponse(tasksAndAllocationsService.getAllTaskDetailsBasedOnTaskId(taskId));
		return message;

	}

	@RequestMapping(value = "/getAllTaskDetailsBasedOnAssigne", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getAllTaskDetailsBasedOnAssigne(@RequestParam Long assignee,
			@RequestParam Long statusLookUpId, @RequestParam String viewType) {
		ResponseEntity<Message> message = toResponse(
				tasksAndAllocationsService.getAllTaskDetailsBasedOnAssigne(assignee, statusLookUpId, viewType));
		return message;

	}

	@RequestMapping(value = "/updateTaskAndAllocations", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> updateTaskAndAllocations(
			@RequestBody TasksAndAllocationsDTO tasksAndAllocationsDTO) {
		ResponseEntity<Message> message = toResponse(
				tasksAndAllocationsService.updateTaskAndAllocations(tasksAndAllocationsDTO, getLoggedInUser()));
		return message;
	}

	@RequestMapping(value = "/getAllMembersByProjectIdAndStatusLookUpId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getAllMembersByProjectIdAndStatusLookUpId(@RequestParam Long projectId,
			@RequestParam Long statusLookUpId) {
		ResponseEntity<Message> message = toResponse(
				tasksAndAllocationsService.getAllMembersByProjectIdAndStatusLookUpId(projectId, statusLookUpId));
		return message;
	}

	@RequestMapping(value = "/getAllActiveTaskDetailsBasedOnAssigne", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getAllActiveTaskDetailsBasedOnAssigne(@RequestParam Long assignee) {
		ResponseEntity<Message> message = toResponse(
				tasksAndAllocationsService.getAllActiveTaskDetailsBasedOnAssigne(assignee));
		return message;

	}
}
