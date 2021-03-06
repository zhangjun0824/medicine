package com.medicine.death.dao.info;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.medicine.death.entity.table.TableConfig;
import com.medicine.death.vo.SqlVo;
import com.medicine.framework.util.page.PageInfo;

/**
 * 信息维护
 *
 */
public interface InfoOperationMapper {

	TableConfig queryTable(TableConfig table);

	List<TableConfig> queryList(TableConfig table);

	void save(SqlVo sqlVo);

	void update(SqlVo sqlVo);

	void delete(SqlVo sqlVo);

	List<Map<String, Object>> executeListPage(@Param("page") PageInfo pageInfo,@Param("sqlVo") SqlVo sqlVo);

}
