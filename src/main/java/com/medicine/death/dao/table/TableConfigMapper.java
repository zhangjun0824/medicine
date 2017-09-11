package com.medicine.death.dao.table;

import java.util.List;

import com.medicine.death.entity.table.TableConfig;
import com.medicine.death.vo.SqlVo;

/**
 * 表
 *
 */
public interface TableConfigMapper {

	TableConfig queryOneById(TableConfig table);

	List<TableConfig> queryList(TableConfig table);

	void save(TableConfig table);

	void update(TableConfig table);

	void delete(TableConfig table);

	void execute(SqlVo sqlVo);


}
