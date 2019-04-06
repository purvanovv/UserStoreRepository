package com.purvanovv.user_store.model;

import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority {
	private Integer id;

	private String roleName;

	public Role() {
		super();
	}

	public Role(Integer id, String roleName) {
		super();
		this.id = id;
		this.roleName = roleName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String getAuthority() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
