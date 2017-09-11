package com.medicine.death.controller.info;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.medicine.death.entity.table.TableConfig;
import com.medicine.death.service.info.InfoOperationService;
import com.medicine.death.service.table.TableConfigService;
import com.medicine.framework.base.BaseController;
import com.medicine.framework.util.State;

@Controller
@RequestMapping("/info")
public class InfoOperationController extends BaseController {
	
    @Autowired
    private InfoOperationService service;
    
    @Autowired
    private TableConfigService tableService;
    
    @RequestMapping("main")
    public ModelAndView main(HttpServletRequest request, HttpServletResponse response) throws Exception{
    	ModelAndView result = new ModelAndView();
    	State state=new State("1");
    	List<TableConfig> tables=tableService.queryList(new TableConfig());
    	result.addObject("state", state);
    	result.addObject("tables", tables);
    	return  result;
    }
    
    @RequestMapping("save")
    public ModelAndView save(HttpServletRequest request, HttpServletResponse response,String tableStr) throws Exception{
    	ModelAndView result = new ModelAndView();
    	State state=new State("1");
    	service.save(tableStr);
    	result.addObject("state", state);
    	return  result;
    }
    
    @RequestMapping("edit")
    public ModelAndView edit(HttpServletRequest request, HttpServletResponse response,String tableStr) throws Exception{
    	ModelAndView result = new ModelAndView();
    	State state=new State("1");
    	service.edit(tableStr);
    	result.addObject("state", state);
    	return  result;
    }
    
    @RequestMapping("delete")
    public ModelAndView delete(HttpServletRequest request, HttpServletResponse response,String tableStr) throws Exception{
    	ModelAndView result = new ModelAndView();
    	State state=new State("1");
    	service.delete(tableStr);
    	result.addObject("state", state);
    	return  result;
    }
    
    @RequestMapping("infoList")
    public ModelAndView infoList(HttpServletRequest request, HttpServletResponse response,String table,String searchVal) throws Exception{
    	ModelAndView result = new ModelAndView();
    	State state=new State("1");
    	List<Map<String,Object>> list = service.infoList(pageInfo,table,searchVal);
    	result.addObject("infoList", list);
    	result.addObject("pageInfo", pageInfo);
    	result.addObject("state", state);
    	return  result;
    }
    
}
