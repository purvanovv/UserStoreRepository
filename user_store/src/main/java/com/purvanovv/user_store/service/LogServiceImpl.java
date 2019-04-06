package com.purvanovv.user_store.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.purvanovv.user_store.model.LogInfo;

import java.util.regex.Matcher;

@Service
public class LogServiceImpl implements LogService {

	@Autowired
	public FileLoader fileLoader;

	@Override
	public List<LogInfo> getAllLogs() {
		return fileLoader.readLog();
	}

	@Override
	public List<LogInfo> getAllLogsForUser(int userId) {
		List<LogInfo> logs = fileLoader.readLog();
		List<LogInfo> result = new ArrayList<LogInfo>();
		logs.forEach(log -> {
			String description = log.getDescription();
			String regex = "id '([0-9]+)'";
			Pattern r = Pattern.compile(regex);
			Matcher m = r.matcher(description);

			if (m.find()) {
				int id = Integer.parseInt(m.group(1));
				if (id == userId) {
					result.add(log);
				}
			}
		});
		return result;
	}

}
