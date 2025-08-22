//package org.vrnda.hrms.service.impl;
//
//import java.util.List;
//
//import org.apache.commons.beanutils.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.repository.PagingAndSortingRepository;
//import org.springframework.stereotype.Service;
//import org.vrnda.hrms.entity.CmnSalaryAllowanceConfigDetailsEntity;
//import org.vrnda.hrms.repository.AllowanceLevelRepository;
//import org.vrnda.hrms.repository.dto.CmnSalaryAllowanceConfigDetailsCommitBean;
//import org.vrnda.hrms.repository.dto.CmnSalaryAllowanceConfigDetailsDTO;
//import org.vrnda.hrms.service.CmnSalaryAllowanceLevelService;
//import org.vrnda.hrms.service.CmnTableSeqService;
//import org.vrnda.hrms.service.resultset.CmnSalaryAllowanceConfigDetailResultSet;
//import org.vrnda.hrms.utils.ApplicationConstants;
//import org.vrnda.hrms.utils.BaseUtils;
//
//@Service
//public class CmnSalaryAllowanceLevelServiceImpl extends GenericServiceImpl<CmnSalaryAllowanceConfigDetailsEntity, Long>
//implements CmnSalaryAllowanceLevelService{
//
//	public CmnSalaryAllowanceLevelServiceImpl(PagingAndSortingRepository<CmnSalaryAllowanceConfigDetailsEntity, Long> typeRepository) {
//		super(typeRepository);
//	}
//	@Autowired
//	private AllowanceLevelRepository allowanceLevelRepository;
//
//	@Autowired
//	CmnTableSeqService cmnTableSeqService;
//
//	@Override
//	public CmnSalaryAllowanceConfigDetailResultSet getAllowanceLevelById(Long configurationId) {
//		CmnSalaryAllowanceConfigDetailResultSet cmnAllowanceSlabResultSet = new CmnSalaryAllowanceConfigDetailResultSet();
//		try {
//			List<CmnSalaryAllowanceConfigDetailsEntity> cmnEntities = allowanceLevelRepository.getAllowanceLevelDetailsById(configurationId);
//			if (cmnEntities.size() > 0) {
//				cmnAllowanceSlabResultSet.setCmnSalaryAllowanceConfigDetailsEntityList(cmnEntities);
//			} else {
//				cmnAllowanceSlabResultSet.setStatus(false);
//				cmnAllowanceSlabResultSet.setMessage("Failed");
//				cmnAllowanceSlabResultSet.setMessageDescription("Allowance level  List not available");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			cmnAllowanceSlabResultSet.setStatus(false);
//			cmnAllowanceSlabResultSet.setMessage("Exception");
//			cmnAllowanceSlabResultSet.setMessageDescription(e.getMessage());
//		}
//		return cmnAllowanceSlabResultSet;
//	}
//
//	@Override
//	public CmnSalaryAllowanceConfigDetailResultSet saveUpdateAllowanceLevelDetails(CmnSalaryAllowanceConfigDetailsCommitBean configBean) {
//		CmnSalaryAllowanceConfigDetailResultSet cmnAlLevelResultSet = new CmnSalaryAllowanceConfigDetailResultSet();
//		try {
//
//			if (configBean.getInsertAllowanceDetails() != null && configBean.getInsertAllowanceDetails().size() > 0) {
//				for (CmnSalaryAllowanceConfigDetailsDTO obj : configBean.getInsertAllowanceDetails()) {
//					// if (!verifyDuplicateName(obj.getDeptname())) {
//					CmnSalaryAllowanceConfigDetailsEntity cmnConfigurationMstEntity = new CmnSalaryAllowanceConfigDetailsEntity();
//					BeanUtils.copyProperties(cmnConfigurationMstEntity,obj);
//					cmnConfigurationMstEntity.setCmnSalaryAllowanceConfigDetlsId(cmnTableSeqService.getNextSequence(ApplicationConstants.CMN_SALARY_ALLOWANCE_CONFIG_DETAILS, ApplicationConstants.CMN_SALARY_ALLOWANCE_CONFIG_DETLS_ID));
//					//cmnConfigurationMstEntity.setStatusLookupId(2l);
//					BaseUtils.setBaseData(cmnConfigurationMstEntity, obj.getStatusLookupId());
//					save(cmnConfigurationMstEntity);
//				}
//
//			}
//
//			//			if ((configBean.getUpdateSalSlabDetails()).size() > 0) {
//			//				for (CmnSalarySlabLevelConfigDetailsDto obj : configBean.getUpdateSalSlabDetails()) {
//			//					if (obj.getCmnSalarySlabLevelConfigDetlsId() != null) {
//			//						cmnSalConfigEntities = cmnSalarySlabLevelConfigDetailsRepository.retriveSlabLevelById(obj.getCmnSalarySlabLevelConfigDetlsId());
//			//						BeanUtils.copyProperties( cmnSalConfigEntities,obj);
//			//						BaseUtils.modifyBaseData(cmnSalConfigEntities);
//			//						update(cmnSalConfigEntities);
//			//					}
//			//				}
//			//			}
//
//			//			if (configBean.getDeleteSalSlabDetails().size() > 0) {
//			//				for (CmnSalarySlabLevelConfigDetailsDto obj : configBean.getDeleteSalSlabDetails()) {
//			//					if (obj.getCmnSalarySlabLevelConfigDetlsId() != null) {
//			//						CmnSalarySlabLevelConfigDetailsEntity cmnConfigurationMstEntity = new CmnSalarySlabLevelConfigDetailsEntity();
//			//						BeanUtils.copyProperties(cmnConfigurationMstEntity,obj);
//			//						BaseUtils.modifyBaseData(cmnConfigurationMstEntity);
//			//						delete(cmnConfigurationMstEntity);
//			//					}
//			//				}
//			//			}
//		}catch(Exception e)
//		{
//			cmnAlLevelResultSet.setStatus(false);
//			cmnAlLevelResultSet.setMessage("Exception");
//			cmnAlLevelResultSet.setMessageDescription(e.getMessage());
//		}
//		return cmnAlLevelResultSet;
//	}
//
//
//}
