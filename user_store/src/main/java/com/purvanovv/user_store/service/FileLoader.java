package com.purvanovv.user_store.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import com.purvanovv.user_store.model.LogInfo;

@Component
public class FileLoader {
	public List<LogInfo> readLog() {
		Map<String, String> mapping = new HashMap<String, String>();
		mapping.put("time", "Time");
		mapping.put("eventContext", "Event context");
		mapping.put("component", "Component");
		mapping.put("eventName", "Event name");
		mapping.put("description", "Description");
		mapping.put("origin", "Origin");
		mapping.put("ipAdress", "IP address");

		HeaderColumnNameTranslateMappingStrategy<LogInfo> strategy = new HeaderColumnNameTranslateMappingStrategy<LogInfo>();
		strategy.setType(LogInfo.class);
		strategy.setColumnMapping(mapping);

		CSVReader csvReader = null;
		try {
			csvReader = new CSVReader(new FileReader("C:\\projects\\UserStorePTS\\resource\\logs_BCS37_20181103.csv"));
		} catch (FileNotFoundException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CsvToBean csvToBean = new CsvToBean();

		// call the parse method of CsvToBean
		// pass strategy, csvReader to parse method
		List<LogInfo> list = csvToBean.parse(strategy, csvReader);

		return list;
	}

}
