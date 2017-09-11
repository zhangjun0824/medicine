package com.medicine.death.entity.table;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.medicine.framework.base.BaseEntity;
import com.medicine.framework.mybatis.annotations.PK;

/**
 * 表
 */
@PK()
@JsonIgnoreProperties(ignoreUnknown=true)
public class TableConfig extends BaseEntity {

	private List<ColumnConfig> columnList;

	public List<ColumnConfig> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<ColumnConfig> columnList) {
		this.columnList = columnList;
	}
	
	
}
