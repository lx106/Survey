package com.lx.survey.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.lx.survey.model.security.Right;
import com.lx.survey.model.security.Role;
/**
 * 用户类
 * @author Administrator
 *
 */
public class User extends BaseEntity{

	private static final long serialVersionUID = -4151479835438615449L;
	private Integer id;
	private String email;
	private String name;
	private String password;
	private String nickName;
	
	//注册时间
	private Date regDate = new Date();

	//权限总和
	private long[] rightSum;
	
	//角色集合
	private Set<Role> roles = new HashSet<Role>();
	
	//是否是超级管理员
	private boolean superAdmin ;
	
	public boolean isSuperAdmin() {
		return superAdmin;
	}

	public void setSuperAdmin(boolean superAdmin) {
		this.superAdmin = superAdmin;
	}

	public long[] getRightSum() {
		return rightSum;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public void setRightSum(long[] ls) {
	}
    /**
     * 计算用户权限总和
     */
	public void calculateRightSum() {
	    int pos = 0;
	    long code  = 0;
	    for (Role role : roles) {
			//判断是否超级管理员
	    	if("-1".equals(role.getRoleValue())){
	    		this.superAdmin = true ;
	    		//释放资源
	    		roles = null;
	    		return ;
	    	}
	    	for (Right r : role.getRights()) {
				pos = r.getRightPos();
				code = r.getRightCode();
				rightSum[pos] = rightSum[pos] | code ;
			}
		}
	    //释放资源
	    roles = null;
	}
	/**
	 * 判断 用户是否具有指定权限
	 */
    public boolean hasRight(Right r){
    	int pos = r.getRightPos();
    	long code = r.getRightCode();
    	return !((rightSum[pos] & code) == 0);
    }
    
  	
	
	
}
