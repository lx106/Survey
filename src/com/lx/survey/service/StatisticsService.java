package com.lx.survey.service;

import com.lx.survey.model.statistics.QuestionStatisticsModel;

/**
 * 统计服务
 * @author Administrator
 *
 */
public interface StatisticsService {

	public QuestionStatisticsModel statistics(Integer qid);
}
