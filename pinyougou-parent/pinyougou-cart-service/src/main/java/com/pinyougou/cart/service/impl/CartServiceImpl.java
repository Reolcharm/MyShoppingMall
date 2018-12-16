package com.pinyougou.cart.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.cart.service.CartService;
import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.mapper.TbOrderItemMapper;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojo.TbOrderItem;
import com.pinyougou.pojo.TbOrderItemExample;
import com.pinyougou.pojo.TbOrderItemExample.Criteria;
import com.pinyougou.pojogroup.Cart;

@Service
@Transactional
public class CartServiceImpl implements CartService {

	@Autowired
	private TbOrderItemMapper tbOrderItemMapper;
	@Autowired
	private TbItemMapper tbItemMapper;

	// 1.根据商品SKU ID查询SKU商品信息
	// 2.获取商家ID
	// 3.根据商家ID判断购物车列表中是否存在该商家的购物车
	// 4.如果购物车列表中不存在该商家的购物车
	// 4.1 新建购物车对象
	// 4.2 将新建的购物车对象添加到购物车列表
	// 5.如果购物车列表中存在该商家的购物车
	// 查询购物车明细列表中是否存在该商品
	// 5.1. 如果没有，新增购物车明细
	// 5.2. 如果有，在原购物车明细上添加数量，更改金额
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
	@Override
	public List<Cart> addGoodsToCartList(List<Cart> cartList, Long itemId, Integer num) {
		// 商品详细信息
		TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemId);
		// 保证健壮性
		if (tbItem == null) {
			throw new RuntimeException("商品不存在");
		}
		if (!"1".equals(tbItem.getStatus())) {
			throw new RuntimeException("商品状态无效");
		}
		String sellerId = tbItem.getSellerId();
		// 根据商家 id 获取到购物车对象
		Cart cart = searchCartBySellerId(cartList, sellerId);

		if (cart == null) {
			// 没有该商家的 Cart
			cart = new Cart();
			// 添加该商品的商家 id 和 店铺名称
			cart.setSellerId(sellerId);
			cart.setSellerName(tbItem.getSeller());
			// 添加 订单明细列表
			List<TbOrderItem> orderItems = new ArrayList<>();
			TbOrderItem orderItemCreated = createTbOrderItem(tbItem, num);
			orderItems.add(orderItemCreated);
			cart.setOrderItemList(orderItems);
			cartList.add(cart);
		} else {
			// 5.如果购物车列表中存在该商家的购物车
			// 查询购物车明细列表中是否存在该商品
			TbOrderItem orderItem = searchOrderListByItemId(cart.getOrderItemList(), itemId);

			if (orderItem == null) {
				// 5.1. 如果没有，新增购物车明细, 并添加到该 sellerId 的购物车列表中.
				orderItem = createTbOrderItem(tbItem, num);
				cart.getOrderItemList().add(orderItem);
			} else {
				// 5.2. 如果有，在原购物车明细上添加数量，更改金额
				Integer orderItemNum = orderItem.getNum();
				orderItem.setNum(orderItemNum + num);
				orderItem.setTotalFee(new BigDecimal(orderItem.getPrice().doubleValue() * orderItemNum));
				// // 保证健壮性
				// 如果数量更改后 <= 0, 则将 该 orderItem 从 orderItems 中移除
				if (orderItemNum <= 0) {
					cart.getOrderItemList().remove(orderItem);
				}
				// 如果移除后 cart 的明细数量为0，则将cart移除
				if (cart.getOrderItemList().size() == 0) {
					cartList.remove(cart);
				}
			}
		}
		return cartList;
	}

	/**
	 * @param orderItemList
	 *            当前 sellerId 下 Cart 的 orderItemList
	 * @param itemId
	 *            页面传递而来的商品id.
	 * @return 判断该sellerId 的 cart 中的 orderItemList 是否有该商品, 有的话返回 订单明细, 以便进行数量和价格的增减
	 */
	private TbOrderItem searchOrderListByItemId(List<TbOrderItem> orderItemList, Long itemId) {
		for (TbOrderItem tbOrderItem : orderItemList) {
			if (tbOrderItem.getItemId() == itemId) {
				return tbOrderItem;
			}
		}
		return null;
	}

	/**
	 * @param tbItem
	 *            根据商品SKU ID查询SKU商品信息
	 * @param num
	 *            商品数量
	 * @return 将商品详情添加到订单详情中
	 */
	private TbOrderItem createTbOrderItem(TbItem tbItem, Integer num) {
		if (num <= 0) {
			throw new RuntimeException("数量非法");
		}
		TbOrderItem orderItem = new TbOrderItem();
		orderItem.setGoodsId(tbItem.getGoodsId());
		// 商品 item (sku_id)
		orderItem.setItemId(tbItem.getId());
		orderItem.setNum(num);
		orderItem.setPicPath(tbItem.getImage());
		orderItem.setPrice(tbItem.getPrice());
		orderItem.setSellerId(tbItem.getSellerId());
		orderItem.setTitle(tbItem.getTitle());
		orderItem.setTotalFee(new BigDecimal(tbItem.getPrice().doubleValue() * num));
		return orderItem;
	}

	/**
	 * @param cartList
	 *            从 cookie 或者 redis 中查到的购物车列表.
	 * @param sellerId
	 *            1.根据商品SKU ID查询SKU商品信息 2.获取到的商家ID
	 * @return Cart 根据商家ID 查询购物车对象 3.根据商家ID判断购物车列表中是否存在该商家的购物车,
	 * 
	 */
	private Cart searchCartBySellerId(List<Cart> cartList, String sellerId) {
		for (Cart cart : cartList) {
			if (cart.getSellerId().equals(sellerId)) {
				return cart;
			}
		}
		return null;
	}
}
