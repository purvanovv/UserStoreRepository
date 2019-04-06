package com.purvanovv.user_store.repository;

import java.util.List;

import com.purvanovv.user_store.model.User;

public interface UserRepository {
	public List<User> getAllUsers();
	
	public User findUserByUsername(String username);
}
