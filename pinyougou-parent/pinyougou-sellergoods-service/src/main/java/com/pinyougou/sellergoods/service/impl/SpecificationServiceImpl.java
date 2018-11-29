package com.pinyougou.sellergoods.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbSpecificationMapper;
import com.pinyougou.mapper.TbSpecificationOptionMapper;
import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.pojo.TbSpecificationExample;
import com.pinyougou.pojo.TbSpecificationExample.Criteria;
import com.pinyougou.pojo.TbSpecificationOption;
import com.pinyougou.pojo.TbSpecificationOptionExample;
import com.pinyougou.pojogroup.Specification;
import com.pinyougou.sellergoods.service.SpecificationService;

import entity.PageResult;

/**
 * 服务实现层
 * 
 * @author Administrator
 *
 */
@Service
public class SpecificationServiceImpl implements SpecificationService {

	@Autowired
	private TbSpecificationMapper specificationMapper;
	@Autowired
	private TbSpecificationOptionMapper specificationOptionMapper;

	/**
	 * 查询全部
	 */
	@Override
	public List<TbSpecification> findAll() {
		return specificationMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		Page<TbSpecification> page = (Page<TbSpecification>) specificationMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加 两表联合增加数据.
	 */
	@Override
	public void add(Specification specification) {
		// 从结果集中获取要新增的 tbSpecification 对象.
		TbSpecification tbSpecification = specification.getSpecification();
		// 更新 tbSpecification 表
		specificationMapper.insert(tbSpecification);

		// 获取 结果集 中的规格选项列表
		List<TbSpecificationOption> options = specification.getSpecificationOptionList();

		for (TbSpecificationOption option : options) {
			// 从 specification 表中 获取 新插入的 id 封装到 specificationOption 表 (一对多的关系)
			option.setSpecId(specification.getSpecification().getId());
			// 更新 tbSpecificationOption 表
			specificationOptionMapper.insert(option);
		}
	}

	/**
	 * 修改数据
	 *
	 */
	@Override
	public void update(Specification specification) {
		
		TbSpecification tbSpecification = specification.getSpecification();
//		保存修改的规格
		specificationMapper.updateByPrimaryKey(tbSpecification);
		Long specId = tbSpecification.getId();
		
		TbSpecificationOptionExample example = new TbSpecificationOptionExample();
		com.pinyougou.pojo.TbSpecificationOptionExample.Criteria condition = example.createCriteria();
		condition.andSpecIdEqualTo(specId);
//		先删除 相关联的规格选项. 
		specificationOptionMapper.deleteByExample(example);
			
		List<TbSpecificationOption> specificationOptionList = specification.getSpecificationOptionList();
//		再批量增加
		for (TbSpecificationOption option : specificationOptionList) {
//			每个规格选项设置 specId
			option.setSpecId(specId);
//			批量增加
			specificationOptionMapper.insert(option);
		}
		
	}

	/**
	 * 根据ID获取实体
	 * 
	 * @param id
	 * @return
	 */
	@Override
	public Specification findOne(Long id) {
		
		TbSpecification tbSpecification= specificationMapper.selectByPrimaryKey(id);
		Long specId = tbSpecification.getId();
//		返回 list 集合 TbSpecificationOptionExample
		TbSpecificationOptionExample example = new TbSpecificationOptionExample();
		
//		添加条件
		com.pinyougou.pojo.TbSpecificationOptionExample.Criteria criteria= example.createCriteria();
//		根据 规格ID 查询	
		criteria.andSpecIdEqualTo(specId);
		
		List<TbSpecificationOption> options = specificationOptionMapper.selectByExample(example);
//		封装结果集.
		Specification specification = new Specification();
		
		specification.setSpecification(tbSpecification);
		specification.setSpecificationOptionList(options);
		
		return specification;
	}

	/**
	 * 批量删除, 
	 */
	@Override
	public void delete(Long[] ids) {
		for (Long id : ids) {
//			删除规格表内规格
			specificationMapper.deleteByPrimaryKey(id);
//			删除规格的同时，还要记得将关联的规格选项删除掉。
			TbSpecificationOptionExample example = new TbSpecificationOptionExample();
			com.pinyougou.pojo.TbSpecificationOptionExample.Criteria condition = example.createCriteria();
//			指定规格ID为条件
			condition.andSpecIdEqualTo(id);
			
			specificationOptionMapper.deleteByExample(example);
		}
		
		
	}

	@Override
	public PageResult findPage(TbSpecification specification, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);

		TbSpecificationExample example = new TbSpecificationExample();
		Criteria criteria = example.createCriteria();

		if (specification != null) {
			if (specification.getSpecName() != null && specification.getSpecName().length() > 0) {
				criteria.andSpecNameLike("%" + specification.getSpecName() + "%");
			}

		}

		Page<TbSpecification> page = (Page<TbSpecification>) specificationMapper.selectByExample(example);
		return new PageResult(page.getTotal(), page.getResult());
	}

	@Override
	public List<Map> selectOptionList() {
		return specificationMapper.selectOptionList();
	}

}
