package com.purvanovv.user_store.service;

import java.util.List;

import com.purvanovv.user_store.model.LogInfo;

public interface LogService {
	public List<LogInfo> getAllLogs();

	public List<LogInfo> getAllLogsForUser(int userId);
}
