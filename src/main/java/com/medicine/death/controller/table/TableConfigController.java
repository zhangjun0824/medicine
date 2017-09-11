package com.medicine.death.controller.table;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.medicine.death.entity.table.TableConfig;
import com.medicine.death.service.table.TableConfigService;
import com.medicine.framework.base.BaseController;
import com.medicine.framework.util.State;

@Controller
@RequestMapping("/table")
public class TableConfigController extends BaseController {
	
    @Autowired
    private TableConfigService service;

    @RequestMapping("save")
    public ModelAndView save(HttpServletRequest request, HttpServletResponse response,String tableStr) throws Exception{
    	ModelAndView result = new ModelAndView();
    	State state=new State("1");
    	service.save(tableStr);
    	result.addObject("state", state);
    	return  result;
    }
    @RequestMapping("update")
    public ModelAndView update(HttpServletRequest request, HttpServletResponse response,String tableStr) throws Exception{
    	ModelAndView result = new ModelAndView();
    	State state=new State("1");
		service.update(tableStr);
    	result.addObject("state", state);
    	return  result;
    }
    @RequestMapping("delete")
    public ModelAndView delete(HttpServletRequest request, HttpServletResponse response,String tableId){
    	ModelAndView result = new ModelAndView();
    	State state=new State("1");
    	service.delete(tableId);
    	result.addObject("state", state);
    	return  result;
    }
    @RequestMapping("queryOneById")
    public ModelAndView queryOneById(HttpServletRequest request, HttpServletResponse response,TableConfig table){
    	ModelAndView result = new ModelAndView();
    	State state=new State("1");
    	TableConfig tc=service.queryOneById(table);
    	result.addObject("table", tc);
    	result.addObject("state", state);
    	return  result;
    }
    @RequestMapping("queryList")
    public ModelAndView queryList(HttpServletRequest request, HttpServletResponse response,TableConfig table){
    	ModelAndView result = new ModelAndView();
    	State state=new State("1");
    	List<TableConfig> list=service.queryList(table);
    	result.addObject("tableList", list);
    	result.addObject("state", state);
    	return  result;
    }
    
}
