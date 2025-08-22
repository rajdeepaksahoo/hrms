package org.vrnda.hrms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.vrnda.hrms.service.CmnTableSeqService;

@RestController
@CrossOrigin(origins = "*")
public class CmnTableSequenceMstController extends GenericController {

	@Autowired
	CmnTableSeqService cmnTableSeqService;
	
	@RequestMapping(value = "/getNextSequence", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getNextSequence(@RequestParam String tableName,  @RequestParam String columnName) {
		ResponseEntity<Message> message = toResponse(cmnTableSeqService.getNextSequence(tableName, columnName));
		return message;
	}
	
	
}
