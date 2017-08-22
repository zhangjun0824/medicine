package com.medicine.death.controller.table;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.medicine.death.entity.table.ColumnConfig;
import com.medicine.death.service.table.ColumnConfigService;
import com.medicine.framework.base.BaseController;
import com.medicine.framework.util.State;

@Controller
@RequestMapping("/column")
public class ColumnConfigController extends BaseController {
	
    @Autowired
    private ColumnConfigService service;

    @RequestMapping("save")
    public ModelAndView save(HttpServletRequest request, HttpServletResponse response,ColumnConfig Column){
    	ModelAndView result = new ModelAndView();
    	State state=new State("1");
    	service.save(Column);
    	result.addObject("state", state);
    	return  result;
    }
    @RequestMapping("update")
    public ModelAndView update(HttpServletRequest request, HttpServletResponse response,ColumnConfig Column){
    	ModelAndView result = new ModelAndView();
    	State state=new State("1");
    	service.update(Column);
    	result.addObject("state", state);
    	return  result;
    	
    }
    @RequestMapping("delete")
    public ModelAndView delete(HttpServletRequest request, HttpServletResponse response,ColumnConfig Column){
    	ModelAndView result = new ModelAndView();
    	State state=new State("1");
    	service.delete(Column);
    	result.addObject("state", state);
    	return  result;
    }
    @RequestMapping("queryList")
    public ModelAndView queryList(HttpServletRequest request, HttpServletResponse response,ColumnConfig Column){
    	ModelAndView result = new ModelAndView();
    	State state=new State("1");
    	List<ColumnConfig> list=service.queryList(Column);
    	result.addObject("columnList", list);
    	result.addObject("state", state);
    	return  result;
    }
    
}