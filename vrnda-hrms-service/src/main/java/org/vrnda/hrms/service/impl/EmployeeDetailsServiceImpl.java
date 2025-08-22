package org.vrnda.hrms.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.vrnda.hrms.entity.CmnCompanyRolesMstEntity;
import org.vrnda.hrms.entity.CmnCountrysMstEntity;
import org.vrnda.hrms.entity.CmnLeaveTypesMstEntity;
import org.vrnda.hrms.entity.CmnLookupMstEntity;
import org.vrnda.hrms.entity.EmployeeDetailsEntity;
import org.vrnda.hrms.repository.CmnCompanyRolesMstRepository;
import org.vrnda.hrms.repository.CmnCountrysMstRepository;
import org.vrnda.hrms.repository.CmnLookupMstRepository;
import org.vrnda.hrms.repository.EmployeeDetailsRepository;
import org.vrnda.hrms.repository.dto.CmnAddressesMstDTO;
import org.vrnda.hrms.repository.dto.CmnContactsMstDTO;
import org.vrnda.hrms.repository.dto.CmnLeaveTypesMstDTO;
import org.vrnda.hrms.repository.dto.CmnLookupMstDTO;
import org.vrnda.hrms.repository.dto.EmployeeContactPersonsDTO;
import org.vrnda.hrms.repository.dto.EmployeeDetailsDTO;
import org.vrnda.hrms.repository.dto.EmployeeFinancialAccountDetailsDTO;
import org.vrnda.hrms.service.CmnAddressesMstService;
import org.vrnda.hrms.service.CmnContactsMstService;
import org.vrnda.hrms.service.CmnLookupMstService;
import org.vrnda.hrms.service.CmnTableSeqService;
import org.vrnda.hrms.service.EmployeeContactPersonsService;
import org.vrnda.hrms.service.EmployeeDetailsService;
import org.vrnda.hrms.service.EmployeeFinancialAccountDetailsService;
import org.vrnda.hrms.service.resultset.CmnAddressesMstResultset;
import org.vrnda.hrms.service.resultset.CmnContactsMstResultSet;
import org.vrnda.hrms.service.resultset.CmnLookupMstResultSet;
import org.vrnda.hrms.service.resultset.EmployeeContactPersonsResultSet;
import org.vrnda.hrms.service.resultset.EmployeeDtlsResultSet;
import org.vrnda.hrms.service.resultset.FinancialAccountDetailsResultSet;
import org.vrnda.hrms.utils.ApplicationConstants;
import org.vrnda.hrms.utils.BaseUtils;

@Service
public class EmployeeDetailsServiceImpl extends GenericServiceImpl<EmployeeDetailsEntity, Long>
		implements EmployeeDetailsService {

	private Logger logger = LogManager.getLogger(EmployeeDetailsServiceImpl.class.getName());

	@Autowired
	EmployeeDetailsRepository employeeDetailsRepository;

	@Autowired
	private CmnCompanyRolesMstRepository cmnCompanyRoleMstRepository;

	@Autowired
	private CmnLookupMstRepository cmnLookupMstRepository;

	@Autowired
	private CmnLookupMstService cmnLookupMstService;

	@Autowired
	private CmnAddressesMstService cmnAddressesMstService;

	@Autowired
	private CmnContactsMstService cmnContactsMstService;

	@Autowired
	private EmployeeContactPersonsService employeeContactPersonsService;

	@Autowired
	private CmnTableSeqService cmnTableSeqService;

	@Autowired
	private EmployeeFinancialAccountDetailsService employeeFinancialAccountDetailsService;

	@Autowired
	private CmnCountrysMstRepository cmnCountrysMstRepository;

	public EmployeeDetailsServiceImpl(PagingAndSortingRepository<EmployeeDetailsEntity, Long> typeRepository) {
		super(typeRepository);
	}

	@Override
	public EmployeeDtlsResultSet getAllEmployeeDtls() {

		EmployeeDtlsResultSet employeeDtlsResultSet = new EmployeeDtlsResultSet();
		List<EmployeeDetailsEntity> employeesList = new ArrayList<EmployeeDetailsEntity>();

		try {
			employeesList = employeeDetailsRepository.getAllEmployeeDetails();
			if (employeesList.size() >= 0) {
				employeeDtlsResultSet.setEmployeeDtlsMst(employeesList);
			} else {
				employeeDtlsResultSet.setStatus(false);
				employeeDtlsResultSet.setMessage(ApplicationConstants.FAILED);
				employeeDtlsResultSet.setMessageDescription("Employees does not exist");
			}
		} catch (Exception e) {
			employeeDtlsResultSet.setStatus(false);
			employeeDtlsResultSet.setMessage(ApplicationConstants.EXCEPTION);
			employeeDtlsResultSet.setMessageDescription(e.getMessage());
		}
		return employeeDtlsResultSet;
	}

	public EmployeeDtlsResultSet getEmployeeDtlsById(Long employeeId) {
		EmployeeDetailsEntity employeeDtlsEntity = null;
		EmployeeDtlsResultSet employeeDtlsResultSet = new EmployeeDtlsResultSet();
		try {
			List<Object[]> resultObj = employeeDetailsRepository.getEmployeeDetailsWithEmployeeId(employeeId);
			for (Object[] obj : resultObj) {
				employeeDtlsEntity = (EmployeeDetailsEntity) obj[0];
			}
			if (employeeDtlsEntity != null) {
				employeeDtlsResultSet.setEmployeeDetailsEntity(employeeDtlsEntity);
			} else {
				employeeDtlsResultSet.setStatus(false);
				employeeDtlsResultSet.setMessage(ApplicationConstants.FAILED);
				employeeDtlsResultSet.setMessageDescription("Employee Id does not exist");
			}
		} catch (Exception e) {
			employeeDtlsResultSet.setStatus(false);
			employeeDtlsResultSet.setMessage(ApplicationConstants.EXCEPTION);
			employeeDtlsResultSet.setMessageDescription(e.getMessage());
		}
		return employeeDtlsResultSet;
	}

	@Override
	public EmployeeDtlsResultSet getAllLeadsAndManagers() {
		EmployeeDtlsResultSet employeeDtlsResultSet = new EmployeeDtlsResultSet();
		List<EmployeeDetailsDTO> employeeDto = new ArrayList<EmployeeDetailsDTO>();
		try {
			CmnLookupMstEntity cmnLookUpObj = cmnLookupMstRepository.getLookupByLookupName(ApplicationConstants.ACTIVE);
			List<CmnCompanyRolesMstEntity> cmnCompanyRoleMstList = cmnCompanyRoleMstRepository
					.getCompanyRolesByStatusLookupIdAndManager(cmnLookUpObj.getLookupId());
			List<Long> companyRoleIds = new ArrayList<Long>();
			cmnCompanyRoleMstList.forEach(e -> {
				if (e.getIsLeadOrManager() != null && ApplicationConstants.Y.equalsIgnoreCase(e.getIsLeadOrManager())) {
					companyRoleIds.add(e.getCompanyRoleId());
				}
			});
			List<EmployeeDetailsEntity> mngrEmpDto = employeeDetailsRepository.getAllLeadsAndManagers(companyRoleIds);
			mngrEmpDto.forEach(e -> {
				EmployeeDetailsDTO empDTO = new EmployeeDetailsDTO();
				BeanUtils.copyProperties(e, empDTO);
				String fullName = "";
				if (e.getEmployeeLastName() != null) {
					fullName = e.getEmployeeLastName();
				}
//				if (e.getEmployeeMiddleName() != null) {
//					fullName = fullName + " " + e.getEmployeeMiddleName();
//				}
				if (e.getEmployeeFirstName() != null) {
					fullName = fullName + " " + e.getEmployeeFirstName();
				}
				empDTO.setEmployeeFullName(fullName);
				employeeDto.add(empDTO);

			});
			employeeDtlsResultSet.setEmployeeDtlsMst(mngrEmpDto);
			employeeDtlsResultSet.setEmployeeDetailsDTOList(employeeDto);
		} catch (Exception e) {
			e.printStackTrace();
			employeeDtlsResultSet.setStatus(false);
			employeeDtlsResultSet.setMessage(ApplicationConstants.EXCEPTION);
			employeeDtlsResultSet.setMessageDescription(e.getMessage());
		}
		return employeeDtlsResultSet;
	}

	@Override
	public EmployeeDtlsResultSet getAllLeadsAndManagersByStatusLookUpId(Long statusLookUpId) {
		EmployeeDtlsResultSet employeeDtlsResultSet = new EmployeeDtlsResultSet();
		List<EmployeeDetailsDTO> employeeDto = new ArrayList<EmployeeDetailsDTO>();
		try {
			List<CmnCompanyRolesMstEntity> cmnCompanyRoleMstList = cmnCompanyRoleMstRepository
					.getCompanyRolesByStatusLookupIdAndManager(statusLookUpId);
			List<Long> companyRoleIds = new ArrayList<Long>();
			cmnCompanyRoleMstList.forEach(e -> {
				if (e.getIsLeadOrManager() != null && ApplicationConstants.Y.equalsIgnoreCase(e.getIsLeadOrManager())) {
					companyRoleIds.add(e.getCompanyRoleId());
				}
			});
			List<EmployeeDetailsEntity> mngrEmpDto = employeeDetailsRepository
					.getAllLeadsAndManagersByStatusLookUpId(statusLookUpId, companyRoleIds);
			mngrEmpDto.forEach(e -> {
				EmployeeDetailsDTO empDTO = new EmployeeDetailsDTO();
				BeanUtils.copyProperties(e, empDTO);
				String fullName = "";
				if (e.getEmployeeLastName() != null) {
					fullName = e.getEmployeeLastName();
				}
//				if (e.getEmployeeMiddleName() != null) {
//					fullName = fullName + " " + e.getEmployeeMiddleName();
//				}
				if (e.getEmployeeFirstName() != null) {
					fullName = fullName + " " + e.getEmployeeFirstName();
				}
				empDTO.setEmployeeFullName(fullName);
				employeeDto.add(empDTO);

			});
			employeeDtlsResultSet.setEmployeeDtlsMst(mngrEmpDto);
			employeeDtlsResultSet.setEmployeeDetailsDTOList(employeeDto);
		} catch (Exception e) {
			e.printStackTrace();
			employeeDtlsResultSet.setStatus(false);
			employeeDtlsResultSet.setMessage(ApplicationConstants.EXCEPTION);
			employeeDtlsResultSet.setMessageDescription(e.getMessage());
		}
		return employeeDtlsResultSet;
	}

	@Override
	public EmployeeDtlsResultSet getAllHrs() {
		EmployeeDtlsResultSet employeeDtlsResultSet = new EmployeeDtlsResultSet();
		List<EmployeeDetailsDTO> employeeDto = new ArrayList<EmployeeDetailsDTO>();
		try {
			CmnLookupMstEntity cmnLookUpObj = cmnLookupMstRepository.getLookupByLookupName(ApplicationConstants.ACTIVE);
			List<CmnCompanyRolesMstEntity> cmnCompanyRoleMstList = cmnCompanyRoleMstRepository
					.getCompanyRolesByStatusLookupIdAndHr(cmnLookUpObj.getLookupId());
			List<Long> companyRoleIds = new ArrayList<Long>();
			cmnCompanyRoleMstList.forEach(e -> {
				if (e.getIsHr() != null && ApplicationConstants.Y.equalsIgnoreCase(e.getIsHr())) {
					companyRoleIds.add(e.getCompanyRoleId());
				}
			});
			List<EmployeeDetailsEntity> mngrEmpDto = employeeDetailsRepository.getAllLeadsAndManagers(companyRoleIds);
			mngrEmpDto.forEach(e -> {
				EmployeeDetailsDTO empDTO = new EmployeeDetailsDTO();
				BeanUtils.copyProperties(e, empDTO);
				String fullName = "";
				if (e.getEmployeeLastName() != null) {
					fullName = e.getEmployeeLastName();
				}
//				if (e.getEmployeeMiddleName() != null) {
//					fullName = fullName + " " + e.getEmployeeMiddleName();
//				}
				if (e.getEmployeeFirstName() != null) {
					fullName = fullName + " " + e.getEmployeeFirstName();
				}
				empDTO.setEmployeeFullName(fullName);
				employeeDto.add(empDTO);

			});
			employeeDtlsResultSet.setEmployeeDtlsMst(mngrEmpDto);
			employeeDtlsResultSet.setEmployeeDetailsDTOList(employeeDto);
		} catch (Exception e) {
			e.printStackTrace();
			employeeDtlsResultSet.setStatus(false);
			employeeDtlsResultSet.setMessage(ApplicationConstants.EXCEPTION);
			employeeDtlsResultSet.setMessageDescription(e.getMessage());
		}
		return employeeDtlsResultSet;
	}

	@Override
	public EmployeeDtlsResultSet getAllHrsByStatusLookUpId(Long statusLookUpId) {
		EmployeeDtlsResultSet employeeDtlsResultSet = new EmployeeDtlsResultSet();
		List<EmployeeDetailsDTO> employeeDto = new ArrayList<EmployeeDetailsDTO>();
		try {
			List<CmnCompanyRolesMstEntity> cmnCompanyRoleMstList = cmnCompanyRoleMstRepository
					.getCompanyRolesByStatusLookupIdAndHr(statusLookUpId);
			List<Long> companyRoleIds = new ArrayList<Long>();
			cmnCompanyRoleMstList.forEach(e -> {
				if (e.getIsHr() != null && ApplicationConstants.Y.equalsIgnoreCase(e.getIsHr())) {
					companyRoleIds.add(e.getCompanyRoleId());
				}
			});
			List<EmployeeDetailsEntity> mngrEmpDto = employeeDetailsRepository
					.getAllLeadsAndManagersByStatusLookUpId(statusLookUpId, companyRoleIds);
			mngrEmpDto.forEach(e -> {
				EmployeeDetailsDTO empDTO = new EmployeeDetailsDTO();
				BeanUtils.copyProperties(e, empDTO);
				String fullName = "";
				if (e.getEmployeeLastName() != null) {
					fullName = e.getEmployeeLastName();
				}
//				if (e.getEmployeeMiddleName() != null) {
//					fullName = fullName + " " + e.getEmployeeMiddleName();
//				}
				if (e.getEmployeeFirstName() != null) {
					fullName = fullName + " " + e.getEmployeeFirstName();
				}
				empDTO.setEmployeeFullName(fullName);
				employeeDto.add(empDTO);

			});
			employeeDtlsResultSet.setEmployeeDtlsMst(mngrEmpDto);
			employeeDtlsResultSet.setEmployeeDetailsDTOList(employeeDto);
		} catch (Exception e) {
			e.printStackTrace();
			employeeDtlsResultSet.setStatus(false);
			employeeDtlsResultSet.setMessage(ApplicationConstants.EXCEPTION);
			employeeDtlsResultSet.setMessageDescription(e.getMessage());
		}
		return employeeDtlsResultSet;
	}

	public EmployeeDtlsResultSet createOrUpdateEmployeeDtls(EmployeeDetailsDTO employeeDtlsDto, String loggedInUser) {

		EmployeeDtlsResultSet employeeDtlsResultSet = new EmployeeDtlsResultSet();
		EmployeeDetailsEntity employeeDetailsEntity = null;
		try {
			if (employeeDtlsDto != null) {
//				if (employeeDtlsDto.getEmployeeId() == null) {
				CmnLookupMstResultSet cmnLookupMstResultSet = cmnLookupMstService
						.getLookupIdAndNameListByParentLookupName(ApplicationConstants.ADDRESS_TYPE);
				Long presentAddressLookUpId = null;
				Long permanentAddressLookUpId = null;
				if (cmnLookupMstResultSet != null && cmnLookupMstResultSet.getCmnLookupMstDtoList() != null) {
					for (CmnLookupMstDTO e : cmnLookupMstResultSet.getCmnLookupMstDtoList()) {
						if (ApplicationConstants.PERMANENT_ADDRESS.equals(e.getLookupName())) {
							permanentAddressLookUpId = e.getLookupId();
						} else if (ApplicationConstants.PRESENT_ADDRESS.equals(e.getLookupName())) {
							presentAddressLookUpId = e.getLookupId();
						}
					}
				}
				employeeDtlsDto.getEmployeeAddress().setAddressTypeLookupId(presentAddressLookUpId);
				logger.info("Before Inserting Employee present Address");
				CmnAddressesMstResultset cmnAddressesMstResultset = cmnAddressesMstService
						.saveOrUpdateAddress(employeeDtlsDto.getEmployeeAddress(), loggedInUser);
				logger.info("After Inserting Employee present Address and response is "
						+ cmnAddressesMstResultset.toString());
				if (cmnAddressesMstResultset != null) {
					if (cmnAddressesMstResultset.getCmnAddressesMstEntity() != null
							&& cmnAddressesMstResultset.getCmnAddressesMstEntity().getAddressId() != null) {

						employeeDtlsDto.setPresentAddressId(
								cmnAddressesMstResultset.getCmnAddressesMstEntity().getAddressId());
						if (employeeDtlsDto.getIsPermanentAddress() != null
								&& employeeDtlsDto.getIsPermanentAddress().equals(ApplicationConstants.Y)) {
							employeeDtlsDto.setPermAddressId(employeeDtlsDto.getPresentAddressId());
						} else {
							logger.info("Before Inserting Employee present Address");
							employeeDtlsDto.getEmployeePermanentAddress()
									.setAddressTypeLookupId(permanentAddressLookUpId);
							CmnAddressesMstResultset cmnAddressesMstPermanentResultset = cmnAddressesMstService
									.saveOrUpdateAddress(employeeDtlsDto.getEmployeePermanentAddress(), loggedInUser);
							logger.info("After Inserting Employee present Address and response is "
									+ cmnAddressesMstPermanentResultset.toString());
							if (cmnAddressesMstPermanentResultset != null) {
								if (cmnAddressesMstPermanentResultset.getCmnAddressesMstEntity() != null
										&& cmnAddressesMstPermanentResultset.getCmnAddressesMstEntity()
												.getAddressId() != null) {
									employeeDtlsDto.setPermAddressId(cmnAddressesMstPermanentResultset
											.getCmnAddressesMstEntity().getAddressId());
								} else {
									employeeDtlsResultSet.setStatus(false);
									employeeDtlsResultSet.setMessage(ApplicationConstants.EXCEPTION);
									employeeDtlsResultSet
											.setMessageDescription(cmnAddressesMstPermanentResultset.getMessage());
									return employeeDtlsResultSet;
								}
							}
						}
					} else {
						employeeDtlsResultSet.setStatus(false);
						employeeDtlsResultSet.setMessage(ApplicationConstants.EXCEPTION);
						employeeDtlsResultSet.setMessageDescription(cmnAddressesMstResultset.getMessage());
						return employeeDtlsResultSet;
					}
				}

				// Employee contacts Insert
				if (employeeDtlsDto.getEmployeeContacts() != null) {
					CmnContactsMstResultSet cmnContactsMstResultSet = cmnContactsMstService
							.saveOrUpdateCmnContactsMst(employeeDtlsDto.getEmployeeContacts(), loggedInUser);
					if (cmnContactsMstResultSet != null && cmnContactsMstResultSet.getCmnContactsMstEntity() != null
							&& cmnContactsMstResultSet.getCmnContactsMstEntity().getContactId() != null) {
						employeeDtlsDto.setContactId(cmnContactsMstResultSet.getCmnContactsMstEntity().getContactId());
						if (employeeDtlsDto.getEmployeeEmergencyContact() != null) {
							CmnContactsMstDTO emergencyContact = new CmnContactsMstDTO();
							emergencyContact.setPhoneNo(
									employeeDtlsDto.getEmployeeEmergencyContact().getEmergencyPhoneNumber());
							emergencyContact.setStatusLookupId(
									employeeDtlsDto.getEmployeeEmergencyContact().getStatusLookupId());
							CmnContactsMstResultSet emergencyContactResultSet = cmnContactsMstService
									.saveOrUpdateCmnContactsMst(emergencyContact, loggedInUser);
							if (emergencyContactResultSet != null
									&& emergencyContactResultSet.getCmnContactsMstEntity() != null
									&& emergencyContactResultSet.getCmnContactsMstEntity().getContactId() != null) {
								employeeDtlsDto.getEmployeeEmergencyContact().setContactId(
										emergencyContactResultSet.getCmnContactsMstEntity().getContactId());
							}
							EmployeeContactPersonsResultSet employeeContactPersonsResultSet = employeeContactPersonsService
									.saveOrUpdateEmployeeContactPersons(employeeDtlsDto.getEmployeeEmergencyContact(),
											loggedInUser);
							if (employeeContactPersonsResultSet != null
									&& employeeContactPersonsResultSet.getEmployeeContactPersonsEntity() != null
									&& employeeContactPersonsResultSet.getEmployeeContactPersonsEntity()
											.getEmployeeCnctPrsnId() != null) {
								employeeDtlsDto.setEmployeeCnctPrsnId(employeeContactPersonsResultSet
										.getEmployeeContactPersonsEntity().getEmployeeCnctPrsnId());
							} else {
								employeeDtlsResultSet.setStatus(false);
								employeeDtlsResultSet.setMessage(ApplicationConstants.EXCEPTION);
								employeeDtlsResultSet
										.setMessageDescription(employeeContactPersonsResultSet.getMessage());
								return employeeDtlsResultSet;
							}
						}
					} else {
						employeeDtlsResultSet.setStatus(false);
						employeeDtlsResultSet.setMessage(ApplicationConstants.EXCEPTION);
						employeeDtlsResultSet.setMessageDescription(cmnContactsMstResultSet.getMessage());
						return employeeDtlsResultSet;
					}
				}

				// Employee Financial Account Insert

				if (employeeDtlsDto.getEmployeeFinancialDetails() != null) {
					FinancialAccountDetailsResultSet financialAccountDetailsResultSet = employeeFinancialAccountDetailsService
							.saveOrUpdateFinancialAccountDetails(employeeDtlsDto.getEmployeeFinancialDetails(),
									loggedInUser);
					if (financialAccountDetailsResultSet != null
							&& financialAccountDetailsResultSet.getEmployeeFinancialAccountDetailsEntity() != null
							&& financialAccountDetailsResultSet.getEmployeeFinancialAccountDetailsEntity()
									.getFinAcctDetId() != null) {
						employeeDtlsDto.setFinAcctDetId(financialAccountDetailsResultSet
								.getEmployeeFinancialAccountDetailsEntity().getFinAcctDetId());
					} else {
						employeeDtlsResultSet.setStatus(false);
						employeeDtlsResultSet.setMessage(ApplicationConstants.EXCEPTION);
						employeeDtlsResultSet.setMessageDescription(financialAccountDetailsResultSet.getMessage());
					}
				}

				if (employeeDtlsDto.getEmployeeId() == null) {
					employeeDtlsDto.setEmployeeId(cmnTableSeqService
							.getNextSequence(ApplicationConstants.EMPLOYEE_DETAILS, ApplicationConstants.EMPLOYEE_ID));
					employeeDetailsEntity = new EmployeeDetailsEntity();
					BeanUtils.copyProperties(employeeDtlsDto, employeeDetailsEntity);
					BaseUtils.setBaseData(employeeDetailsEntity, loggedInUser);
				} else {
					employeeDetailsEntity = new EmployeeDetailsEntity();
					BeanUtils.copyProperties(employeeDtlsDto, employeeDetailsEntity);
					BaseUtils.modifyBaseData(employeeDetailsEntity, loggedInUser);
				}
				employeeDetailsEntity.setStatusLookupId(employeeDtlsDto.getStatusLookupId());
				save(employeeDetailsEntity);
				employeeDtlsResultSet.setEmployeeDetailsEntity(employeeDetailsEntity);
//				}
			}
		} catch (Exception e) {
			employeeDtlsResultSet.setStatus(false);
			employeeDtlsResultSet.setMessage(ApplicationConstants.EXCEPTION);
			employeeDtlsResultSet.setMessageDescription(e.getMessage());
		}

		return employeeDtlsResultSet;
	}

	@Override
	public EmployeeDtlsResultSet getEmployeeCompleteDetailsByEmployeeId(Long employeeId) {
		EmployeeDtlsResultSet employeeDtlsResultSet = new EmployeeDtlsResultSet();
		EmployeeDetailsEntity employeeDetailsEntity = null;
		String desigName = null;
		String locationName = null;
		String companyRoleDescription = null;
		String departmentDescription = null;
		String managerFullName = null;
		String hrFullName = null;
		String employmentTypeName = null;
		try {
			List<Object[]> resultObj = employeeDetailsRepository.getEmployeeDetailsWithEmployeeId(employeeId);
			for (Object[] obj : resultObj) {
				employeeDetailsEntity = (EmployeeDetailsEntity) obj[0];
				desigName = (String) obj[1];
				locationName = (String) obj[2];
				companyRoleDescription = (String) obj[3];
				departmentDescription = (String) obj[4];
				managerFullName = (String) obj[5];
				hrFullName = (String) obj[6];
				employmentTypeName = (String) obj[7];
			}
			if (employeeDetailsEntity != null) {
				List<CmnCountrysMstEntity> countryList = cmnCountrysMstRepository.getAllCmnCountryMstList();
				EmployeeDetailsDTO employeeDetailsDto = new EmployeeDetailsDTO();
				BeanUtils.copyProperties(employeeDetailsEntity, employeeDetailsDto);
				employeeDetailsDto.setDesignationDescription(desigName);
				employeeDetailsDto.setLocationName(locationName);
				employeeDetailsDto.setCompanyRoleDescription(companyRoleDescription);
				employeeDetailsDto.setDepartmentDescription(departmentDescription);
				employeeDetailsDto.setManagerFullName(managerFullName);
				employeeDetailsDto.setHrFullName(hrFullName);
				employeeDetailsDto.setEmploymentTypeName(employmentTypeName);
				if (employeeDetailsDto.getPresentAddressId() != null) {
					CmnAddressesMstResultset cmnAddressesMstResultset = cmnAddressesMstService
							.getPresentAddress(employeeDetailsDto.getPresentAddressId());
					if (cmnAddressesMstResultset.getCmnAddressesMstEntity() != null) {
						CmnAddressesMstDTO cmnAddressesMstDto = new CmnAddressesMstDTO();
						BeanUtils.copyProperties(cmnAddressesMstResultset.getCmnAddressesMstEntity(),
								cmnAddressesMstDto);
						if (countryList != null) {
							List<CmnCountrysMstEntity> countryFilteredList = countryList.stream()
									.filter(e -> e.getCountryId().equals(cmnAddressesMstDto.getCountryId()))
									.collect(Collectors.toList());
							if (countryFilteredList != null && countryFilteredList.size() > 0) {
								cmnAddressesMstDto.setCountryName(countryFilteredList.get(0).getCountryName());
							}
						}
						employeeDetailsDto.setEmployeeAddress(cmnAddressesMstDto);
					}
				}
				if (employeeDetailsDto.getPermAddressId() != null) {
					if (employeeDetailsDto.getPermAddressId() == employeeDetailsDto.getPresentAddressId()) {
						employeeDetailsDto.setEmployeePermanentAddress(employeeDetailsDto.getEmployeeAddress());
					} else {
						CmnAddressesMstResultset cmnAddressesMstResultset = cmnAddressesMstService
								.getPresentAddress(employeeDetailsDto.getPermAddressId());
						if (cmnAddressesMstResultset.getCmnAddressesMstEntity() != null) {
							CmnAddressesMstDTO cmnAddressesMstDto = new CmnAddressesMstDTO();
							BeanUtils.copyProperties(cmnAddressesMstResultset.getCmnAddressesMstEntity(),
									cmnAddressesMstDto);
							if (countryList != null) {
								List<CmnCountrysMstEntity> countryFilteredList = countryList.stream()
										.filter(e -> e.getCountryId().equals(cmnAddressesMstDto.getCountryId()))
										.collect(Collectors.toList());
								if (countryFilteredList != null && countryFilteredList.size() > 0) {
									cmnAddressesMstDto.setCountryName(countryFilteredList.get(0).getCountryName());
								}
							}
							employeeDetailsDto.setEmployeePermanentAddress(cmnAddressesMstDto);
						}
					}
				}

				if (employeeDetailsDto.getEmployeeCnctPrsnId() != null) {
					EmployeeContactPersonsResultSet employeeContactPersonsResultSet = employeeContactPersonsService
							.getEmployeeContactPersonsByPersonId(employeeDetailsDto.getEmployeeCnctPrsnId());
					if (employeeContactPersonsResultSet != null
							&& employeeContactPersonsResultSet.getEmployeeContactPersonsEntity() != null) {
						EmployeeContactPersonsDTO employeeContactPersonsDto = new EmployeeContactPersonsDTO();
						BeanUtils.copyProperties(employeeContactPersonsResultSet.getEmployeeContactPersonsEntity(),
								employeeContactPersonsDto);
						CmnContactsMstResultSet cmnContactsMstResultSet = cmnContactsMstService
								.getCmnContactsByContactId(employeeContactPersonsDto.getContactId());
						if (cmnContactsMstResultSet != null
								&& cmnContactsMstResultSet.getCmnContactsMstEntity() != null) {
							employeeContactPersonsDto.setEmergencyPhoneNumber(
									cmnContactsMstResultSet.getCmnContactsMstEntity().getPhoneNo());
						}
						employeeDetailsDto.setEmployeeEmergencyContact(employeeContactPersonsDto);
					}
				}
				if (employeeDetailsDto.getContactId() != null) {
					CmnContactsMstResultSet cmnContactsMstResultSet = cmnContactsMstService
							.getCmnContactsByContactId(employeeDetailsDto.getContactId());
					if (cmnContactsMstResultSet != null && cmnContactsMstResultSet.getCmnContactsMstEntity() != null) {
						CmnContactsMstDTO cmnContactsMstDto = new CmnContactsMstDTO();
						BeanUtils.copyProperties(cmnContactsMstResultSet.getCmnContactsMstEntity(), cmnContactsMstDto);
						employeeDetailsDto.setEmployeeContacts(cmnContactsMstDto);
					}
				}

				if (employeeDetailsDto.getFinAcctDetId() != null) {
					FinancialAccountDetailsResultSet financialAccountDetailsResultSet = employeeFinancialAccountDetailsService
							.getFinancialAccountDetailsByFinAcctDetId(employeeDetailsDto.getFinAcctDetId());
					if (financialAccountDetailsResultSet != null
							&& financialAccountDetailsResultSet.getEmployeeFinancialAccountDetailsEntity() != null) {
						EmployeeFinancialAccountDetailsDTO employeeFinancialAccountDetailsDTO = new EmployeeFinancialAccountDetailsDTO();
						BeanUtils.copyProperties(
								financialAccountDetailsResultSet.getEmployeeFinancialAccountDetailsEntity(),
								employeeFinancialAccountDetailsDTO);
						employeeDetailsDto.setEmployeeFinancialDetails(employeeFinancialAccountDetailsDTO);
					}
				}
				employeeDtlsResultSet.setEmployeeDetailsDTO(employeeDetailsDto);
			} else {
				employeeDtlsResultSet.setStatus(false);
				employeeDtlsResultSet.setMessage(ApplicationConstants.FAILED);
				employeeDtlsResultSet.setMessageDescription("No Data Found with given Employee ID : " + employeeId);
			}
		} catch (Exception e) {
			employeeDtlsResultSet.setStatus(false);
			employeeDtlsResultSet.setMessage(ApplicationConstants.EXCEPTION);
			employeeDtlsResultSet.setMessageDescription(e.getMessage());
		}
		return employeeDtlsResultSet;
	}

	@Override
	public EmployeeDtlsResultSet getEmployeeDetailsBySearchParams(EmployeeDetailsDTO employeeDetailsDTO) {
		EmployeeDtlsResultSet employeeDtlsResultSet = new EmployeeDtlsResultSet();
		try {
			if (employeeDetailsDTO != null) {
				if (employeeDetailsDTO.getFromDateOfJoining() != null) {
					employeeDetailsDTO.getFromDateOfJoining().setHours(0);
					employeeDetailsDTO.getFromDateOfJoining().setMinutes(0);
					employeeDetailsDTO.getFromDateOfJoining().setSeconds(0);
				}
				if (employeeDetailsDTO.getToDateOfJoining() != null) {
					employeeDetailsDTO.getToDateOfJoining().setHours(0);
					employeeDetailsDTO.getToDateOfJoining().setMinutes(0);
					employeeDetailsDTO.getToDateOfJoining().setSeconds(0);
				}

				Specification<EmployeeDetailsEntity> spec = Specification
						.where(EmployeeSpecifications.employeeIdEquals(employeeDetailsDTO.getEmployeeId()))
						.and(EmployeeSpecifications.employeeLastNameContains(employeeDetailsDTO.getEmployeeLastName()))
						.and(EmployeeSpecifications.betweenDatesOfDOJ(employeeDetailsDTO.getFromDateOfJoining(),
								employeeDetailsDTO.getToDateOfJoining()))
						.and(EmployeeSpecifications.employeeIdStatus(employeeDetailsDTO.getStatusLookupId()))
						.and(EmployeeSpecifications.managerIdEquals(employeeDetailsDTO.getManagerId()));
				List<EmployeeDetailsEntity> employeeDetailsEntity = employeeDetailsRepository.findAll(spec);
				if (employeeDetailsEntity != null) {
					EmployeeDtlsResultSet leadResultSet = getAllLeadsAndManagers();
					List<EmployeeDetailsDTO> employeeDetailsDTOList = new ArrayList<EmployeeDetailsDTO>();
					for (EmployeeDetailsEntity e : employeeDetailsEntity) {
						EmployeeDetailsDTO employeeDetails = new EmployeeDetailsDTO();
						BeanUtils.copyProperties(e, employeeDetails);
						if (leadResultSet != null && leadResultSet.getEmployeeDetailsDTOList() != null) {
							List<EmployeeDetailsDTO> filteredList = leadResultSet.getEmployeeDetailsDTOList().stream()
									.filter(e1 -> e1.getEmployeeId() == e.getManagerId()).collect(Collectors.toList());
							if (filteredList != null && filteredList.size() > 0) {
								employeeDetails.setManagerFullName(filteredList.get(0).getEmployeeFullName());
							}
						}
						employeeDetailsDTOList.add(employeeDetails);
					}
					employeeDtlsResultSet.setEmployeeDetailsDTOList(employeeDetailsDTOList);

				} else {
					employeeDtlsResultSet.setStatus(false);
					employeeDtlsResultSet.setMessage(ApplicationConstants.FAILED);
					employeeDtlsResultSet.setMessageDescription("No Data Found with given Employee Details");
				}
			}
		} catch (Exception e) {
			employeeDtlsResultSet.setStatus(false);
			employeeDtlsResultSet.setMessage(ApplicationConstants.EXCEPTION);
			employeeDtlsResultSet.setMessageDescription(e.getMessage());
		}

		return employeeDtlsResultSet;
	}

	@Override
	public EmployeeDtlsResultSet getAllActiveEmployees() {
		EmployeeDtlsResultSet employeeDtlsResultSet = new EmployeeDtlsResultSet();
		Map<String, Long> employeeStatus = cmnLookupMstService
				.getLookupNameAndLookupIdMapingsByParentLookupName(ApplicationConstants.STATUS);
		try {
			Long lookupStatusID = employeeStatus.get(ApplicationConstants.ACTIVE);
			List<EmployeeDetailsEntity> EmpDto = employeeDetailsRepository.getAllActiveEmployees(lookupStatusID);
			employeeDtlsResultSet.setEmployeeDtlsMst(EmpDto);

		} catch (Exception e) {
			e.printStackTrace();
			employeeDtlsResultSet.setStatus(false);
			employeeDtlsResultSet.setMessage(ApplicationConstants.EXCEPTION);
			employeeDtlsResultSet.setMessageDescription(e.getMessage());
		}
		return employeeDtlsResultSet;

	}

	@Override
	public EmployeeDtlsResultSet getAllActiveEmployeesAndUserIsNull(Long statusLookupId) {
		EmployeeDtlsResultSet employeeDtlsResultSet = new EmployeeDtlsResultSet();
		List<EmployeeDetailsDTO> employeeDto = new ArrayList<EmployeeDetailsDTO>();
		try {
			List<Object[]> resultObject = employeeDetailsRepository.getAllActiveEmployeesAndUserIsNull(statusLookupId);
			for (Object[] obj : resultObject) {
				EmployeeDetailsEntity EmployeeDetailsEntity = (EmployeeDetailsEntity) obj[0];
				EmployeeDetailsDTO employeeDetailsDTO = new EmployeeDetailsDTO();
				BeanUtils.copyProperties(EmployeeDetailsEntity, employeeDetailsDTO);
				String fullName = "";
				if (employeeDetailsDTO.getEmployeeLastName() != null) {
					fullName = employeeDetailsDTO.getEmployeeLastName();
				}
				if (employeeDetailsDTO.getEmployeeFirstName() != null) {
					fullName = fullName + " " + employeeDetailsDTO.getEmployeeFirstName();
				}
				employeeDetailsDTO.setEmployeeFullName(fullName);
				employeeDetailsDTO.setProfessionalEmail((String) obj[1]);
				employeeDto.add(employeeDetailsDTO);
			}
			employeeDtlsResultSet.setEmployeeDetailsDTOList(employeeDto);
		} catch (Exception e) {
			e.printStackTrace();
			employeeDtlsResultSet.setStatus(false);
			employeeDtlsResultSet.setMessage(ApplicationConstants.EXCEPTION);
			employeeDtlsResultSet.setMessageDescription(e.getMessage());
		}
		return employeeDtlsResultSet;
	}

	@Override
	public EmployeeDtlsResultSet getEmployeeDetailsByManagerIdOrHrId(Long employeeId) {
		EmployeeDtlsResultSet employeeDtlsResultSet = new EmployeeDtlsResultSet();
		try {
			List<EmployeeDetailsDTO> empdetailsDtoList = new ArrayList<EmployeeDetailsDTO>();
			List<EmployeeDetailsEntity> EmployeeDtlsEntityList = employeeDetailsRepository
					.getEmployeeDetailsByManagerIdOrHrId(employeeId);
			if (EmployeeDtlsEntityList != null && EmployeeDtlsEntityList.size() > 0) {
				EmployeeDtlsEntityList.forEach(obj -> {
					EmployeeDetailsDTO empdetailsdto = new EmployeeDetailsDTO();
					BeanUtils.copyProperties(obj, empdetailsdto);
					empdetailsDtoList.add(empdetailsdto);
				});

				employeeDtlsResultSet.setEmployeeDetailsDTOList(empdetailsDtoList);
			} else {
				employeeDtlsResultSet.setStatus(false);
				employeeDtlsResultSet.setMessage("Failed");
				employeeDtlsResultSet.setMessageDescription("Employee Details is empty");
			}
		} catch (Exception e) {
			employeeDtlsResultSet.setStatus(false);
			employeeDtlsResultSet.setMessage("Error");
			employeeDtlsResultSet.setMessageDescription(e.getMessage());
		}

		return employeeDtlsResultSet;
	}

	@Override
	public EmployeeDtlsResultSet getAllLeadsAndManagersByStatusLookUpIdAndDepartMentId(Long statusLookUpId,
			Long departMentId) {
		EmployeeDtlsResultSet employeeDtlsResultSet = new EmployeeDtlsResultSet();
		List<EmployeeDetailsDTO> employeeDto = new ArrayList<EmployeeDetailsDTO>();
		try {
			List<CmnCompanyRolesMstEntity> cmnCompanyRoleMstList = cmnCompanyRoleMstRepository
					.getCompanyRolesByStatusLookupIdAndManagerAndDepartId(statusLookUpId, departMentId);
			List<Long> companyRoleIds = new ArrayList<Long>();
			cmnCompanyRoleMstList.forEach(e -> {
				if (e.getIsLeadOrManager() != null && ApplicationConstants.Y.equalsIgnoreCase(e.getIsLeadOrManager())) {
					companyRoleIds.add(e.getCompanyRoleId());
				}
			});
			List<EmployeeDetailsEntity> mngrEmpDto = employeeDetailsRepository
					.getAllLeadsAndManagersByStatusLookUpId(statusLookUpId, companyRoleIds);
			mngrEmpDto.forEach(e -> {
				EmployeeDetailsDTO empDTO = new EmployeeDetailsDTO();
				BeanUtils.copyProperties(e, empDTO);
				String fullName = "";
				if (e.getEmployeeLastName() != null) {
					fullName = e.getEmployeeLastName();
				}
//				if (e.getEmployeeMiddleName() != null) {
//					fullName = fullName + " " + e.getEmployeeMiddleName();
//				}
				if (e.getEmployeeFirstName() != null) {
					fullName = fullName + " " + e.getEmployeeFirstName();
				}
				empDTO.setEmployeeFullName(fullName);
				employeeDto.add(empDTO);

			});
			employeeDtlsResultSet.setEmployeeDtlsMst(mngrEmpDto);
			employeeDtlsResultSet.setEmployeeDetailsDTOList(employeeDto);
		} catch (Exception e) {
			e.printStackTrace();
			employeeDtlsResultSet.setStatus(false);
			employeeDtlsResultSet.setMessage(ApplicationConstants.EXCEPTION);
			employeeDtlsResultSet.setMessageDescription(e.getMessage());
		}
		return employeeDtlsResultSet;
	}

	@Override
	public EmployeeDtlsResultSet getAllLeadsAndHRsByStatusLookUpIdAndDepartMentId(Long statusLookUpId,
			Long departMentId) {
		EmployeeDtlsResultSet employeeDtlsResultSet = new EmployeeDtlsResultSet();
		List<EmployeeDetailsDTO> employeeDto = new ArrayList<EmployeeDetailsDTO>();
		try {
			List<CmnCompanyRolesMstEntity> cmnCompanyRoleMstList = cmnCompanyRoleMstRepository
					.getAllLeadsAndHRsByStatusLookUpIdAndDepartMentId(statusLookUpId,departMentId);
			List<Long> companyRoleIds = new ArrayList<Long>();
			cmnCompanyRoleMstList.forEach(e -> {
				if (e.getIsHr() != null && ApplicationConstants.Y.equalsIgnoreCase(e.getIsHr())) {
					companyRoleIds.add(e.getCompanyRoleId());
				}
			});
			List<EmployeeDetailsEntity> mngrEmpDto = employeeDetailsRepository
					.getAllLeadsAndManagersByStatusLookUpId(statusLookUpId, companyRoleIds);
			mngrEmpDto.forEach(e -> {
				EmployeeDetailsDTO empDTO = new EmployeeDetailsDTO();
				BeanUtils.copyProperties(e, empDTO);
				String fullName = "";
				if (e.getEmployeeLastName() != null) {
					fullName = e.getEmployeeLastName();
				}
//				if (e.getEmployeeMiddleName() != null) {
//					fullName = fullName + " " + e.getEmployeeMiddleName();
//				}
				if (e.getEmployeeFirstName() != null) {
					fullName = fullName + " " + e.getEmployeeFirstName();
				}
				empDTO.setEmployeeFullName(fullName);
				employeeDto.add(empDTO);

			});
			employeeDtlsResultSet.setEmployeeDtlsMst(mngrEmpDto);
			employeeDtlsResultSet.setEmployeeDetailsDTOList(employeeDto);
		} catch (Exception e) {
			e.printStackTrace();
			employeeDtlsResultSet.setStatus(false);
			employeeDtlsResultSet.setMessage(ApplicationConstants.EXCEPTION);
			employeeDtlsResultSet.setMessageDescription(e.getMessage());
		}
		return employeeDtlsResultSet;
	}
}
