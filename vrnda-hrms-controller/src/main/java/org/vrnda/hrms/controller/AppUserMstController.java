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
import org.vrnda.hrms.repository.dto.AppUsersMstDTO;
import org.vrnda.hrms.service.AppUsersMstService;

@RestController
@CrossOrigin(origins = "*")
public class AppUserMstController extends GenericController {

	@Autowired
	AppUsersMstService appUsersMstService;

	@RequestMapping(value = "/saveOrUpdateAppUser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> saveOrUpdateAppUser(@RequestBody AppUsersMstDTO appUsersMstDTO) {
		ResponseEntity<Message> message = toResponse(
				appUsersMstService.saveOrUpdateAppUser(appUsersMstDTO, getLoggedInUser()));
		return message;
	}

	@RequestMapping(value = "/deleteAppUserByAppUserId", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> deleteAppUserByAppUserId(@RequestParam Long appUserId) {
		ResponseEntity<Message> message = toResponse(appUsersMstService.deleteAppUserByAppUserId(appUserId));
		return message;
	}

	@RequestMapping(value = "/getAllAppUsers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getAllAppUsers() {
		ResponseEntity<Message> message = toResponse(appUsersMstService.getAllAppUsers());
		return message;
	}

	@RequestMapping(value = "/getAppUsersByStatusLookupId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getAppUsersByStatusLookupId(@RequestParam Long statusLookupId) {
		ResponseEntity<Message> message = toResponse(appUsersMstService.getAppUsersByStatusLookupId(statusLookupId));
		return message;
	}

	@RequestMapping(value = "/getAppUserByUserId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getAppUserByUserId(Long appUserId) {
		ResponseEntity<Message> message = toResponse(appUsersMstService.getAppUserByUserId(appUserId));
		return message;
	}

	@RequestMapping(value = "/getAppUserAllDetails", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getAppUserAllDetails(@RequestParam String username) {
		ResponseEntity<Message> message = toResponse(appUsersMstService.getAppUserAllDetails(username));
		return message;
	}

	@RequestMapping(value = "/changeUserPassword", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> changeUserPassword(@RequestBody AppUsersMstDTO appUsersMstDTO) {
		ResponseEntity<Message> message = toResponse(
				appUsersMstService.changeUserPassword(appUsersMstDTO, getLoggedInUser()));
		return message;
	}

	@RequestMapping(value = "/firstTimeLoginChangePass", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> firstTimeLoginChangePass(@RequestBody AppUsersMstDTO appUsersMstDTO) {
		ResponseEntity<Message> message = toResponse(appUsersMstService.firstTimeLoginChangePass(appUsersMstDTO));
		return message;
	}

}
