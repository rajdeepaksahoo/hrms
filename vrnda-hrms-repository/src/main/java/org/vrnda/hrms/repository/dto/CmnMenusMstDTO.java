package org.vrnda.hrms.repository.dto;

import lombok.Data;

@Data
public class CmnMenusMstDTO {

	Long menuId;

	Long parentMenuId;

	String menuCode;

	String menuName;

	String menuPath;

	Long menuType;

	Long menuLevel;

	int menuSeq;

	String iconType;

	String iconName;

	String description;

	String systemConfigFlag;

	Long statusLookupId;

	String menuTypeName;

	String parentMenuName;

}
