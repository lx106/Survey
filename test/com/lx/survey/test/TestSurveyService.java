package com.lx.survey.test;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lx.survey.model.User;
import com.lx.survey.model.security.Right;
import com.lx.survey.service.RightService;
import com.lx.survey.service.SurveyService;

/**
 * 测试TestSurveyService
 * @author Administrator
 *
 */
public class TestSurveyService {

	private static RightService rs;
	
	@BeforeClass
	public static void initSurveyService(){
		ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
		rs = (RightService) ac.getBean("rightService");
	}
	/**
	 * 新建调查
	 */
//	@Test
//	public void newSurvey() {
//		User u = new User();
//		u.setId(3);
//		rs.newSurvey(u);
//	}
	@Test
	public void savaRight(){
		Right r = new Right();
		rs.saveEntity(r);
	}
	
}
