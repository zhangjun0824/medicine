package com.medicine.framework.controller.privilege;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.medicine.framework.base.BaseController;
import com.medicine.framework.entity.privilege.Privilege;
import com.medicine.framework.entity.privilege.PrivilegeResources;
import com.medicine.framework.service.privilege.PrivilegeService;
import com.medicine.framework.util.State;
import com.medicine.framework.vo.privilege.PrivilegeVO;

/**
 * 系统权限菜单
 *
 */
@Controller
@RequestMapping({"/privilege"})
public class PrivilegeController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(PrivilegeController.class);

    @Autowired
    private PrivilegeService service;
    
    @RequestMapping("saveOrUpdate")
    public ModelAndView save(HttpServletRequest request, HttpServletResponse response,String privilege) throws Exception{
    	ModelAndView result = new ModelAndView();
    	State state=new State("1");
		service.saveOrUpdate(privilege);
    	result.addObject("state", state);
    	return  result;
    }
    @RequestMapping("queryList")
    public ModelAndView queryList(HttpServletRequest request, HttpServletResponse response){
    	ModelAndView result = new ModelAndView();
    	State state=new State("1");
    	List<Privilege> privileges=service.queryList();
    	result.addObject("privileges", privileges);
    	result.addObject("state", state);
    	return  result;
    }
    @RequestMapping("queryList4RoleId")
    public ModelAndView queryList4RoleId(HttpServletRequest request, HttpServletResponse response,String roleId){
    	ModelAndView result = new ModelAndView();
    	State state=new State("1");
    	List<Privilege> privileges=service.queryList4RoleId(roleId);
    	result.addObject("privileges", privileges);
    	result.addObject("state", state);
    	return  result;
    }
    @RequestMapping("querySourceList")
    public ModelAndView querySourceList(HttpServletRequest request, HttpServletResponse response,Privilege privilege){
    	ModelAndView result = new ModelAndView();
    	State state=new State("1");
    	List<PrivilegeResources> resources=service.querySourceList(privilege);
    	result.addObject("resources", resources);
    	result.addObject("state", state);
    	return  result;
    }
    @RequestMapping("deletePrivilege")
    public ModelAndView deletePrivilege(HttpServletRequest request, HttpServletResponse response,String privilege) throws Exception{
    	ModelAndView result = new ModelAndView();
    	State state=new State("1");
    	service.deletePrivilege(privilege);
    	result.addObject("state", state);
    	return  result;
    }
    @RequestMapping("deleteSource")
    public ModelAndView deleteSource(HttpServletRequest request, HttpServletResponse response,PrivilegeResources source){
    	ModelAndView result = new ModelAndView();
    	State state=new State("1");
    	service.deleteSource(source);
    	result.addObject("state", state);
    	return  result;
    }

}
