package com.pf.assignment.consumer;

import java.text.MessageFormat;
import java.time.Duration;
import java.util.concurrent.CountDownLatch;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pf.assignment.kafka.consumer.LogEventKafkaConsumer;
import com.pf.assignment.kafka.deserializer.KafkaJsonDeserializer;
import com.pf.assignment.model.dto.LogEventDto;

public class LoggingConsumer {
	
	private static Logger logger = LoggerFactory.getLogger(LoggingConsumer.class);
	
	public static void main(String[] args) {
		logger.info("Starting logging consumer application..");
		new LoggingConsumer().run();
	}
	
	private void run() {
		CountDownLatch latch = new CountDownLatch(1);
		Runnable concumerRunnable = new ConsumerRunnable(latch);
		Thread thread = new Thread(concumerRunnable);
		thread.start();
		
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			logger.info("Stopping logging consumer application..");
			((ConsumerRunnable)concumerRunnable).shutdown();
			try {
				latch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}));
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			logger.error("Consumer pplciation got interupted", e);
		}finally {
			logger.info("Consumer application is closing");
		}
	}
	
	private class ConsumerRunnable implements Runnable{

		private Logger threadLogger = LoggerFactory.getLogger(ConsumerRunnable.class);
		private CountDownLatch latch;
		private LogEventKafkaConsumer<String, LogEventDto> consumer;
		
		public ConsumerRunnable(CountDownLatch latch) {
			this.latch = latch;
			consumer = new LogEventKafkaConsumer<String, LogEventDto>(new StringDeserializer(), new KafkaJsonDeserializer<LogEventDto>(LogEventDto.class));
		}
		
		@Override
		public void run() {
			threadLogger.info("Kafka consumer started polling");
			try {
				while (true) {
					ConsumerRecords<String, LogEventDto> consumerRecords = consumer.poll(Duration.ofMillis(500));
					threadLogger.info(MessageFormat.format("Consumed {0} records", consumerRecords.count()));
					
					for (ConsumerRecord<String, LogEventDto> record : consumerRecords) {
						threadLogger.info(MessageFormat.format("Consumed LogEvent record with uuid {0} and offset {1}", 
								record.value().getUuid(), record.offset()));
					}
					
				}
			}catch (WakeupException ew) {
				threadLogger.info("Received shutdown signal");
			}catch (Exception e) {
				threadLogger.error("Error occured while polling", e);
			}finally {
				consumer.close();
				latch.countDown();
			}
		}
		
		public void shutdown() {
			consumer.wakeup();
		}
	}

}
