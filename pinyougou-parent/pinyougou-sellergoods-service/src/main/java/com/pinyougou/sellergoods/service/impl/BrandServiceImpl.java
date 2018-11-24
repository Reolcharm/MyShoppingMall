package com.pinyougou.sellergoods.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbBrandMapper;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.sellergoods.service.BrandService;

import entity.PageResult;

@Service
public class BrandServiceImpl implements BrandService {

	@Autowired
	private TbBrandMapper tbBrandMapper;

	@Override
	public List<TbBrand> findAll() {
		return tbBrandMapper.selectByExample(null);
	}

	@Override
	public PageResult findPage(Integer pageNum, Integer pageSize) {
		// 开启分页
		PageHelper.startPage(pageNum, pageSize);
		// 查询所有品牌, 封装到Page这个 arrayList 中
		Page<TbBrand> page = (Page<TbBrand>) tbBrandMapper.selectByExample(null);
		// 返回分页好的结果.
		return new PageResult(page.getTotal(), page.getResult());
	}

}
