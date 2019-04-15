package com.purvanovv.user_store.controller;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.purvanovv.user_store.model.LogInfo;
import com.purvanovv.user_store.model.User;
import com.purvanovv.user_store.service.LogService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "LogsController")
@RequestMapping("/log")
public class LogController {

	private final static Logger performanceLog = LoggerFactory
			.getLogger("performance." + MethodHandles.lookup().lookupClass().getCanonicalName());

	@Autowired
	private LogService logService;

	@PreAuthorize("hasAuthority('ADMIN')")
	@ApiOperation(value = "getLogs", notes = "getting all logs from file")
	@RequestMapping(value = "/getLogs", method = RequestMethod.GET)
	public ResponseEntity<List<LogInfo>> getAllLogs() {
		long opStart = System.currentTimeMillis();
		ResponseEntity<List<LogInfo>> response = new ResponseEntity<List<LogInfo>>(logService.getAllLogs(),
				HttpStatus.OK);
		performanceLog.info("Call to LogService for getAllLogs {} ms", System.currentTimeMillis() - opStart);
		return response;
	}

	@PreAuthorize("hasAuthority('USER')")
	@ApiOperation(value = "getAllLogsForUser", notes = "getting all logs fom logged user")
	@RequestMapping(value = "/getAllLogsForUser", method = RequestMethod.GET)
	public ResponseEntity<List<LogInfo>> getLogsForUser(@AuthenticationPrincipal User user) {
		long opStart = System.currentTimeMillis();
		ResponseEntity<List<LogInfo>> response = new ResponseEntity<List<LogInfo>>(
				logService.getAllLogsForUser(user.getId()), HttpStatus.OK);
		performanceLog.info("Call to LogService for get getAllLogsForUser {} ms", System.currentTimeMillis() - opStart);
		return response;
	}

}
