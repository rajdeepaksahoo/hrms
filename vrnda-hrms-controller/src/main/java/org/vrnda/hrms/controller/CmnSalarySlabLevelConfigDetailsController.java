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
import org.vrnda.hrms.repository.dto.CmnSalarySlabLevelConfigDetailsDTO;
import org.vrnda.hrms.service.CmnSalarySlabLevelConfigDetailsService;

@RestController
@CrossOrigin(origins = "*")
public class CmnSalarySlabLevelConfigDetailsController extends GenericController {

	@Autowired
	private CmnSalarySlabLevelConfigDetailsService cmnSalarySlabLevelConfigDetails;
	

	@RequestMapping(value = "/getAllsalarySlabDetails", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> getAllsalarySlabDetails(@RequestParam Long configurationId) {
		ResponseEntity<Message> message = toResponse(cmnSalarySlabLevelConfigDetails.getAllsalarySlabDetails(configurationId));
		return message; 
	}
	
	@RequestMapping(value = "/saveOrUpdatesalarySlab", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<Message> saveOrUpdatesalarySlab(@RequestBody CmnSalarySlabLevelConfigDetailsDTO cmnSalarySlabLevelDto)  {
		ResponseEntity<Message> message = toResponse(cmnSalarySlabLevelConfigDetails.saveOrUpdateSalarySlab(cmnSalarySlabLevelDto,getLoggedInUser()));
		return message;
	}
	
	@RequestMapping(value = "/deleteSalarySlabByCmnSalarySlabLevelConfigDetlsId", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public  ResponseEntity<Message> deleteSalarySlabById(@RequestBody Long cmnSalarySlabLevelConfigDetlsId)  {
		ResponseEntity<Message> message = toResponse(cmnSalarySlabLevelConfigDetails.deleteSalarySlabBycmnSalarySlabLevelConfigDetlsId(cmnSalarySlabLevelConfigDetlsId));
		return message;
	}

	@RequestMapping(value = "/deleteSalarySlabs", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Message> deleteSalarySlabs(@RequestBody List<CmnSalarySlabLevelConfigDetailsDTO> cmnSalarySlabLevelDto) {
	ResponseEntity<Message> message = toResponse(cmnSalarySlabLevelConfigDetails.deleteSalarySlabs(cmnSalarySlabLevelDto));
		return message;
	}
	
	
	
	
	
}
