package org.vrnda.hrms.service;

import java.util.List;

import org.vrnda.hrms.entity.CmnBandsMstEntity;
import org.vrnda.hrms.repository.dto.CmnBandsMstDTO;
import org.vrnda.hrms.service.resultset.BandsResultset;

public interface CmnBandsMstService extends GenericService<CmnBandsMstEntity, Long> {

	public BandsResultset saveOrUpdateBand(CmnBandsMstDTO cmnBandsMstDTO, String loggedInUser);

	public BandsResultset deleteBandById(Long bandId);

	public BandsResultset deleteBandsList(List<CmnBandsMstDTO> cmnBandsMstDTOList);

	public BandsResultset getAllBands();

	public BandsResultset getBandById(Long bandId);

	public BandsResultset getBandsByStatusLookupId(Long statusLookupId);

	public boolean verifyDuplicateBandName(String bandName, Long bandId);

}
