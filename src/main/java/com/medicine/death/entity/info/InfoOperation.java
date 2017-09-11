package com.medicine.death.entity.info;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.medicine.death.entity.table.TableConfig;
import com.medicine.framework.base.BaseEntity;
import com.medicine.framework.mybatis.annotations.PK;

/**
 * 信息维护
 *
 */
@PK()
@JsonIgnoreProperties(ignoreUnknown=true)
public class InfoOperation extends BaseEntity {

	private TableConfig table;

	private List<Map<String,String>> infoList;

	public TableConfig getTable() {
		return table;
	}

	public void setTable(TableConfig table) {
		this.table = table;
	}

	public List<Map<String, String>> getInfoList() {
		return infoList;
	}

	public void setInfoList(List<Map<String, String>> infoList) {
		this.infoList = infoList;
	}
	
}
