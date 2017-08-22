package com.medicine.death.service.table;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medicine.death.dao.table.ColumnConfigMapper;
import com.medicine.death.dao.table.TableConfigMapper;
import com.medicine.death.entity.table.ColumnConfig;
import com.medicine.death.entity.table.TableConfig;
import com.medicine.death.vo.SqlVo;
import com.medicine.framework.base.BaseService;

/**
 * 用户service
 *
 */
@Service()
public class TableConfigService extends BaseService {

    @Autowired
    private TableConfigMapper mapper;
    
    @Autowired
    private ColumnConfigMapper columnMapper;

	public List<TableConfig> queryList(TableConfig table) {
		return mapper.queryList(table);
	}

	public void save(String tableStr) throws Exception {
		TableConfig table=objectMapper.readValue(tableStr, TableConfig.class);
		mapper.save(table);
		List<ColumnConfig> columnList=table.getColumnList();
		String sql="CREATE TABLE "+table.getName()+" ( ID VARCHAR2(20) NOT NULL ,";
		for (int i=0; i< columnList.size();i++) {
			ColumnConfig columnConfig=columnList.get(i);
			if(i!=columnList.size()-1){
				sql+=columnConfig.getName()+" "+columnConfig.getColumnType()+"("+ columnConfig.getColumnLength() +") NULL ,";
			}else{
				sql+=columnConfig.getName()+" "+columnConfig.getColumnType()+"("+ columnConfig.getColumnLength() +") NULL )";
			}
			columnConfig.setTableId(table.getId());
			columnMapper.save(columnConfig);
		}
		SqlVo sqlVo=new SqlVo();
		sqlVo.setSql(sql);
		mapper.execute(sqlVo);
		String sql1="ALTER TABLE "+table.getName()+" ADD PRIMARY KEY (ID)";
		sqlVo.setSql(sql1);
		mapper.execute(sqlVo);
	}

	public void update(String tableStr) throws Exception {
		TableConfig table=objectMapper.readValue(tableStr, TableConfig.class);
		TableConfig oldTable=mapper.queryTable(table);
		mapper.update(table);
		List<ColumnConfig> columnList=table.getColumnList();
		String sql="";
		SqlVo sqlVo=new SqlVo();
		if(!oldTable.getName().equals(table.getName())){
			sql="ALTER TABLE "+oldTable.getName()+" RENAME TO "+table.getName();
			sqlVo.setSql(sql);
			mapper.execute(sqlVo);
		}
		
		for (int i=0; i< columnList.size();i++) {
			ColumnConfig columnConfig=columnList.get(i);
			if(StringUtils.isNotBlank(columnConfig.getId())){
				ColumnConfig oldColumn=columnMapper.queryColumn(columnConfig);
				columnMapper.update(columnConfig);
				if(columnConfig.getColumnType().equals("DATE")){
					sql="ALTER TABLE "+table.getName()+" MODIFY ( "+oldColumn.getName()+" "+columnConfig.getColumnType()+")";
				}else{
					sql="ALTER TABLE "+table.getName()+" MODIFY ( "+oldColumn.getName()+" "+columnConfig.getColumnType()+"("+columnConfig.getColumnLength()+"))";
				}
				sqlVo.setSql(sql);
				mapper.execute(sqlVo);
				if(!oldColumn.getName().equals(columnConfig.getName())){
					sql="ALTER TABLE "+table.getName()+" RENAME COLUMN "+oldColumn.getName()+" TO "+columnConfig.getName();
					sqlVo.setSql(sql);
					mapper.execute(sqlVo);
				}
			}else{
				if(columnConfig.getColumnType().equals("DATE")){
					sql="ALTER TABLE "+table.getName()+" ADD ( "+columnConfig.getName()+" "+columnConfig.getColumnType()+" NULL  ) ";
				}else{
					sql="ALTER TABLE "+table.getName()+" ADD ( "+columnConfig.getName()+" "+columnConfig.getColumnType()+"("+columnConfig.getColumnLength()+") NULL  ) ";
				}
				sqlVo.setSql(sql);
				mapper.execute(sqlVo);
				columnConfig.setTableId(table.getId());
				columnMapper.save(columnConfig);
			}
		}
		
	}

	public void delete(String tableId) {
		TableConfig tableNew=new TableConfig();
		tableNew.setId(tableId);
		TableConfig table=mapper.queryTable(tableNew);
		String sql="DROP TABLE "+table.getName();
		SqlVo sqlVo=new SqlVo();
		sqlVo.setSql(sql);
		mapper.execute(sqlVo);
		columnMapper.deleteByTableId(table.getId());
		mapper.delete(table);
	}
    

}
