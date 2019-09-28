#!/bin/bash
#initialize the environment

KAFKA_BIN_DIR="--kafka-bin"
CQLSH_BIN_DIR="--cqlsh-dir"

KAFKA_BIN=
CQLSH_BIN=

function usage {
	echo "Usage $0 [OPTION]"
	echo "Creates the kafka topic"
	echo "Sets the cassandra schema"
	echo ""
	echo "Options:"
	echo " $KAFKA_BIN_DIR        the path to the bin directory of kafka"
	echo " $CQLSH_BIN_DIR        [optional]the path to cqlsh"
}

function parse_and_check_args {
	while(("$#")); do
		if [ $1 = $KAFKA_BIN_DIR ]; then
			shift
			KAFKA_BIN=$1
		elif [ $1 = $CQLSH_BIN_DIR ]; then
			shift
			CQLSH_BIN=$1
		fi
		shift
	done

	echo "Kafka bin directory specified:         ${KAFKA_BIN:-MISSING}"
	echo "CQLSH bin directory specified:     ${CASSANDRA_BIN:-MISSING}"
	echo

	if [ -z "$KAFKA_BIN" ]; then
		echo "ERROR: Some required information is missing, see bellow!"
		echo
		usage
		exit 1
	fi
}

parse_and_check_args $*
#create the kafka topic
echo "Creating the kafka topic"
"$KAFKA_BIN"kafka-topics.sh --zookeeper 127.0.0.1:2181 --topic logging_topic --create --partitions 3 --replication-factor 1

echo "Creating cassanda schema"
#create the cassandra schema
"$CQLSH_BIN"cqlsh -f cql/create_schemma.cql 




