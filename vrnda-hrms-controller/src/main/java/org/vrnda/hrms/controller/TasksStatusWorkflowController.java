package org.vrnda.hrms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.vrnda.hrms.service.TasksStatusWorkflowService;

@RestController
@CrossOrigin(origins = "*")
public class TasksStatusWorkflowController extends GenericController {

	@Autowired
	private TasksStatusWorkflowService tasksStatusWorkflowService;

	@RequestMapping(value = "/getAllTasksStatusWorkFlow", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getAllTasksStatusWorkFlow() {
		ResponseEntity<Message> message = toResponse(tasksStatusWorkflowService.getAllTasksStatusWorkFlow());
		return message;

	}

}
