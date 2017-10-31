package com.lx.survey.struts2.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lx.survey.model.Page;
import com.lx.survey.model.Survey;
import com.lx.survey.model.User;
import com.lx.survey.service.SurveyService;
import com.lx.survey.struts2.UserAware;
@Controller
@Scope("prototype")
public class MoveOrCopyPageAction extends BaseAction<Page> implements UserAware{

	
	private static final long serialVersionUID = -8295998651740640789L;

	private List<Survey> mySurveys ;
	
	@Resource
	private SurveyService surveyService;

	private User user;
	
	private Integer srcPid;
	
	private Integer targPid;
	
	private Integer sid;
	
	private int pos;
	
	public Integer getSrcPid() {
		return srcPid;
	}

	public void setSrcPid(Integer srcPid) {
		this.srcPid = srcPid;
	}

	public Integer getTargPid() {
		return targPid;
	}

	public void setTargPid(Integer targPid) {
		this.targPid = targPid;
	}

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	public List<Survey> getMySurveys() {
		return mySurveys;
	}

	public void setMySurveys(List<Survey> mySurveys) {
		this.mySurveys = mySurveys;
	}
	
	@Override
	public void setUser(User user) {
		this.user = user;
	}
    /**
     * 跳转到移动复制页面 
     */
	public String toSelectTargetPage(){
		this.mySurveys = surveyService.getSurveyWithPages(user);
		return "moveOrCopyPageListPage";
	}
	public String doMoveOrCopyPage() {
		surveyService.moveOrCopyPage(srcPid,targPid ,pos);
		return "designSurveyAction";
	}
	

	
}
