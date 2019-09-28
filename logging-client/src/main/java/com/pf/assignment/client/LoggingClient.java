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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pf.assignment.api.LogEvent;
import com.pf.assignment.api.LogEventDate;
import com.pf.assignment.api.LoggingService;

public class LoggingClient {

	private static Logger logger = LoggerFactory.getLogger(LoggingTimerTask.class);
	private static LoggingService.Client client;
	private static TTransport transport;
	private static final String DATE_PATTERN = "dd-MM-yyyy HH:mm:ss";

	public LoggingClient() {
		logger.info("Initialising thrift client..");
		try {
			transport = new TSocket("localhost", 9090);
			TProtocol protocol = new TBinaryProtocol(transport);
			client = new LoggingService.Client(protocol);
			transport.open();
		} catch (TTransportException e) {
			e.printStackTrace();
		}

	}

	public void sendLog() throws TException {
		LogEvent le = generateRandomLogEvent();
		logger.info(MessageFormat.format("About to send log event with id: {0}", le.getUuid()));
		client.send_log(le);
	}

	private LogEvent generateRandomLogEvent() {
		logger.info("Generating new random log event");
		LogEvent le = new LogEvent();
		String date = new SimpleDateFormat(DATE_PATTERN).format(new Date());
		LogEventDate d = new LogEventDate(date, DATE_PATTERN);
		le.setApplication("application_1");
		le.setDate(d);
		le.setUuid(UUID.randomUUID().toString());
		le.setMessage(MessageFormat.format("LogEvent created on {0}", date));
		return le;
	}

}
