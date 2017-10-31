package com.lx.survey.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 自定义数据源路由器（分布式数据库）
 * @author xun
 *
 */
public class SurveyDataSourceRouter extends AbstractRoutingDataSource {

	/**
	 * 检测当前key
	 */
	protected Object determineCurrentLookupKey() {
		//得到当前线程绑定的令牌
		SurveyparkToken token = SurveyparkToken.getCurrentToken();
		if(token != null){
			Integer id = token.getSurvey().getId();
			//解除令牌的绑定
			SurveyparkToken.unbindToken();
			return ((id % 2) == 0) ? "even" : "odd" ;
		}
		return null;
	}
}
