package com.pinyougou.pojogroup;

import java.io.Serializable;
import java.util.List;

import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojo.TbGoodsDesc;
import com.pinyougou.pojo.TbItem;

public class Goods implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TbGoods goods;// 商品 SPU
	private TbGoodsDesc goodsDesc;// 商品扩展
	private List<TbItem> itemList;// 商品 SKU 列表

	/**
	 * @return the goods
	 */
	public TbGoods getGoods() {
		return goods;
	}

	/**
	 * @param goods
	 *            the goods to set
	 */
	public void setGoods(TbGoods goods) {
		this.goods = goods;
	}

	/**
	 * @return the goodsDesc
	 */
	public TbGoodsDesc getGoodsDesc() {
		return goodsDesc;
	}

	/**
	 * @param goodsDesc
	 *            the goodsDesc to set
	 */
	public void setGoodsDesc(TbGoodsDesc goodsDesc) {
		this.goodsDesc = goodsDesc;
	}

	/**
	 * @return the itemList
	 */
	public List<TbItem> getItemList() {
		return itemList;
	}

	/**
	 * @param itemList
	 *            the itemList to set
	 */
	public void setItemList(List<TbItem> itemList) {
		this.itemList = itemList;
	}

}
