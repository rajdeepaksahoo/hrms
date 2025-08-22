package org.vrnda.hrms.service;

import org.vrnda.hrms.entity.CmnAddressesMstEntity;
import org.vrnda.hrms.repository.dto.CmnAddressesMstDTO;
import org.vrnda.hrms.service.resultset.CmnAddressesMstResultset;
import org.vrnda.hrms.service.resultset.CompanyLocationResultSet;

public interface CmnAddressesMstService extends GenericService<CmnAddressesMstEntity, Long> {

	public CmnAddressesMstResultset saveOrUpdateAddress( CmnAddressesMstDTO cmnAddressesMstDto, String loggedInUser);
	
	public CmnAddressesMstResultset getPresentAddress(Long addressId);
	
	public CmnAddressesMstResultset getAddressDetailsWithAddressId(Long addressId1,Long addressId2);
	
	public CmnAddressesMstResultset deleteAddressByAddressId(Long locationId);

}
