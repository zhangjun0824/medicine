package com.medicine.framework.entity.privilege;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.medicine.framework.base.BaseEntity;
import com.medicine.framework.mybatis.annotations.PK;

/**
 * 系统菜单
 *
 */

@PK
@JsonIgnoreProperties(ignoreUnknown=true)
public class Privilege extends BaseEntity {


    /**
     *
     */
    private static final long serialVersionUID = -236145746315460048L;

    /**
     * 上级ID
     */
    private String parentId;
    
    private String parentName;

    private String router;

    /**
     * 描述
     */
    private String des;

    /**
     * 菜单URL
     */
    private String menuUrl;


    private List<Privilege> children;
    
    private List<PrivilegeResources> resource;

    /**
     * 图标样式
     */
    private String iconClass;

    /**
     * 排序
     */
    private Integer sort;


    /**
     * 是否菜单
     */
    private Boolean isMenu;

    /**
     * 是否权限控制
     */
    private Boolean isPrivilege;


    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }


    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public List<Privilege> getChildren() {
        return children;
    }

    public void setChildren(List<Privilege> children) {
        this.children = children;
    }


    public String getIconClass() {
        return iconClass;
    }

    public void setIconClass(String iconClass) {
        this.iconClass = iconClass;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Boolean getIsMenu() {
        return isMenu;
    }

    public void setIsMenu(Boolean isMenu) {
        this.isMenu = isMenu;
    }

    public Boolean getIsPrivilege() {
        return isPrivilege;
    }

    public void setIsPrivilege(Boolean isPrivilege) {
        this.isPrivilege = isPrivilege;
    }

	public String getRouter() {
		return router;
	}

	public void setRouter(String router) {
		this.router = router;
	}

	public List<PrivilegeResources> getResource() {
		return resource;
	}

	public void setResource(List<PrivilegeResources> resource) {
		this.resource = resource;
	}

}
