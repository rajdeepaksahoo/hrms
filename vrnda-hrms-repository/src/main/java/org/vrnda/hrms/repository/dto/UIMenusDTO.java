package org.vrnda.hrms.repository.dto;

import java.util.List;

import lombok.Data;

@Data
public class UIMenusDTO {

	Long menuId = 0l;

	String menuName;

	String menuCode;

	String menuPath;

	String iconType;

	String iconName;

	String menuClass = "";

	Long menuType = 0l;

	Boolean groupTitle = false;

	String badge = "";

	String badgeClass = "";

	List<String> role;

	List<UIMenusDTO> subMenusDto;

}
