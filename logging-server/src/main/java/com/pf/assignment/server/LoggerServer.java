package com.pf.assignment.server;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;

import com.pl.assignment.Logger;

public class LoggerServer {

	public static void main(String [] args) {
		try {
			LoggerHandler handler = new LoggerHandler();
			Logger.Processor<LoggerHandler> processor = new Logger.Processor<LoggerHandler>(handler);
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
