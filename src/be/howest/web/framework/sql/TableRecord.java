package be.howest.web.framework.sql;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;

/**
 * Represents a record from a database table. 
 * @author Daan Pape
 *
 */
public class TableRecord extends HashMap<String, Object>{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Construct a new table record.
	 */
	public TableRecord() {
		
	}
	
	/**
	 * Get a string value from this record given the column name. 
	 * @param column the name of the column to get the value from.
	 * @param def the default value to return when the column name is not found.
	 * @return the value if found, default otherways. 
	 */
	public String getString(String column, String def) {
		Object str = this.get(column);
		return str == null ? def : (String) str;
	}
	
	/**
	 * Get a string value from this record given the column name.
	 * @param column the name of the column to get the value from.
	 * @return the value if found, null otherways.
	 */
	public String getString(String column) {
		return this.getString(column, null);
	}
	
	/**
	 * Get a double value from this record given the column name.
	 * @param column the name of the column to get the value from.
	 * @param def the default value if it is not found or it is not a double. 
	 * @return the value if found, or default otherways. 
	 */
	public double getDouble(String column, double def) {
		Object num = this.get(column);
		
		if(num == null) {
			return def;
		}
		try {
			return (Double) num;
		} catch (ClassCastException e) {
			if(num instanceof BigDecimal) {
				return ((BigDecimal) num).doubleValue();
			}
			
			if(num instanceof String) {
				try {
					return Double.parseDouble((String) num);
				} catch (NumberFormatException ex) {
					return def;
				}
			}
			
			return def;
		}
	}
	
	/**
	 * Get a double value from this record given the column name.
	 * @param column the name of the column to get the value from.
	 * @return the value if found, or 0 otherways.
	 */
	public double getDouble(String column) {
		return this.getDouble(column, 0);
	}
	
	/**
	 * Get an integer value from this record given the column name.
	 * @param column the name of the column to get the value from.
	 * @param def the default value if it is not found or it is not an integer. 
	 * @return the value if found, or default otherways. 
	 */
	public int getInteger(String column, int def) {
		Object num = this.get(column);
		
		if(num == null) {
			return def;
		}
		
		try {
			return (Integer) num;
		} catch (ClassCastException ex) {
			if(num instanceof Long) {
				return (int) ((long) num);
			}
			
			if(num instanceof BigInteger) {
				return ((BigInteger) num).intValue();
			}
			
			if(num instanceof BigDecimal) {
				return ((BigDecimal) num).intValue();
			}
			
			if(num instanceof String) {
				try {
					return Integer.parseInt((String) num);
				} catch (NumberFormatException e) {
					return def;
				}
			}
			
			return def;
		}
	} 
	
	/**
	 * Get an integer value from this record given the column name.
	 * @param column the name of the column to get the value from.
	 * @return the value if found, or 0 otherways. 
	 */
	public int getInteger(String column) {
		return this.getInteger(column, 0);
	}
	
	/**
	 * Get a long value from this record given the column name.
	 * @param column the name of the column to get the value from.
	 * @param def the default value if it is not found or it is not a long. 
	 * @return the value if found, or default otherways. 
	 */
	public long getLong(String column, long def) {
		Object num = this.get(column);
		
		if(num == null) {
			return def;
		}
		
		try {
			return (Long) num;
		} catch (ClassCastException ex) {
			if(num instanceof Integer) {
				return (long) ((int) num);
			}
			
			if(num instanceof BigInteger) {
				return ((BigInteger) num).longValue();
			}
			
			if(num instanceof BigDecimal) {
				return ((BigDecimal) num).longValue();
			}
			
			if(num instanceof String) {
				try {
					return Long.parseLong((String) num);
				} catch (NumberFormatException e) {
					return def;
				}
			}
			
			return def;
		}
	}
	
	/**
	 * Get a long value from this record given the column name.
	 * @param column the name of the column to get the value from.
	 * @return the value if found, or 0 otherways. 
	 */
	public long getLong(String column) {
		return this.getLong(column, 0);
	}
	
}
