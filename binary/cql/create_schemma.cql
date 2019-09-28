CREATE KEYSPACE log_event WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'};
USE log_event;
CREATE TABLE logevent_by_application (application text, offset bigint, version int, uuid text, message text, log_date timestamp, PRIMARY KEY(application, offset));
