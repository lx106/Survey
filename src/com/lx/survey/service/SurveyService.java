package com.lx.survey.service;

import java.util.List;





















import com.lx.survey.model.Answer;
import com.lx.survey.model.Page;
import com.lx.survey.model.Question;
import com.lx.survey.model.Survey;
import com.lx.survey.model.User;

public interface SurveyService {

	/**
	 * 查询调查列表
	 */
	public List<Survey> findMySurveys(User user);
	
	/**
	 * 新建调查
	 */
	public Survey newSurvey(User user);
	/**
	 * 按照 id 查询 Survey
	 */
	public Survey getSurvey(Integer sid) ;
	/**
	 * 按照 id 查询 Survey 同时 查询 它的所有 集合属性
	 */
	public Survey getSurveyWithChildren(Integer sid);
    /**
     * 更新调查
     */
	public void updateSurvey(Survey model);
    /**
     * 保存、更新调查
     */
	public void saveOrUpdatePage(Page model);
    /**
     * 获取指定页面
     */
	public Page getPage(Integer pid);
    
	/**
	 * 保存/ 更新问题
	 */
	public void saveOrUpdateQuestion(Question model);
    /**
     * 删除问题
     */
	public void deleteQuestion(Integer qid);
    /**
     * 删除页面
     */
	public void deletePage(Integer pid);
    /**
     * 删除调查 
     */
	public void deleteSurvey(Integer sid);
    
	public Question getQuestion(Integer qid);
    
	/**
	 * 打开/关闭 调查 
	 */
	public void toggleStatus(Integer sid);
     /**
      * 增加logo
      */
	public void updateLogoPhotoPath(Integer sid, String string);
	
	public List<Survey> getSurveyWithPages(User user);
    /**
     * 移动或复制页
     */
	public void moveOrCopyPage(Integer srcPid, Integer targPid, int pos);
    
	public List<Survey> findAllAvailableSurveys();
    
	public Page getFirstPage(Integer sid);
    
	public Page getPrePage(Integer currPid);

	public Page getNextPage(Integer currPid);
    
	 /**
     * 批量保存答案
     */
	public void saveAnswers(List<Answer> processAnswers);
    /**
     * 查询指定调查的所有问题
     */
	public List<Question> getQuestions(Integer sid);
	/**
     * 查询指定问题的所有答案
     */
	public List<Answer> getAnswers(Integer sid);
    /**
     * 清除答案
     */
	void clearAnswers(Integer sid);
}
