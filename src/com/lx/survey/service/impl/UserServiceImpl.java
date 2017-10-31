package com.lx.survey.service.impl;

import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lx.survey.dao.BaseDao;
import com.lx.survey.model.User;
import com.lx.survey.model.security.Role;
import com.lx.survey.service.RoleService;
import com.lx.survey.service.UserService;
import com.lx.survey.util.ValidateUtil;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
 
	@Resource
	private RoleService roleService;
	
	@Resource(name="userDao")
	public void setDao(BaseDao<User> dao){
		super.setDao(dao);
	}
    
	/*
	 * 判断 email 时候被占用
	 */
	public boolean isRegisted(String email) {
		
		String hql = "from User u where u.email = ?";
		List<User> list = this.findEntityByHQL(hql, email);
		return ValidateUtil.isValid(list);
	}
    /**
     * 验证登陆信息
     */
	@Override
	public User validateLoginInfo(String email, String md5) {
		String hql  = "from User u where u.email =? and u.password =?";
		List<User> list = this.findEntityByHQL(hql, email,md5);
		return ValidateUtil.isValid(list) ? list.get(0) : null ;
	}
	/**
	 * 更新用户授权(只能更新角色设置)
	 */
	@Override
	public void updateAuthorize(User model, Integer[] ownRoleIds) {
		//查询新对象,不可以对原有对象操纵
		User newUser = this.getEntity(model.getId());
		if(!ValidateUtil.isValid(ownRoleIds)){
			newUser.getRoles().clear();
		}else{
			List<Role> roles = roleService.findRolesInRange(ownRoleIds);
			newUser.setRoles(new HashSet<Role>(roles));
		}
	}
    /**
     * 清除授权
     */
	@Override
	public void clearAuthorize(Integer userId) {
	   this.getEntity(userId).getRoles().clear();
	}
}
