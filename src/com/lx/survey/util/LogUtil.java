package com.lx.survey.util;

import java.text.DecimalFormat;
import java.util.Calendar;

/**
 * 日志工具类
 * @author xun
 *
 */
public class LogUtil {

	/**
	 * 生成日志表名称：logs_2016_3
	 * offset:偏移量
	 */
	public static String generateLogTableName(int offset){
		Calendar c = Calendar.getInstance();
		//2016
		int year = c.get(Calendar.YEAR);
		//0- 11
		int month = c.get(Calendar.MONTH) + 1 + offset;
		
		if(month >12){
			year ++;
			month = month -12 ;
		}
		if(month <1){
			year--;
			month =month + 12;
		}
		DecimalFormat df = new DecimalFormat();
		df.applyPattern("00");
		return "logs_"+year+"_"+df.format(month);
	}
}
