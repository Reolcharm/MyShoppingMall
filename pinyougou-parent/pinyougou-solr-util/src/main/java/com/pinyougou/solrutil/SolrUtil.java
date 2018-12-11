package com.pinyougou.solrutil;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojo.TbItemExample;
import com.pinyougou.pojo.TbItemExample.Criteria;

@Component
public class SolrUtil {
	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private SolrTemplate solrTemplate;

	/**
	 * 批量导入商品数据
	 */
	public void importItemData() {
		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo("1");// 已审核

		List<TbItem> itemList = itemMapper.selectByExample(example);
		System.out.println("===商品列表===");
		for (TbItem item : itemList) {
			System.out.println(item.getTitle());
			Map specMap = JSON.parseObject(item.getSpec());
			// 将spec字段中的json字符串转换为map
			item.setSpecMap(specMap);// 给带注解的specMap字段赋值
		}
		System.out.println("===结束===");
		// 按主键删除[内部自动做了转换, 他不知道传入的是int, long,
		// 接收一个字符串, ]
		// solrTemplate.deleteById("1");
		// 删除全部
		// Query query = new SimpleQuery("*:*");
		// solrTemplate.delete(query);
		// solrTemplate.commit();
		solrTemplate.saveBeans(itemList);
		solrTemplate.commit();
	}

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring/applicationContext*.xml");
		SolrUtil solrUtil = (SolrUtil) context.getBean("solrUtil");
		solrUtil.importItemData();
	}
}