package com.lx.survey.service;

import java.util.List;

import com.lx.survey.model.Log;

public interface LogService extends BaseService<Log> {

	/**
	 * 通过表名创建日志表
	 */
	public void createLogTable(String tableName);
	
	/**
	 * 查询最近指定月份数的日志
	 */
	public List<Log> findNearestLogs(int i);
}
