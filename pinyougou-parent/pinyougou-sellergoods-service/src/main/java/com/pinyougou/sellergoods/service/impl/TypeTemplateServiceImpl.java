package com.pinyougou.sellergoods.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbSpecificationOptionMapper;
import com.pinyougou.mapper.TbTypeTemplateMapper;
import com.pinyougou.pojo.TbSpecificationOption;
import com.pinyougou.pojo.TbSpecificationOptionExample;
import com.pinyougou.pojo.TbTypeTemplate;
import com.pinyougou.pojo.TbTypeTemplateExample;
import com.pinyougou.pojo.TbTypeTemplateExample.Criteria;
import com.pinyougou.sellergoods.service.TypeTemplateService;

import entity.PageResult;

/**
 * 服务实现层
 * 
 * @author Administrator
 *
 */
@Service
public class TypeTemplateServiceImpl implements TypeTemplateService {

	/**
	 * 返回规格列表, 除了显示规格名称还要显示规格下的规格选项
	 */
	@Autowired
	private TbSpecificationOptionMapper specificationOptionMapper;
	@Autowired
	private TbTypeTemplateMapper typeTemplateMapper;

	/**
	 * 查询全部
	 */
	@Override
	public List<TbTypeTemplate> findAll() {
		return typeTemplateMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Page<TbTypeTemplate> page = (Page<TbTypeTemplate>) typeTemplateMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbTypeTemplate typeTemplate) {
		typeTemplateMapper.insert(typeTemplate);
	}

	/**
	 * 修改
	 */
	@Override
	public void update(TbTypeTemplate typeTemplate) {
		typeTemplateMapper.updateByPrimaryKey(typeTemplate);
	}

	/**
	 * 根据ID获取实体
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public TbTypeTemplate findOne(Long id) {
		return typeTemplateMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for (Long id : ids) {
			typeTemplateMapper.deleteByPrimaryKey(id);
		}
	}

	@Override
	public PageResult findPage(TbTypeTemplate typeTemplate, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);

		TbTypeTemplateExample example = new TbTypeTemplateExample();
		Criteria criteria = example.createCriteria();

		if (typeTemplate != null) {
			if (typeTemplate.getName() != null && typeTemplate.getName().length() > 0) {
				criteria.andNameLike("%" + typeTemplate.getName() + "%");
			}
			if (typeTemplate.getSpecIds() != null && typeTemplate.getSpecIds().length() > 0) {
				criteria.andSpecIdsLike("%" + typeTemplate.getSpecIds() + "%");
			}
			if (typeTemplate.getBrandIds() != null && typeTemplate.getBrandIds().length() > 0) {
				criteria.andBrandIdsLike("%" + typeTemplate.getBrandIds() + "%");
			}
			if (typeTemplate.getCustomAttributeItems() != null && typeTemplate.getCustomAttributeItems().length() > 0) {
				criteria.andCustomAttributeItemsLike("%" + typeTemplate.getCustomAttributeItems() + "%");
			}

		}

		Page<TbTypeTemplate> page = (Page<TbTypeTemplate>) typeTemplateMapper.selectByExample(example);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 返回模板列表
	 */
	@Override
	public List<Map> selectOptionList() {
		/*
		 * 朴素的想法 List<Map> result = new ArrayList<>(); 能从 dao sql解决的, 就别自己创造啊. 前台数据:
		 * [{35:"手机",37:"电视"},{"$ref":"$[0]"}] ... List<TbTypeTemplate> tempLists=
		 * typeTemplateMapper.selectByExample(null); Map<Long, String> tempMap = new
		 * HashMap<Long, String>(); for (TbTypeTemplate tbTypeTemplate : tempLists) {
		 * tempMap.put(tbTypeTemplate.getId(), tbTypeTemplate.getName());
		 * result.add(tempMap); }
		 */
		return typeTemplateMapper.selectOptionList();

	}

	@Override
	public List<Map> findSpecList(Long id) {
		// 查询模板 //在TypeTemplate 表根据id 查spec_ids
		TbTypeTemplate typeTemplate = typeTemplateMapper.selectByPrimaryKey(id);
		// fastJSON 字符串转换成集合.解析`表中表`:[{"id":27,"text":"网络"},{"id":32,"text":"机身内存"}]
		List<Map> list = null;
		list = JSON.parseArray(typeTemplate.getSpecIds(), Map.class);
		for (Map map : list) {
			// 查询规格选项列表
			TbSpecificationOptionExample example = new TbSpecificationOptionExample();
			com.pinyougou.pojo.TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
			// 本身是 Integer 类型, 强转,再转换成 Long并且specificationOption表中 specId 等于
			// [{"id":27,"text":"网络"},{"id":32,"text":"机身内存"}] 中的 27.
			criteria.andSpecIdEqualTo(new Long((Integer) map.get("id")));
			// 查到规格id 对应的 规格选项集合后, 添加到 每一条数据中
			List<TbSpecificationOption> options = specificationOptionMapper.selectByExample(example);
			map.put("options", options);
		}

		return list;
	}

}
