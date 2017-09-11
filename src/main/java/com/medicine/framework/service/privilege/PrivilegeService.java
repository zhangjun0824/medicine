package com.medicine.framework.service.privilege;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.gexin.fastjson.JSONObject;
import com.medicine.framework.base.BaseService;
import com.medicine.framework.dao.privilege.PrivilegeMapper;
import com.medicine.framework.entity.privilege.Privilege;
import com.medicine.framework.entity.privilege.PrivilegeResources;
import com.medicine.framework.entity.user.User;
import com.medicine.framework.vo.privilege.PrivilegeVO;

/**
 * 系统菜单
 *
 */
@Service
public class PrivilegeService extends BaseService {

	@Autowired
	private PrivilegeMapper privilegeMapper;


	public void saveOrUpdate(String privilege) throws Exception {
		Privilege pri=objectMapper.readValue(privilege, Privilege.class);
		if(StringUtils.isNotBlank(pri.getId())){
			pri.setEditUserId(getUserId());
			pri.setEditDate(new Date());
			privilegeMapper.updatePrivilege(pri);
		}else{
			pri.setCreateUserId(getUserId());
			pri.setCreateDate(new Date());
			pri.setEditUserId(getUserId());
			pri.setEditDate(new Date());
			privilegeMapper.savePrivilege(pri);
		}
		for (PrivilegeResources source : pri.getResource()) {
			if(StringUtils.isNotBlank(source.getId())){
				source.setEditUserId(getUserId());
				source.setEditDate(new Date());
				privilegeMapper.updateSource(source);
			}else{
				source.setCreateUserId(getUserId());
				source.setCreateDate(new Date());
				source.setEditUserId(getUserId());
				source.setEditDate(new Date());
				source.setPrivilegeId(pri.getId());
				privilegeMapper.saveSource(source);
			}
		}
	}
	
	public List<Privilege> queryList() {
		List<Privilege> privilegeList = privilegeMapper.queryList4Menu();
		if (privilegeList != null) {
			privilegeList = toTree(privilegeList);
		}
		return privilegeList;
	}
	
	public List<PrivilegeResources> querySourceList(Privilege privilege) {
		return privilegeMapper.querySourceList(privilege);
	}
	
	
	
	public List<Privilege> getUserDefaultMenuByUserId(User user) {
		List<Privilege> privilegeList = null;
		privilegeList = privilegeMapper.getMyMenuByPOD(user);
		if (privilegeList != null) {
			privilegeList = toTree(privilegeList);
		}
		return privilegeList;
	}

	private List<Privilege> toTree(List<Privilege> list) {
		List<Privilege> treeList = new ArrayList<Privilege>();
		for (Privilege sysMenu1 : list) {
			boolean mark = false;
			for (Privilege sysMenu2 : list) {
				if (StringUtils.isNotBlank(sysMenu1.getParentId()) && sysMenu1.getParentId().equals(sysMenu2.getId())) {
					mark = true;
					if (sysMenu2.getChildren() == null) {
						sysMenu2.setChildren(new ArrayList<Privilege>());
					}
					sysMenu2.getChildren().add(sysMenu1);
					break;
				}
			}

			if (!mark) {
				treeList.add(sysMenu1);
			}
		}
		return treeList;
	}

	public void deletePrivilege(String privilege) throws Exception {
		Privilege pri=objectMapper.readValue(privilege, Privilege.class);
		privilegeMapper.deletePrivilege(pri);
	}
	
	public void deleteSource(PrivilegeResources source) {
		privilegeMapper.deleteSource(source);
	}

	public List<Privilege> queryList4RoleId(String roleId) {
		return privilegeMapper.queryList4RoleId(roleId);
	}


}
