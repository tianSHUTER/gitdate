package com.liuboss.ssm.service;

import java.util.List;

import com.liuboss.ssm.entity.User;

public interface UserService {

	List<User> getUserList(int offset, int limit);
	 
}
