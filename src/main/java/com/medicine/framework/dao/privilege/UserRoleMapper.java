package com.medicine.framework.dao.privilege;

import com.medicine.framework.entity.privilege.UserRole;

/**
 * 用户角色
 *
 */
public interface UserRoleMapper {

	void deleteUserRole(String userId);

	void saveUserRole(UserRole userRole);

}
