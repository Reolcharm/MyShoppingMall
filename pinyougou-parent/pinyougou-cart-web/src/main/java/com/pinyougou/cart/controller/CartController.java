package com.pinyougou.cart.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpRequest;
import org.apache.http.protocol.HttpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.pinyougou.cart.service.CartService;
import com.pinyougou.pojogroup.Cart;

import entity.Result;
import util.CookieUtil;

@RestController
@RequestMapping("/cart")
public class CartController {
	@Reference
	private CartService cartService;
	@Autowired
	private HttpServletRequest request;

	@Autowired
	private HttpServletResponse response;

	@RequestMapping("/findCartList")
	public List<Cart> findCartList() {
		String cartListStr = CookieUtil.getCookieValue(request, "cartList", "UTF-8");
		if (cartListStr == null || cartListStr.length() == 0) {
			cartListStr = "[]";
		}
		List<Cart> cartList_cookie = JSON.parseArray(cartListStr, Cart.class);
		return cartList_cookie;
	}

	@RequestMapping("/addGoodsToCartList")
	public Result addGoodsToCartList(Long itemId, Integer num) {
		try {
			// 读取 cookie 中的购物车列表
			List<Cart> findCartList = findCartList();
			// 将读取到的 cartList 进行保存操作
			List<Cart> addGoodsToCartList = cartService.addGoodsToCartList(findCartList, itemId, num);
			// 将保存后的 cartList 重新设置到 cookie 中
			CookieUtil.setCookie(request, response, "cartList", JSON.toJSONString(addGoodsToCartList), 3600 * 24,
					"UTF-8");
			return new Result(true, "添加成功");
		} catch (Exception e) {
			return new Result(false, "请重试");
		}
	}
}
