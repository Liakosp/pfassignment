# This folder contains the binary for running the aggregation system for logging events

## System prerequisites
For the applications to run properly we will need the following:
- Zookeeper up and running locally in port *2181*
- kafka up and running locally in port *9092*
- Cassandra up and running locally in port *9042*

## Initialise the envirement
For the applications to run properly we will need the kafka topic with name *logging_topic* to exist in kafka and the cassandra "schema" to be created.
The script *environment_setup.sh* takes kare of that. You will need to provide the following arguments:
- -kafka-bin : The path to the bin directory of kafka in order to execute the *kafka-topics.sh* for creating the topic
- -cqlsh-dir : The path to the directory where *cqlsh.py* script is located. (This is an optional argument)
Example usage:
> ./environment_setup.sh --kafka-bin /usr/local/kafka/bin/ --cqlsh-bin /usr/local/cassandra/bin/

## Starting applications
From the binary directory simply run the following commands:
> ./start-consumer.sh
> ./start-server.sh
> ./start-client.sh

