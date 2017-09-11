package com.medicine.framework.controller.privilege;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.medicine.framework.base.BaseController;
import com.medicine.framework.entity.privilege.Role;
import com.medicine.framework.service.privilege.RoleService;
import com.medicine.framework.util.State;
import com.medicine.framework.vo.privilege.RoleVO;

/**
 * 角色
 *
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService service;

    @RequestMapping("save")
    public ModelAndView save(HttpServletRequest request, HttpServletResponse response,Role role){
    	ModelAndView result = new ModelAndView();
    	State state=new State("1");
		service.save(role);
    	result.addObject("state", state);
    	return  result;
    }
    @RequestMapping("update")
    public ModelAndView update(HttpServletRequest request, HttpServletResponse response,Role role){
    	ModelAndView result = new ModelAndView();
    	State state=new State("1");
    	service.update(role);
    	result.addObject("state", state);
    	return  result;
    }
    @RequestMapping("delete")
    public ModelAndView delete(HttpServletRequest request, HttpServletResponse response,Role role){
    	ModelAndView result = new ModelAndView();
    	State state=new State("1");
    	service.delete(role);
    	result.addObject("state", state);
    	return  result;
    }
    @RequestMapping("queryListPage")
    public ModelAndView queryListPage(HttpServletRequest request, HttpServletResponse response,String searchVal){
    	ModelAndView result = new ModelAndView();
    	State state=new State("1");
    	List<Role> roles=service.queryListPage(pageInfo,searchVal);
    	result.addObject("roles", roles);
    	result.addObject("pageInfo", pageInfo);
    	result.addObject("state", state);
    	return  result;
    }
    @RequestMapping("queryListByUser")
    public ModelAndView queryListByUser(HttpServletRequest request, HttpServletResponse response,String searchVal,String userId){
    	ModelAndView result = new ModelAndView();
    	State state=new State("1");
    	List<RoleVO> roles=service.queryListByUser(searchVal,userId);
    	result.addObject("roles", roles);
    	result.addObject("state", state);
    	return  result;
    }
    @RequestMapping("saveRolePrivilege")
    public ModelAndView saveRolePrivilege(HttpServletRequest request, HttpServletResponse response,String rpList,String roleId) throws Exception{
    	ModelAndView result = new ModelAndView();
    	State state=new State("1");
    	service.saveRolePrivilege(rpList,roleId);
    	result.addObject("state", state);
    	return  result;
    }

}
