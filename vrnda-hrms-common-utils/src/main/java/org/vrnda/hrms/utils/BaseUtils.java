package org.vrnda.hrms.utils;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.stereotype.Component;
import org.vrnda.hrms.entity.EntityBaseModel;
import org.vrnda.hrms.utils.exceptions.BusinessException;

@Component
public class BaseUtils {
	
	public static Timestamp getCurrentTime() {
		return (new Timestamp(new Date().getTime()));
	}

	public static void setBaseData(EntityBaseModel baseModel, String loggedInUser) {
		try {
			if(null==baseModel.getCreatedDate()) {
				baseModel.setCreatedDate(getCurrentTime());
			}
			if(null==baseModel.getCreatedBy()) {
				baseModel.setCreatedBy(loggedInUser);
			}
		} catch (Exception e) {
			throw new BusinessException("Exception in setBaseData " + e);
		}
	}

	public static void modifyBaseData(EntityBaseModel baseModel, String loggedInUser) {
		try {
			baseModel.setUpdatedDate(getCurrentTime());
			baseModel.setUpdatedBy(loggedInUser);
		} catch (Exception e) {
			throw new BusinessException("Exception in modifyBaseData " + e);
		}
	}

	public static void setActiveFlagToNo(EntityBaseModel baseModel, Long statusLookupId, String loggedInUser) {
		try {
			baseModel.setStatusLookupId(statusLookupId);
			baseModel.setUpdatedDate(getCurrentTime());
			baseModel.setUpdatedBy(loggedInUser);
		} catch (Exception e) {
			throw new BusinessException("Exception in setActiveFlagToNo " + e);
		}
	}

	public static void setActiveFlagToYes(EntityBaseModel baseModel, Long statusLookupId, String loggedInUser) {
		try {
			baseModel.setStatusLookupId(statusLookupId);
			baseModel.setUpdatedDate(getCurrentTime());
			baseModel.setUpdatedBy(loggedInUser); 
		} catch (Exception e) {
			throw new BusinessException("Exception in setActiveFlagToYes " + e);
		}
	}

}
