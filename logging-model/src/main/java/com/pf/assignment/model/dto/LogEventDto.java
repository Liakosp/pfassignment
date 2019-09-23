package com.pf.assignment.model.dto;

import java.util.Date;

public class LogEventDto {

	private Short version;
	private String application;
	private String uuid;
	private Date date;
	private String message;
	
	public Short getVersion() {
		return version;
	}
	public void setVersion(Short version) {
		this.version = version;
	}
	public String getApplication() {
		return application;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
