package com.lx.survey.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lx.survey.dao.BaseDao;
import com.lx.survey.model.security.Right;
import com.lx.survey.model.security.Role;
import com.lx.survey.service.RightService;
import com.lx.survey.service.RoleService;
import com.lx.survey.util.DataUtil;
import com.lx.survey.util.StringUtil;
import com.lx.survey.util.ValidateUtil;

@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService{

	
	@Resource
	private RightService rightService ;
	
	@Resource(name="roleDao")
	public void setDao(BaseDao<Role> dao) {
		super.setDao(dao);
	}
	
	@Override
	public void saveOrUpdateRole(Role model, Integer[] ownRightIds) {
		//没有给角色授予任何权限
		if(! ValidateUtil.isValid(ownRightIds)){
			model.getRights().clear();
		}else{
			List<Right> rights = rightService.findRightsInRange(ownRightIds);
			model.setRights(new HashSet<Right>(rights));
		}
		this.saveOrUpdateEntity(model);
	}
	/**
	 * 查询不在指定范围中的角色
	 */
	@Override
	public List<Role> findRolesNotInRange(Set<Role> roles) {
		if(!ValidateUtil.isValid(roles)){
			return this.findAllEntities();
		}else{
			String hql ="from Role r where r.id in (" +DataUtil.extractRightIds(roles) +")";
			return this.findEntityByHQL(hql);
		}
	}
	/**
     *  查询指定范围的权限
     */
	@Override
	public List<Role> findRolesInRange(Integer[] ids) {
		if(ValidateUtil.isValid(ids)){
			String hql ="from Role r where r.id in (" +StringUtil.arr2Str(ids) +")";
		    return this.findEntityByHQL(hql);
		}
		return null;
	}

}
