package com.lx.survey.scheduler;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.lx.survey.service.LogService;
import com.lx.survey.util.LogUtil;

/**
 * 创建日志表的石英任务 
 */
public class CreateLogTablesTask extends QuartzJobBean{

	private LogService logService;
	
	//注入LogService
	public void setLogService(LogService logService){
		this.logService = logService;
	}
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		//下个月
		String tableName = LogUtil.generateLogTableName(1);
		logService.createLogTable(tableName);
		
		//下二月
		 tableName = LogUtil.generateLogTableName(2);
		logService.createLogTable(tableName);
		
		//下三月
		 tableName = LogUtil.generateLogTableName(3);
		logService.createLogTable(tableName);
		
	}

}
