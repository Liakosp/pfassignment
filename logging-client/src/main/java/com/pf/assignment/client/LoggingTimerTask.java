package com.pf.assignment.client;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingTimerTask extends TimerTask {

	private static Logger logger = LoggerFactory.getLogger(LoggingTimerTask.class);
	
	private static Timer timer = new Timer();
	private static LoggingClient client = new LoggingClient();
	
	@Override
	public void run() {
		logger.info("Running task..");
		int delay = (5 + new Random().nextInt(5)) * 1000;
		timer.schedule(new LoggingTimerTask(), delay);
		try {
			client.sendLog();
		} catch (TException e) {
			System.out.println("Failed to send log: " + e.getMessage());
			e.printStackTrace();
		}
	}

}
