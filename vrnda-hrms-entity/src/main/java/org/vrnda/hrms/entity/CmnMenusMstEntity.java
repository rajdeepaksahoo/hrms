package org.vrnda.hrms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "CMN_MENUS_MST")
public class CmnMenusMstEntity extends EntityBaseModel {

	@Id
	@Column(name = "MENU_ID")
	private Long menuId;

	@Column(name = "PARENT_MENU_ID")
	private Long parentMenuId;

	@Column(name = "MENU_CODE")
	private String menuCode;

	@Column(name = "MENU_NAME")
	private String menuName;

	@Column(name = "MENU_PATH")
	private String menuPath;

	@Column(name = "MENU_TYPE")
	private Long menuType;

	@Column(name = "MENU_LEVEL")
	private Long menuLevel;

	@Column(name = "MENU_SEQ")
	private int menuSeq;

	@Column(name = "ICON_TYPE")
	private String iconType;

	@Column(name = "ICON_NAME")
	private String iconName;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "SYSTEM_CONFIG_FLAG")
	private String systemConfigFlag;

}
