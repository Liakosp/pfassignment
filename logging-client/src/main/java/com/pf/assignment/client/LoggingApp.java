package com.pf.assignment.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingApp {

	private static Logger logger = LoggerFactory.getLogger(LoggingApp.class);

	public static void main(String[] args) {
		logger.info("Starting logging client application..");
		new LoggingTimerTask().run();
	}

}
