package com.pinyougou.pojogroup;
import java.io.Serializable;
import java.util.List;
import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.pojo.TbSpecificationOption;
/**
 * 规格组合实体类 ,封装页面传递的: 
 * entity={specification:{SpecName}, 
 * specificationOptionList:[{orders:xx,optionName:xx},{},{}]};
 * @author Administrator
 *
 */
public class Specification implements Serializable {	
	
	private static final long serialVersionUID = 1L;
	
	private TbSpecification specification;
	private List<TbSpecificationOption> specificationOptionList;
	
	
	/**
	 * 实体/表: TbSpecification
	 * @return
	 */
	public TbSpecification getSpecification() {
		return specification;
	}
	public void setSpecification(TbSpecification specification) {
		this.specification = specification;
	}
	/**
	 * 实体/表集合: TbSpecificationOption
	 * @return
	 */
	public List<TbSpecificationOption> getSpecificationOptionList() {
		return specificationOptionList;
	}
	public void setSpecificationOptionList(List<TbSpecificationOption> specificationOptionList) {
		this.specificationOptionList = specificationOptionList;
	}	
}