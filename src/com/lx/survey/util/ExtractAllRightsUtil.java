package com.lx.survey.util;

import java.io.File;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URISyntaxException;
import java.net.URL;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lx.survey.service.RightService;

/**
 * 提取所有权限工具类
 */
public class ExtractAllRightsUtil {

	public static void main(String[] args) throws URISyntaxException {
		ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
		RightService rs = (RightService) ac.getBean("rightService");
		
		ClassLoader loader = ExtractAllRightsUtil.class.getClassLoader();
		URL url = loader.getResource("com/lx/survey/struts2/action");
		File dir = new File(url.toURI());
		File[] files = dir.listFiles();
		String fname = "";
		for (File f : files) {
			fname = f.getName();
			if(fname.endsWith(".class")&& !fname.endsWith("BaseAction.class")){
				processAction(fname,rs);
			}
		}
	}
    /**
     * 处理action 类 捕获 所有url 地址，形成权限 
     */
	@SuppressWarnings("rawtypes")
	private static void processAction(String fname, RightService rs) {
		try {
			String pkgName = "com.lx.survey.struts2.action" ;
			String simpleClassName = fname.substring(0,fname.indexOf(".class"));
			String className = pkgName + "." +simpleClassName;
			//得到具体类
			Class clazz = Class.forName(className);
			Method[] methods = clazz.getDeclaredMethods();
			Class retType = null ;
			String mname = null ;
			Class[] paramType = null ;
			String url = null ;
			for (Method m : methods) {
				retType = m.getReturnType();//返回值类型
				mname = m.getName();
				paramType = m.getParameterTypes();//参数类型
				if(retType == String.class && !ValidateUtil.isValid(paramType) && Modifier.isPublic(m.getModifiers())){
					if(mname.equals("execute")){
						url = "/" +simpleClassName ;
					}else{
						url = "/" +simpleClassName +"_"+ mname;
					}
					rs.appendRightByURL(url);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
