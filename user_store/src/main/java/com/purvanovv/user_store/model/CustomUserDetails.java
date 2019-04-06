package com.purvanovv.user_store.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails{
	
	private String username;
	
	private String firstName;
	
	private String lastName;
	
	private String password;
	
	private List<Role> roles;
	
	

	public CustomUserDetails(String username, String firstName, String lastName, List<Role> roles) {
		super();
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.roles = roles;
	}

	@Override
	public List<Role> getAuthorities() {
		return roles;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	

}
