package com.purvanovv.user_store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.purvanovv.user_store.exception.WrongCredentialException;
import com.purvanovv.user_store.model.LoginDTO;
import com.purvanovv.user_store.model.User;
import com.purvanovv.user_store.repository.UserRepository;
import com.purvanovv.user_store.security.JwtProvider;

@Service
public class AuthorizeServiceImpl implements AuthorizeService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	JwtProvider jwtProvider;

	@Override
	public String createToken(LoginDTO loginDto) throws WrongCredentialException {
		User currentUser = userRepository.findUserByUsername(loginDto.getUsername());
		if (currentUser != null && currentUser.checkPassword(currentUser.getPassword())) {
			return jwtProvider.createToken(currentUser);
		} else {
			throw new WrongCredentialException("Wrong username or password");
		}
	}

}
