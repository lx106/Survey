package com.lx.survey.test;

import java.sql.SQLException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lx.survey.service.StatisticsService;

/**
 * 测试StatisticsService
 * @author Administrator
 *
 */
public class TestStatisticsService {

	private static StatisticsService ss;
	
	@BeforeClass
	public static void iniSurveyService(){
		ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
		ss = (StatisticsService) ac.getBean("statisticsService");
	}
	@Test
	public void statistics() throws SQLException{
		ss.statistics(11);
	}
}
