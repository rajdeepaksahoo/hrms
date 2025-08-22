//package org.vrnda.hrms.service.impl;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.repository.PagingAndSortingRepository;
//import org.springframework.stereotype.Service;
//import org.vrnda.hrms.entity.LeaveConfigurationDetailsEntity;
//import org.vrnda.hrms.repository.CmnLookupMstRepository;
//import org.vrnda.hrms.repository.LeaveConfigurationDetailsRepository;
//import org.vrnda.hrms.repository.dto.LeaveConfigurationDetailsCommitBean;
//import org.vrnda.hrms.repository.dto.LeaveConfigurationDetailsDto;
//import org.vrnda.hrms.repository.dto.LookupIdAndNameDTO;
//import org.vrnda.hrms.service.CmnLookupMstService;
//import org.vrnda.hrms.service.CmnTableSeqService;
//import org.vrnda.hrms.service.LeaveConfigurationDetailsService;
//import org.vrnda.hrms.service.resultset.CmnLookupMstResultSet;
//import org.vrnda.hrms.service.resultset.LeaveConfigurationDetailsResultSet;
//import org.vrnda.hrms.utils.ApplicationConstants;
//import org.vrnda.hrms.utils.BaseUtils;
//
//@Service
//public class LeaveConfigurationDetailsServiceImpl extends GenericServiceImpl<LeaveConfigurationDetailsEntity, Long>
//		implements LeaveConfigurationDetailsService {
//
//	public LeaveConfigurationDetailsServiceImpl(
//			PagingAndSortingRepository<LeaveConfigurationDetailsEntity, Long> typeRepository) {
//		super(typeRepository);
//	}
//
//	@Autowired
//	LeaveConfigurationDetailsRepository leaveConfigDtlsRepository;
//
//	@Autowired
//	CmnLookupMstService cmnLookupMstService;
//
//	@Autowired
//	CmnLookupMstRepository cmnLookupMstRepository;
//	
//	@Autowired
//	CmnTableSeqService cmnTableSeqService;
//
//	@Override
//	public LeaveConfigurationDetailsResultSet getAllLeaveConfigDtls(Long lvConfLookupid) {
//		LeaveConfigurationDetailsResultSet leaveConfigDtlsResultSet = new LeaveConfigurationDetailsResultSet();
//		List<LeaveConfigurationDetailsEntity> leaveConfigDtlsEntity = new ArrayList<LeaveConfigurationDetailsEntity>();
//		List<LeaveConfigurationDetailsDto> leaveConfigDtlsDto = new ArrayList<LeaveConfigurationDetailsDto>();
//		try {
//			leaveConfigDtlsEntity = leaveConfigDtlsRepository.getDataForConfMst(lvConfLookupid);
//			if (leaveConfigDtlsEntity != null && leaveConfigDtlsEntity.size() > 0) {
//				for (LeaveConfigurationDetailsEntity lvConfEntityobj : leaveConfigDtlsEntity) {
//					LeaveConfigurationDetailsDto lvCnfigObj = new LeaveConfigurationDetailsDto();
//					BeanUtils.copyProperties(lvConfEntityobj, lvCnfigObj);
//					leaveConfigDtlsDto.add(lvCnfigObj);
//				}
//				leaveConfigDtlsResultSet.setLeaveConfigurationDetailsDtoList(leaveConfigDtlsDto);
//			} else {
//				leaveConfigDtlsResultSet.setStatus(false);
//				leaveConfigDtlsResultSet.setMessage("No Data found");
//				leaveConfigDtlsResultSet.setMessageDescription("Data not found with the given Id");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			leaveConfigDtlsResultSet.setStatus(false);
//			leaveConfigDtlsResultSet.setMessage("Exception");
//			leaveConfigDtlsResultSet.setMessageDescription(e.getMessage());
//		}
//		return leaveConfigDtlsResultSet;
//	}
//
//	@Override
//	public LeaveConfigurationDetailsResultSet getAllLeavesConfigGridData(long lvConfLookupId) {
//		LeaveConfigurationDetailsResultSet leaveConfigDtlsResultSet = new LeaveConfigurationDetailsResultSet();
//		List<LeaveConfigurationDetailsDto> lstLvConfgResultsetDto = new ArrayList<LeaveConfigurationDetailsDto>();
//		try {
//			List<LeaveConfigurationDetailsEntity> lv = leaveConfigDtlsRepository.getDataForConfMst(lvConfLookupId);
//			for (LeaveConfigurationDetailsEntity obj : lv) {
//				LeaveConfigurationDetailsDto lvConfgResultset = new LeaveConfigurationDetailsDto();
//				lvConfgResultset.setLeaveConfigDetlsId(obj.getLeaveConfigDetlsId());
//				lvConfgResultset.setMonthId(obj.getMonthId());
//				lvConfgResultset.setMonth(cmnLookupMstRepository.getMonth(obj.getMonthId()));
//				lvConfgResultset.setConfigurationId(obj.getConfigurationId());
//				lvConfgResultset.setLvTypeLookupId(obj.getLvTypeLookupId());
//				lvConfgResultset.setLeaveType(cmnLookupMstRepository.getLeaveType(obj.getLvTypeLookupId()));
//				lvConfgResultset.setNoOfLeaves(obj.getNoOfLeaves());
//				lstLvConfgResultsetDto.add(lvConfgResultset);
//				leaveConfigDtlsResultSet.setLeaveConfigurationDetailsDtoList(lstLvConfgResultsetDto);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			leaveConfigDtlsResultSet.setStatus(false);
//			leaveConfigDtlsResultSet.setMessage("Exception");
//			leaveConfigDtlsResultSet.setMessageDescription(e.getMessage());
//		}
//
//		return leaveConfigDtlsResultSet;
//	}
//
//	@Override
//	public LeaveConfigurationDetailsResultSet insertLeaveConfigDtls(
//			LeaveConfigurationDetailsCommitBean leaveConfigDetailsCommitBean) {
//		LeaveConfigurationDetailsResultSet leaveConfigDtlsResultSet = new LeaveConfigurationDetailsResultSet();
//		LeaveConfigurationDetailsEntity leaveConfigDtlsEntity = new LeaveConfigurationDetailsEntity();
//		CmnLookupMstResultSet monthLookuoId = new CmnLookupMstResultSet();
//		monthLookuoId = cmnLookupMstService.getLookupDetailsWithLookupName(ApplicationConstants.MONTHS);
//		CmnLookupMstResultSet lvTypeLookupId = new CmnLookupMstResultSet();
////		lvTypeLookupId = cmnLookupMstService.getLookupDetailsWithLookupName(ApplicationConstants.LEAVE_TYPE);
//		try {
//			if (leaveConfigDetailsCommitBean.getInsertLeaveConfigDetails() != null
//					&& leaveConfigDetailsCommitBean.getInsertLeaveConfigDetails().size() > 0) {
////				lvTypeLookupId = cmnLookupMstService.getLookupDetailsWithLookupName(leaveConfigDetailsCommitBean.getInsertLeaveConfigDetails().get(1));
//				for (LeaveConfigurationDetailsDto obj : leaveConfigDetailsCommitBean.getInsertLeaveConfigDetails()) {
//					if (obj.getLeaveConfigDetlsId() == null) {
//						for (LookupIdAndNameDTO obj2 : monthLookuoId.getLookupIdAndNameDTOList()) {
//							if (obj2.getLookupName().equals(obj.getMonth())) {
//								obj.setMonthId(obj2.getLookupId());
//							}
//						}
//						lvTypeLookupId = cmnLookupMstService.getLookupDetailsWithLookupName(obj.getLvConfLookupName());
//						for (LookupIdAndNameDTO obj2 : lvTypeLookupId.getLookupIdAndNameDTOList()) {
//							if (obj2.getLookupName().equals(obj.getLeaveType())) {
//								obj.setLvTypeLookupId(obj2.getLookupId());
//							}
//						}
//						obj.setLeaveConfigDetlsId(cmnTableSeqService.getNextSequence(ApplicationConstants.CMN_COMMON_LEAVE_CONFIG_DETAILS, ApplicationConstants.CMN_COMMON_LV_CONFIG_DETLS_ID));
//						BeanUtils.copyProperties(obj, leaveConfigDtlsEntity);
//						BaseUtils.setBaseData(leaveConfigDtlsEntity, obj.getStatusLookupId());
//						save(leaveConfigDtlsEntity);
//					}
//				}
//			}
//			if (leaveConfigDetailsCommitBean.getUpdateLeaveConfigDetails() != null
//					&& leaveConfigDetailsCommitBean.getUpdateLeaveConfigDetails().size() > 0) {
//
//				for (LeaveConfigurationDetailsDto obj : leaveConfigDetailsCommitBean.getUpdateLeaveConfigDetails()) {
//					for (LookupIdAndNameDTO obj2 : monthLookuoId.getLookupIdAndNameDTOList()) {
//						if (obj2.getLookupName().equals(obj.getMonth())) {
//							obj.setMonthId(obj2.getLookupId());
//						}
//					}
//					lvTypeLookupId = cmnLookupMstService.getLookupDetailsWithLookupName(obj.getLvConfLookupName());
//					for (LookupIdAndNameDTO obj2 : lvTypeLookupId.getLookupIdAndNameDTOList()) {
//						if (obj2.getLookupName().equals(obj.getLeaveType())) {
//							obj.setLvTypeLookupId(obj2.getLookupId());
//						}
//					}
//					leaveConfigDtlsEntity = getDataByValues(obj);
//					if (leaveConfigDtlsEntity == null || leaveConfigDtlsEntity.getLeaveConfigDetlsId() == null) {
//						leaveConfigDtlsEntity = new LeaveConfigurationDetailsEntity();
//						obj.setLeaveConfigDetlsId(cmnTableSeqService.getNextSequence(ApplicationConstants.CMN_COMMON_LEAVE_CONFIG_DETAILS, ApplicationConstants.CMN_COMMON_LV_CONFIG_DETLS_ID));
//						BeanUtils.copyProperties(obj, leaveConfigDtlsEntity);
//						BaseUtils.setBaseData(leaveConfigDtlsEntity, null);
//						save(leaveConfigDtlsEntity);
//					} else {
//						if (leaveConfigDtlsEntity.getNoOfLeaves() != obj.getNoOfLeaves()) {
//							leaveConfigDtlsEntity.setNoOfLeaves(obj.getNoOfLeaves());
//							BaseUtils.modifyBaseData(leaveConfigDtlsEntity);
//							save(leaveConfigDtlsEntity);
//						}
//					}
//				}
//			}
//		} catch (Exception e) {
//			leaveConfigDtlsResultSet.setStatus(false);
//			leaveConfigDtlsResultSet.setMessage("Exception ");
//			leaveConfigDtlsResultSet.setMessageDescription(e.getMessage());
//		}
//		return leaveConfigDtlsResultSet;
//	}
//
//	public LeaveConfigurationDetailsEntity getDataByValues(LeaveConfigurationDetailsDto leaveConfigurationDto) {
//		LeaveConfigurationDetailsEntity leaveConfigDtlsEntity = new LeaveConfigurationDetailsEntity();
//		try {
//			leaveConfigDtlsEntity = leaveConfigDtlsRepository.getLeaveConfigurationDetailsByConfidIdAndMonthIdAndLeaveTypeId(leaveConfigurationDto.getConfigurationId(),
//					leaveConfigurationDto.getMonthId(), leaveConfigurationDto.getLvTypeLookupId());
//		} catch (Exception e) {
//			leaveConfigDtlsEntity.setLeaveConfigDetlsId(null);
//		}
//		return leaveConfigDtlsEntity;
//	}
//
//	@Override
//	public LeaveConfigurationDetailsResultSet deleteLvConfigDetails(LeaveConfigurationDetailsDto delLvConfigDtls) {
//		LeaveConfigurationDetailsEntity leaveConfigDtlsEntity = new LeaveConfigurationDetailsEntity();
//		LeaveConfigurationDetailsResultSet leaveConfigDtlsResultSet = new LeaveConfigurationDetailsResultSet();
//		try {
//			if (delLvConfigDtls != null) {
//				BeanUtils.copyProperties(delLvConfigDtls, leaveConfigDtlsEntity);
//				delete(leaveConfigDtlsEntity);
//			}
//		} catch (Exception e) {
//			leaveConfigDtlsResultSet.setStatus(false);
//			leaveConfigDtlsResultSet.setMessage("Exception ");
//			leaveConfigDtlsResultSet.setMessageDescription(e.getMessage());
//		}
//		return leaveConfigDtlsResultSet;
//	}
//}
