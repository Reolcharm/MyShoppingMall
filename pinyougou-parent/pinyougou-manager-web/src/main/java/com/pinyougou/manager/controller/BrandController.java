package com.pinyougou.manager.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.sellergoods.service.BrandService;

import entity.PageResult;
import entity.Result;

@RestController
@RequestMapping("/brand")
public class BrandController {
	@Reference
	private BrandService brandService;

	/**
	 * 查询所有品牌
	 */
	@RequestMapping("/findAll")
	public List<TbBrand> findAll() {
		return brandService.findAll();
	}

	/**
	 * 分页查询所有品牌
	 * 
	 * @param page
	 *            当前页
	 * @param rows
	 *            每页显示条数
	 * @return 封装好的分页结果集
	 */
	@RequestMapping("/findPage")
	public PageResult findPage(Integer page, Integer rows) {

		return brandService.findPage(page, rows);
	}

	/**
	 * 新增品牌
	 * 
	 * @param tbBrand
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody TbBrand tbBrand) {

		if (tbBrand != null) {
			try {
				brandService.add(tbBrand);
				return new Result(true, "新增成功!");
			} catch (Exception e) {
				e.printStackTrace();
				return new Result(false, "新增失败!");
			}
		} else {
			return new Result(false, "新增失败!");
		}

	}

	/**
	 * 获取实体,进行数据回显
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/findOne")
	public TbBrand findOne(Long id) {
		return brandService.findOne(id);
	}

	/**
	 * 修改品牌
	 * 
	 * @param tbBrand
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody TbBrand tbBrand) {

		if (tbBrand != null) {
			try {
				brandService.update(tbBrand);
				return new Result(true, "修改成功!");
			} catch (Exception e) {
				e.printStackTrace();
				return new Result(false, "修改失败!");
			}
		} else {
			return new Result(false, "修改失败!");
		}

	}

	/**
	 * 批量删除功能
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Result delete(Long[] ids) {

		if (ids != null && ids.length > 0) {
			try {
				brandService.delete(ids);
				return new Result(true, "删除成功!");
			} catch (Exception e) {
				e.printStackTrace();
				return new Result(false, "删除失败!");
			}
		} else {
			return new Result(false, "删除失败!");
		}
	}

	/**
	 * 方法重载, 条件查询, 分页展示
	 * 
	 * @param tbBrand
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/findByExample")
	public PageResult findPage(@RequestBody TbBrand tbBrand, Integer page, Integer rows) {
		return brandService.findPage(tbBrand, page, rows);
	}

	@RequestMapping("/selectOptionList")
	public List<Map> selectOptionList() {
		return brandService.selectOptionList();
	}
}
