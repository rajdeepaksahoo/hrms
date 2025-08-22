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
import org.vrnda.hrms.repository.dto.CmnLookupMstDTO;
import org.vrnda.hrms.service.CmnLookupMstService;

@RestController
@CrossOrigin(origins = "*")
public class CmnLookupMstController extends GenericController {

	@Autowired
	CmnLookupMstService cmnLookupMstService;

	@RequestMapping(value = "/getLookupDetailsByLookupName", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getLookupDetailsByLookupName(@RequestParam String lookupName) {
		ResponseEntity<Message> message = toResponse(cmnLookupMstService.getLookupDetailsByLookupName(lookupName));
		return message;
	}

	@RequestMapping(value = "/saveOrUpdateLookup", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> saveOrUpdateLookup(@RequestBody CmnLookupMstDTO cmnLookupMstDto) {
		ResponseEntity<Message> message = toResponse(cmnLookupMstService.saveOrUpdateLookup(cmnLookupMstDto, getLoggedInUser()));
		return message;
	}

	@RequestMapping(value = "/getLookupIdAndNameListByParentLookupName", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getLookupIdAndNameListByParentLookupName(@RequestParam String parentLookupName ) {
		ResponseEntity<Message> message = toResponse(cmnLookupMstService.getLookupIdAndNameListByParentLookupName(parentLookupName));
		return message;
	}

	@RequestMapping(value = "/getLookupIdAndNameListByParentLookupNameAndParentLookupId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getLookupIdAndNameListByParentLookupNameAndParentLookupId(@RequestParam String parentLookupName, @RequestParam Long parentLookupId) {
		ResponseEntity<Message> message = toResponse(cmnLookupMstService.getLookupIdAndNameListByParentLookupNameAndParentLookupId(parentLookupName, parentLookupId));
		return message;
	}

	@RequestMapping(value = "/getLookupIdByLookupNameAndParentLookupName", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getLookupIdByLookupNameAndParentLookupName(@RequestParam String lookupName, @RequestParam String parentLookupName){
		ResponseEntity<Message> message = toResponse(cmnLookupMstService.getLookupIdByLookupNameAndParentLookupName(lookupName, parentLookupName));
		return message;
	}

	@RequestMapping(value = "/getLookupByLookupName", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getLookupByLookupName(@RequestParam String lookupName){
		ResponseEntity<Message> message = toResponse(cmnLookupMstService.getLookupByLookupName(lookupName));
		return message;
	}

	@RequestMapping(value = "/getLookupByParentLookupName", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getLookupByParentLookupName(@RequestParam String parentLookupName){
		ResponseEntity<Message> message = toResponse(cmnLookupMstService.getLookupByParentLookupName(parentLookupName));
		return message;
	}

	@RequestMapping(value = "/deleteLookupByLookupId", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> deleteLookupByLookupId(@RequestBody Long lookupId){
		ResponseEntity<Message> message = toResponse(cmnLookupMstService.deleteLookupByLookupId(lookupId));
		return message;
	}

	@RequestMapping(value = "/deleteLookupByLookup", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> deleteLookupByLookup(@RequestBody CmnLookupMstDTO cmnLookupMstDto){
		ResponseEntity<Message> message = toResponse(cmnLookupMstService.deleteLookupByLookup(cmnLookupMstDto));
		return message;
	}

	@RequestMapping(value = "/deleteLookupByLookupList", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> deleteLookupByLookupList(@RequestBody List<CmnLookupMstDTO> cmnLookupMstDtoList){
		ResponseEntity<Message> message = toResponse(cmnLookupMstService.deleteLookupByLookupList(cmnLookupMstDtoList));
		return message;
	}

	@RequestMapping(value = "/getLookupNameAndLookupIdMapingsByParentLookupName", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getLookupNameAndLookupIdMapingsByParentLookupName(@RequestParam String parentLookupName){
		ResponseEntity<Message> message = toResponse(cmnLookupMstService.getLookupNameAndLookupIdMapingsByParentLookupName(parentLookupName));
		return message;
	}

	@RequestMapping(value = "/getLookupIdAndLookupNameMapingsByParentLookupName", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getLookupIdAndLookupNameMapingsByParentLookupName(@RequestBody String parentLookupName){
		ResponseEntity<Message> message = toResponse(cmnLookupMstService.getLookupIdAndLookupNameMapingsByParentLookupName(parentLookupName));
		return message;
	}

}
