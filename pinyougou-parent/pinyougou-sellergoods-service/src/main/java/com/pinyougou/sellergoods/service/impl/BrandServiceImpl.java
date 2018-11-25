package com.pinyougou.sellergoods.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbBrandMapper;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.pojo.TbBrandExample;
import com.pinyougou.pojo.TbBrandExample.Criteria;
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

	/* (non-Javadoc)
	 * @see com.pinyougou.sellergoods.service.BrandService#findPage(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public PageResult findPage(Integer pageNum, Integer pageSize) {
		// 开启分页
		PageHelper.startPage(pageNum, pageSize);
		// 查询所有品牌, 封装到Page这个 arrayList 中
		Page<TbBrand> page = (Page<TbBrand>) tbBrandMapper.selectByExample(null);
		// 返回分页好的结果.
		return new PageResult(page.getTotal(), page.getResult());
	}

	/* (non-Javadoc)
	 * @see com.pinyougou.sellergoods.service.BrandService#add(com.pinyougou.pojo.TbBrand)
	 */
	@Override
	public void add(TbBrand tbBrand) {
		tbBrandMapper.insert(tbBrand);
	}

	/* (non-Javadoc)
	 * @see com.pinyougou.sellergoods.service.BrandService#findOne(java.lang.Long)
	 */
	@Override
	public TbBrand findOne(Long id) {
		return tbBrandMapper.selectByPrimaryKey(id);
	}

	/* (non-Javadoc)
	 * @see com.pinyougou.sellergoods.service.BrandService#save(com.pinyougou.pojo.TbBrand)
	 */
	@Override
	public void update(TbBrand tbBrand) {
//		 			update tb_brand 
//					set name = #{name,jdbcType=VARCHAR},
//	    	      	first_char = #{firstChar,jdbcType=VARCHAR}
//	    	   		where id = #{id,jdbcType=BIGINT}
		tbBrandMapper.updateByPrimaryKey(tbBrand);
	}

	/* (non-Javadoc)
	 * @see com.pinyougou.sellergoods.service.BrandService#delete(java.lang.Long[])
	 */
	@Override
	public void delete(Long[] ids) {
		for (Long id: ids) {
			tbBrandMapper.deleteByPrimaryKey(id);
		}
	}

	
	/* (non-Javadoc)
	 * @see com.pinyougou.sellergoods.service.BrandService#findPage(com.pinyougou.pojo.TbBrand, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public PageResult findPage(TbBrand tbBrand, Integer page, Integer size) {
		PageHelper.startPage(page, size);
		
		TbBrandExample example = new TbBrandExample();
//      添加条件.
		Criteria criteria =example.createCriteria();
		
		if (tbBrand!=null) {
//			字符串非空判断
			if(tbBrand.getName() != null && tbBrand.getName().length() > 0) {
				criteria.andNameLike("%" + tbBrand.getName() + "%")	;
			}
			if(tbBrand.getFirstChar() != null && tbBrand.getFirstChar().length() > 0) {
//				SELECT * FROM tb_brand WHERE first_char = 'P';
				criteria.andFirstCharEqualTo(tbBrand.getFirstChar())	;
			}	
		}
//		分页,封装结果集
		Page<TbBrand> pages = (Page<TbBrand>) tbBrandMapper.selectByExample(example);
		return new PageResult(pages.getTotal(),pages.getResult());
	}

	
	
	

}
