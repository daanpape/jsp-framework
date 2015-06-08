package be.howest.web.app.controller;

import be.howest.web.framework.controller.Controller;
import be.howest.web.framework.sql.ConnectionManager;
import be.howest.web.framework.sql.DbObject;
import be.howest.web.framework.sql.QueryResult;

public class LanguageController extends Controller {
	
	/**
	 * Get a list of languages currently in the database.
	 */
	public String getLanguages() {
		DbObject sql = new DbObject(ConnectionManager.getConnection(this.context));
		sql.setSQL("SELECT * FROM languages");
		
		QueryResult result = sql.executeSelect();
		System.out.println("We have query results");
		this.setJspVariable("languages", result);
		return "/app/language-list.jsp";
	}
}
