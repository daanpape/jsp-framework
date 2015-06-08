package be.howest.web.framework.sql;

import java.sql.PreparedStatement;
import java.util.ArrayList;

/**
 * Represents an SQL database table
 * @author Daan Pape
 *
 */
public class TableResult extends ArrayList<TableRecord>{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Construct a new table record.
	 */
	public TableResult() {
		super();
	}
	
	public static String pStmtToString(PreparedStatement stmt) {
		String sql = stmt.toString();
		int sIndex = sql.indexOf(':');
		
		if(sql.startsWith("com.") && sIndex > 0) {
			return sql.substring(sIndex + 2);
		} else {
			return sql;
		}
	}
}
