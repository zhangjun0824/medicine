package com.medicine.framework.dao.user;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.medicine.framework.entity.user.User;
import com.medicine.framework.util.page.PageInfo;

/**
 * 用户
 *
 */
public interface UserMapper {

	User getUserById(String id);

	void deleteUser(String id);

	List<String> getAuthorityName(String id);

	String getPWD(String username);

	List<String> getAllAuthorityName();

	List<String> getResource(String roleId);

	List<String> getNoAuthResource();

	void updatePwd(User user);

	User getUserByUserName(String username);

	List<User> queryListPage(@Param("page") PageInfo pageInfo,@Param("searchVal") String searchVal);

	void save(User user);

	void updateAccount(User user);

	void resetPwd(User user);

}
