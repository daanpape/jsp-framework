package be.howest.web.framework.controller;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.howest.web.framework.helper.HttpStatusHelper;
import be.howest.web.framework.log.LogLevel;
import be.howest.web.framework.log.Logger;

/**
 * Base class for any framework controller. 
 * @author Daan Pape
 *
 */
public class Controller {
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;
	
	/**
	 * Initialise the abstract controller instance. 
	 * @param request the HTTP request. 
	 * @param response the HTTP response.
	 */
	public void init(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.session = request.getSession();
		
		this.response.setCharacterEncoding("UTF-8");		
	}
	
	
	private String handleRequest(String type, String action, int id) throws ServletException, IOException {
		Method method = null;
		
		//Try to find method matching the action
		try {
			if(id == -1) {
				method = this.getClass().getMethod(type + action);
				return (String) method.invoke(this);
			} else {
				method = this.getClass().getMethod(type + action, int.class);
				return (String) method.invoke(this, id);
			}
		} catch (Exception ex) {
			Logger.log(LogLevel.ERROR_CRITICAL, this.getClass().getSimpleName() + " doesnt have a handler for action: " + action);
			return null;
		}
	}

	/**
	 * Handle a GET request with a given action and id and return
	 * the JSP view to show to the end user. 
	 * @param action the action to execute. 
	 * @param id the id. 
	 * @param the JSP view name to display
	 */
	public String handleGet(String action, int id) throws ServletException, IOException {
		String result = this.handleRequest("get", action, id);
		if(result != null) {
			return result;
		} else {
			HttpStatusHelper.generateBadRequest(request, response, "Action '" + action + "' does not have a handler for GET requests in " + this.getClass().getSimpleName());
			return null;
		}
	}
	
	/**
	 * Handle a POST request with a given action and id and return
	 * the JSP view to show to the end user. 
	 * @param action the action to execute. 
	 * @param id the id. 
	 * @param the JSP view name to display
	 */
	public String handlePost(String action, int id) throws ServletException, IOException {
		String result = this.handleRequest("get", action, id);
		if(result != null) {
			return result;
		} else {
			HttpStatusHelper.generateBadRequest(request, response, "Action '" + action + "' does not have a handler for POST requests in " + this.getClass().getSimpleName());
			return null;
		}
	}
	
	/**
	 * Handle a PUT request with a given action and id and return
	 * the JSP view to show to the end user. 
	 * @param action the action to execute. 
	 * @param id the id. 
	 * @param the JSP view name to display
	 */
	public String handlePut(String action, int id) throws ServletException, IOException 
	{
		String result = this.handleRequest("get", action, id);
		if(result != null) {
			return result;
		} else {
			HttpStatusHelper.generateBadRequest(request, response, "Action '" + action + "' does not have a handler for PUT requests in " + this.getClass().getSimpleName());
			return null;
		}
	}
	
	/**
	 * Handle a DELETE request with a given action and id and return
	 * the JSP view to show to the end user. 
	 * @param action the action to execute. 
	 * @param id the id. 
	 * @param the JSP view name to display
	 */
	public String handleDelete(String action, int id) throws ServletException, IOException
	{
		String result = this.handleRequest("get", action, id);
		if(result != null) {
			return result;
		} else {
			HttpStatusHelper.generateBadRequest(request, response, "Action '" + action + "' does not have a handler for DELETE requests in " + this.getClass().getSimpleName());
			return null;
		}
	}
	
	/**
	 * Forward a request to the given jsp.
	 * @param jsp the jsp file to forward to.
	 */
	public void forward(String jsp) {
		try {
			RequestDispatcher aReqDispatcher = this.request.getRequestDispatcher(jsp);
			aReqDispatcher.forward(this.request, this.response);
		} catch(Exception e) {
			Logger.log(LogLevel.ERROR_CRITICAL, "AbstractController could not forward to " + jsp + ", exception: " + Logger.exceptionToString(e));
		}
	}
	
	/**
	 * Store a value in the current HTTP session. 
	 * @param key the key to retrieve the value with. 
	 * @param value the object to store. 
	 */
	public void setInSession(String key, Object value) {
		this.session.setAttribute(key, value);
	}
	
	/**
	 * Retreive a value stored earlier in the current HTTP session. 
	 * @param key the key to retreive the value with. 
	 * @return the object if found, null else.
	 */
	public Object getFromSession(String key) {
		return this.session.getAttribute(key);
	}
	
	/**
	 * Set a value for the JSP in the current request. 
	 * @param key the key to retrieve the value with. 
	 * @param value the object to set. 
	 */
	public void setJspVariable(String key, Object value) {
		this.request.setAttribute(key, value);
	}
	
	/**
	 * Get a request parameter as a String or return the
	 * default if not found. 
	 * @param key the parameter key. 
	 * @param def the default value to return when the key is not found. 
	 * @return the value where the key points to or the default value. 
	 */
	public String getStringParameter(String key, String def) {
		String value = this.request.getParameter(key);
		return value == null ? def : value;
	}
	
	/**
	 * Get a request parameter as integer or return the 
	 * default if not found. 
	 * @param key the parameter key.
	 * @param def the default value to return when the key is not found.
	 * @return the value where the key points to or the default value. 
	 */
	public int getIntegerParameter(String key, int def) {
		String value = this.request.getParameter(key);
		
		if(value == null) {
			return def;
		}
		
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException ex) {
			return def;
		}
	}
	
	/**
	 * Get a request parameter as long or return the 
	 * default if not found. 
	 * @param key the parameter key.
	 * @param def the default value to return when the key is not found.
	 * @return the value where the key points to or the default value. 
	 */
	public long getLongParameter(String key, long def) {
		String value = this.request.getParameter(key);
		
		if(value == null) {
			return def;
		}
		
		try {
			return Long.parseLong(value);
		} catch (NumberFormatException ex) {
			return def;
		}
	}
	
	/**
	 * Get a request parameter as double or return the 
	 * default if not found. 
	 * @param key the parameter key.
	 * @param def the default value to return when the key is not found.
	 * @return the value where the key points to or the default value. 
	 */
	public double getLongParameter(String key, double def) {
		String value = this.request.getParameter(key);
		
		if(value == null) {
			return def;
		}
		
		try {
			return Double.parseDouble(value);
		} catch (NumberFormatException ex) {
			return def;
		}
	}
	
	/**
	 * Get a request parameter as Date or return the 
	 * default if not found. 
	 * @param key the parameter key.
	 * @param def the default value to return when the key is not found.
	 * @return the value where the key points to or the default value. 
	 */
	public Date geDateParameter(String key, Date def) {
		String value = this.request.getParameter(key);
		
		if(value == null) {
			return def;
		}
		
		SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
        try {
        	return date.parse(value);
        } catch (Exception e) {
        	return def;
        }
	}
	
	/**
	 * Get a request parameter as Date or return the 
	 * default if not found. 
	 * @param key the parameter key.
	 * @param format the Date format to accept.
	 * @param def the default value to return when the key is not found.
	 * @return the value where the key points to or the default value. 
	 */
	public Date geDateParameter(String key, String format, Date def) {
		String value = this.request.getParameter(key);
		
		if(value == null) {
			return def;
		}
		
        try {
        	SimpleDateFormat date = new SimpleDateFormat(format);
        	return date.parse(value);
        } catch (Exception e) {
        	return def;
        }
	}
}
