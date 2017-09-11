package com.medicine.framework.dao.dict;

import java.util.List;

import com.medicine.framework.entity.dict.Dict;

/**
 * 用户
 *
 */
public interface DictMapper {

	void save(Dict dict);

	void update(Dict dict);

	void delete(Dict dict);

	List<Dict> queryList(String searchVal);

	Dict queryDict(Dict dict);

	List<Dict> queryTreeByCode(String code);

	List<Dict> queryListByParentCode(String code);

}
