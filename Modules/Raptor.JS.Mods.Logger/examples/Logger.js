/* Logger Example
 * This javascript is an example of how to use the Logger class to write 
 * log files to the system.
 * 
 * Constructor: 
 * var log = new Logger(fileName);
 *  - fileName is an optional string of the file name to write to including 
 *  full path when required
 *  
 *  Methods:
 *  log.setQuiet(value);
 *  - value: boolean value to turn on console printing of log messages
 *  
 *  log.setDebug(value);
 *  - value: boolean value to turn on debugging of log messages
 *  
 *  log.close();
 *  - Close the log file if created
 *  
 *  log.write(message);
 *  - Write the log message to the log file and/or console
 *  
 *  log.debug(message);
 *  - Write the debug message to the log file and/or console if debugging is
 *    turn on
 *    
 *  log.setDateFormat(format);
 *  - format: A string used to set the format of the date output in the log
 *  - The default format is: MM/dd/yyyy HH:mm:ss
 *  - Valide format string values:
 *  
 *  G  	Era designator 	Text  	AD
 *  y 	Year 	Year 	1996; 96
 *  M 	Month in year 	Month 	July; Jul; 07
 *  w 	Week in year 	Number 	27
 *  W 	Week in month 	Number 	2
 *  D 	Day in year 	Number 	189
 *  d 	Day in month 	Number 	10
 *  F 	Day of week in month 	Number 	2
 *  E 	Day in week 	Text 	Tuesday; Tue
 *  a 	Am/pm marker 	Text 	PM
 *  H 	Hour in day (0-23) 	Number 	0
 *  k 	Hour in day (1-24) 	Number 	24
 *  K 	Hour in am/pm (0-11) 	Number 	0
 *  h 	Hour in am/pm (1-12) 	Number 	12
 *  m 	Minute in hour 	Number 	30
 *  s 	Second in minute 	Number 	55
 *  S 	Millisecond 	Number 	978
 *  z 	Time zone 	General time zone 	Pacific Standard Time; PST; GMT-08:00
 *  Z 	Time zone 	RFC 822 time zone 	-0800
 *
 *  log.getLogs()
 *  - Returns an array of the current log list
 *  
 * Written By: Ted Elwartowski - 2008
 * */
var log = new Logger("Test.log");
log.setQuiet(false);
log.setDebug(true);
log.write("Test");
log.debug("Test Debug");
log.close();
