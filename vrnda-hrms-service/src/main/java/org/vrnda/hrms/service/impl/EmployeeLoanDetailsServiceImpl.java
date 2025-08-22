package org.vrnda.hrms.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.vrnda.hrms.entity.CmnDesignationsMstEntity;
import org.vrnda.hrms.entity.CmnGeneralLoanConfigDetailsEntity;
import org.vrnda.hrms.entity.EmployeeLoanDetailsEntity;
import org.vrnda.hrms.repository.EmployeeLoanDetailsRepository;
import org.vrnda.hrms.repository.dto.CmnLookupMstDTO;
import org.vrnda.hrms.repository.dto.EmployeeLoanDetailsDTO;
import org.vrnda.hrms.service.CmnGeneralLoanConfigDetailsService;
import org.vrnda.hrms.service.CmnLookupMstService;
import org.vrnda.hrms.service.CmnTableSeqService;
import org.vrnda.hrms.service.EmployeeLoanDetailsService;
import org.vrnda.hrms.service.resultset.CmnGeneralLoanConfigDetailsResultSet;
import org.vrnda.hrms.service.resultset.CmnLookupMstResultSet;
import org.vrnda.hrms.service.resultset.EmployeeLoanDetailsResultSet;
import org.vrnda.hrms.utils.ApplicationConstants;
import org.vrnda.hrms.utils.BaseUtils;

@Service
public class EmployeeLoanDetailsServiceImpl extends GenericServiceImpl<EmployeeLoanDetailsEntity, Long>
		implements EmployeeLoanDetailsService {

	@Autowired
	EmployeeLoanDetailsRepository employeerepository;

	@Autowired
	CmnGeneralLoanConfigDetailsService cmnGeneralLoanConfigDetailsService;

	@Autowired
	CmnTableSeqService cmnTableSeqService;

	@Autowired
	CmnLookupMstService cmnLookupMstService;

	public EmployeeLoanDetailsServiceImpl(PagingAndSortingRepository<EmployeeLoanDetailsEntity, Long> typeRepository) {
		super(typeRepository);

	}

	@Override
	public EmployeeLoanDetailsResultSet getEmployeeLoanDetailsById(Long employeeId) {
		EmployeeLoanDetailsResultSet employeeLoanDtlsResultsetDetails = new EmployeeLoanDetailsResultSet();

		List<EmployeeLoanDetailsEntity> employeeLoanEntity = new ArrayList<EmployeeLoanDetailsEntity>();
		List<EmployeeLoanDetailsDTO> employeeLoanDetailsDTOList = new ArrayList<EmployeeLoanDetailsDTO>();
		String[] array={ApplicationConstants.LOAN_MIN_SAL_MONTHS,ApplicationConstants.LOAN_MAX_SAL_MONTHS,ApplicationConstants.LOAN_FIXED_AMT_LIMIT}; 
		 List<CmnLookupMstDTO> cmnLookupMstDTO;
		 for(String lookUpName:array){  
				CmnLookupMstResultSet cmnLookupMstRS = new CmnLookupMstResultSet();
				cmnLookupMstRS=cmnLookupMstService.getLookupByLookupName(lookUpName);
			 }  
		try {

			employeeLoanEntity = employeerepository.getEmployeeLoanDetailsWithLoanId(employeeId);
			if (employeeLoanEntity.size() >= 0) {
				for (EmployeeLoanDetailsEntity obj : employeeLoanEntity) {
					EmployeeLoanDetailsDTO employeeLoanDetailsDTO = new EmployeeLoanDetailsDTO();
					BeanUtils.copyProperties(obj, employeeLoanDetailsDTO);
					if (employeeLoanDetailsDTO.getLoanDetails() != null) {
						employeeLoanDetailsDTO
								.setLoanDetailsString(new String(employeeLoanDetailsDTO.getLoanDetails()));
					}

					SimpleDateFormat dateOnly = new SimpleDateFormat("dd-MM-yyyy");
					employeeLoanDetailsDTO.setCreatedDate(dateOnly.format(obj.getCreatedDate()));
					if (employeeLoanDetailsDTO.getRepaymentDetails() != null) {
						employeeLoanDetailsDTO
								.setRepaymentDetailsString(new String(employeeLoanDetailsDTO.getRepaymentDetails()));
					}

					employeeLoanDetailsDTOList.add(employeeLoanDetailsDTO);

				}

				employeeLoanDtlsResultsetDetails.setEmployeeLoanDetailsDtoList(employeeLoanDetailsDTOList);

			} else {
				employeeLoanDtlsResultsetDetails.setStatus(false);
				employeeLoanDtlsResultsetDetails.setMessage("Failed");
				employeeLoanDtlsResultsetDetails.setMessageDescription("Employees does not exist");
			}
		} catch (Exception e) {
			employeeLoanDtlsResultsetDetails.setStatus(false);
			employeeLoanDtlsResultsetDetails.setMessage("Exception");
			employeeLoanDtlsResultsetDetails.setMessageDescription(e.getMessage());
		}
		return employeeLoanDtlsResultsetDetails;

	}

	@Override
	public EmployeeLoanDetailsResultSet saveEmployeeLoanDetails(EmployeeLoanDetailsDTO employeeLoanDto)
			throws Throwable {
		EmployeeLoanDetailsResultSet employeeLoanDtlsResultset = new EmployeeLoanDetailsResultSet();
		EmployeeLoanDetailsEntity employeeLoanDtls = new EmployeeLoanDetailsEntity();
		try { 
			if(employeeLoanDto.getLoanId() == null) {
				
				Long loanStatus = cmnLookupMstService.getLookupIdByLookupNameAndParentLookupName(
						ApplicationConstants.APPLIED, ApplicationConstants.LOAN_STATUS);
				
				BeanUtils.copyProperties(employeeLoanDto, employeeLoanDtls);
				String salDetail = employeeLoanDto.getLoanDetailsString();
				String emploan = new String(salDetail.getBytes());
				employeeLoanDtls.setLoanDetails(emploan.getBytes());
				employeeLoanDtls.setYearId(getFinancialYearId());
				employeeLoanDtls.setLoanStatus(loanStatus != null ? loanStatus : null);
				
				if (employeeLoanDto.getRepaymentDetailsString() != null) {
					
					String repayment = employeeLoanDto.getRepaymentDetailsString();
					String emprepayment = new String(repayment.getBytes());
					employeeLoanDtls.setRepaymentDetails(emprepayment.getBytes());
				} else {
					employeeLoanDtls.setRepaymentDetails(new byte[0]);
					
				}
				
				employeeLoanDtls.setLoanId(cmnTableSeqService.getNextSequence(ApplicationConstants.EMPLOYEE_LOAN_DETAILS,
						ApplicationConstants.LOAN_ID));
				// BaseUtils.setBaseData(employeeLoanDtls, 15L);
				
				BaseUtils.setBaseData(employeeLoanDtls, employeeLoanDto.getEmployeeId().toString());
				save(employeeLoanDtls);
				employeeLoanDtlsResultset.setMessageDescription("Loan saved successfully.");
			}

			 else if (employeeLoanDto != null && employeeLoanDto.getLoanId() > 0) {
					EmployeeLoanDetailsEntity  employeeLoanDetailsEntity= employeerepository.getEmployeeLoanDetailsWithLoneId(employeeLoanDto.getLoanId());

					if (employeeLoanDetailsEntity != null) {
						if (employeeLoanDto.getRepaymentDetailsString() != null) {
							
							String repayment = employeeLoanDto.getRepaymentDetailsString();
							String emprepayment = new String(repayment.getBytes());
							employeeLoanDto.setRepaymentDetails(emprepayment.getBytes());
						} else {
							employeeLoanDto.setRepaymentDetails(new byte[0]);
							
						}
						if(employeeLoanDto.getLoanDetailsString() != null) {
							String salDetail = employeeLoanDto.getLoanDetailsString();
							String emploan = new String(salDetail.getBytes());
							employeeLoanDto.setLoanDetails(emploan.getBytes());

							
						}
						BeanUtils.copyProperties(employeeLoanDto, employeeLoanDetailsEntity);
						BaseUtils.modifyBaseData(employeeLoanDetailsEntity, employeeLoanDto.getEmployeeId().toString());
						save(employeeLoanDetailsEntity);
						employeeLoanDtlsResultset.setMessageDescription("Loan  updated successfully.");
					}

		} 
		}catch (Exception e) {
			System.out.println(e);
		}
		return employeeLoanDtlsResultset;

	}

	@Override
	public EmployeeLoanDetailsResultSet getEmployeeLoanConfigDetails(Long employeeId, String year) {
		EmployeeLoanDetailsResultSet employeeLoanDtlsResultset = new EmployeeLoanDetailsResultSet();
		CmnGeneralLoanConfigDetailsResultSet cmnGeneralLoanConfigDetailsResultSet = cmnGeneralLoanConfigDetailsService
				.getEmployeeLoanConfigDetails(employeeId, year);
		if(cmnGeneralLoanConfigDetailsResultSet != null) {
			
			employeeLoanDtlsResultset.setCmnGeneralLoanConfigDetailsEntityList(
					cmnGeneralLoanConfigDetailsResultSet.getCmnGeneralLoanConfigDetailsEntityList());
		}

		return employeeLoanDtlsResultset;

	}

	@Override
	public Long getFinancialYearId() {
		Long yearId;
		yearId = employeerepository.getFinancialYearId();
		return yearId;

	}

	@Override
	public EmployeeLoanDetailsResultSet getAllLoanRequests() {
		EmployeeLoanDetailsResultSet empLoneDtlsResultset = new EmployeeLoanDetailsResultSet();
		List<EmployeeLoanDetailsDTO> activeLoneRequests = new ArrayList<EmployeeLoanDetailsDTO>();
		try {
			List<Object[]> resultObject = employeerepository.getAllActiveLoanRequests();
			for (Object[] loneRequest : resultObject) {
				EmployeeLoanDetailsEntity employeeLoanDetailsEntity = (EmployeeLoanDetailsEntity) loneRequest[0];
				EmployeeLoanDetailsDTO employeeLoneDetailsDTO = new EmployeeLoanDetailsDTO();
				BeanUtils.copyProperties(employeeLoanDetailsEntity, employeeLoneDetailsDTO);
				employeeLoneDetailsDTO.setRepaymentDetailsString(new String(employeeLoneDetailsDTO.getRepaymentDetails()));
				employeeLoneDetailsDTO.setLoanDetailsString(new String(employeeLoneDetailsDTO.getLoanDetails()));
				employeeLoneDetailsDTO.setEmployeeFullName((String) loneRequest[1]);
				activeLoneRequests.add(employeeLoneDetailsDTO);
			}
			empLoneDtlsResultset.setEmployeeLoanDetailsDtoList(activeLoneRequests);
		} catch (Exception e) {
			empLoneDtlsResultset.setStatus(false);
			empLoneDtlsResultset.setMessage(ApplicationConstants.EXCEPTION);
			empLoneDtlsResultset.setMessageDescription(e.getMessage());
		}
		return empLoneDtlsResultset;
	}

	@Override
	public EmployeeLoanDetailsResultSet approveEmployeeLoan(EmployeeLoanDetailsDTO employeeLoanDto) {
		EmployeeLoanDetailsResultSet employeeLoanDtlsResultset = new EmployeeLoanDetailsResultSet();
		EmployeeLoanDetailsEntity employeeLoanDtls = new EmployeeLoanDetailsEntity();
		try {
			if (employeeLoanDto != null) {
				if (employeeLoanDto.getLoanId() > 0) {
					Long loanStatus = cmnLookupMstService.getLookupIdByLookupNameAndParentLookupName(
						ApplicationConstants.APPROVED, ApplicationConstants.LOAN_STATUS);
					EmployeeLoanDetailsEntity  employeeLoanDetailsEntity= employeerepository.getEmployeeLoanDetailsWithLoneId(employeeLoanDto.getLoanId());
					if(employeeLoanDetailsEntity != null ) {
						
						employeeLoanDetailsEntity.setLoanDetails(employeeLoanDto.getLoanDetailsString().getBytes());
						
						String salDetail = employeeLoanDto.getLoanDetailsString();
						String emploan = new String(salDetail.getBytes());
						employeeLoanDtls.setLoanDetails(emploan.getBytes());
						employeeLoanDetailsEntity.setLoanStatus(loanStatus != null ? loanStatus : null);
						BeanUtils.copyProperties(employeeLoanDetailsEntity ,employeeLoanDto);
						BaseUtils.modifyBaseData(employeeLoanDetailsEntity, employeeLoanDto.getEmployeeId().toString());
						save(employeeLoanDetailsEntity);
						employeeLoanDtlsResultset.setMessageDescription("Record updated successfully.");
					} else {
						employeeLoanDtlsResultset.setStatus(false);
						employeeLoanDtlsResultset.setMessage("Failed");
						employeeLoanDtlsResultset.setMessageDescription("Invalid Inputs.");
					}
				} else {
					employeeLoanDtlsResultset.setStatus(false);
					employeeLoanDtlsResultset.setMessage("Failed");
					employeeLoanDtlsResultset.setMessageDescription("Invalid Inputs.");
				}
			}
		} catch (Exception e) {
			employeeLoanDtlsResultset.setStatus(false);
			employeeLoanDtlsResultset.setMessage("Error");
			employeeLoanDtlsResultset.setMessageDescription("Exception occured while save/update of Common Loan Config Details.");
		}
		return employeeLoanDtlsResultset;
		

}
}
