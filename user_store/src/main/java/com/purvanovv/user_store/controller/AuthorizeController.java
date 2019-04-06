package com.purvanovv.user_store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.purvanovv.user_store.exception.WrongCredentialException;
import com.purvanovv.user_store.model.LoginDTO;
import com.purvanovv.user_store.service.AuthorizeService;

@RestController
@RequestMapping("/authorize")
public class AuthorizeController {

	@Autowired
	AuthorizeService authorizeService;

	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	public ResponseEntity<String> login(@RequestBody LoginDTO loginDto) throws WrongCredentialException {
		ResponseEntity<String> response = ResponseEntity.status(HttpStatus.OK)
				.body(authorizeService.createToken(loginDto));
		return response;
	}

}
