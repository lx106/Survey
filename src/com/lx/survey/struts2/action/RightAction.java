package com.lx.survey.struts2.action;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lx.survey.model.security.Right;
import com.lx.survey.service.RightService;
@Controller
@Scope("prototype")
public class RightAction extends BaseAction<Right>{

	private static final long serialVersionUID = -1622543925783746679L;
	
	private List<Right> allRights;
	
	private Integer rightId;
	@Resource
	private RightService rightService;
	
	/**
	 * 添加权限
	 */
	public String toAddRightPage(){
		return "addRightPage";
	}
	/**
	 * 查询所有权限
	 */
	public String findAllRights(){
		this.allRights = rightService.findAllEntities();
		return "rightListPage";
	}
	/**
	 * 保存/更新权限 
	 */
	public String saveOrUpdateRight(){
		rightService.saveOrUpdateRight(model);
		return "findAllRightAction";
	}
	/**
	 * 编辑权限 
	 */
	public String editRight(){
		this.model = rightService.getEntity(rightId);
		return "editRightPage";
	}
	/**
	 * 删除权限 
	 */
	public String deleteRight(){
		Right r = new Right();
		r.setId(rightId);
		rightService.deleteEntity(r);
		return "findAllRightAction";
	}
	/**
	 * 批量修改权限 
	 */
	public String batchUpdateRights(){
		rightService.batchUpdateRights(allRights);
		return "findAllRightAction";
	}
	

	public Integer getRightId() {
		return rightId;
	}
	public void setRightId(Integer rightId) {
		this.rightId = rightId;
	}
	public List<Right> getAllRights() {
		return allRights;
	}

	public void setAllRights(List<Right> allRights) {
		this.allRights = allRights;
	}
	

}
