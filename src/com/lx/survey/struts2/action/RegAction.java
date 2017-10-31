package com.lx.survey.struts2.action;

import javax.annotation.Resource;


import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lx.survey.model.User;
import com.lx.survey.service.UserService;
import com.lx.survey.util.DataUtil;
import com.lx.survey.util.ValidateUtil;
/*
 *  用户注册action 
 */
@Controller
@Scope("prototype")
public class RegAction extends BaseAction<User>{

	private static final long serialVersionUID = -1873446411219004745L;

	private String confirmPassword;
	
	//注入userService
	@Resource
	private UserService userService;
	
	/*
	 *  到达注册页面
	 */
	@SkipValidation
	public String toRegPage(){
		return "regPage";
	}
	
	public String doReg(){
		//密码加密
		model.setPassword(DataUtil.md5(model.getPassword()));
		userService.saveEntity(model);
		return SUCCESS;
	}
	
	/*
	 * 校验
	 */
	public void validate() {
		// 非空
		if(!ValidateUtil.isValid(model.getEmail())){
			addFieldError("email", "email是必填项");
		}
		if(!ValidateUtil.isValid(model.getPassword())){
			addFieldError("password", "password是必填项");
		}
		if(!ValidateUtil.isValid(model.getNickName())){
			addFieldError("nickName", "nickName是必填项");
		}
		if(hasErrors()){
			return ;
		}
		//密码一致性校验
		if(!model.getPassword().equals(confirmPassword)){
			addFieldError("password", "密码不一致");
		}
		// email 是否占用
		if(userService.isRegisted(model.getEmail())){
			addFieldError("email", "email 已占用");
		}
	}
	
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

}
