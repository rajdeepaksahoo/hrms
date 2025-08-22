//package org.vrnda.hrms.service.impl;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.repository.PagingAndSortingRepository;
//import org.springframework.stereotype.Service;
//import org.vrnda.hrms.entity.CmnConfigurationsMstEntity;
//import org.vrnda.hrms.entity.LeaveApprovalConfigMstEntity;
//import org.vrnda.hrms.repository.CmnConfigurationsMstRepository;
//import org.vrnda.hrms.repository.LeaveApprovalConfigMstRepository;
//import org.vrnda.hrms.repository.dto.CmnConfigurationsMstDTO;
//import org.vrnda.hrms.repository.dto.LeaveApprovalConfigDTO;
//import org.vrnda.hrms.repository.dto.LeaveApprovalConfigMstCommitBean;
//import org.vrnda.hrms.repository.dto.LeaveApprovalConfigMstDto;
//import org.vrnda.hrms.service.CmnConfigurationsMstService;
//import org.vrnda.hrms.service.CmnLookupMstService;
//import org.vrnda.hrms.service.CmnTableSeqService;
//import org.vrnda.hrms.service.LeaveApprovalConfigMstService;
//import org.vrnda.hrms.service.resultset.CmnLookupMstResultSet;
//import org.vrnda.hrms.service.resultset.LeaveApprovalConfigMstResultset;
//import org.vrnda.hrms.utils.ApplicationConstants;
//import org.vrnda.hrms.utils.BaseUtils;
//
//@Service
//public class LeaveApprovalConfigMstServiceImpl extends GenericServiceImpl<LeaveApprovalConfigMstEntity, Long>
//		implements LeaveApprovalConfigMstService {
//
//	public LeaveApprovalConfigMstServiceImpl(
//			PagingAndSortingRepository<LeaveApprovalConfigMstEntity, Long> typeRepository) {
//		super(typeRepository);
//	}
//
//	@Autowired
//	LeaveApprovalConfigMstRepository leaveApprovalConfigMstRepository;
//
//	@Autowired
//	CmnConfigurationsMstService cmnConfigurationsMstService;
//
//	@Autowired
//	CmnConfigurationsMstRepository cmnConfigurationMstRepository;
//
//	@Autowired
//	CmnLookupMstService cmnLookupMstService;
//	
//	@Autowired
//	CmnTableSeqService cmnTableSeqService;
//
//	@Override
//	public LeaveApprovalConfigMstResultset insertLeaveApprovalConfigDtls(
//			LeaveApprovalConfigMstDto leaveApprovalConfigMstDto) {
//		LeaveApprovalConfigMstResultset leaveApprovalConfigMstResultset = new LeaveApprovalConfigMstResultset();
//		List<LeaveApprovalConfigMstCommitBean> leaveApprovalConfigMstCommitBeanList = leaveApprovalConfigMstDto
//				.getLeaveApprovalConfigMstCommitBeanList();
//		CmnLookupMstResultSet lvTypeLookupId = new CmnLookupMstResultSet();
//		lvTypeLookupId = cmnLookupMstService.getLookupDetailsWithLookupName(ApplicationConstants.CONFIG_TYPE);
//		try {
//
//			if (leaveApprovalConfigMstCommitBeanList != null && leaveApprovalConfigMstCommitBeanList.size() > 0) {
//				for (LeaveApprovalConfigMstCommitBean leaveApprovalConfigMstCommitBean : leaveApprovalConfigMstDto
//						.getLeaveApprovalConfigMstCommitBeanList()) {
//					if (leaveApprovalConfigMstCommitBean.getInsertLeaveApprovalConfigCommitBean() != null
//							&& leaveApprovalConfigMstCommitBean.getInsertLeaveApprovalConfigCommitBean().size() > 0) {
//						for (LeaveApprovalConfigDTO obj : leaveApprovalConfigMstCommitBean
//								.getInsertLeaveApprovalConfigCommitBean()) {
//							LeaveApprovalConfigMstEntity lvApprConfigMstEntity = new LeaveApprovalConfigMstEntity();
//							obj.setLvApprovalConfigId(cmnTableSeqService.getNextSequence(ApplicationConstants.LEAVE_APPROVAL_CONFIG_MST, ApplicationConstants.LV_APPROVAL_CONFIG_ID));
//							BeanUtils.copyProperties(obj, lvApprConfigMstEntity);
//							BaseUtils.setBaseData(lvApprConfigMstEntity, lvApprConfigMstEntity.getStatusLookupId());
//							lvApprConfigMstEntity.setConfigurationId(obj.getConfigurationId());
//							save(lvApprConfigMstEntity);
//						}
//					}
//
//					if (leaveApprovalConfigMstCommitBean.getUpdateLeaveApprovalConfigCommitBean() != null
//							&& leaveApprovalConfigMstCommitBean.getUpdateLeaveApprovalConfigCommitBean().size() > 0) {
//						for (LeaveApprovalConfigDTO obj : leaveApprovalConfigMstCommitBean
//								.getUpdateLeaveApprovalConfigCommitBean()) {
//							LeaveApprovalConfigMstEntity lvApprConfigMstEntity = new LeaveApprovalConfigMstEntity();
//							lvApprConfigMstEntity = leaveApprovalConfigMstRepository
//									.getLeaveApprovalConfigMstByLvApprovalConfigId(obj.getLvApprovalConfigId());
//							BeanUtils.copyProperties(obj, lvApprConfigMstEntity);
//							BaseUtils.modifyBaseData(lvApprConfigMstEntity);
//							save(lvApprConfigMstEntity);
//						}
//					}
//
//					if (leaveApprovalConfigMstCommitBean.getDeleteLeaveApprovalConfigCommitBean() != null
//							&& leaveApprovalConfigMstCommitBean.getDeleteLeaveApprovalConfigCommitBean().size() > 0) {
//						for (LeaveApprovalConfigDTO obj : leaveApprovalConfigMstCommitBean
//								.getDeleteLeaveApprovalConfigCommitBean()) {
//							leaveApprovalConfigMstResultset = deleteLeaveApprovalConfigDtlsBYlvApprovalConfigId(
//									obj.getLvApprovalConfigId());
//						}
//						return leaveApprovalConfigMstResultset;
//					}
//
//				}
//			} else {
//				leaveApprovalConfigMstResultset.setStatus(false);
//				leaveApprovalConfigMstResultset.setMessage("Error");
//				leaveApprovalConfigMstResultset.setMessageDescription("Error saving Common configuration details");
//			}
//		} catch (Exception e) {
//			leaveApprovalConfigMstResultset.setStatus(false);
//			leaveApprovalConfigMstResultset.setMessage("Exception");
//			leaveApprovalConfigMstResultset.setMessageDescription(e.getMessage());
//		}
//		return leaveApprovalConfigMstResultset;
//
//	}
//
//	@Override
//	public LeaveApprovalConfigMstResultset deleteLeaveApprovalConfigDtls(Long lvTypeLookupId, Long configurationId) {
//		LeaveApprovalConfigMstResultset leaveApprovalConfigMstResultset = new LeaveApprovalConfigMstResultset();
//
//		List<LeaveApprovalConfigMstEntity> leaveApprovalConfigMstEntity = null;
//		CmnConfigurationsMstEntity cmnConfigMstEntity = null;
//		try {
//
//			if (configurationId != null) {
//				cmnConfigMstEntity = cmnConfigurationMstRepository.getConfigurationByConfigurationId(configurationId);
//
//				if (cmnConfigMstEntity != null && lvTypeLookupId != null) {
//					leaveApprovalConfigMstEntity = leaveApprovalConfigMstRepository
//							.getLeaveApprovalConfigMstByConfigutrationIdAndLvTypeLookupId(configurationId,
//									lvTypeLookupId);
//					if (leaveApprovalConfigMstEntity != null) {
//						for (LeaveApprovalConfigMstEntity obj : leaveApprovalConfigMstEntity) {
//							delete(obj);
//						}
//					}
//					cmnConfigurationMstRepository.delete(cmnConfigMstEntity);
//				} else {
//					leaveApprovalConfigMstResultset.setStatus(false);
//					leaveApprovalConfigMstResultset.setMessage("Unable to delete.");
//					leaveApprovalConfigMstResultset.setMessageDescription("Invalid input.");
//				}
//			}
//
//			else {
//				leaveApprovalConfigMstResultset.setStatus(false);
//				leaveApprovalConfigMstResultset.setMessage("Unable to delete.");
//				leaveApprovalConfigMstResultset.setMessageDescription("Invalid input.");
//			}
//		} catch (Exception e) {
//			leaveApprovalConfigMstResultset.setStatus(false);
//			leaveApprovalConfigMstResultset.setMessage("Error");
//			leaveApprovalConfigMstResultset.setMessageDescription(e.getMessage());
//		}
//		return leaveApprovalConfigMstResultset;
//
//	}
//
//	@Override
//	public LeaveApprovalConfigMstResultset deleteLeaveApprovalConfigDtlsBYlvApprovalConfigId(Long lvApprovalConfigId) {
//		LeaveApprovalConfigMstResultset leaveApprovalConfigMstResultset = new LeaveApprovalConfigMstResultset();
//
//		List<LeaveApprovalConfigMstEntity> leaveApprovalConfigMstEntity = null;
//		// CmnConfigurationsMstEntity cmnConfigMstEntity = null;
//		try {
//
//			// if (lvApprovalConfigId != null) {
//			// cmnConfigMstEntity =
//			// cmnConfigurationMstRepository.getConfigbyId(lvApprovalConfigId);
//			if (lvApprovalConfigId != null) {
//				leaveApprovalConfigMstEntity = leaveApprovalConfigMstRepository
//						.getLeaveApprovalConfigMstByLvAppConfigId(lvApprovalConfigId);
//				if (leaveApprovalConfigMstEntity != null) {
//					for (LeaveApprovalConfigMstEntity obj : leaveApprovalConfigMstEntity) {
//						delete(obj);
//					}
//
//				} else {
//					leaveApprovalConfigMstResultset.setStatus(false);
//					leaveApprovalConfigMstResultset.setMessage("Unable to delete.");
//					leaveApprovalConfigMstResultset.setMessageDescription("Invalid input.");
//				}
//			}
//
//			else {
//				leaveApprovalConfigMstResultset.setStatus(false);
//				leaveApprovalConfigMstResultset.setMessage("Unable to delete.");
//				leaveApprovalConfigMstResultset.setMessageDescription("Invalid input.");
//			}
//		} catch (Exception e) {
//			leaveApprovalConfigMstResultset.setStatus(false);
//			leaveApprovalConfigMstResultset.setMessage("Error");
//			leaveApprovalConfigMstResultset.setMessageDescription(e.getMessage());
//		}
//		return leaveApprovalConfigMstResultset;
//	}
//
//	@Override
//	public LeaveApprovalConfigMstResultset deleteLeaveApprovalConfigDtlsBYConfigId(Long configurationId) {
//		LeaveApprovalConfigMstResultset leaveApprovalConfigMstResultset = new LeaveApprovalConfigMstResultset();
//
//		List<LeaveApprovalConfigMstEntity> leaveApprovalConfigMstEntity = null;
//		CmnConfigurationsMstEntity cmnConfigMstEntity = null;
//		try {
//
//			if (configurationId != null) {
//				cmnConfigMstEntity = cmnConfigurationMstRepository.getConfigurationByConfigurationId(configurationId);
//				if (configurationId != null) {
//					leaveApprovalConfigMstEntity = leaveApprovalConfigMstRepository
//							.getLeaveApprovalConfigMstByConfigutrationId(configurationId);
//					if (leaveApprovalConfigMstEntity != null) {
//						for (LeaveApprovalConfigMstEntity obj : leaveApprovalConfigMstEntity) {
//							delete(obj);
//						}
//					}
//				}
//				/*
//				 * else { leaveApprovalConfigMstResultset.setStatus(false);
//				 * leaveApprovalConfigMstResultset.setMessage("Unable to delete.");
//				 * leaveApprovalConfigMstResultset.setMessageDescription("Invalid input."); }
//				 */
//			}
//
//			else {
//				leaveApprovalConfigMstResultset.setStatus(false);
//				leaveApprovalConfigMstResultset.setMessage("Unable to delete.");
//				leaveApprovalConfigMstResultset.setMessageDescription("Invalid input.");
//			}
//		} catch (Exception e) {
//			leaveApprovalConfigMstResultset.setStatus(false);
//			leaveApprovalConfigMstResultset.setMessage("Error");
//			leaveApprovalConfigMstResultset.setMessageDescription(e.getMessage());
//		}
//		return leaveApprovalConfigMstResultset;
//	}
//
//	@Override
//	public LeaveApprovalConfigMstResultset getAllLeaveApprovalConfigDtlsGrid(Long configurationId) {
//		LeaveApprovalConfigMstResultset leaveApprovalConfigMstResultset = new LeaveApprovalConfigMstResultset();
//		CmnConfigurationsMstEntity cmnConfigMstEntity = null;
//
//		try {
//			if (configurationId != null) {
//				cmnConfigMstEntity = cmnConfigurationMstRepository.getConfigurationByConfigurationId(configurationId);
//				leaveApprovalConfigMstResultset.setCmnConfigMstResultSet(cmnConfigMstEntity);
//				if (cmnConfigMstEntity != null) {
//					List<LeaveApprovalConfigMstEntity> leaveApprovalConfigMstEntity = leaveApprovalConfigMstRepository
//							.getLeaveApprovalConfigMstByConfigutrationId(configurationId);
//					if (leaveApprovalConfigMstEntity.size() > 0) {
//
//						leaveApprovalConfigMstResultset.setLeaveApprovalConfigMstEntity(leaveApprovalConfigMstEntity);
//					}
//
//				}
//			} else {
//				leaveApprovalConfigMstResultset.setStatus(false);
//				leaveApprovalConfigMstResultset.setMessage("Failed");
//				leaveApprovalConfigMstResultset.setMessageDescription("User does not exist");
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			leaveApprovalConfigMstResultset.setStatus(false);
//			leaveApprovalConfigMstResultset.setMessage("Exception");
//			leaveApprovalConfigMstResultset.setMessageDescription(e.getMessage());
//		}
//
//		return leaveApprovalConfigMstResultset;
//	}
//
//	@Override
//	public LeaveApprovalConfigMstResultset getAllLeaveApprovalConfigDtlsGrid2(Long configurationId,
//			Long lvTypeLookupId) {
//		LeaveApprovalConfigMstResultset leaveApprovalConfigMstResultset = new LeaveApprovalConfigMstResultset();
//		List<LeaveApprovalConfigMstEntity> leaveApprovalConfigMstEntity = null;
//		try {
//			if (configurationId != null && lvTypeLookupId != null) {
//				leaveApprovalConfigMstEntity = leaveApprovalConfigMstRepository
//						.getLeaveApprovalConfigMstByConfigutrationIdAndLvTypeLookupId(configurationId, lvTypeLookupId);
//				if (leaveApprovalConfigMstEntity.size() > 0) {
//
//					leaveApprovalConfigMstResultset.setLeaveApprovalConfigMstEntity(leaveApprovalConfigMstEntity);
//				}
//			} else {
//				leaveApprovalConfigMstResultset.setStatus(false);
//				leaveApprovalConfigMstResultset.setMessage("Failed");
//				leaveApprovalConfigMstResultset.setMessageDescription("User does not exist");
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			leaveApprovalConfigMstResultset.setStatus(false);
//			leaveApprovalConfigMstResultset.setMessage("Exception");
//			leaveApprovalConfigMstResultset.setMessageDescription(e.getMessage());
//		}
//
//		return leaveApprovalConfigMstResultset;
//
//	}
//
//	@Override
//	public LeaveApprovalConfigMstResultset getLvApprovalRecords(List<CmnConfigurationsMstDTO> cmnConfigDtoList) {
//		LeaveApprovalConfigMstResultset leaveApprovalConfigMstResultset = new LeaveApprovalConfigMstResultset();
//		Integer returnCount = null;
//		// List<LeaveApprovalConfigMstEntity> leaveApprovalConfigMstEntity = null;
//		try {
//			if (cmnConfigDtoList != null) {
//				for (CmnConfigurationsMstDTO obj : cmnConfigDtoList) {
//					List<LeaveApprovalConfigMstEntity> list = leaveApprovalConfigMstRepository
//							.getRecords(obj.getConfigurationId());
//					if (list != null && list.size() > 0) {
//						returnCount = 1;
//					}
//					leaveApprovalConfigMstResultset.setCount(returnCount);
//				}
//			} else {
//				leaveApprovalConfigMstResultset.setStatus(false);
//				leaveApprovalConfigMstResultset.setMessage("Failed");
//				leaveApprovalConfigMstResultset.setMessageDescription("User does not exist");
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			leaveApprovalConfigMstResultset.setStatus(false);
//			leaveApprovalConfigMstResultset.setMessage("Exception");
//			leaveApprovalConfigMstResultset.setMessageDescription(e.getMessage());
//		}
//
//		return leaveApprovalConfigMstResultset;
//	}
//
//	@Override
//	public LeaveApprovalConfigMstResultset getLvApprovalRecordWithConfigIdAndLevel(Long configurationId,
//			Long lvTypeLookupId) {
//		LeaveApprovalConfigMstResultset lvApprConfigDataResultset = new LeaveApprovalConfigMstResultset();
//		List<LeaveApprovalConfigMstEntity> lvApprConfigData = new ArrayList<LeaveApprovalConfigMstEntity>();
//		lvApprConfigData = leaveApprovalConfigMstRepository.getRecordsByIdLevelLvType(configurationId, lvTypeLookupId);
//		if(lvApprConfigData != null && lvApprConfigData.size()>0) {
//			lvApprConfigDataResultset.setLeaveApprovalConfigMstEntity(lvApprConfigData);
//		} else {
//			lvApprConfigDataResultset.setStatus(false);
//			lvApprConfigDataResultset.setMessage("Error");
//			lvApprConfigDataResultset.setMessageDescription("Leave Approval data is not present with this data.");
//		}
//		return lvApprConfigDataResultset;
//	}
//
//}
