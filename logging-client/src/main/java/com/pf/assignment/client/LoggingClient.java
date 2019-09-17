package com.pf.assignment.client;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import com.pl.assignment.LogEvent;
import com.pl.assignment.Logger;

public class LoggingClient {

	private static Logger.Client client;
	private static TTransport transport;
	private static final String DATE_PATTERN = "dd-MM-yyyy HH:ss";

	public LoggingClient() {
		try {
			transport = new TSocket("localhost", 9090);
			TProtocol protocol = new TBinaryProtocol(transport);
			client = new Logger.Client(protocol);
			transport.open();
		} catch (TTransportException e) {
			e.printStackTrace();
		}

	}

	public void sendLog() throws TException {
		System.out.println("About to send log event");
		client.send_log(generateRandomLogEvent());
	}

	private LogEvent generateRandomLogEvent() {
		LogEvent le = new LogEvent();
		String date = new SimpleDateFormat(DATE_PATTERN).format(new Date());
		com.pl.assignment.Date d = new com.pl.assignment.Date(date, DATE_PATTERN);
		le.setDate(d);
		le.setUuid(UUID.randomUUID().toString());
		le.setMessage(MessageFormat.format("LogEvent created on {0}", date));
		return le;
	}

}
