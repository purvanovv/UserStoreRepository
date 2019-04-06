package com.purvanovv.user_store.controller;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.purvanovv.user_store.model.User;
import com.purvanovv.user_store.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	private final static Logger performanceLog = LoggerFactory
			.getLogger("performance." + MethodHandles.lookup().lookupClass().getCanonicalName());

	@Autowired
	UserService userService;

	@RequestMapping("/getAllUsers")
	public ResponseEntity<List<User>> getAllLogs() {
		long opStart = System.currentTimeMillis();
		ResponseEntity<List<User>> response = new ResponseEntity<List<User>>(userService.getAllUsers(), HttpStatus.OK);
		performanceLog.info("Call to UserService for getAllUsers {} ms", System.currentTimeMillis() - opStart);
		return response;
	}
}
