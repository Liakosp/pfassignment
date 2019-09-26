This project contains the implementation of an aggregation system for logging events.

Please note that it was developed in Windows environment. With the following software versions:
Java 1.8
Thrift
Kafka 2.3
Cassandra  3.11.4

In the context of this implementation a LogEvent is defined by the following fields
version -> The version of the loggin schema
application -> The application/system that generated the log event
uuid -> The uuid of the LogEvent (used for logging purposes thought the 3 application, to track the LogEven from creation to persistence)
LogEventDate -> A custom thrifr struct to replesent the date that the LogEvent was generated
message -> The LogEvent message

The LogEventDate consists the following fields
dateString -> The string representation of the datetime
pattern -> The date format pattern that was used by the client to format the date and will be used by the server to parse it

The only extra field that was used is the "application" that is also used by the thrift server application as the key to the message that will be produced in kafka.
The purpose of this is to make sure that all the log events generated by one application will end up in the same topic partition so that they can be consumed by other application in an orderd fasion.

The API of the client-server interaction i a simple one way method for the client to send LogEvents in the server.
Additional information can be found in logging-api\src\main\resources\log.thrift file

For the random generation of LogEvents we schedule a TimerTask to be executed in random intervals of 5-10 seconds.
Additional information can be fount in the class logging-client\src\main\java\com\pf\assignment\client\LoggingTimerTask.java

In order to create the dinary a simple "mvn clean package" is enough. It will generate all 3 modules needed for this project.
The thrift client is located in the maven module "logging-client" and the final executable jar can be found in logging-client\target\logging-client-app.jar
The thrift server (that also produces messages to kafka) is located in the maven module "logging-server" and the final executable jar can be found in logging-server\target\logging-server-app.jar
The kafka consumer is located in the maven module "logging-consumer" and the final executabe jar can be found in logging-consumer\target\logging-consumer-app.jar

Installation instruction for both Kafka and Cassandra can be found online their respcetive webpages.

For the entire system to run we will need:
Zookeeper running: zookeeper-server-start.bat config\zookeeper.properties (the only changed property was datadir)
Kafka running: kafka-server-start.bat config\server.properties (the only changed property was log.dirs)

Create a topic for the kafka producer and consumer to use with the following command:
kafka-topics --zookeeper 127.0.0.1:2181 --topic logging_topic --create --partitions 3 --replication-factor 1

//cassandra up and running:
//cassandra schema information:

And the final step is to actually execute the modules of the system:
java -jar logging-consumer\target\logging-consumer-app.jar
java -jar logging-server\target\logging-server-app.jar
java -jar logging-client\target\logging-client-app.jar

We should now see from the logs of the application that the client created and sends LogEvents to the server that maps them to a LogEventDto pojo (the serialisation to JSON what having problem with the generated LogEvent class from thrift) 
and procudes a message in the afformantioned kafka topic. Finaly the consumer picks up that message and stores it in Cassandra.
