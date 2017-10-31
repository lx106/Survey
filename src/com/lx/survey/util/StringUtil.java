package com.lx.survey.util;

public class StringUtil {
	/**
	 * 将字符串转换成数组，按照tag分割  
	 */
	public static String[] str2Arr(String str,String tag){
		if(ValidateUtil.isValid(str)){
			return str.split(tag);
		}
		return null ;
	}
    /**
     * 判断在values数组中是否含有指定value字符串
     */
	public static boolean contains(String[] values, String value) {
		if(ValidateUtil.isValid(values)){
			for (String string : values) {
				if(string.equals(value)){
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * 将数组变换成字符串，使用"，"号分割
	 */
	public static String arr2Str(Object[] arr){
		String temp="";
		if(ValidateUtil.isValid(arr)){
			for (Object string : arr) {
				temp+=string+",";
			}
			return temp.substring(0,temp.length()-1);
		}
		return temp;
	}
	//获取字符串的描述信息
	public static String getDescString(String str){
		if(str != null && str.trim().length() > 30){
			return  str.substring(0,30);
		}
		return str;
	}
}
