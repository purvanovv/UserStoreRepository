package com.purvanovv.user_store.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.purvanovv.user_store.model.Role;
import com.purvanovv.user_store.model.User;

@Repository
public class UserRepositoryImpl implements UserRepository {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public List<User> getAllUsers() {
		String sql = "select * from users";
		List<User> users = namedParameterJdbcTemplate.query(sql, rs -> {
			List<User> templUsers = new ArrayList<User>();
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setFirstName(rs.getString("first_name"));
				user.setLastName(rs.getString("last_name"));
				templUsers.add(user);
			}
			return templUsers;
		});
		return users;
	}

	@Override
	public User findUserByUsername(String username) {
		String sql = "select u.id as user_id,u.username,u.password,u.first_name,u.last_name,r.id as role_id,r.role_name from users u join user_role ur on ur.user_id = u.id join roles r on r.id = ur.role_id where u.username = :username;";
		Map<String, String> sqlParams = new HashMap<String, String>();
		sqlParams.put("username", username);
		User findUser = namedParameterJdbcTemplate.query(sql, sqlParams, rs -> {
			User userFromDb = new User();
			while (rs.next()) {
				userFromDb.setId(rs.getInt("role_id"));
				userFromDb.setUsername(rs.getString("username"));
				userFromDb.setPassword(rs.getString("password"));
				userFromDb.setFirstName(rs.getString("first_name"));
				userFromDb.setLastName(rs.getString("last_name"));
				userFromDb.setRole(new Role(rs.getInt("role_id"), rs.getString("role_name")));
			}
			return userFromDb;
		});
		return findUser;
	}

}
