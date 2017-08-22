package com.medicine.framework.dao.privilege;

import com.medicine.framework.entity.privilege.RolePrivilege;

public interface RolePrivilegeMapper {

	void saveRolePrivilege(RolePrivilege rolePrivilege);

	void deleteByRoleId(String roleId);

}
