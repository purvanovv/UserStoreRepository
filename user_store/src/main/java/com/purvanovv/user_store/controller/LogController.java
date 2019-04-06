package com.purvanovv.user_store.controller;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.purvanovv.user_store.model.LogInfo;
import com.purvanovv.user_store.service.LogService;

@RestController
@RequestMapping("/log")
public class LogController {

	private final static Logger performanceLog = LoggerFactory
			.getLogger("performance." + MethodHandles.lookup().lookupClass().getCanonicalName());

	@Autowired
	private LogService logService;

	@RequestMapping(value = "/getLogs", method = RequestMethod.GET)
	public ResponseEntity<List<LogInfo>> getAllLogs() {
		long opStart = System.currentTimeMillis();
		ResponseEntity<List<LogInfo>> response = new ResponseEntity<List<LogInfo>>(logService.getAllLogs(),
				HttpStatus.OK);
		performanceLog.info("Call to LogService for getAllLogs {} ms", System.currentTimeMillis() - opStart);
		return response;
	}

	@RequestMapping(value = "/getAllLogsForUser", method = RequestMethod.GET)
	public ResponseEntity<List<LogInfo>> getLogsForUser(@RequestParam int userId) {
		long opStart = System.currentTimeMillis();
		ResponseEntity<List<LogInfo>> response = new ResponseEntity<List<LogInfo>>(logService.getAllLogsForUser(userId),
				HttpStatus.OK);
		performanceLog.info("Call to LogService for get getAllLogsForUser {} ms", System.currentTimeMillis() - opStart);
		return response;
	}

}
