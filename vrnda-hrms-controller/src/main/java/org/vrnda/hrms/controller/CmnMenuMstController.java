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
import org.vrnda.hrms.repository.dto.CmnMenusMstDTO;
import org.vrnda.hrms.service.CmnMenusMstService;

@RestController
@CrossOrigin(origins = "*")
public class CmnMenuMstController extends GenericController {

	@Autowired
	CmnMenusMstService cmnMenusMstService;

	@RequestMapping(value = "/saveOrUpdateMenu", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> saveOrUpdateMenu(@RequestBody CmnMenusMstDTO cmnMenusMstDto) {
		ResponseEntity<Message> message = toResponse(cmnMenusMstService.saveOrUpdateMenu(cmnMenusMstDto, getLoggedInUser()));
		return message;
	}

	@RequestMapping(value = "/getAllMenus", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getAllMenus() {
		ResponseEntity<Message> message = toResponse(cmnMenusMstService.getAllMenus());
		return message;
	}

	@RequestMapping(value = "/getMenusByMenuTypeId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getMenusByMenuType(@RequestParam Long menuTypeId) {
		ResponseEntity<Message> message = toResponse(cmnMenusMstService.getMenusByMenuTypeId(menuTypeId));
		return message;
	}

	@RequestMapping(value = "/getMenusByParentMenuId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getMenusByParentMenuId(@RequestParam Long parentMenuId) {
		ResponseEntity<Message> message = toResponse(cmnMenusMstService.getMenusByParentMenuId(parentMenuId));
		return message;
	}

}
