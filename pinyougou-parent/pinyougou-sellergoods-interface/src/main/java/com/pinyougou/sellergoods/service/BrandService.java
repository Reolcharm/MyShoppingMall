package com.pinyougou.sellergoods.service;

import java.util.List;
import java.util.Map;

import com.pinyougou.pojo.TbBrand;

import entity.PageResult;
import entity.Result;

/**
 * @author Reolcharm
 *
 */
public interface BrandService {

	/**
	 * @return
	 * 查询所有品牌, 无分页
	 */
	List<TbBrand> findAll();
	
	/**
	 * 分页查询.封装结果集.
	 * @param pageNum 当前页码
	 * @param pageSize 每页显示条数
	 * @return
	 */
	PageResult findPage(Integer pageNum, Integer pageSize);
	
	/** 新增品牌功能
	 * @param tbBrand 
	 * @return 
	 */
	void add(TbBrand tbBrand);
	
	/** 编辑品牌时,进行数据回显功能
	 * @param id
	 * @return
	 */
	TbBrand findOne(Long id);
	
	/** 修改品牌功能
	 * @param tbBrand 
	 * @return 
	 */
	void update(TbBrand tbBrand);
	
	/**
	 * 删除品牌功能
	 * @param ids
	 */
	void delete(Long[] ids);
	
	/**
	 * 方法重载, 条件查询, 分页展示
	 * @param tbBrand
	 * @param page
	 * @param size
	 * @return
	 */
	PageResult findPage(TbBrand tbBrand, Integer page, Integer size);
	
	
	/**
	* 品牌下拉框数据
	*/
	List<Map> selectOptionList();
}
