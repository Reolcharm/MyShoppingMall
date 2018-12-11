package com.pinyougou.search.service;

import java.util.Map;

public interface ItemSearchService {

	/**
	 * 搜索
	 * 
	 * @param searchMap
	 *            中的 keywords页面传递而来
	 * @return 返回页面 map, rows 查询结果集合
	 */
	public Map<String, Object> search(Map searchMap);
}
