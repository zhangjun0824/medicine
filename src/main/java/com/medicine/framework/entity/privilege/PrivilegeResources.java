package com.medicine.framework.entity.privilege;

import com.medicine.framework.base.BaseEntity;
import com.medicine.framework.mybatis.annotations.PK;

/**
 * 权限资源表
 *
 */
@PK
public class PrivilegeResources extends BaseEntity{

    /**
	 * 
	 */
	private static final long serialVersionUID = -9096534948206588350L;

	private String privilegeId;

    private Boolean isMenu;

    private String router;
    
    private String resourcesUrl;

    public String getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(String privilegeId) {
        this.privilegeId = privilegeId;
    }

    public Boolean getIsMenu() {
        return isMenu;
    }

    public void setIsMenu(Boolean isMenu) {
        this.isMenu = isMenu;
    }

    public String getRouter() {
		return router;
	}

	public void setRouter(String router) {
		this.router = router;
	}

	public String getResourcesUrl() {
        return resourcesUrl;
    }

    public void setResourcesUrl(String resourcesUrl) {
        this.resourcesUrl = resourcesUrl;
    }

}
