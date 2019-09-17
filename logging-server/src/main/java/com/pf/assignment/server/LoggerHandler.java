package com.pf.assignment.server;

import org.apache.thrift.TException;

import com.pl.assignment.LogEvent;
import com.pl.assignment.Logger.Iface;

public class LoggerHandler implements Iface{

	public void log(LogEvent l) throws TException {
		System.out.println("Recieved log event with id: " + l.getUuid());
	}

}
