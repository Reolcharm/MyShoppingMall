package com.pinyougou.manager.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.sellergoods.service.BrandService;

import entity.PageResult;

@RestController
@RequestMapping("/brand")
public class BrandController {
	@Reference
	private BrandService brandService;
//	/**
//	 *查询所有品牌 */
//	@RequestMapping("/findAll")
//	public List<TbBrand> findAll() {
//		return brandService.findAll();
//	}
	
	/**
	 *分页查询所有品牌
	 * @param page 当前页
	 * @param rows 每页显示条数
	 * @return 封装好的分页结果集	格式: [total:,{}]
	 */
	@RequestMapping("/findPage")
	public PageResult findPage(Integer page, Integer rows) {
		
		return brandService.findPage(page, rows);
	}
	
}
