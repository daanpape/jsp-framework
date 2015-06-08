package be.howest.web.framwork.log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
	
	/**
	 * Get the stacktrace from an exception as a string value. 
	 * @param ex the exception to get the stacktrace from.
	 * @return the stacktrace as a string.
	 */
	public static String exceptionToString(Throwable ex) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        return sw.toString();
	}
    
    /**
     * Append timestamp string to the current logging string.
     * @param parent the parent StringBuilder instance to append to. 
     * @return the parent with the timestamp string appended to it.
     */
    private static StringBuilder genTimestamp(StringBuilder parent){
        parent.append("[");
        parent.append(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()));
        parent.append("]");
        return parent;
    }
    
    /**
     * Append logging label string to the current logging string.
     * @param parent the parent StringBuilder instance to append to.
     * @param level the LogLevel to make the label for.
     * @return the parent with the LogLevel label appended to it.
     */
    private static StringBuilder genLoglabel(StringBuilder parent, LogLevel level)
    {
        parent.append("[");
        parent.append(level.name());
        parent.append("]");
        return parent;
    }
    
    /**
     * Log a message 
     * @param level the logging level
     * @param message the message to log
     */
    public static void log(LogLevel level, String message){
        StringBuilder logBuilder = Logger.genLoglabel(new StringBuilder(), level);
        Logger.genTimestamp(logBuilder);
        logBuilder.append(" ");
        logBuilder.append(message);
        
        // Create the log string
        String logEntry = logBuilder.toString();
        
        System.out.println(logEntry);
    }
    
    /**
     * Log an error with a stacktrace
     * @param level the logging level
     * @param message the message to log
     * @param ex the stacktrace to print
     */
    public static void log(LogLevel level, String message, Throwable ex)
    {
        StringBuilder logBuilder = Logger.genLoglabel(new StringBuilder(), level);
        Logger.genTimestamp(logBuilder);
        logBuilder.append(" ");
        logBuilder.append(message);
        logBuilder.append(", stacktrace:\r\n");
        
        logBuilder.append(exceptionToString(ex));
        
        // Create the log string
        String logEntry = logBuilder.toString();
        
        System.out.println(logEntry);
    }
}
