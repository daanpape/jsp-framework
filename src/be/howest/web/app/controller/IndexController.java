package be.howest.web.app.controller;

import be.howest.web.framework.controller.Controller;
import be.howest.web.framwork.log.LogLevel;
import be.howest.web.framwork.log.Logger;

public class IndexController extends Controller {
	
	/**
	 * Show application index page. 
	 * @return the application index page. 
	 */
	public String getIndex() {
		Logger.log(LogLevel.DEBUG, "Index page was requested");
		return "/app/index.jsp";
	}
	
	/**
	 * Show application index page. 
	 * @return the application index page. 
	 */
	public String getIndex(int id) {
		Logger.log(LogLevel.DEBUG, "Index page was requested with id: " + id);
		return "/app/index.jsp";
	}
	
	public String getTekst() {
		Logger.log(LogLevel.DEBUG, "Tekst page was requested");
		return "/app/index.jsp";
	}
}
