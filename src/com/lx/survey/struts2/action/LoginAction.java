package com.lx.survey.struts2.action;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lx.survey.model.User;
import com.lx.survey.service.RightService;
import com.lx.survey.service.UserService;
import com.lx.survey.util.DataUtil;

/*
 * 登陆action
 */
@Controller
@Scope("prototype")
public class LoginAction extends BaseAction<User> implements SessionAware{

	
	private static final long serialVersionUID = -3066988907019078808L;

	
	@Resource
	private UserService userService;
	
	@Resource
	private RightService rightService;
	
	// 接收 session的Map
	private Map<String,Object> sessionMap;
	
    /*
     * 到达登陆页面
     */
	public String toLoginPage(){
		return "loginPage";
	}
	
	/*
	 * 进行登陆处理 , 会先调用 validateDologin()方法
	 */
	public String doLogin(){
		return "success";
	}
	
	/**
	 * 校验登陆信息
	 */
	public void validateDoLogin(){
		
		User user = userService.validateLoginInfo(model.getEmail(),DataUtil.md5(model.getPassword()));
		if( user == null ){
			 addActionError("email/password错误");
		}else{
			 //初始化权限总和数组
			int maxPos = rightService.getMaxRightPos();
			user.setRightSum(new long[maxPos + 1]);
			//计算用户权限总和
			user.calculateRightSum();
			sessionMap.put("user", user);
		}
		System.out.println("-----");
	}

	// 注入sesion 的 map
	public void setSession(Map<String, Object> arg0) {
		this.sessionMap = arg0;
	}
	

}
