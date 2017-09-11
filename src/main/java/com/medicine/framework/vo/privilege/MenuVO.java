package com.medicine.framework.vo.privilege;

import java.util.List;

public class MenuVO {

    private String id;

    private String name;

    /**
     * 上级ID
     */
    private String parentId;

    /**
     * 菜单URL
     */
    private String menuUrl;

    private String menuActive;

    private List<MenuVO> children;

    /**
     * 图标样式
     */
    private String iconClass;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getMenuActive() {
		return menuActive;
	}

	public void setMenuActive(String menuActive) {
		this.menuActive = menuActive;
	}

	public List<MenuVO> getChildren() {
		return children;
	}

	public void setChildren(List<MenuVO> children) {
		this.children = children;
	}

	public String getIconClass() {
		return iconClass;
	}

	public void setIconClass(String iconClass) {
		this.iconClass = iconClass;
	}

    
}
