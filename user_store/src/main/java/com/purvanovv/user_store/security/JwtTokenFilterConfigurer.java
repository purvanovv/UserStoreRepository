package com.purvanovv.user_store.security;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


public class JwtTokenFilterConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>{
	private JwtProvider jwtProvider;

	public JwtTokenFilterConfigurer(JwtProvider jwtProvider) {
		this.jwtProvider = jwtProvider;
	}

	@Override
	public void configure(HttpSecurity builder) throws Exception {
		JwtFilter customFilter = new JwtFilter(jwtProvider);
		builder.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	

}
