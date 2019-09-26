package com.pf.assignment.cassandra;

import java.net.InetSocketAddress;
import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.CqlSessionBuilder;

public class CassandraConnector {

	private static Logger logger = LoggerFactory.getLogger(CassandraConnector.class);
	
	private CqlSession session;
	
	public CassandraConnector(String node, Integer port) {
		logger.info(MessageFormat.format("Initializing Cassandra connection on {0}:{1}", node, port));
		CqlSessionBuilder builder = CqlSession.builder();
		builder.addContactPoint(new InetSocketAddress(node, port));
		builder.withLocalDatacenter("datacenter1");
		this.session = builder.build();
	}
	
	public CqlSession getSession() {
		return session;
	}
	
	public void close() {
		logger.info("Closing Cassandra connection");
		session.close();
	}
}
