package com.pinyougou.sellergoods.service;

import java.util.List;

import com.pinyougou.pojo.TbBrand;

import entity.PageResult;

/**
 * @author Reolcharm
 *
 */
public interface BrandService {

	/**
	 * @return
	 * 查询所有品牌
	 */
	List<TbBrand> findAll();
	
	/**
	 * 分页查询.
	 * @param pageNum 当前页码
	 * @param pageSize 每页显示条数
	 * @return
	 */
	PageResult findPage(Integer pageNum, Integer pageSize);
}
