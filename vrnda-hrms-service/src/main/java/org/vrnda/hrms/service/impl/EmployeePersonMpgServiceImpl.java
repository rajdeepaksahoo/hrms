//package org.vrnda.hrms.service.impl;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.repository.PagingAndSortingRepository;
//import org.springframework.stereotype.Service;
//import org.vrnda.hrms.entity.EmployeeContactPersonsEntity;
//import org.vrnda.hrms.repository.EmployeeContactPersonsRepository;
//import org.vrnda.hrms.repository.dto.EmployeeContactPersonsDTO;
//import org.vrnda.hrms.service.CmnContactsMstService;
//import org.vrnda.hrms.service.CmnTableSeqService;
//import org.vrnda.hrms.service.EmployeeContactPersonsService;
//import org.vrnda.hrms.service.resultset.CmnContactsMstResultSet;
//import org.vrnda.hrms.service.resultset.EmployeeContactPersonsResultSet;
//import org.vrnda.hrms.utils.ApplicationConstants;
//import org.vrnda.hrms.utils.BaseUtils;
//
//@Service
//public class EmployeeContactPersonsServiceImpl  extends GenericServiceImpl<EmployeeContactPersonsEntity, Long> implements EmployeeContactPersonsService  {
//	@Autowired
//	EmployeeContactPersonsRepository emplPersonMpgRepository;
//
//	@Autowired
//	CmnContactsMstService cmnContactsMstService;
//
//	@Autowired
//	CmnTableSeqService cmnTableSeqService;
//
//	public EmployeeContactPersonsServiceImpl(PagingAndSortingRepository<EmployeeContactPersonsEntity, Long> typeRepository) {
//		super(typeRepository);
//	}
//
//	@Override
//	public EmployeeContactPersonsResultSet cmnEmployeePersonMpgEntityByEmployeePrsnId(Long employeePrsnId) {
//		EmployeeContactPersonsResultSet employeePersonMpgResultset = new EmployeeContactPersonsResultSet();
//		EmployeeContactPersonsEntity employeeContactPersonsEntity = null;
//		try {
//
//			employeeContactPersonsEntity = emplPersonMpgRepository.getEmployeePersonMpgWithEmployeePrsnId(employeePrsnId);
//			if(employeeContactPersonsEntity!=null) {
//				List<EmployeeContactPersonsEntity>  List = new ArrayList<EmployeeContactPersonsEntity>();
//				List.add(employeeContactPersonsEntity);
//				employeePersonMpgResultset.setEmployeePersonMpgEntityList( List);
//			}
//			else {
//				employeePersonMpgResultset.setStatus(false);
//				employeePersonMpgResultset.setMessage("Failed");
//				employeePersonMpgResultset.setMessageDescription("EmployeePrsnId not exist ");
//			}
//		} catch (Exception e) {
//			employeePersonMpgResultset.setStatus(false);
//			employeePersonMpgResultset.setMessage("Exception");
//			employeePersonMpgResultset.setMessageDescription(e.getMessage());
//		}
//
//		return employeePersonMpgResultset;
//	}
//
//
//	@Override
//	public EmployeeContactPersonsResultSet createOrUpdateEmployeePersonMpg(EmployeeContactPersonsDTO employeeContactPersonsDto) {
//		EmployeeContactPersonsResultSet employeePersonMpgResultset = new EmployeeContactPersonsResultSet();
//		EmployeeContactPersonsEntity employeeContactPersonsEntity = null;
//		CmnContactsMstResultSet cmnContactMstResultSet= null;
//		try {
//
//			//Insert
//			if(employeeContactPersonsDto.getEmployeePrsnId()==null) {
//				employeeContactPersonsEntity = new EmployeeContactPersonsEntity();
//				BeanUtils.copyProperties(employeeContactPersonsDto,employeeContactPersonsEntity );
//				employeeContactPersonsEntity.setEmployeePrsnId(cmnTableSeqService.getNextSequence(ApplicationConstants.EMPLOYEE_PERSON_MPG, ApplicationConstants.EMPLOYEE_PRSN_ID));
//				cmnContactMstResultSet=cmnContactsMstService.createOrUpdateCmnContactIdMstEntity(employeeContactPersonsDto.getCmnContactMstDto());
//				employeeContactPersonsEntity.setContactId(cmnContactMstResultSet.getContactId());
//				BaseUtils.setBaseData(employeeContactPersonsEntity,employeeContactPersonsDto.getStatusLookupId());
//				save(employeeContactPersonsEntity);
//				employeePersonMpgResultset.setEmployeePrsnId(employeeContactPersonsEntity.getEmployeePrsnId());
//			}
//			if(employeeContactPersonsDto.getEmployeePrsnId()!=null){
//				//Update
//				employeeContactPersonsEntity = emplPersonMpgRepository.getEmployeePersonMpgWithEmployeePrsnId(employeeContactPersonsDto.getEmployeePrsnId());
//				if(employeeContactPersonsEntity != null) {
//					BeanUtils.copyProperties(employeeContactPersonsDto,employeeContactPersonsEntity );
//					cmnContactMstResultSet=cmnContactsMstService.createOrUpdateCmnContactIdMstEntity(employeeContactPersonsDto.getCmnContactMstDto());
//					BaseUtils.modifyBaseData(employeeContactPersonsEntity);
//					save(employeeContactPersonsEntity);
//				}
//				else {
//					employeePersonMpgResultset.setStatus(false);
//					employeePersonMpgResultset.setMessage("Unable to update.");
//					employeePersonMpgResultset.setMessageDescription("Given alredy exist.");
//				}	return employeePersonMpgResultset;
//			}
//		}
//		catch (Exception e) {
//			employeePersonMpgResultset.setStatus(false);
//			employeePersonMpgResultset.setMessage("Exception");
//			employeePersonMpgResultset.setMessageDescription(e.getMessage());
//		}
//		return employeePersonMpgResultset;
//	}
//
//}
