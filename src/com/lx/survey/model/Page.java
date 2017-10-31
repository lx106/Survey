package com.lx.survey.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 页面类
 * @author Administrator
 *
 */
public class Page extends BaseEntity{
   
	private static final long serialVersionUID = 7817739017773726903L;

	private Integer id;
	
	private String title = "未命名";
	
	private String description;
	
	//建立 从Page 到 Survey 之间多对一关联关系
	private transient Survey survey;
	
	//建立从Page 到 Question 之间一对多的关联关系
	private Set<Question> questions = new HashSet<Question>();
	
	private float orderno; 

	public float getOrderno() {
		return orderno;
	}

	public void setOrderno(float orderno) {
		this.orderno = orderno;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
		if(id != null){
			this.orderno = id ;
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Survey getSurvey() {
		return survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	public Set<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
	}

	public Page(Integer id) {
		super();
		this.id = id;
	}
	public Page() {
	}
	
}
