# This project contains the implementation of an aggregation system for logging events.

## The following software versions where used
- Java 1.8
- Thrift 0.12.0
- Kafka 2.3
- Cassandra  3.11.4

## LogEvent and logging API description

In the context of this implementation a **LogEvent** is defined by the following fields

|Field|Description|
|-----|-----------|
|version|The version of the logging schema|
|application|The application/system that generated the log event|
|uuid|The uuid of the LogEvent|
|LogEventDate|A custom thrifr struct to represent the date that the LogEvent was generated|
|message|The LogEvent message|

The **LogEventDate** consists the following fields

|Field|Description|
|-----|-----------|
|dateString|The string representation of the date|
|pattern|The date format pattern that was used by the client to format the date and will be used by the server to parse it|

The only extra fields that where used are the "application" and "uuid" fields.
The application field is used to identify the application/system that produced the log message and is also used bny the thrift server application as the key of the message that wiill be produced for Kafka. The purpose of this is to make sure that all the log events generated by one application will end up in the same topic partition so that they can be consumed by other applications in an orderd fasion. 
The uuid field is used to identify the message produced by the client and track its progrees via the various applications (client->server->kafka->cassandra)

The API of the client-server interaction is a simple one way method for the client to send LogEvents in the server.

Additional information can be found in the following file
>logging-api\src\main\resources\log.thrift


## Generation of random events
For the random generation of LogEvents we schedule a TimerTask to be executed in random intervals of 5-10 seconds.Additional information can be fount in the class:
>logging-client\src\main\java\com\pf\assignment\client\LoggingTimerTask.java

## How to create the binary
In order to create the dinary a simple **mvn clean package** is enough. It will generate all 3 modules needed for this project.
The thrift client is located in the maven module **logging-client** and the final executable jar can be found in
>logging-client\target\logging-client-app.jar

The thrift server is located in the maven module **logging-server** and the final executable jar can be found in
>logging-server\target\logging-server-app.jar

The kafka consumer is located in the maven module **logging-consumer** and the final executabe jar can be found in
>logging-consumer\target\logging-consumer-app.jar

## Binary execution
Please go to the *binary* directory and follow the instructions in the README file there in order to set upd the environment and execute the applications.
