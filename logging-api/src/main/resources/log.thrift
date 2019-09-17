namespace java com.pl.assignment

/**
 * We define a Date struct to represnt the time of the logging evenr.
 * The reason for doing so is to ensure that the server can parse the date 
 * by using the pattern provided without any additional information or convention
 * between the client and the server applications.
 */
struct Date {
	1: string date,
	2: string pattern
}

/**
 * We define the LogEvent struct that contains all the information needed 
 */
struct LogEvent {
	1: i16 version = 1,
	2: string uuid,
	3: Date date,
	4: string message
}

service Logger {
	/**
	* We define a single oneway method so that the client can send the LogEvent
	* to the server whithout having to listen for a response, since it seems irrelevant 
	* for the purpose of this assignment
	*/
	oneway void log(1:LogEvent l)
}