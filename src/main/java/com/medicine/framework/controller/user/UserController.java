package com.medicine.framework.controller.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.medicine.framework.base.BaseController;
import com.medicine.framework.entity.user.User;
import com.medicine.framework.service.user.UserService;
import com.medicine.framework.util.State;

/**
 * 系统权限菜单
 *
 */
@Controller
@RequestMapping({"/user"})
public class UserController<Paginator> extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService service;
    
    @RequestMapping("save")
    public ModelAndView save(HttpServletRequest request, HttpServletResponse response,User user){
    	ModelAndView result = new ModelAndView();
    	State state=new State("1");
		service.save(user);
    	result.addObject("state", state);
    	return  result;
    }
    
    @RequestMapping("update")
    public ModelAndView update(HttpServletRequest request, HttpServletResponse response,User user){
    	ModelAndView result = new ModelAndView();
    	State state=new State("1");
    	service.update(user);
    	result.addObject("state", state);
    	return  result;
    }
    
    @RequestMapping("updatePwd")
    public ModelAndView updatePwd(HttpServletRequest request, HttpServletResponse response,User user){
    	ModelAndView result = new ModelAndView();
    	State state=new State("1");
    	service.updatePwd(user);
    	result.addObject("state", state);
    	return  result;
    }
    
    @RequestMapping("resetPwd")
    public ModelAndView resetPwd(HttpServletRequest request, HttpServletResponse response,User user){
    	ModelAndView result = new ModelAndView();
    	State state=new State("1");
    	service.resetPwd(user);
    	result.addObject("state", state);
    	return  result;
    }
    @RequestMapping("checkPwd")
    public ModelAndView checkPwd(HttpServletRequest request, HttpServletResponse response,User user){
    	ModelAndView result = new ModelAndView();
    	State state=new State("1");
    	service.checkPwd(user,state);
    	result.addObject("state", state);
    	return  result;
    }
    
    @RequestMapping("delete")
    public ModelAndView delete(HttpServletRequest request, HttpServletResponse response,User user){
    	ModelAndView result = new ModelAndView();
    	State state=new State("1");
    	service.deleteUser(user.getId());
    	result.addObject("state", state);
    	return  result;
    }
    
    @RequestMapping("queryListPage")
    public ModelAndView queryListPage(HttpServletRequest request, HttpServletResponse response,String searchVal){
    	ModelAndView result = new ModelAndView();
    	State state=new State("1");
    	List<User> users=service.queryListPage(pageInfo,searchVal);
    	result.addObject("users", users);
    	result.addObject("pageInfo", pageInfo);
    	result.addObject("state", state);
    	return  result;
    }

    @RequestMapping("saveUserRole")
    public ModelAndView saveUserRole(HttpServletRequest request, HttpServletResponse response,String userId,String ruList) throws Exception{
    	ModelAndView result = new ModelAndView();
    	State state=new State("1");
    	service.saveUserRole(userId,ruList);
    	result.addObject("state", state);
    	return  result;
    }
}
