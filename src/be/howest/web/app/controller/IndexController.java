package be.howest.web.app.controller;

import be.howest.web.framework.controller.Controller;
import be.howest.web.framework.log.LogLevel;
import be.howest.web.framework.log.Logger;

public class IndexController extends Controller {
	
	/**
	 * Show application index page. 
	 * @return the application index page. 
	 */
	public String getIndex() {
		Logger.log(LogLevel.DEBUG, "Index page was requested");
		return "/app/index.jsp";
	}
}
