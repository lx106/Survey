package com.lx.survey.struts2.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lx.survey.model.Page;
import com.lx.survey.model.Question;
import com.lx.survey.service.SurveyService;

@Controller
@Scope("prototype")
public class QuestionAction extends BaseAction<Question>{

	private static final long serialVersionUID = 5774852184656857579L;
	//调查id
	private Integer sid;
	//页面id
	private Integer pid;
	//问题id
	private Integer qid;

	@Resource
	private SurveyService surveyService;
	/**
	 *  添加问题页面 
	 */
	public String toSelectQuestionType() {
		return "selectQuestionTypePage";
	}
	/**
	 *  设计问题页面0~8
	 */
    public String toDesignQuestionPage(){
    	return model.getQuestionType()+"";
    }
    /**
     * 保存/更新问题
     */
    public String saveOrUpdateQuestion(){
    	//维护关联关系
    	Page p = new Page();
    	p.setId(pid);
    	model.setPage(p);
    	surveyService.saveOrUpdateQuestion(model);
    	return "designSurveyAction";
    }
    /**
     * 删除问题
     */
    public String deleteQuestion() {
    	surveyService.deleteQuestion(qid);
    	return "designSurveyAction";
    }
    /**
     * 编辑问题 
     */
    public String editQuestion() {
    	model = surveyService.getQuestion(qid);
    	return model.getQuestionType()+"";
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
	
	public Integer getQid() {
		return qid;
	}
	public void setQid(Integer qid) {
		this.qid = qid;
	}
    
}
