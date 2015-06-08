package be.howest.web.framework.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import be.howest.web.framework.log.LogLevel;
import be.howest.web.framework.log.Logger;



/**
 * Represents a database object. 
 * @author Daan Pape
 *
 */
public class DbObject {
	private Connection conn;
	private PreparedStatement stmt;
	private Map<String, Integer> parameterMap;
	
	/**
	 * Convert a prepared statement to a string.
	 * @param stmt the statement to convert. 
	 * @return the Prepared Statement as a string. 
	 */
	public static String stmtToString(PreparedStatement stmt) {
		String sql = stmt.toString();
		int sIndex = sql.indexOf(':');
		
		if(sql.startsWith("com.") && sIndex > 0) {
			return sql.substring(sIndex + 2);
		} else {
			return sql;
		}
	}
	
	/**
	 * Create a new database object
	 */
	public DbObject(Connection conn) {
		this.stmt = null;
		this.parameterMap = new HashMap<String, Integer>();
		this.conn = conn;
	}
	
	/**
	 * Close this database object and discard the prepared statement
	 * embedded inside if any. 
	 */
	public void close() {
		if(this.stmt != null) {
			try {
				this.stmt.close();
			} catch (SQLException e) {
				Logger.log(LogLevel.ERROR_CRITICAL, "Could not close prepared statement", e);
			}
		}
		// Do not use this SQL statement anyway afther the close. 
		this.stmt = null;
	}
	
	/**
	 * Set the SQL statement for this database object. You must use
	 * named parameters in the form of ':name' in your query. 
	 * @param SQL the SQL statement.
	 * @return true when the statement could be prepared.
	 */
	public boolean setSQL(String SQL) {
		String[] pcs = SQL.split(" ");
		StringBuilder indexedSQL = new StringBuilder();
		
		// Substitute named parameters to indexed parameters
		int i = 1;
		for(String fragment : pcs) {
			if(fragment.length() > 0 && fragment.startsWith(":")) {
				parameterMap.put(fragment.substring(1), i++);
				indexedSQL.append("? ");
			} else {
				indexedSQL.append(fragment);
				indexedSQL.append(" ");
			}
		}
		
		// Prepare the statement 
		try {
			this.stmt = this.conn.prepareStatement(indexedSQL.toString());
			return true;
		} catch (SQLException e) {
			Logger.log(LogLevel.ERROR_CRITICAL, "Could not prepare statement", e);
			return false;
		}
	}
	
	/**
	 * Set a String value in the SQL statement
	 * @param key the key of the value.
	 * @param value the value itself. 
	 * @return the DbObject.
	 */
	public DbObject setValue(String key, String value) {
		try {
			this.stmt.setString(parameterMap.get(key), value);
		} catch (SQLException e) {
			Logger.log(LogLevel.ERROR_CRITICAL, "Could not set value in SQL statement", e);
		}
		
		return this;
	}
	
	/**
	 * Set an integer value in the SQL statement
	 * @param key the key of the value.
	 * @param value the value itself. 
	 * @return the DbObject.
	 */
	public DbObject setValue(String key, int value) {
		try {
			this.stmt.setInt(parameterMap.get(key), value);
		} catch (SQLException e) {
			Logger.log(LogLevel.ERROR_CRITICAL, "Could not set value in SQL statement", e);
		}
		
		return this;
	}
	
	/**
	 * Set a long value in the SQL statement
	 * @param key the key of the value.
	 * @param value the value itself. 
	 * @return the DbObject.
	 */
	public DbObject setValue(String key, long value) {
		try {
			this.stmt.setLong(parameterMap.get(key), value);
		} catch (SQLException e) {
			Logger.log(LogLevel.ERROR_CRITICAL, "Could not set value in SQL statement", e);
		}
		
		return this;
	}
	
	/**
	 * Set a double value in the SQL statement
	 * @param key the key of the value.
	 * @param value the value itself. 
	 * @return the DbObject.
	 */
	public DbObject setValue(String key, double value) {
		try {
			this.stmt.setDouble(parameterMap.get(key), value);
		} catch (SQLException e) {
			Logger.log(LogLevel.ERROR_CRITICAL, "Could not set value in SQL statement", e);
		}
		
		return this;
	}
	
	/**
	 * Set a Date value in the SQL statement
	 * @param key the key of the value.
	 * @param value the value itself. 
	 * @return the DbObject.
	 */
	public DbObject setValue(String key, Date value) {
		try {
			this.stmt.setDate(parameterMap.get(key), new java.sql.Date(value.getTime()));
		} catch (SQLException e) {
			Logger.log(LogLevel.ERROR_CRITICAL, "Could not set value in SQL statement", e);
		}
		
		return this;
	}
	
	/**
	 * Execute a select statement and get the result. 
	 * @return the QueryResult when there is one, null on error.
	 */
	public QueryResult executeSelect() {
		QueryResult result = new QueryResult();
		
		if (this.stmt == null)  {
			System.out.println("SQL.getResults: no SQL statement provided");
			Logger.log(LogLevel.WARNING, "Requested QueryResult from database object without prepared statement");
			return null;
		}

		try {
			ResultSet rs = this.stmt.executeQuery();	
			rs.beforeFirst();

			// Count the number of table columns
			ResultSetMetaData meteData = rs.getMetaData();
			int colCount = meteData.getColumnCount();

			// Place all records in the result. 
			while (rs.next()) {
				TableRecord record = new TableRecord();

				for (int i=1; i<=colCount; i++) {
					Object obj = rs.getObject(i);
					if (obj != null) {
						record.put(meteData.getColumnLabel(i), obj);
					}
				}
				result.add(record);
			}		
			
			// Close the ResultSet
			rs.close();

		} catch(Exception e) {
			Logger.log(LogLevel.ERROR_CRITICAL, "Could not fetch results for: " + stmtToString(this.stmt), e);
			return null;
		}
		
		this.close();
		return result;
	}
	
	/**
	 * Execute a select statement and get the result. 
	 * @return true when the execution was successful, false else. 
	 */
	public boolean execute() {
		if (this.stmt == null)  {
			System.out.println("SQL.getResults: no SQL statement provided");
			Logger.log(LogLevel.WARNING, "Requested QueryResult from database object without prepared statement");
			return false;
		}

		try {
			this.stmt.executeUpdate();

		} catch(Exception e) {
			Logger.log(LogLevel.ERROR_CRITICAL, "Could not fetch results for: " + stmtToString(this.stmt), e);
			return false;
		}
		
		this.close();
		return true;
	}
	
}
