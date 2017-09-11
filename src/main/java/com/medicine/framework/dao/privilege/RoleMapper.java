package com.medicine.framework.dao.privilege;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.medicine.framework.entity.privilege.Role;
import com.medicine.framework.util.page.PageInfo;
import com.medicine.framework.vo.privilege.RoleVO;

/**
 * 角色
 *
 */
public interface RoleMapper {

	void save(Role role);

	List<Role> queryListPage(@Param("page") PageInfo pageInfo,@Param("searchVal") String searchVal);

	void update(Role role);

	void delete(Role role);

	List<Role> queryListByUser(String searchVal);

	List<RoleVO> queryListByUser(@Param("searchVal") String searchVal,@Param("userId") String userId);

}
