package cn.itcast.dubboxdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;

import cn.itcast.dubboxdemo.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Reference
	private UserService userService;
			
	@RequestMapping("/showName.do")
	@ResponseBody
	public String getName() {
		return userService.getName();
	}
	
}
