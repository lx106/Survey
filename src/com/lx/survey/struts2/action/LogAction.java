package com.lx.survey.struts2.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lx.survey.model.Log;
import com.lx.survey.service.LogService;
import com.lx.survey.util.LogUtil;

@Controller
@Scope("prototype")
public class LogAction extends BaseAction<Log> {

	private static final long serialVersionUID = 6482075950878779066L;

	private List<Log> logs ;
	
	//默认查询日志的月份数
	private Integer monthNum =2;
	
	@Resource
	private LogService logService;
	
	public String findLogs(){
		this.logs = logService.findAllEntities();
		return "logListPage";
	}
	/**
	 * 查询全部日志
	 */
	public String findAllLogs(){
		this.logs = logService.findAllEntities();
		return "logListPage" ;
	}
	/**
	 * 查询最近的日志 
	 */
	public String findNearestLogs(){
		this.logs = logService.findNearestLogs(monthNum);
        return "logListPage";		
	}
	
	public Integer getMonthNum() {
		return monthNum;
	}
	public void setMonthNum(Integer monthNum) {
		this.monthNum = monthNum;
	}
	public List<Log> getLogs() {
		return logs;
	}
	public void setLogs(List<Log> l) {
		logs = l;
	}
	
	
	
}
