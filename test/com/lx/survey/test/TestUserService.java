package com.lx.survey.test;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lx.survey.model.User;
import com.lx.survey.service.UserService;

/*
 * 
 * 测试UserService
 */
public class TestUserService {

	private static UserService us ;
	
	@BeforeClass
	public static void initUserService(){
		ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
		us = (UserService) ac.getBean("userService");
	}
	/*
	 * 
	 * 插入用户
	 */
	@Test
	public void insertUser() {
		User u =  new User();
		u.setEmail("1062461248@qq.com");
		u.setPassword("123456");
		u.setName("liuxun");
		u.setNickName("坚哥");
		us.saveEntity(u);
	}
}
