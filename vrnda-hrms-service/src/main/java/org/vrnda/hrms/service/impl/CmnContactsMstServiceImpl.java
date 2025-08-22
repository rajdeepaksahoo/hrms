package org.vrnda.hrms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.vrnda.hrms.entity.CmnContactsMstEntity;
import org.vrnda.hrms.repository.CmnContactsMstRepository;
import org.vrnda.hrms.repository.dto.CmnContactsMstDTO;
import org.vrnda.hrms.service.CmnContactsMstService;
import org.vrnda.hrms.service.CmnTableSeqService;
import org.vrnda.hrms.service.resultset.CmnContactsMstResultSet;
import org.vrnda.hrms.utils.ApplicationConstants;
import org.vrnda.hrms.utils.BaseUtils;
@Service()
public class CmnContactsMstServiceImpl  extends GenericServiceImpl<CmnContactsMstEntity, Long> implements CmnContactsMstService  {
	
	@Autowired
	CmnContactsMstRepository cmnContactsMstRepository;

	@Autowired
	CmnTableSeqService cmnTableSeqService;
	
	public CmnContactsMstServiceImpl(PagingAndSortingRepository<CmnContactsMstEntity, Long> typeRepository) {
		super(typeRepository);
	}

	@Override
	public CmnContactsMstResultSet getCmnContactsByContactId(Long contactId) {
		CmnContactsMstResultSet cmnContactMstResultSet = new CmnContactsMstResultSet();
		CmnContactsMstEntity cmnContactsMstEntity = null;
		try {
			cmnContactsMstEntity = cmnContactsMstRepository.getCmnContactMstByContactId(contactId);
			if(cmnContactsMstEntity!=null) {
				List<CmnContactsMstEntity> list = new ArrayList<CmnContactsMstEntity>();
				list.add(cmnContactsMstEntity);
				cmnContactMstResultSet.setCmnContactsMstEntityList(list);
				cmnContactMstResultSet.setCmnContactsMstEntity(cmnContactsMstEntity);
			}
			else {
				cmnContactMstResultSet.setStatus(false);
				cmnContactMstResultSet.setMessage("Failed");
				cmnContactMstResultSet.setMessageDescription(" ContactId not exist ");
			}
		} catch (Exception e) {
			cmnContactMstResultSet.setStatus(false);
			cmnContactMstResultSet.setMessage("Exception");
			cmnContactMstResultSet.setMessageDescription(e.getMessage());
		}

		return cmnContactMstResultSet;
	}

	@Override
	public CmnContactsMstResultSet saveOrUpdateCmnContactsMst(CmnContactsMstDTO cmnContactsMstDto,String loggedInUser) {
		CmnContactsMstResultSet cmnContactMstResultSet = new CmnContactsMstResultSet();
		CmnContactsMstEntity cmnContactsMstEntity = null;
		try {
			//Insert
			if(cmnContactsMstDto.getContactId()==null) {
				cmnContactsMstEntity = new CmnContactsMstEntity();
				BeanUtils.copyProperties(cmnContactsMstDto,cmnContactsMstEntity );
				cmnContactsMstEntity.setContactId(cmnTableSeqService.getNextSequence(ApplicationConstants.CMN_CONTACTS_MST, ApplicationConstants.CONTACT_ID));
				BaseUtils.setBaseData(cmnContactsMstEntity, loggedInUser);
				save(cmnContactsMstEntity);
				cmnContactMstResultSet.setCmnContactsMstEntity(cmnContactsMstEntity);
			}  

			//Update
			if( cmnContactsMstDto.getContactId() != null) {
				cmnContactsMstEntity = cmnContactsMstRepository.getCmnContactMstByContactId(cmnContactsMstDto.getContactId());
				if(cmnContactsMstEntity!=null) {
					BeanUtils.copyProperties(cmnContactsMstDto,cmnContactsMstEntity );
					BaseUtils.modifyBaseData(cmnContactsMstEntity,loggedInUser);
					save(cmnContactsMstEntity);
					cmnContactMstResultSet.setCmnContactsMstEntity(cmnContactsMstEntity);
				}
				else {
					cmnContactMstResultSet.setStatus(false);
					cmnContactMstResultSet.setMessage("Unable to update.");
					cmnContactMstResultSet.setMessageDescription("Given contactId not used in Employee details.");
					return cmnContactMstResultSet;}
			}
		}
		catch (Exception e) {
			cmnContactMstResultSet.setStatus(false);
			cmnContactMstResultSet.setMessage("Exception");
			cmnContactMstResultSet.setMessageDescription(e.getMessage());
		}
		return cmnContactMstResultSet;
	}

}
