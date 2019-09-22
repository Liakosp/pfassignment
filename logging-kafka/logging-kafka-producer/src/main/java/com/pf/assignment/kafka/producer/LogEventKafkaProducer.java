package com.pf.assignment.kafka.producer;

import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import com.pf.assignment.LogEvent;
import com.pf.assignment.kafka.serializer.KafkaJsonSerializer;

public class LogEventKafkaProducer {

	private String topic = "logging_topic";
	private KafkaProducer<String, LogEvent> producer;
	
	public LogEventKafkaProducer() {
		producer = new KafkaProducer<String, LogEvent>(properties(), 
				new StringSerializer(), new KafkaJsonSerializer<LogEvent>());
	}
	
	public void send(LogEvent le) {
		ProducerRecord<String, LogEvent> record = new ProducerRecord<String, LogEvent>(topic, le.getApplication(), le);
		producer.send(record, new Callback() {
			@Override
			public void onCompletion(RecordMetadata metadata, Exception exception) {
				if (exception != null) {
					//log some shit
				}else {
					//log some other shit
				}
				
			}
		});
		producer.flush();
	}
	
	private Properties properties() {
		Properties properties = new Properties();
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		return properties;
	}
}
