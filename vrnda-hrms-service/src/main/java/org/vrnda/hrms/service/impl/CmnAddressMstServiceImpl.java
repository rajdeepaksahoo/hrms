package org.vrnda.hrms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.vrnda.hrms.entity.CmnAddressesMstEntity;
import org.vrnda.hrms.repository.CmnAddressesMstRepository;
import org.vrnda.hrms.repository.dto.CmnAddressesMstDTO;
import org.vrnda.hrms.service.CmnAddressesMstService;
import org.vrnda.hrms.service.CmnContactsMstService;
import org.vrnda.hrms.service.CmnTableSeqService;
import org.vrnda.hrms.service.resultset.CmnAddressesMstResultset;
import org.vrnda.hrms.utils.ApplicationConstants;
import org.vrnda.hrms.utils.BaseUtils;

@Service
public class CmnAddressMstServiceImpl extends GenericServiceImpl<CmnAddressesMstEntity, Long>
		implements CmnAddressesMstService {

	@Autowired
	CmnAddressesMstRepository cmnAddressMstRepository;

	@Autowired
	CmnContactsMstService cmnContactsMstService;

	@Autowired
	CmnTableSeqService cmnTableSeqService;

	public CmnAddressMstServiceImpl(PagingAndSortingRepository<CmnAddressesMstEntity, Long> typeRepository) {
		super(typeRepository);
	}

	@Override
	public CmnAddressesMstResultset saveOrUpdateAddress(CmnAddressesMstDTO cmnAddressesMstDto, String loggedInUser) {
		CmnAddressesMstEntity cmnAddressMstEntity = null;
		CmnAddressesMstResultset cmnAddressMstResultset = new CmnAddressesMstResultset();

		try {
			// update
			if (cmnAddressesMstDto.getAddressId() != null &&cmnAddressesMstDto.getAddressId()>0) {
				cmnAddressMstEntity = cmnAddressMstRepository
						.getAddressDetailsWithAddressId(cmnAddressesMstDto.getAddressId());
				if (cmnAddressMstEntity != null) {
					BeanUtils.copyProperties(cmnAddressesMstDto, cmnAddressMstEntity);
					BaseUtils.modifyBaseData(cmnAddressMstEntity, loggedInUser);
					save(cmnAddressMstEntity);
					cmnAddressMstResultset.setCmnAddressesMstEntity(cmnAddressMstEntity);
				} else {
					cmnAddressMstResultset.setStatus(false);
					cmnAddressMstResultset.setMessage("Failed");
					cmnAddressMstResultset.setMessageDescription(" not exist ");
				}
			}
			// Insert
			if (cmnAddressesMstDto.getAddressId()== null || cmnAddressesMstDto.getAddressId() == 0) {
				cmnAddressMstEntity = new CmnAddressesMstEntity();
				cmnAddressesMstDto.setAddressId(cmnTableSeqService
						.getNextSequence(ApplicationConstants.CMN_ADDRESSES_MST, ApplicationConstants.ADDRESS_ID));
				BeanUtils.copyProperties(cmnAddressesMstDto, cmnAddressMstEntity);
				BaseUtils.setBaseData(cmnAddressMstEntity, loggedInUser);
				save(cmnAddressMstEntity);
				cmnAddressMstResultset.setCmnAddressesMstEntity(cmnAddressMstEntity);
			}

		} catch (Exception e) {
			cmnAddressMstResultset.setStatus(false);
			cmnAddressMstResultset.setMessage("Exception");
			cmnAddressMstResultset.setMessageDescription(e.getMessage());
		}

		return cmnAddressMstResultset;
	}

	@Override
	public CmnAddressesMstResultset getPresentAddress(Long addressId) {
		CmnAddressesMstEntity cmnAddressMstEntity = null;
		CmnAddressesMstResultset cmnAddressMstResultset = new CmnAddressesMstResultset();
		try {
			cmnAddressMstEntity = cmnAddressMstRepository.getAddressDetailsWithAddressId(addressId);
			if (cmnAddressMstEntity != null) {
				List<CmnAddressesMstEntity> addressList = new ArrayList<CmnAddressesMstEntity>();
				addressList.add(cmnAddressMstEntity);
				cmnAddressMstResultset.setCmnAddressesMstEntityList(addressList);
				cmnAddressMstResultset.setCmnAddressesMstEntity(cmnAddressMstEntity);
			} else {
				cmnAddressMstResultset.setStatus(false);
				cmnAddressMstResultset.setMessage("Failed");
				cmnAddressMstResultset.setMessageDescription("Role Id not exist ");
			}
		} catch (Exception e) {
			cmnAddressMstResultset.setStatus(false);
			cmnAddressMstResultset.setMessage("Exception");
			cmnAddressMstResultset.setMessageDescription(e.getMessage());
		}

		return cmnAddressMstResultset;
	}

	@Override
	public CmnAddressesMstResultset getAddressDetailsWithAddressId(Long presentAddressId, Long permAddressId) {
		CmnAddressesMstResultset cmnAddressMstResultset = new CmnAddressesMstResultset();
		try {
			List<CmnAddressesMstEntity> cmnAddressMstEntity = new ArrayList<CmnAddressesMstEntity>();
			cmnAddressMstEntity = cmnAddressMstRepository.getAddressDetails(presentAddressId, permAddressId);
			if (!cmnAddressMstEntity.isEmpty()) {
				cmnAddressMstResultset.setCmnAddressesMstEntityList(cmnAddressMstEntity);
			}
		} catch (Exception e) {
			cmnAddressMstResultset.setStatus(false);
			cmnAddressMstResultset.setMessage("Exception");
			cmnAddressMstResultset.setMessageDescription(e.getMessage());
		}
		return cmnAddressMstResultset;
	}

	@Override
	public CmnAddressesMstResultset deleteAddressByAddressId(Long addressId) {
		CmnAddressesMstResultset cmnAddressMstResultset = new CmnAddressesMstResultset();
		try {
			if(addressId != null) {
				CmnAddressesMstEntity CmnAddressesMstEntity = cmnAddressMstRepository.getAddressDetailsWithAddressId(addressId);
				delete(CmnAddressesMstEntity);
				cmnAddressMstResultset.setMessageDescription("Address deleted successfully.");
			} else {
				cmnAddressMstResultset.setStatus(false);
				cmnAddressMstResultset.setMessage("Failed");
				cmnAddressMstResultset.setMessageDescription("Invalid Inputs.");
			}
		} catch (Exception e) {
			cmnAddressMstResultset.setStatus(false);
			cmnAddressMstResultset.setMessage("Exception");
			cmnAddressMstResultset.setMessageDescription(e.getMessage());
		}
		return cmnAddressMstResultset;
	}

//	@Override
//	public CmnAddressesMstResultset getAllCmnAddressMst() {
//		CmnAddressesMstResultset cmnAddressMstResultset = new CmnAddressesMstResultset();
//		try {
//			List<CmnAddressesMstEntity> cmnEntities = (List<CmnAddressesMstEntity>) cmnAddressMstRepository.findAll();
//			if (cmnEntities.size() > 0) {
//				cmnAddressMstResultset.setCmnAddressMstEntity(cmnEntities);
//
//			} else {
//				cmnAddressMstResultset.setStatus(false);
//				cmnAddressMstResultset.setMessage("Failed");
//				cmnAddressMstResultset.setMessageDescription("Address  List not available");
//
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			cmnAddressMstResultset.setStatus(false);
//			cmnAddressMstResultset.setMessage("Exception");
//			cmnAddressMstResultset.setMessageDescription(e.getMessage());
//		}
//		return cmnAddressMstResultset;
//	}

}
