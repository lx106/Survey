package com.lx.survey.struts2.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lx.survey.model.Page;
import com.lx.survey.model.Survey;
import com.lx.survey.service.SurveyService;
/**
 * PageAction
 */
@Controller
@Scope("prototype")
public class PageAction extends BaseAction<Page>{

	private static final long serialVersionUID = 7662507752387519439L;

	private Integer sid;
	
	private Integer pid;
    
	//注入surveyService
	@Resource
	private SurveyService surveyService ;
	
	/**
	 *  到达添加page 的页面
	 */
	public String toAddPage() {
		return "toAddPagePage";
	}
	/**
	 * 保存/更新页面 
	 */
	public String saveOrUpdatePage(){
		//维护关联关系
		Survey s = new Survey();
		s.setId(sid);
		model.setSurvey(s);
		surveyService.saveOrUpdatePage(model);
		return "designSurveyAction";
	}
	/**
	 * 编辑页面 
	 */
	public String editPage() {
		this.model =surveyService.getPage(pid);
		return "editPagePage";
	}
	/**
	 *  删除页面
	 */
	public String deletePage() {
		surveyService.deletePage(pid); 
		return "designSurveyAction";
	}
	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}
	
	
}
