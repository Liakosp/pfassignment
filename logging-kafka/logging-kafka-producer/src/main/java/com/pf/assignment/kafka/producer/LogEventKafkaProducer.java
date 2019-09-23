package com.pf.assignment.kafka.producer;

import java.text.MessageFormat;
import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogEventKafkaProducer<K, V> {

	private static Logger logger = LoggerFactory.getLogger(LogEventKafkaProducer.class);
	
	private String topic = "logging_topic";
	private KafkaProducer<K, V> producer;
	
	public LogEventKafkaProducer(Serializer<K> keySerializer, Serializer<V> valueSerializer) {
		logger.info(MessageFormat.format("Initializing KafkaProducer for topic {0}", topic));
		producer = new KafkaProducer<K, V>(properties(), 
				keySerializer, valueSerializer);
	}
	
	public void send(K k, V v) {
		ProducerRecord<K, V> record = new ProducerRecord<K, V>(topic, k, v);
		producer.send(record, new Callback() {
			@Override
			public void onCompletion(RecordMetadata metadata, Exception exception) {
				if (exception != null) {
					logger.error("Error occured while sending to kafka", exception);
				}else {
					logger.info("Log event successfully send to kafka");
				}
				
			}
		});
		producer.flush();
	}
	
	public void close() {
		logger.info("Clossing kafka producer..");
		producer.close();
	}
	
	private Properties properties() {
		Properties properties = new Properties();
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		return properties;
	}
}
