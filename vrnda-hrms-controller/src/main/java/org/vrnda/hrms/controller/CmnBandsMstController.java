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
import org.vrnda.hrms.repository.dto.CmnBandsMstDTO;
import org.vrnda.hrms.service.CmnBandsMstService;

@RestController
@CrossOrigin(origins = "*")
public class CmnBandsMstController extends GenericController {

	@Autowired
	CmnBandsMstService cmnBandsMstService;

	@RequestMapping(value = "/saveOrUpdateBand", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<Message> saveOrUpdateBand(@RequestBody CmnBandsMstDTO cmnBandsMstDTO)  {
		ResponseEntity<Message> message = toResponse(cmnBandsMstService.saveOrUpdateBand(cmnBandsMstDTO, getLoggedInUser()));
		return message;
	}

	@RequestMapping(value = "/deleteBandById", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<Message> deleteBandById(@RequestBody Long bandId)  {
		ResponseEntity<Message> message = toResponse(cmnBandsMstService.deleteBandById(bandId));
		return message;
	}
	@RequestMapping(value = "/deleteBandsList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> deleteBandsList(@RequestBody List<CmnBandsMstDTO> cmnBandsMstDTOList) {
		ResponseEntity<Message> message = toResponse(cmnBandsMstService.deleteBandsList(cmnBandsMstDTOList));
		return message;
	}

	@RequestMapping(value = "/getAllBands", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getAllBands() {
		ResponseEntity<Message> message = toResponse(cmnBandsMstService.getAllBands());
		return message;
	}

	@RequestMapping(value = "/getBandById", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getBandById(@RequestParam Long bandId) {
		ResponseEntity<Message> message = toResponse(cmnBandsMstService.getBandById(bandId));
		return message;
	}

	@RequestMapping(value = "/getBandsByStatusLookupId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getBandsByStatusLookupId(@RequestParam Long statusLookupId) {
		ResponseEntity<Message> message = toResponse(cmnBandsMstService.getBandsByStatusLookupId(statusLookupId));
		return message;
	}

}
