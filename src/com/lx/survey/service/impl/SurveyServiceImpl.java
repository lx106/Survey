package com.lx.survey.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lx.survey.dao.BaseDao;
import com.lx.survey.model.Answer;
import com.lx.survey.model.Page;
import com.lx.survey.model.Question;
import com.lx.survey.model.Survey;
import com.lx.survey.model.User;
import com.lx.survey.service.SurveyService;
import com.lx.survey.util.DataUtil;
@Service("surveyService")
public class SurveyServiceImpl implements SurveyService{

	@Resource(name="surveyDao")
	private BaseDao<Survey> surveyDao;
	
	@Resource(name="pageDao")
	private BaseDao<Page> pageDao;
	
	@Resource(name="questionDao")
	private BaseDao<Question> questionDao;
	
	@Resource(name="answerDao")
	private BaseDao<Answer> answerDao;
	/**
	 * 查询调查集合
	 */
	@Override
	public List<Survey> findMySurveys(User user) {
		String hql ="from Survey s where s.user.id = ?";
		return surveyDao.findEntityByHQL(hql, user.getId());
	}

	/**
	 * 新建调查
	 */
	@Override
	public Survey newSurvey(User user) {
		Survey s = new Survey();
		Page p = new Page();
		//设置关联关系
		s.setUser(user);
		p.setSurvey(s);
		s.getPages().add(p);
		
		surveyDao.saveEntity(s);
		pageDao.saveEntity(p);
		return s;
	}
    
	@Override
	public Survey getSurveyWithChildren(Integer sid) {
		Survey s = this.getSurvey(sid);
		//强行 初始化 pages 和 questions 集合
		for (Page p : s.getPages()) {
			p.getQuestions().size();
		}
		return s;
	}
    /**
     *  根据id 查询 Survey
     */
	@Override
	public Survey getSurvey(Integer sid) {
		return surveyDao.getEntity(sid);
	}

	@Override
	public void updateSurvey(Survey model) {
		 surveyDao.updateEntity(model);
	}

	@Override
	public void saveOrUpdatePage(Page mode) {
		pageDao.saveOrUpdateEntity(mode);
	}

	@Override
	public Page getPage(Integer pid) {
		
		return pageDao.getEntity(pid);
	}
    /**
     * 保存/更新问题
     */
	@Override
	public void saveOrUpdateQuestion(Question model) {
		questionDao.saveOrUpdateEntity(model); 
	}
    /**
     * 删除问题
     */
	@Override
	public void deleteQuestion(Integer qid) {
		 //1.删除答案
         String hql = "delete from Answer a where a.questionId = ?";
        
		 answerDao.batchEntityByHQL(hql,qid);
		
        // 2.删除问题
         hql = "delete from Question q where q.id = ?";
        questionDao.batchEntityByHQL(hql, qid);
       
	}
    // 删除页面
	@Override
	public void deletePage(Integer pid) {
		String hql ="delete from Page p where p.id = ?";
		pageDao.batchEntityByHQL(hql, pid);
	}
    //删除调查
	@Override
	public void deleteSurvey(Integer sid) {
		//1.删除答案
		String hql = "delete from Answer a where a.surveyId = ?";
		answerDao.batchEntityByHQL(hql, sid);
		//2.删除问题
		// hql ="delete from question q where q.page.survey.id = ?";
		hql = "delete from Question q where q.page.id in (select p.id from Page p where p.survey.id = ?)";
		questionDao.batchEntityByHQL(hql, sid);
		//3.删除页面
		hql ="delete from Page p where p.survey.id =?";
		pageDao.batchEntityByHQL(hql, sid);
		//4.删除调查
		hql = "delete from Survey s where s.id =?";
		surveyDao.batchEntityByHQL(hql, sid);
	}

	@Override
	public Question getQuestion(Integer qid) {
		
		return questionDao.getEntity(qid);
	}
    /**
     * 清除调查
     */
	@Override
	public void clearAnswers(Integer sid) {
		String hql = "delete from Answer a where a.surveyId = ?";
		surveyDao.batchEntityByHQL(hql, sid);
	}
	/**
	 * 打开/关闭 调查 
	 */
	@Override
	public void toggleStatus(Integer sid) {
		Survey s = surveyDao.getEntity(sid);
		// hql = "update Survey s set s.closed = !s.closed where s.id =?; 不能使用
        String hql = "update Survey s set s.closed =? where s.id =?";
        surveyDao.batchEntityByHQL(hql, !s.isClosed(),sid);
	}

	@Override
	public void updateLogoPhotoPath(Integer sid, String string) {
		String hql = "update Survey s set s.logoPhotoPath = ? where s.id =?";
		surveyDao.batchEntityByHQL(hql, string, sid);
	}
    /**
     *  查询调查集合 ,携带pages 
     */
	public List<Survey> getSurveyWithPages(User user) {
		String hql = "from Survey s where s.user.id = ?";
		List<Survey> list = surveyDao.findEntityByHQL(hql, user.getId());
		for (Survey survey : list) {
			survey.getPages().size();
		}
		return list;
	}
    /**
     * 进行页面移动/复制
     */
	@Override
	public void moveOrCopyPage(Integer srcPid, Integer targPid, int pos) {
		Page srcPage = this.getPage(srcPid);
		Survey srcSurvey = srcPage.getSurvey();
		Page targPage = this.getPage(targPid);
		Survey targSurvey = targPage.getSurvey();
		// 判断移动还是复制
		//移动
		if(srcSurvey.getId() == targSurvey.getId()){
			setOrderno(srcPage,targPage,pos);
		}
		//复制
		else{
			// 强行初始化问题集合，否则深度复制的页面对象没有问题集合
			srcPage.getQuestions().size();
			//深度复制
			Page copyPage = (Page) DataUtil.deeplyCopy(srcPage);
			//设置页面和目标调查关联
			copyPage.setSurvey(targSurvey);
			//保存页面
			pageDao.saveEntity(copyPage);
			for (Question q : copyPage.getQuestions()) {
				questionDao.saveEntity(q);
			}
			setOrderno(srcPage,targPage,pos);
		}
	}
    //设置叶序
	public void setOrderno(Page srcPage,Page targPage,int pos) {
		//判断位置 之前0 之后1
		//之前
		if( pos == 0 ){
			//是否为首项
			if(isFirstPage(targPage)){
				srcPage.setOrderno(targPage.getOrderno() - 0.01f );
			}else{
				//取得目标页的前一页
				Page prePage = getPrePage(targPage);
				srcPage.setOrderno((targPage.getOrderno() + prePage.getOrderno()) / 2);
			}
		}
		//之后
		else{
			//是否是尾页
			if(isLastPage(targPage)){
				srcPage.setOrderno(targPage.getOrderno() + 0.01f );
			}else{
				//取得目标页的前一页
				Page nextPage = getNextPage(targPage);
				srcPage.setOrderno((targPage.getOrderno() + nextPage.getOrderno()) / 2);
			}
		}
		
	}
    //查询指定页面的下一页
	public Page getNextPage(Page targPage) {
		String hql = "from Page p where p.orderno > ? and p.survey.id =? order by p.orderno asc";
		List<Page> list = pageDao.findEntityByHQL(hql, targPage.getOrderno(),targPage.getSurvey().getId());
		return list.get(0);
	}
	//查询指定页面的上一页
	public Page getPrePage(Page targPage) {
		String hql = "from Page p where p.orderno < ? and p.survey.id =? order by p.orderno desc";
		List<Page> list = pageDao.findEntityByHQL(hql, targPage.getOrderno(),targPage.getSurvey().getId());
		return list.get(0);
	}
	/**
	 * 判断指定页面是否是所在调查尾页
	 */
	public boolean isLastPage(Page targPage) {
		String hql ="select count(*) from Page p where p.orderno > ? and p.survey.id =?";
		long result = (Long) pageDao.uniqueResult(hql, targPage.getOrderno(),targPage.getSurvey().getId());
		return result==0;
	}
	/**
	 * 判断指定页面是否是所在调查首页
	 */
	public boolean isFirstPage(Page targPage) {
		String hql ="select count(*) from Page p where p.orderno < ? and p.survey.id =?";
		long result = (Long) pageDao.uniqueResult(hql, targPage.getOrderno(),targPage.getSurvey().getId());
		return result==0;
	}

	@Override
	public List<Survey> findAllAvailableSurveys() {
		String hql = "from Survey s where s.closed = ?";
		return surveyDao.findEntityByHQL(hql, false);
	}

	@Override
	public Page getFirstPage(Integer sid) {
		String hql ="from Page p where p.survey.id =? order by p.orderno";
		List<Page> list = pageDao.findEntityByHQL(hql, sid);
		Page p = list.get(0);
		p.getQuestions().size();
		p.getSurvey().getTitle();
		return p;
	}

	@Override
	public Page getPrePage(Integer currPid) {
		Page p = this.getPrePage(this.getPage(currPid));
		p.getQuestions().size();
		return p;
	}

	@Override
	public Page getNextPage(Integer currPid) {
		Page p = this.getNextPage(this.getPage(currPid));
		p.getQuestions().size();
		return p;
	}
	/**
     * 批量保存答案
     */
	@Override
	public void saveAnswers(List<Answer> list) {
		Date date = new Date();
		String uuid = UUID.randomUUID().toString();
        for (Answer a : list) {
		   a.setUuid(uuid);
		   a.setAnswerTime(date);
		   answerDao.saveEntity(a);
		}		
		
	}
	/**
	 * 查询指定调查的所有问题
	 */
	@Override
	public List<Question> getQuestions(Integer sid) {
		String hql = "from Question q where q.page.survey.id = ?";
		return questionDao.findEntityByHQL(hql, sid);
	}
	/**
	 * 查询指定调查的所有答案
	 */
	@Override
	public List<Answer> getAnswers(Integer sid) {
		String hql = "from Answer a where a.surveyId = ? order by a.uuid asc";
		return answerDao.findEntityByHQL(hql, sid);
	}

}
