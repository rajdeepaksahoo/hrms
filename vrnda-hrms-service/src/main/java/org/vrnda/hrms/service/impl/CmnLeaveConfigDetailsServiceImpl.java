package org.vrnda.hrms.service.impl;

import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.vrnda.hrms.entity.CmnLeaveConfigDetailsEntity;
import org.vrnda.hrms.repository.CmnLeaveConfigDetailsRepository;
import org.vrnda.hrms.repository.dto.CmnLeaveConfigDetailsDTO;
import org.vrnda.hrms.repository.dto.CmnLeaveTypesMstDTO;
import org.vrnda.hrms.repository.dto.LeaveConfigsDTO;
import org.vrnda.hrms.service.CmnLeaveConfigDetailsService;
import org.vrnda.hrms.service.CmnLeaveTypesMstService;
import org.vrnda.hrms.service.resultset.CmnLeaveConfigDetailsResultSet;
import org.vrnda.hrms.service.resultset.CmnLeaveTypesMstResultSet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.vrnda.hrms.utils.BaseUtils;

import javax.sql.rowset.serial.SerialBlob;

@Service
public class CmnLeaveConfigDetailsServiceImpl extends GenericServiceImpl<CmnLeaveConfigDetailsEntity, Long>
implements CmnLeaveConfigDetailsService {

	@Autowired
	CmnLeaveConfigDetailsRepository cmnLeaveConfigDetailsRepository;
	
	@Autowired 
	CmnLeaveTypesMstService cmnLeaveTypesMstService;


	public CmnLeaveConfigDetailsServiceImpl(
			PagingAndSortingRepository<CmnLeaveConfigDetailsEntity, Long> typeRepository) {
		super(typeRepository);
		// TODO Auto-generated constructor stub
	}

	@Override
	public CmnLeaveConfigDetailsResultSet getLeaveConfigDetailsByConfigurationId(Long configurationId) {
		CmnLeaveConfigDetailsResultSet cmnLeaveConfigDetailsResultSet = new CmnLeaveConfigDetailsResultSet();
		CmnLeaveTypesMstResultSet cmnLeaveTypesMstResultSet = null;
		try {
			if (configurationId != null) {
				CmnLeaveConfigDetailsEntity cmnLeaveConfigDetailsEntity = cmnLeaveConfigDetailsRepository.getLeaveConfigDetailsByConfigurationId(configurationId);
				cmnLeaveTypesMstResultSet = cmnLeaveTypesMstService.getAllLeaveTypes();
				if(cmnLeaveConfigDetailsEntity != null && cmnLeaveConfigDetailsEntity.getLeaveConfigs() != null && 
						cmnLeaveTypesMstResultSet != null && cmnLeaveTypesMstResultSet.getCmnLeaveTypesMstDtoList() != null) {
					String leaveConfigs = new String(cmnLeaveConfigDetailsEntity.getLeaveConfigs().getBytes(1, (int) cmnLeaveConfigDetailsEntity.getLeaveConfigs().length()));
					CmnLeaveConfigDetailsDTO cmnLeaveConfigDetailsDTO = new ObjectMapper().readValue(leaveConfigs, CmnLeaveConfigDetailsDTO.class);
					if(cmnLeaveConfigDetailsDTO != null) {
						cmnLeaveConfigDetailsDTO.setConfigurationId(configurationId);
						cmnLeaveConfigDetailsResultSet.setCmnLeaveConfigDetailsDTO(cmnLeaveConfigDetailsDTO);
					}
				}
				else {
					cmnLeaveConfigDetailsResultSet.setStatus(false);
					cmnLeaveConfigDetailsResultSet.setMessage("Error");
					cmnLeaveConfigDetailsResultSet.setMessageDescription("No common leaves configuration available in db");
				}

			} else {
				cmnLeaveConfigDetailsResultSet.setStatus(false);
				cmnLeaveConfigDetailsResultSet.setMessage("Failed");
				cmnLeaveConfigDetailsResultSet.setMessageDescription("Invalid Inputs");
			}
		} catch (Exception e) {
			cmnLeaveConfigDetailsResultSet.setStatus(false);
			cmnLeaveConfigDetailsResultSet.setMessage("Error");
			cmnLeaveConfigDetailsResultSet.setMessageDescription(e.getMessage());
		}
		return cmnLeaveConfigDetailsResultSet;
	}

	@Override
	public CmnLeaveConfigDetailsResultSet saveAndUpdateLeaveConfigDetails(List<CmnLeaveConfigDetailsDTO> leaveconfigdetailsList,String loginUser) {
		CmnLeaveConfigDetailsResultSet leaveconfigdetailsResultset = new CmnLeaveConfigDetailsResultSet();
		CmnLeaveConfigDetailsEntity leaveconfigEntity = null;
		try {
			if(leaveconfigdetailsList!=null && leaveconfigdetailsList.size()>0) {
				for(CmnLeaveConfigDetailsDTO leaveconfigdto:leaveconfigdetailsList) {
					leaveconfigEntity = cmnLeaveConfigDetailsRepository.getLeaveConfigDetailsByConfigurationId(leaveconfigdto.getConfigurationId());
					if(leaveconfigEntity != null) {
//						BeanUtils.copyProperties(leaveconfigdto.getLeaveConfigs(), leaveconfigEntity);
						// Step 1: Convert to JSON
						ObjectMapper objectMapper = new ObjectMapper();
						String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(leaveconfigdto);

						// Step 2: Convert to Blob
						byte[] jsonBytes = jsonString.getBytes(StandardCharsets.UTF_8);
						Blob jsonBlob = new SerialBlob(jsonBytes);
						leaveconfigEntity.setLeaveConfigs(jsonBlob);
						BaseUtils.modifyBaseData(leaveconfigEntity,loginUser);
						save(leaveconfigEntity);
						leaveconfigdetailsResultset.setMessageDescription("Record saved successfully.");
					} else {
						leaveconfigEntity = new CmnLeaveConfigDetailsEntity();
//						BeanUtils.copyProperties(leaveconfigdto.getLeaveConfigs(), leaveconfigEntity);
						ObjectMapper objectMapper = new ObjectMapper();
						String jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(leaveconfigdto);

						// Step 2: Convert to Blob
						byte[] jsonBytes = jsonString.getBytes(StandardCharsets.UTF_8);
						Blob jsonBlob = new SerialBlob(jsonBytes);
						leaveconfigEntity.setLeaveConfigs(jsonBlob);
						BaseUtils.setBaseData(leaveconfigEntity, loginUser);
						save(leaveconfigEntity);
						leaveconfigdetailsResultset.setMessageDescription("Record saved successfully.");
					}
				}

			}
		}catch (Exception e) {
			leaveconfigdetailsResultset.setStatus(false);
			leaveconfigdetailsResultset.setMessage("Exception");
			leaveconfigdetailsResultset.setMessageDescription(e.getMessage());
		}
		return leaveconfigdetailsResultset;
	}

	@Override
	public CmnLeaveConfigDetailsResultSet deleteLeaveConfigDetailsbyConfigurationId(Long configurationId) {
		CmnLeaveConfigDetailsResultSet cmnLeaveConfigDetailsResultSet = new CmnLeaveConfigDetailsResultSet();
		try {
			if(configurationId != null) {
				CmnLeaveConfigDetailsEntity cmnLeaveConfigDetailsEntity = cmnLeaveConfigDetailsRepository.getLeaveConfigDetailsByConfigurationId(configurationId);
				if(cmnLeaveConfigDetailsEntity != null ) {
					delete(cmnLeaveConfigDetailsEntity);
					cmnLeaveConfigDetailsResultSet.setMessageDescription("Leave Config deleted successfully.");
					cmnLeaveConfigDetailsResultSet.setMessage("Success");
				} else {
					cmnLeaveConfigDetailsResultSet.setStatus(false);
					cmnLeaveConfigDetailsResultSet.setMessage("Failed");
					cmnLeaveConfigDetailsResultSet.setMessageDescription("No records to delete.");					
				}
			} else {
				cmnLeaveConfigDetailsResultSet.setStatus(false);
				cmnLeaveConfigDetailsResultSet.setMessage("Failed");
				cmnLeaveConfigDetailsResultSet.setMessageDescription("Invalid Inputs.");
			}
		} catch (Exception e) {
			cmnLeaveConfigDetailsResultSet.setStatus(false);
			cmnLeaveConfigDetailsResultSet.setMessage("Exception");
			cmnLeaveConfigDetailsResultSet.setMessageDescription(e.getMessage());
		}
		return cmnLeaveConfigDetailsResultSet;
	}

}
