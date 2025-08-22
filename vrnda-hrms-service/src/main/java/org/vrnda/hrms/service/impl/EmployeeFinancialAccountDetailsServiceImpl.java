package org.vrnda.hrms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.vrnda.hrms.entity.EmployeeFinancialAccountDetailsEntity;
import org.vrnda.hrms.repository.EmployeeFinancialAccountDetailsRepository;
import org.vrnda.hrms.repository.dto.EmployeeFinancialAccountDetailsDTO;
import org.vrnda.hrms.service.CmnTableSeqService;
import org.vrnda.hrms.service.EmployeeFinancialAccountDetailsService;
import org.vrnda.hrms.service.resultset.FinancialAccountDetailsResultSet;
import org.vrnda.hrms.utils.ApplicationConstants;
import org.vrnda.hrms.utils.BaseUtils;

@Service
public class EmployeeFinancialAccountDetailsServiceImpl
		extends GenericServiceImpl<EmployeeFinancialAccountDetailsEntity, Long>
		implements EmployeeFinancialAccountDetailsService {

	@Autowired
	EmployeeFinancialAccountDetailsRepository employeeFinancialAccountDetailsRepository;

	@Autowired
	CmnTableSeqService cmnTableSeqService;

	public EmployeeFinancialAccountDetailsServiceImpl(
			PagingAndSortingRepository<EmployeeFinancialAccountDetailsEntity, Long> typeRepository) {
		super(typeRepository);
	}

	@Override
	public FinancialAccountDetailsResultSet getFinancialAccountDetailsByFinAcctDetId(Long finAcctDetId) {
		FinancialAccountDetailsResultSet financialAccountDetailsResultSet = new FinancialAccountDetailsResultSet();
		EmployeeFinancialAccountDetailsEntity financialAccountDetailsEntity = null;
		try {

			financialAccountDetailsEntity = employeeFinancialAccountDetailsRepository
					.getFinancialAccountDetailsById(finAcctDetId);
			if (financialAccountDetailsEntity != null) {
				List<EmployeeFinancialAccountDetailsEntity> List = new ArrayList<EmployeeFinancialAccountDetailsEntity>();
				List.add(financialAccountDetailsEntity);
				financialAccountDetailsResultSet.setEmployeeFinancialAccountDetailsEntityList(List);
				financialAccountDetailsResultSet.setEmployeeFinancialAccountDetailsEntity(financialAccountDetailsEntity);
			} else {
				financialAccountDetailsResultSet.setStatus(false);
				financialAccountDetailsResultSet.setMessage("Failed");
				financialAccountDetailsResultSet.setMessageDescription("Financial Account Details Id not exist ");
			}
		} catch (Exception e) {
			financialAccountDetailsResultSet.setStatus(false);
			financialAccountDetailsResultSet.setMessage("Exception");
			financialAccountDetailsResultSet.setMessageDescription(e.getMessage());
		}

		return financialAccountDetailsResultSet;

	}

	@Override
	public FinancialAccountDetailsResultSet saveOrUpdateFinancialAccountDetails(
			EmployeeFinancialAccountDetailsDTO financialAccountDetailsDto, String loggedInUser) {
		FinancialAccountDetailsResultSet financialAccountDetailsResultSet = new FinancialAccountDetailsResultSet();
		EmployeeFinancialAccountDetailsEntity financialAccountDetailsEntity = null;
		try {

			// Insert
			if (financialAccountDetailsDto.getFinAcctDetId() == null) {
				financialAccountDetailsEntity = new EmployeeFinancialAccountDetailsEntity();
				BeanUtils.copyProperties(financialAccountDetailsDto, financialAccountDetailsEntity);
				financialAccountDetailsEntity.setFinAcctDetId(cmnTableSeqService.getNextSequence(
						ApplicationConstants.EMPLOYEE_FINANCIAL_ACCOUNT_DETAILS, ApplicationConstants.FIN_ACCT_DET_ID));
				BaseUtils.setBaseData(financialAccountDetailsEntity, loggedInUser);
				save(financialAccountDetailsEntity);
				financialAccountDetailsResultSet.setFinAcctDetId(financialAccountDetailsEntity.getFinAcctDetId());
				financialAccountDetailsResultSet.setEmployeeFinancialAccountDetailsEntity(financialAccountDetailsEntity);
			}
			if (financialAccountDetailsDto.getFinAcctDetId() != null) {
				// Update
				financialAccountDetailsEntity = employeeFinancialAccountDetailsRepository
						.getFinancialAccountDetailsById(financialAccountDetailsDto.getFinAcctDetId());
				if (financialAccountDetailsEntity != null) {
					BeanUtils.copyProperties(financialAccountDetailsDto, financialAccountDetailsEntity);
					BaseUtils.modifyBaseData(financialAccountDetailsEntity, loggedInUser);
					save(financialAccountDetailsEntity);
					financialAccountDetailsResultSet.setEmployeeFinancialAccountDetailsEntity(financialAccountDetailsEntity);
				} else {
					financialAccountDetailsResultSet.setStatus(false);
					financialAccountDetailsResultSet.setMessage("Unable to update.");
					financialAccountDetailsResultSet.setMessageDescription("Given alredy exist.");
				}
				return financialAccountDetailsResultSet;
			}
		} catch (Exception e) {
			financialAccountDetailsResultSet.setStatus(false);
			financialAccountDetailsResultSet.setMessage("Exception");
			financialAccountDetailsResultSet.setMessageDescription(e.getMessage());
		}
		return financialAccountDetailsResultSet;
	}

}
