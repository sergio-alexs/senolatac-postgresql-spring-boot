package com.sha.postgresql.service;

import java.util.List;

import com.sha.postgresql.model.Role;
import com.sha.postgresql.model.User;

public interface IUserService {

	User saveUser(User user);

	User changeRole(Role newRole, String username);

	User findByUsername(String username);

	List<User> findAllUsers();
}
