package com.lx.survey.test;



import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 测试数据源
 * @author Administrator
 *
 */
public class TestDataSource {

	@Test
    public void getConnection() throws SQLException{
    	ApplicationContext ac  = new ClassPathXmlApplicationContext("beans.xml");
    	DataSource ds = (DataSource) ac.getBean("dataSource");
    	if(null != ds){
    		System.out.println(ds.getConnection());
    	}else{
    		System.out.println("数据库连接出错");
    	}
    }
}
