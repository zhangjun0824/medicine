package com.medicine.death.dao.table;

import java.util.List;

import com.medicine.death.entity.table.ColumnConfig;

/**
 * 列
 *
 */
public interface ColumnConfigMapper {

	ColumnConfig queryColumn(ColumnConfig columnConfig);

	List<ColumnConfig> queryList(ColumnConfig column);

	void save(ColumnConfig column);

	void update(ColumnConfig column);

	void delete(ColumnConfig column);

	void deleteByTableId(String id);

	List<ColumnConfig> queryListByIds(String[] ids);


}
