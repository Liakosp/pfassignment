#!/bin/sh

#create the kafka topic
kafka-topics.sh --zookeeper 127.0.0.1:2181 --topic logging_topic2 --create --partitions 3 --replication-factor 1

#create the cassandra schema
cqlsh -f scripts/create_schemma.cql 