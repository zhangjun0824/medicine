package com.medicine.death.service.info;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medicine.death.dao.info.InfoOperationMapper;
import com.medicine.death.entity.table.ColumnConfig;
import com.medicine.death.entity.table.TableConfig;
import com.medicine.death.vo.SqlVo;
import com.medicine.framework.base.BaseService;
import com.medicine.framework.util.page.PageInfo;

/**
 * 信息维护service
 *
 */
@Service()
public class InfoOperationService extends BaseService {

    @Autowired
    private InfoOperationMapper mapper;

	public void save(String tableStr) {
		
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
