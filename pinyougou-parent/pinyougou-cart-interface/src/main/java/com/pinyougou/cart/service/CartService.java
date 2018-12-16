package com.pinyougou.cart.service;

import java.util.List;

import com.pinyougou.pojogroup.Cart;

public interface CartService {

	/**
	 * @param cartList
	 *            从 cookie 或者 redis 中查到的购物车列表.
	 * @param itemId
	 *            商品的 SKU 编号
	 * @param num
	 *            购买的商品数量
	 * @return List<Cart> 添加成功后将购物车列表返回.
	 * @description 添加商品到购物车.
	 */
	public List<Cart> addGoodsToCartList(List<Cart> cartList, Long itemId, Integer num);
}
