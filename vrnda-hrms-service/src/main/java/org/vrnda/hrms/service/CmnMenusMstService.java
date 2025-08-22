package org.vrnda.hrms.service;

import org.vrnda.hrms.entity.CmnMenusMstEntity;
import org.vrnda.hrms.repository.dto.CmnMenusMstDTO;
import org.vrnda.hrms.service.resultset.CmnMenusMstResultSet;

public interface CmnMenusMstService extends GenericService<CmnMenusMstEntity, Long> {

	public CmnMenusMstResultSet saveOrUpdateMenu(CmnMenusMstDTO cmnMenusMstDto, String loggedInUser);

	public CmnMenusMstResultSet getAllMenus();

	public CmnMenusMstResultSet getMenusByMenuTypeId(Long  menuTypeId);

	public CmnMenusMstResultSet getMenusByParentMenuId(Long parentMenuId);
	
	public CmnMenusMstResultSet getMenuByMenuIdAndMenuTypeId(Long menuId, Long menuTypeId);
	
	public CmnMenusMstResultSet getMenuByMenuCode(String  menuCode);
	
	public boolean verifyDuplicateMenuCode(String menuCode, Long menuId);
	
	public boolean verifyDuplicateMenuName(String menuName, Long menuId);
	
	public boolean verifyDuplicateMenuPath(String menuPath, Long menuId);
	
}
