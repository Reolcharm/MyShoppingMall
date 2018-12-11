package com.pinyougou.search.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.search.service.ItemSearchService;

@Service(timeout=13000)
public class ItemSearchServiceImpl implements ItemSearchService {

	@Autowired
	private SolrTemplate solrTemplate;

	@Override
	public Map<String, Object> search(Map searchMap) {
		Map map = new HashMap<>();
		Query query = new SimpleQuery();
		// 去复制域中匹配和页面传递过来的关键字
		Criteria criteria = new Criteria("item_keywords").is(searchMap.get("keywords"));
		// 将查询条件添加到 query 对象中
		query.addCriteria(criteria);
		// 核心代码: 去solr 中(分页)查询结果
		ScoredPage<TbItem> page = solrTemplate.queryForPage(query, TbItem.class);
		// 将关键字结果 put 到map集合中
		map.put("rows", page.getContent());
		return map;
	}

}
