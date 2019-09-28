package com.pf.assignment.model.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.pf.assignment.api.LogEvent;
import com.pf.assignment.model.dto.LogEventDto;

public class LogEventDtoMapper {

	public static LogEventDto apply(LogEvent logEvent) {
		LogEventDto dto = new LogEventDto();
		dto.setVersion(logEvent.getVersion());
		dto.setApplication(logEvent.getApplication());
		dto.setUuid(logEvent.getUuid());
		try {
			dto.setDate(new SimpleDateFormat(logEvent.getDate().getPattern()).parse(logEvent.getDate().getDateString()));
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		dto.setMessage(logEvent.getMessage());
		return dto;
	}
}
