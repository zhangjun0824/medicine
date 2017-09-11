package com.medicine.framework.controller.dict;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.medicine.framework.base.BaseController;
import com.medicine.framework.entity.dict.Dict;
import com.medicine.framework.service.dict.DictService;
import com.medicine.framework.util.State;

@Controller
@RequestMapping("/dict")
public class DictController extends BaseController {
	
    @Autowired
    private DictService service;

    @RequestMapping("save")
    public ModelAndView save(HttpServletRequest request, HttpServletResponse response,String dict) throws Exception{
    	ModelAndView result = new ModelAndView();
    	State state=new State("1");
    	service.save(dict);
    	result.addObject("state", state);
    	return  result;
    }
    
    @RequestMapping("update")
    public ModelAndView update(HttpServletRequest request, HttpServletResponse response,String dict) throws Exception{
    	ModelAndView result = new ModelAndView();
    	State state=new State("1");
    	service.update(dict);
    	result.addObject("state", state);
    	return  result;
    }
    
    @RequestMapping("delete")
    public ModelAndView delete(HttpServletRequest request, HttpServletResponse response,Dict dict){
    	ModelAndView result = new ModelAndView();
    	State state=new State("1");
    	service.delete(dict);
    	result.addObject("state", state);
    	return  result;
    }
    
    @RequestMapping("queryList")
    public ModelAndView queryListPage(HttpServletRequest request, HttpServletResponse response,String searchVal){
    	ModelAndView result = new ModelAndView();
    	State state=new State("1");
    	List<Dict> list=service.queryList(searchVal);
    	result.addObject("dicts", list);
    	result.addObject("state", state);
    	return  result;
    }
    @RequestMapping("queryListByParentCode")
    public ModelAndView queryListByParentCode(HttpServletRequest request, HttpServletResponse response,String code){
    	ModelAndView result = new ModelAndView();
    	State state=new State("1");
    	List<Dict> list=service.queryListByParentCode(code);
    	result.addObject("dicts", list);
    	result.addObject("state", state);
    	return  result;
    }
    @RequestMapping("queryTreeByCode")
    public ModelAndView queryTreeByCode(HttpServletRequest request, HttpServletResponse response,String code){
    	ModelAndView result = new ModelAndView();
    	State state=new State("1");
    	List<Dict> list=service.queryTreeByCode(code);
    	result.addObject("dicts", list);
    	result.addObject("state", state);
    	return  result;
    }
    @RequestMapping("checkCode")
    public ModelAndView checkCode(HttpServletRequest request, HttpServletResponse response,Dict dict){
    	ModelAndView result = new ModelAndView();
    	State state=new State("1");
    	service.checkCode(dict,state);
    	result.addObject("state", state);
    	return  result;
    }
}
