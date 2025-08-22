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
import org.springframework.web.multipart.MultipartFile;
import org.vrnda.hrms.service.TasksAndAllocationsCommentsService;

@RestController
@CrossOrigin(origins = "*")
public class TasksAndAllocationsCommentsController extends GenericController {

	@Autowired
	private TasksAndAllocationsCommentsService tasksAndAllocationsCommentsService;

	@RequestMapping(value = "/createComment", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> createComment(@RequestBody MultipartFile commentBody, @RequestParam String taskId) {
		ResponseEntity<Message> message = toResponse(
				tasksAndAllocationsCommentsService.createComment(commentBody, taskId, getLoggedInUser()));
		return message;
	}

	@RequestMapping(value = "/getCommentsByTaskId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getCommentsByTaskId(@RequestParam String taskId) {
		ResponseEntity<Message> message = toResponse(tasksAndAllocationsCommentsService.getCommentsByTaskId(taskId));
		return message;
	}

}
