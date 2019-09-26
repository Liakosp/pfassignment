package com.pf.assignment.cassandra.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.insert.RegularInsert;
import com.pf.assignment.model.dto.LogEventDto;

public class LogEventRepository {

	private static Logger logger = LoggerFactory.getLogger(LogEventRepository.class);
	private static final String KEYSPACE = "log_event";
	private static final String TABLE_NAME = "logevent_by_application";
	private CqlSession session;
	private PreparedStatement insertStatement;
	
	public LogEventRepository(CqlSession session) {
		logger.info("Initialising LogEventRepository");
		this.session = session;
		RegularInsert insert = QueryBuilder.insertInto(KEYSPACE, TABLE_NAME)	
				.value("application", QueryBuilder.bindMarker())
				.value("offset", QueryBuilder.bindMarker())
				.value("version", QueryBuilder.bindMarker())
				.value("uuid", QueryBuilder.bindMarker())
				.value("message", QueryBuilder.bindMarker())
				.value("log_date", QueryBuilder.bindMarker());
		
		
		SimpleStatement simpleStatement = insert.build();
		insertStatement = session.prepare(simpleStatement);
		
	}
	
	public void insertLogEvent(LogEventDto event, Long offset) {
		logger.info("inserting LogEvent in cassandra db");
		BoundStatement boundStatement = insertStatement.bind()
				.setString(0, event.getApplication())
				.setLong(1, offset)
				.setInt(2, event.getVersion())
				.setString(3, event.getUuid())
				.setString(4, event.getMessage())
				.setInstant(5, event.getDate().toInstant());
		session.execute(boundStatement);
	}
}
