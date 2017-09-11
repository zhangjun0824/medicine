package com.medicine.death.service.info;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medicine.death.dao.info.InfoOperationMapper;
import com.medicine.death.dao.table.ColumnConfigMapper;
import com.medicine.death.entity.table.ColumnConfig;
import com.medicine.death.entity.table.TableConfig;
import com.medicine.death.vo.SqlVo;
import com.medicine.framework.base.BaseService;
import com.medicine.framework.util.SnowflakeIdWorker;
import com.medicine.framework.util.page.PageInfo;

/**
 * 信息维护service
 *
 */
@Service()
public class InfoOperationService extends BaseService {

    @Autowired
    private InfoOperationMapper mapper;
    
    @Autowired
    private ColumnConfigMapper columnMapper;

	public void save(String tableStr) throws JSONException {
		JSONObject table=new JSONObject(tableStr);
		JSONArray columnList=table.getJSONArray("columnList");
		JSONArray newList=new JSONArray();
		for (int i=0; i<columnList.length() ;i++) {
			JSONObject json = columnList.getJSONObject(i);
			if(json.has("value")&&StringUtils.isNotBlank(json.getString("value"))){
				newList.put(json);
			}
		}
		if(newList.length()>0){
			String sql="insert into " + table.get("name") +" ";
			String keyStr="(id,";
			String valueStr=" values ("+SnowflakeIdWorker.getInstance().nextId()+",";
			for (int i=0; i<newList.length() ;i++) {
				JSONObject json = newList.getJSONObject(i);
				keyStr+=json.getString("name");
				valueStr+=json.getString("value");
				if(i!=newList.length()-1){
					keyStr+=",";
					valueStr+=",";
				}
			}
			keyStr+=") ";
			valueStr+=") ";
			sql+=keyStr;
			sql+=valueStr;
			SqlVo sqlVo=new SqlVo();
			sqlVo.setSql(sql);
			mapper.save(sqlVo);
		}
	}
	
	public void edit(String tableStr) throws JSONException {
		JSONObject table=new JSONObject(tableStr);
		JSONArray infoList=table.getJSONArray("infoList");
		
		String sql="update " + table.get("name") +" set ";
		for (int i=0; i<infoList.length() ;i++) {
			JSONObject json = infoList.getJSONObject(i);
			ColumnConfig cc=columnMapper.queryColumnById(json.getString("id"));
			if("NUMBER".equals(cc.getColumnType())){
				sql+=json.getString("name")+"="+json.getString("value");
			}else{
				sql+=json.getString("name")+"='"+json.getString("value")+"'";
			}
			if(i!=infoList.length()-1){
				sql+=",";
			}
		}
		sql+=" where id="+table.getString("infoId");
		SqlVo sqlVo=new SqlVo();
		sqlVo.setSql(sql);
		mapper.update(sqlVo);
		
	}

	public void delete(String tableStr) throws JSONException {
		JSONObject table=new JSONObject(tableStr);
		String sql="delete from " + table.get("name") + " where id="+table.getString("infoId");
		SqlVo sqlVo=new SqlVo();
		sqlVo.setSql(sql);
		mapper.delete(sqlVo);
	}

	public List<Map<String, Object>> infoList(PageInfo pageInfo,String table, String searchVal) throws Exception {
		
		TableConfig tc=objectMapper.readValue(table, TableConfig.class);
		
		List<ColumnConfig> columnList=tc.getColumnList();
		
		
		String sql="SELECT id,";
		for (int i=0;i< columnList.size();i++) {
			ColumnConfig columnConfig=columnList.get(i);
			if(i!=columnList.size()-1){
				sql+=" "+columnConfig.getName()+",";
			}else{
				sql+=" "+columnConfig.getName();
			}
		}
		sql+=" FROM "+tc.getName()+" WHERE 1=1 ";
		if(StringUtils.isNotBlank(searchVal)){
			sql+=" and ( ";
			for (int i=0;i< columnList.size();i++) {
				ColumnConfig columnConfig=columnList.get(i);
				if(i==0){
					sql+=columnConfig.getName()+" LIKE '%"+searchVal+"%'";
				}else{
					sql+=" OR "+columnConfig.getName()+" LIKE '%"+searchVal+"%'";
				}
			}
			sql+=" ) ";
		}
		SqlVo sqlVo=new SqlVo();
		sqlVo.setSql(sql);
		List<Map<String, Object>> list = mapper.executeListPage(pageInfo,sqlVo);
		return list;
	}

}
