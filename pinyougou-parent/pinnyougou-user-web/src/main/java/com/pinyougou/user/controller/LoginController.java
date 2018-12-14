package com.pinyougou.user.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Reolcharm
 * 获取登陆名称.
 */
@RestController
@RequestMapping("/login")
public class LoginController {
	@RequestMapping("name")
	public Map<String, String> name() {
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		Map<String, String> map = new HashMap<>();
		map.put("loginName", name);
		return map;
	}
}