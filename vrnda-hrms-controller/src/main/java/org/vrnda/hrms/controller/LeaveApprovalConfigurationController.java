//package org.vrnda.hrms.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.vrnda.hrms.repository.dto.CmnConfigurationsMstDTO;
//import org.vrnda.hrms.repository.dto.LeaveApprovalConfigMstDto;
//import org.vrnda.hrms.service.CmnConfigurationsMstService;
//import org.vrnda.hrms.service.LeaveApprovalConfigMstService;
//
//@RestController
//@CrossOrigin(origins = "*")
//public class LeaveApprovalConfigurationController extends GenericController {
//	@Autowired
//	LeaveApprovalConfigMstService leaveApprovalConfigMstService;
//	@Autowired
//	CmnConfigurationsMstService cmnConfigurationsMstService;
//
//	@RequestMapping(value = "/insertLeaveApprovalConfigDtls", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<Message> insertLeaveApprovalConfigDtls(
//			@RequestBody LeaveApprovalConfigMstDto leaveApprovalConfigMstDto) {
//		ResponseEntity<Message> message = toResponse(
//				leaveApprovalConfigMstService.insertLeaveApprovalConfigDtls(leaveApprovalConfigMstDto));
//		return message;
//	}
//
//	// Operations on CmnConfigurationMst table for Grid1
//
//	// @RequestMapping(value = "/getAllLeaveApprovalConfigDtls", method =
//	// RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//	// public ResponseEntity<Message> getAllLeaveApprovalConfigDtls(String
//	// lookupName) {
//	// ResponseEntity<Message> message =
//	// toResponse(cmnConfigurationsMstService.getAllCmnConfigsMst(lookupName));
//	// return message;
//	// }
//
//	@RequestMapping(value = "/deleteLeaveApprovalConfigDtls", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<Message> deleteLeaveApprovalConfigDtls(@RequestParam Long lvTypeLookupId,
//			@RequestParam Long configurationId) {
//		ResponseEntity<Message> message = toResponse(
//				leaveApprovalConfigMstService.deleteLeaveApprovalConfigDtls(lvTypeLookupId, configurationId));
//		return message;
//	}
//
//	@RequestMapping(value = "/deleteLeaveApprovalConfigDtlsBYConfigId", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<Message> deleteLeaveApprovalConfigDtlsBYConfigId(@RequestParam Long configurationId) {
//		ResponseEntity<Message> message = toResponse(
//				leaveApprovalConfigMstService.deleteLeaveApprovalConfigDtlsBYConfigId(configurationId));
//		return message;
//	}
//
//	@RequestMapping(value = "/getAllLeaveApprovalConfigDtlsGrid", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<Message> getAllLeaveApprovalConfigDtlsGrid(@RequestParam Long configurationId) {
//		ResponseEntity<Message> message = toResponse(
//				leaveApprovalConfigMstService.getAllLeaveApprovalConfigDtlsGrid(configurationId));
//		return message;
//	}
//
//	@RequestMapping(value = "/getAllLeaveApprovalConfigDtlsGrid2", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<Message> getAllLeaveApprovalConfigDtlsGrid2(@RequestParam Long configurationId,
//			@RequestParam Long lvTypeLookupId) {
//		ResponseEntity<Message> message = toResponse(
//				leaveApprovalConfigMstService.getAllLeaveApprovalConfigDtlsGrid2(configurationId, lvTypeLookupId));
//		return message;
//	}
//
//	@RequestMapping(value = "/getLvApprovalRecords", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<Message> getLvApprovalRecords(@RequestBody List<CmnConfigurationsMstDTO> cmnConfigDtoList) {
//		ResponseEntity<Message> message = toResponse(
//				leaveApprovalConfigMstService.getLvApprovalRecords(cmnConfigDtoList));
//		return message;
//	}
//
//}