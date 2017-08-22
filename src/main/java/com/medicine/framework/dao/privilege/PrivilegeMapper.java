package com.medicine.framework.dao.privilege;

import java.util.List;

import com.medicine.framework.entity.privilege.Privilege;
import com.medicine.framework.entity.privilege.PrivilegeResources;
import com.medicine.framework.entity.user.User;

/**
 * 系统菜单
 *
 */
public interface PrivilegeMapper {


	public List<Privilege> getMyMenuByPOD(User user);

	public List<Privilege> queryList();

	public List<Privilege> queryList4Menu();

	public List<PrivilegeResources> querySourceList(Privilege privilege);

	public void deleteSource(PrivilegeResources source);

	public void savePrivilege(Privilege pri);

	public void updatePrivilege(Privilege pri);

	public void saveSource(PrivilegeResources source);

	public void updateSource(PrivilegeResources source);

	public void deletePrivilege(Privilege pri);

	public List<Privilege> queryList4RoleId(String roleId);

}
