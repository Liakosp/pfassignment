package com.pf.assignment.kafka.consumer;

import java.text.MessageFormat;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LogEventKafkaConsumer<K, V> {

	private static Logger logger = LoggerFactory.getLogger(LogEventKafkaConsumer.class);
	private String topic = "logging_topic";
	private KafkaConsumer<K, V> consumer;
	
	public LogEventKafkaConsumer(Deserializer<K> keyDeserializer, Deserializer<V> valueDeserializer) {
		logger.info(MessageFormat.format("Initializing KafkaConsumer for topic {0}", topic));
		consumer = new KafkaConsumer<K, V>(properties(), keyDeserializer, valueDeserializer);
		consumer.subscribe(Collections.singletonList(topic));
	}
	
	public ConsumerRecords<K, V> poll(Duration duration){
		return consumer.poll(duration);
	}
	
	public void close() {
		logger.info("Clossing kafka consumer..");
		consumer.close();
	}
	
	public void wakeup() {
		logger.info("Wakeup kafka consumer..");
		consumer.wakeup();
	}
	
	private Properties properties() {
		Properties properties = new Properties();
		properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "logging-consumer-group");
		properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		return properties;
	}
}
