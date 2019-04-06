package com.purvanovv.user_store.service;

import com.purvanovv.user_store.exception.WrongCredentialException;
import com.purvanovv.user_store.model.LoginDTO;

public interface AuthorizeService {
	public String createToken(LoginDTO loginDto) throws WrongCredentialException;
}
