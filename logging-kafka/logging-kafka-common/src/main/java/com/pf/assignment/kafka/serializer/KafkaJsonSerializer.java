package com.pf.assignment.kafka.serializer;

import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.databind.ObjectMapper;

public class KafkaJsonSerializer<T> implements Serializer<T> {

	@Override
	public byte[] serialize(String topic, T data) {
		byte[] retVal = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			retVal = objectMapper.writeValueAsBytes(data);
		} catch (Exception e) {
			throw new RuntimeException("Error while serializing object", e);
		}
		return retVal;
	}

}
