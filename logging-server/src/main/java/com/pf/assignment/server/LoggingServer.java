package com.pf.assignment.server;

import java.util.concurrent.CountDownLatch;

import org.apache.kafka.common.errors.WakeupException;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pf.assignment.LoggingService;

public class LoggingServer {

	private static Logger logger = LoggerFactory.getLogger(LoggingServer.class);
	
	public static void main(String[] args) {
		logger.info("Starting logging server application..");
		new LoggingServer().run();
	}
	
	private void run() {
		CountDownLatch latch = new CountDownLatch(1);
		Runnable loggingServerRunnable = new LoggingServerRunnable(latch);
		
		Thread thread = new Thread(loggingServerRunnable);
		thread.start();
		
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			logger.info("Stopping logging server application..");
			((LoggingServerRunnable)loggingServerRunnable).shutdown();
			try {
				latch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}));
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			logger.error("Logging server applciation got interupted", e);
		}finally {
			logger.info("Logging server application is closing");
		}
	}
	
	private class LoggingServerRunnable implements Runnable{

		private Logger threadLogger = LoggerFactory.getLogger(LoggingServerRunnable.class);
		private LoggingHandler handler;
		private CountDownLatch latch;
		
		public LoggingServerRunnable(CountDownLatch latch) {
			this.latch = latch;
		}

		@Override
		public void run() {
			try {
				handler = new LoggingHandler();
				LoggingService.Processor<LoggingHandler> processor = new LoggingService.Processor<LoggingHandler>(handler);
				TServerTransport serverTransport = new TServerSocket(9090);
				TServer server = new TSimpleServer(new Args(serverTransport).processor(processor));
				threadLogger.info("Starting the simple server...");
				server.serve();
			} catch (TTransportException e) {
				threadLogger.error("Error occured: ", e);
			}catch(WakeupException we) {
				threadLogger.info("Received shutdown signal");
			}finally {
				handler.close();
				latch.countDown();
			}
		}
		
		public void shutdown() {
			handler.close();
			latch.countDown();
		}
		
	}
}
