package com.medicine.framework.service.user;

import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.medicine.framework.base.BaseService;
import com.medicine.framework.dao.privilege.UserRoleMapper;
import com.medicine.framework.dao.user.UserMapper;
import com.medicine.framework.entity.privilege.UserRole;
import com.medicine.framework.entity.user.User;
import com.medicine.framework.util.State;
import com.medicine.framework.util.SysUtil;
import com.medicine.framework.util.page.PageInfo;

/**
 * 用户service
 *
 */
@Service("userService")
public class UserService extends BaseService {

    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private UserRoleMapper userRoleMapper;


    /**
     * 根据用户Id查询用户
     *
     * @param id
     * @return
     */
    public User getUserById(String id) {
        return userMapper.getUserById(id);
    }

    /**
     * 删除用户
     *
     * @param id
     */
    public void deleteUser(String id) {
        userMapper.deleteUser(id);
    }

    /**
     * 根据用户名获取权限列表
     *
     * @return
     */
    public List<String> getAuthorityName(String userId) {
        List<String> list = userMapper.getAuthorityName(userId);
        list.add("NoAuthResource");
        return list;
    }

    /**
     * 根据用户名查找密码
     *
     * @param username
     * @return
     */
    public String getPWD(String username) {
        return userMapper.getPWD(username);
    }

    /**
     * 得到所有权限
     *
     * @return
     */
    public List<String> getAllAuthorityName() {
        return userMapper.getAllAuthorityName();
    }

    /**
     * 根据权限获得可以访问的页面
     *
     * @return
     */
    @Cacheable(value = "privilege", key = "'resource'+#roleId")
    public List<String> getResource(String roleId) {
        List<String> list = userMapper.getResource(roleId);
        return list;
    }

    /**
     * 不需权限的资源
     *
     * @return
     */
    public List<String> getNoAuthResource() {
        List<String> list = userMapper.getNoAuthResource();
        return list;
    }

    public void updatePwd(User user) {
    	user.setPassword(SysUtil.encodePassword(user.getPassword(), ""));
        userMapper.updatePwd(user);
    }

	public User getUserByUserName(String username) {
		return userMapper.getUserByUserName(username);
	}

	public List<User> queryListPage(PageInfo pageInfo,String searchVal) {
		return userMapper.queryListPage(pageInfo,searchVal);
	}

	public void save(User user) {
		user.setCreateUserId(getUserId());
		user.setCreateDate(new Date());
		user.setEditUserId(getUserId());
		user.setEditDate(new Date());
		user.setPassword(SysUtil.encodePassword(user.getPassword(), ""));
		userMapper.save(user);
	}

	public void saveUserRole(String userId, String ruList) throws Exception {
		List<UserRole> list=objectMapper.readValue(new JSONArray(ruList).toString(), new TypeReference<List<UserRole>>() {});
		userRoleMapper.deleteUserRole(userId);
		for (UserRole userRole : list) {
			userRole.setCreateUserId(getUserId());
			userRole.setCreateDate(new Date());
			userRoleMapper.saveUserRole(userRole);
		}
		
	}

	public void update(User user) {
		user.setEditDate(new Date());
		user.setEditUserId(getUserId());
		userMapper.updateAccount(user);
	}

	public void resetPwd(User user) {
		user.setPassword(SysUtil.encodePassword(user.getUsername(), ""));
		userMapper.resetPwd(user);
	}

	public void checkPwd(User user,State state) {
		User u=userMapper.getUserById(user.getId());
		String oldPwd=SysUtil.encodePassword(user.getPassword(), "");
		if(!oldPwd.equals(u.getPassword())){
			state.setCode("0");
			state.setMsg("旧密码输入错误.");
		}
	}
}
