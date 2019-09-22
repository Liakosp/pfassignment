package com.pf.assignment.server;

import org.apache.thrift.TException;

import com.pf.assignment.LogEvent;
import com.pf.assignment.LoggingService.Iface;
import com.pf.assignment.kafka.producer.LogEventKafkaProducer;

public class LoggingHandler implements Iface {

	private LogEventKafkaProducer kafkaProducer;
	
	public LoggingHandler() {
		kafkaProducer = new LogEventKafkaProducer();
	}
	
	public void log(LogEvent l) throws TException { 
		//the the retrieval of the log event
		kafkaProducer.send(l);
	}

}
