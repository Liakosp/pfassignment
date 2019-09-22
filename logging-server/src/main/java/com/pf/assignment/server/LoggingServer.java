package com.pf.assignment.server;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;

import com.pf.assignment.LoggingService;

public class LoggingServer {

	public static void main(String[] args) {
		try {
			LoggingHandler handler = new LoggingHandler();
			LoggingService.Processor<LoggingHandler> processor = new LoggingService.Processor<LoggingHandler>(handler);
			TServerTransport serverTransport = new TServerSocket(9090);
			TServer server = new TSimpleServer(new Args(serverTransport).processor(processor));
			System.out.println("Starting the simple server...");
			server.serve();
		} catch (TTransportException e) {
			System.out.println("Error occured: " + e.getMessage());
			e.printStackTrace();
		}

	}
}
