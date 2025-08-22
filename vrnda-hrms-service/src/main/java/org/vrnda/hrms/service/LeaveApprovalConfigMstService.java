//package org.vrnda.hrms.service;
//
//import java.util.List;
//
//import org.vrnda.hrms.entity.LeaveApprovalConfigMstEntity;
//import org.vrnda.hrms.repository.dto.CmnConfigurationsMstDTO;
//import org.vrnda.hrms.repository.dto.LeaveApprovalConfigMstDto;
//import org.vrnda.hrms.service.resultset.LeaveApprovalConfigMstResultset;
//
//
//public interface LeaveApprovalConfigMstService  extends GenericService<LeaveApprovalConfigMstEntity, Long>{
//
//	public LeaveApprovalConfigMstResultset getAllLeaveApprovalConfigDtlsGrid(Long configurationId );
//	
//	public LeaveApprovalConfigMstResultset getAllLeaveApprovalConfigDtlsGrid2(Long configurationId, Long lvTypeLookupId );
//
//	public LeaveApprovalConfigMstResultset insertLeaveApprovalConfigDtls(LeaveApprovalConfigMstDto leaveApprovalConfigMstDto);
//	
//	public LeaveApprovalConfigMstResultset deleteLeaveApprovalConfigDtlsBYlvApprovalConfigId(Long lvApprovalConfigId);
//
//	public LeaveApprovalConfigMstResultset deleteLeaveApprovalConfigDtlsBYConfigId(Long configurationId);
//
//	public LeaveApprovalConfigMstResultset deleteLeaveApprovalConfigDtls(Long lvTypeLookupId, Long configurationId);
//	
//	public LeaveApprovalConfigMstResultset getLvApprovalRecords(List<CmnConfigurationsMstDTO> cmnConfigDtoList);
//	
//	public LeaveApprovalConfigMstResultset getLvApprovalRecordWithConfigIdAndLevel(Long configurationId, Long lvTypeLookupId);
//
//}
