package com.lx.survey.service;

import com.lx.survey.model.User;

public interface UserService extends BaseService<User>{

	public boolean isRegisted(String email);

	public User validateLoginInfo(String email, String md5);

	public void updateAuthorize(User model, Integer[] ownRoleIds);

	public void clearAuthorize(Integer userId);

}
