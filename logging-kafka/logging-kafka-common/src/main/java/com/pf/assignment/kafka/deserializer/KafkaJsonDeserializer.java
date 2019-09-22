package com.pf.assignment.kafka.deserializer;

import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;

public class KafkaJsonDeserializer<T> implements Deserializer<T>{

	private Class <T> type;
	
	public KafkaJsonDeserializer(Class<T> type) {
		this.type = type;
	}
	
	@Override
	public T deserialize(String topic, byte[] data) {
		ObjectMapper mapper = new ObjectMapper();
        T obj = null;
        try {
            obj = mapper.readValue(data, type);
        } catch (Exception e) {
        	throw new RuntimeException("Error while deserializing object", e);
        }
        return obj;
	}

}
