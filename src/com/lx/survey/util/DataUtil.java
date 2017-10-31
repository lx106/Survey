package com.lx.survey.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Set;

import com.lx.survey.model.BaseEntity;
import com.lx.survey.model.security.Right;

public class DataUtil {

	public static String md5(String src){
		
		StringBuffer sb =null;
		try {
			char[] chars = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
			byte[] bytes = src.getBytes();
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] targ = md.digest(bytes);
			sb = new  StringBuffer();
			for (byte b : targ) {
				sb.append(chars[(b>>4) & 0X0f]);
				sb.append(chars[b & 0X0f]);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return sb.toString();
	}
	
	/**
	 *  深度复制，复制的整个对象图
	 */
	public static Serializable deeplyCopy(Serializable src){
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(src);
			baos.close();
			oos.close();
			
			byte[] bytes = baos.toByteArray();
			ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(bais);
			Serializable copy = (Serializable) ois.readObject();
			bais.close();
			ois.close();
			return copy;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static void main(String[] args) {
		System.out.println(DataUtil.md5("123456"));
	}
    /*
     * 抽取实体的id,形成字符串
     */
	public static String extractRightIds(Set<? extends BaseEntity> entities) {
		String temp ="" ;
		if(ValidateUtil.isValid(entities)){
			for (BaseEntity  e: entities) {
				temp += e.getId() +",";
			}
			return temp.substring(0,temp.length()-1);
		}
		return temp;
	}
	
	
	
	
	
	
	
	
	
	
	
}
