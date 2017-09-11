package com.medicine.death.service.table;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medicine.death.dao.table.ColumnConfigMapper;
import com.medicine.death.dao.table.TableConfigMapper;
import com.medicine.death.entity.table.ColumnConfig;
import com.medicine.death.entity.table.TableConfig;
import com.medicine.death.vo.SqlVo;
import com.medicine.framework.base.BaseService;

/**
 * 列service
 *
 */
@Service()
public class ColumnConfigService extends BaseService {

	@Autowired
	private TableConfigMapper tablemapper;
	
    @Autowired
    private ColumnConfigMapper mapper;

	public List<ColumnConfig> queryList(ColumnConfig colmun) {
		return mapper.queryList(colmun);
	}

	public void save(ColumnConfig colmun) {
		mapper.save(colmun);
	}

	public void update(ColumnConfig colmun) {
		mapper.update(colmun);
	}

	public void delete(ColumnConfig column) {
		TableConfig tableNew=new TableConfig();
		tableNew.setId(column.getTableId());
		TableConfig table=tablemapper.queryOneById(tableNew);
		String sql="ALTER TABLE "+table.getName()+" DROP("+column.getName()+")";
		SqlVo sqlVo=new SqlVo();
		sqlVo.setSql(sql);
		tablemapper.execute(sqlVo);
		mapper.delete(column);
	}

	public List<ColumnConfig> queryListByIds(String columnIds) {
		return mapper.queryListByIds(columnIds.split(","));
	}
    

}
