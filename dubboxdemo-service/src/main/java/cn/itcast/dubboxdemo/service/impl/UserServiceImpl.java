/**
 * 
 */
package cn.itcast.dubboxdemo.service.impl;

import com.alibaba.dubbo.config.annotation.Service;

import cn.itcast.dubboxdemo.service.UserService;

/**
 * @author Reolcharm
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Hello Dobbox!";
	}

}
