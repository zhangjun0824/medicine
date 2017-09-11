package com.medicine.framework.service.dict;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medicine.framework.base.BaseService;
import com.medicine.framework.dao.dict.DictMapper;
import com.medicine.framework.entity.dict.Dict;
import com.medicine.framework.util.State;

/**
 * 角色
 *
 */
@Service
public class DictService extends BaseService {

    @Autowired
    private DictMapper mapper;

	public void save(String dictStr) throws Exception {
		Dict dict=objectMapper.readValue(dictStr, Dict.class);
		dict.setCreateUserId(getUserId());
		dict.setCreateDate(new Date());
		dict.setEditUserId(getUserId());
		dict.setEditDate(new Date());
		mapper.save(dict);
	}

	public void update(String dictStr) throws Exception {
		Dict dict=objectMapper.readValue(dictStr, Dict.class);
		dict.setEditUserId(getUserId());
		dict.setEditDate(new Date());
		mapper.update(dict);
	}

	public void delete(Dict dict) {
		mapper.delete(dict);
	}

	public List<Dict> queryList(String searchVal) {
		List<Dict> list=mapper.queryList(searchVal);
		if(list!=null){
			list = toTree(list);
		}
		return list;
	}
	private List<Dict> toTree(List<Dict> list) {
		List<Dict> treeList = new ArrayList<Dict>();
		for (Dict dict : list) {
			boolean mark = false;
			for (Dict dict2 : list) {
				if (StringUtils.isNotBlank(dict.getParentCode()) && dict.getParentCode().equals(dict2.getCode())) {
					mark = true;
					if (dict2.getChildren() == null) {
						dict2.setChildren(new ArrayList<Dict>());
					}
					dict2.getChildren().add(dict);
					break;
				}
			}
			if (!mark) {
				treeList.add(dict);
			}
		}
		return treeList;
	}

	public void checkCode(Dict dict, State state) {
		if(dict.getCode().equals(dict.getParentCode())){
			state.setCode("0");
			state.setMsg("编码不能与父级编码相同.");
		}else{
			Dict d=mapper.queryDict(dict);
			if(d!=null){
				if(StringUtils.isNotBlank(dict.getId())){
					if(!d.getId().equals(dict.getId()) ){
						state.setCode("0");
						state.setMsg("编码已存在,不能保存.");
					}
				}else{
					state.setCode("0");
					state.setMsg("编码已存在,不能保存.");
				}
			}
		}
	}

	public List<Dict> queryTreeByCode(String code) {
		return mapper.queryTreeByCode(code);
	}

	public List<Dict> queryListByParentCode(String code) {
		return mapper.queryListByParentCode(code);
	}
    
}
