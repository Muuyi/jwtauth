package com.muabatech.jwtauth.service;

import com.muabatech.jwtauth.entity.Role;
import com.muabatech.jwtauth.entity.User;

public interface UserService {
	User saveUser(User user);
	Role saveRole(Role role);
	void addToUser(String username, String rolename);
}
