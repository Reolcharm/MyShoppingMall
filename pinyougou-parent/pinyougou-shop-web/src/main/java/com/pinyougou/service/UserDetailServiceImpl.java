package com.pinyougou.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.pinyougou.pojo.TbSeller;
import com.pinyougou.sellergoods.service.SellerService;

/**
 * @author Reolcharm
 * spring security 的验证实现类
 */
public class UserDetailServiceImpl implements UserDetailsService {
	// 本地接口, set 方式, 远程配置文件注入
	private SellerService sellerService;

	public void setSellerService(SellerService sellerService) {
		this.sellerService = sellerService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 构建角色列表
		List<GrantedAuthority> authenLists = new ArrayList<>();
		authenLists.add(new SimpleGrantedAuthority("ROLE_SELLER"));
		// 根据用户名查询到具体 商家实体.
		TbSeller seller = sellerService.findOne(username);
		if (seller != null) {
			// 用户名正确, 且为审核通过状态.
			if (seller.getStatus().equals("1")) {
				// 返回一个正确的 user 对象, 框架帮我们验证
				return new User(username, seller.getPassword(), authenLists);
			} else {
				return null;
			}
		} else {
			return null;
		}

	}

}
