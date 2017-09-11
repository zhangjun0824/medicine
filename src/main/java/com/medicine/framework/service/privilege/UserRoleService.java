package com.medicine.framework.service.privilege;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medicine.framework.base.BaseService;
import com.medicine.framework.dao.privilege.UserRoleMapper;
import com.medicine.framework.entity.privilege.Role;
import com.medicine.framework.entity.privilege.UserRole;
import com.medicine.framework.entity.user.User;
import com.medicine.framework.vo.privilege.RoleVO;

/**
 * 用户角色
 *
 */
@Service
public class UserRoleService extends BaseService {

    @Autowired
    private UserRoleMapper userRoleMapper;


}
