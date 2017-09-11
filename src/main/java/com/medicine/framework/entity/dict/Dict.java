package com.medicine.framework.entity.dict;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.medicine.framework.base.BaseEntity;
import com.medicine.framework.mybatis.annotations.PK;

/**
 * 字典
 */
@PK()
@JsonIgnoreProperties(ignoreUnknown=true)
public class Dict extends BaseEntity {

	private String parentCode;
	
	private String unit;
	
	private String val;
	
	private Integer sort;

	private String type;
	
	private List<Dict> children;
	
	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Dict> getChildren() {
		return children;
	}

	public void setChildren(List<Dict> children) {
		this.children = children;
	}
	
	
}
