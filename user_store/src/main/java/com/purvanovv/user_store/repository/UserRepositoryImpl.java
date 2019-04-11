package com.purvanovv.user_store.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.relation.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.purvanovv.user_store.exception.DatabaseException;
import com.purvanovv.user_store.model.User;
import com.purvanovv.user_store.model.UserAuthority;

@Repository
public class UserRepositoryImpl implements UserRepository {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public List<User> getAllUsers() throws DatabaseException {
		try{
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
		}catch(Exception e){
			throw new DatabaseException("Can't get users!");
		}
	}

	@Override
	public User findUserByUsername(String username) throws DatabaseException {
		try{
			String sql = "select u.id,u.username,u.password,u.first_name,u.last_name from users u where u.username = :username;";
			Map<String, String> sqlParams = new HashMap<String, String>();
			sqlParams.put("username", username);
			User findUser = namedParameterJdbcTemplate.query(sql, sqlParams, rs -> {
				User userFromDb = new User();
				while (rs.next()) {
					userFromDb.setId(rs.getInt("id"));
					userFromDb.setUsername(rs.getString("username"));
					userFromDb.setPassword(rs.getString("password"));
					userFromDb.setFirstName(rs.getString("first_name"));
					userFromDb.setLastName(rs.getString("last_name"));
				}
				return userFromDb;
			});
			
			List<UserAuthority> userRoles = getAuthoritiesForUser(findUser.getId());
			findUser.addAuthorities(userRoles);
			return findUser;			
		}catch (Exception e) {
			throw new DatabaseException("Can't get user!");
		}
	}

	@Override
	public List<UserAuthority> getAuthoritiesForUser(Integer userId) throws DatabaseException {
		try{
			String sql = "select r.id,r.role_name from roles r join user_role ur on ur.role_id = r.id and ur.user_id = 1;";
			Map<String, Integer> sqlParams = new HashMap<String, Integer>();
			sqlParams.put("userId", userId);
			List<UserAuthority> findRoles = namedParameterJdbcTemplate.query(sql, sqlParams,rs ->{
				List<UserAuthority> rolesFromDb = new ArrayList<UserAuthority>();
				while(rs.next()){
					UserAuthority role = new UserAuthority();
					role.setAuthority(rs.getString("role_name"));
					rolesFromDb.add(role);
				}
				return rolesFromDb;
			});
			return findRoles;			
		}catch (Exception e) {
			throw new DatabaseException("Can't get user authorities!");
		}
	}

}
