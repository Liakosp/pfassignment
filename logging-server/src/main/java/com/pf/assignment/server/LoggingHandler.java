package com.pf.assignment.server;

import java.text.MessageFormat;

import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pf.assignment.api.LogEvent;
import com.pf.assignment.api.LoggingService.Iface;
import com.pf.assignment.kafka.producer.LogEventKafkaProducer;
import com.pf.assignment.kafka.serializer.KafkaJsonSerializer;
import com.pf.assignment.model.dto.LogEventDto;
import com.pf.assignment.model.mapper.LogEventDtoMapper;

public class LoggingHandler implements Iface {

	private static Logger logger = LoggerFactory.getLogger(LoggingHandler.class);
	private LogEventKafkaProducer<String, LogEventDto> kafkaProducer;
	
	public LoggingHandler() {
		kafkaProducer = new LogEventKafkaProducer<String, LogEventDto>(new StringSerializer(), new KafkaJsonSerializer<LogEventDto>());
	}
	
	public void log(LogEvent l) throws TException { 
		logger.info(MessageFormat.format("Received log event with id: {0}", l.getUuid()));
		kafkaProducer.send(l.getApplication(), LogEventDtoMapper.apply(l));
	}
	
	public void close() {
		logger.info("Clossing LoggingHandler ..");
		kafkaProducer.close();
	}

}
