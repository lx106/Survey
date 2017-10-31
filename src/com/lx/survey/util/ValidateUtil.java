package com.lx.survey.util;

import java.util.Collection;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import antlr.NameSpace;

import com.lx.survey.model.User;
import com.lx.survey.model.security.Right;
import com.lx.survey.struts2.UserAware;
import com.lx.survey.struts2.action.BaseAction;

public class ValidateUtil {

	/*
	 *判断 字符串有效性 
	 */
	public static boolean isValid(String str){
		if(str == null || "".equals(str)){
			return false;
		}
		return true;
	}
	@SuppressWarnings("rawtypes")
	public static boolean isValid(Collection col){
		if(col == null || col.isEmpty()){
			return false;
		}
		return true;
	}
	public static boolean isValid(Object[] values) {
		if(values == null || values.length ==0){
			return false;
		}
		return true;
	}
	/**
	 * 判断是否有权限
	 */
	public static boolean hasRight(String ns, String actionName,
			HttpServletRequest request, BaseAction action) {
		if(!ValidateUtil.isValid(ns) || "/".equals(ns)){
			ns = "";
		}
		//将超链接的参数部分滤掉 
		if(actionName.contains("?")){
			actionName = actionName.substring(0, actionName.indexOf("?"));
		}
		String url = ns +"/" +actionName;
		HttpSession session = request.getSession();
		
		ServletContext sc = session.getServletContext();
		
		Map<String,Right> map = (Map<String, Right>) sc.getAttribute("all_rights_map");
		
		Right r = map.get(url);
		
		//公共资源？
		if(r == null || r.isCommon()){
			return true;
		}else{
			User user = (User) session.getAttribute("user");
			//登陆？
			if(user == null){
				return false;
			}else{
				//userAware处理
				if(action != null && action instanceof UserAware){
					((UserAware)action).setUser(user);
				}
				//超级管理员
				if(user.isSuperAdmin()){
					return true;
				}else{
					//有权限？
					if(user.hasRight(r)){
						return true;
					}else{
						return false;
					}
					
				}
			}
			
		}
	}
}
