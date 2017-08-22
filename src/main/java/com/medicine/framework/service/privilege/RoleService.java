package com.medicine.framework.service.privilege;

import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.medicine.framework.base.BaseService;
import com.medicine.framework.dao.privilege.RoleMapper;
import com.medicine.framework.dao.privilege.RolePrivilegeMapper;
import com.medicine.framework.entity.privilege.Role;
import com.medicine.framework.entity.privilege.RolePrivilege;
import com.medicine.framework.util.page.PageInfo;
import com.medicine.framework.vo.privilege.RoleVO;

/**
 * 角色
 *
 */
@Service
public class RoleService extends BaseService {

    @Autowired
    private RoleMapper roleMapper;
    
    @Autowired
    private RolePrivilegeMapper rolePrivilegeMapper;
    

	public void save(Role role) {
		role.setCreateUserId(getUserId());
		role.setCreateDate(new Date());
		role.setEditUserId(getUserId());
		role.setEditDate(new Date());
		roleMapper.save(role);
	}

	public List<Role> queryListPage(PageInfo pageInfo, String searchVal) {
		return roleMapper.queryListPage(pageInfo,searchVal);
	}

	public void update(Role role) {
		role.setEditUserId(getUserId());
		role.setEditDate(new Date());
		roleMapper.update(role);
	}

	public void delete(Role role) {
		roleMapper.delete(role);
	}

	public void saveRolePrivilege(String rpList,String roleId) throws Exception {
		List<RolePrivilege> list=objectMapper.readValue(new JSONArray(rpList).toString(), new TypeReference<List<RolePrivilege>>() {});
		rolePrivilegeMapper.deleteByRoleId(roleId);
		for (RolePrivilege rolePrivilege : list) {
			rolePrivilege.setCreateUserId(getUserId());
			rolePrivilege.setCreateDate(new Date());
			rolePrivilegeMapper.saveRolePrivilege(rolePrivilege);
		}
	}

	public List<RoleVO> queryListByUser(String searchVal, String userId) {
		return roleMapper.queryListByUser(searchVal,userId);
	}
    
}
