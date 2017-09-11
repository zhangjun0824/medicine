package com.medicine.death.entity.table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.medicine.framework.base.BaseEntity;
import com.medicine.framework.mybatis.annotations.PK;

/**
 * 列
 */
@PK()
@JsonIgnoreProperties(ignoreUnknown=true)
public class ColumnConfig extends BaseEntity {


	private String columnLength;
	
	private String columnType;

	private String tableId;
	
	public String getColumnLength() {
		return columnLength;
	}

	public void setColumnLength(String columnLength) {
		this.columnLength = columnLength;
	}

	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}
	
}
