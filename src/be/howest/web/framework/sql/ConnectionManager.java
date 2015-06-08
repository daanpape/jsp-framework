package be.howest.web.framework.sql;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletContext;

import be.howest.web.framwork.log.LogLevel;
import be.howest.web.framwork.log.Logger;

public class ConnectionManager {

	/**
	 * Create a JDBC MySQL connection string. 
	 * @param host the host to connect to.
	 * @param port the port to connect to.
	 * @param db the database to use.
	 * @return
	 */
	private static String getJdbcConnectionString(String host, int port, String db) {		
		StringBuilder buff = new StringBuilder("jdbc:mysql://");
		buff.append(host);
		buff.append(':');
		buff.append(port);
		buff.append('/');
		buff.append(db);
		
		return buff.toString();
	}
	
	public Connection getConnection(ServletContext ctx) {
		String host = ctx.getInitParameter("sqlHost");
		if(host == null) {
			host = "localhost";
			Logger.log(LogLevel.WARNING, "ConnectionManager could not read SQL host from context, falling back to default 'localhost'");
		}
		
		int port = 3306;
		try {
			port = Integer.parseInt(ctx.getInitParameter("sqlPort"));
		} catch (NumberFormatException ex) {
			Logger.log(LogLevel.WARNING, "ConnectionManager could not parse SQL port from context to integer, falling back to default 3306");
		}
		
		String database = ctx.getInitParameter("sqlDatabase");
		if(database == null) {
			Logger.log(LogLevel.ERROR_FATAL, "ConnectionManager could not read SQL database from context");
			return null;
		}
		
		String user = ctx.getInitParameter("sqlUser");
		if(user == null) {
			Logger.log(LogLevel.ERROR_FATAL, "ConnectionManager could not read SQL user from context");
			return null;
		}
		
		String pass = ctx.getInitParameter("sqlPassword");
		if(pass == null) {
			Logger.log(LogLevel.ERROR_FATAL, "ConnectionManager could not read SQL password from context");
			return null;
		}
		
		return this.getConnection(host, port, database, user, pass);
	}
	
	/**
	 * Get a database connection.
	 * @param host the host to connect to.
	 * @param port the port to connect to.
	 * @param db the database to use.
	 * @param user the username which has access to the database.
	 * @param pass the password for user authentication.
	 * @return the connection on success, null on error.
	 */
    public Connection getConnection(String host, int port, String db, String user, String pass) {
    	Connection conn = null;
    	String connString = getJdbcConnectionString(host, port, db);
    	
    	try {
    		Class.forName("com.mysql.jdbc.Driver").newInstance();
    		conn = DriverManager.getConnection(connString, user, pass);
    		Logger.log(LogLevel.INFO, "Connection with database " + connString + " established");
    	} catch(Exception e) {
    		Logger.log(LogLevel.ERROR_CRITICAL, "Could not connect to " + connString, e);
    		e.printStackTrace();
    	}
    	
    	return conn;
    }
}
