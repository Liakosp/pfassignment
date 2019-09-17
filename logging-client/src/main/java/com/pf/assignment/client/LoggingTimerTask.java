package com.pf.assignment.client;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.thrift.TException;

public class LoggingTimerTask extends TimerTask {

	private static Timer timer = new Timer();
	private static LoggingClient client = new LoggingClient();
	
	
	@Override
	public void run() {
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
