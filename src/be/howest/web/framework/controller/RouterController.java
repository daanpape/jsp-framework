package be.howest.web.framework.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.howest.web.framework.helper.HttpStatusHelper;
import be.howest.web.framework.log.LogLevel;
import be.howest.web.framework.log.Logger;

/**
 * Servlet implementation class RouterController
 */
public class RouterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RouterController() {
        super();
    }
    
    /**
     * Try to find a controller matching the name.  
     * @param name the controller name. 
     * @return the controller class when found, null else.
     */
    private Controller createController(String name) {
    	try {
 	       Class<?> controller = Class.forName("be.howest.web.app.controller." + name);
 	       return (Controller) controller.newInstance();
    	} catch (Exception e) {
  	       Logger.log(LogLevel.ERROR_CRITICAL, "Could not create controller '"+ name +"', exception: " + e.toString());
  	       return null;
    	}
    }
    
    /**
     * Helper method to handle a request and return the controller request. 
     * @param request the HTTP request. 
     * @param response the HTTP response.
     * @return the ControllerRequest containing the controller that will handle request if found. 
     * @throws ServletException
     * @throws IOException
     */
    protected ControllerRequest handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo();
		String controller = "";
		String action = "";
		int id = -1;
		
		try {
			String[] pathSections = path.split("/");
			int pathLen = pathSections.length;
			
			if(pathLen > 1) {
				String ctlName = pathSections[1].toLowerCase();
				if(ctlName.length() > 0) {
					ctlName = Character.toUpperCase(ctlName.charAt(0)) + ctlName.substring(1);
				}
				controller = ctlName + "Controller";
				
				if(pathLen > 2) {
					action = pathSections[2];
				}
				
				if(pathLen > 3) {
					id = Integer.parseInt(pathSections[3]);
				}
			}
			
			// Allow to pass the action as a POST/GET parameter
			String actionParam = request.getParameter("action");
			if(actionParam != null) {
				action = actionParam;
			}
			
			// Allow to pass the id as a POST/GET parameter
			String idParam = request.getParameter("id");
			if(idParam != null) {
				id = Integer.parseInt(idParam);
			}
			
			// Normalize action case to make URI case insensitive
			action = action.toLowerCase();
			if(action.length() > 0) {
				action = Character.toUpperCase(action.charAt(0)) + action.substring(1);
			}
			
			Logger.log(LogLevel.INFO, "RouterController handling request for controller: " + controller + ", action: " + action + ", id: " + id);
			
			// Return this controller request
			return new ControllerRequest(this.createController(controller), controller, action, id, path);

		} catch (Exception e) {
			Logger.log(LogLevel.ERROR_CRITICAL, "RouterController received exception: ", e);
			HttpStatusHelper.generateBadRequest(request, response, "The request could not be processed by the router, is ID an integer?", e);
			return null;
		}	
    }

	/**
	 * Handle GET request
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ControllerRequest ctlRequest = this.handleRequest(request, response);
		if(ctlRequest == null) {
			return;
		}
		
		Controller ctl = ctlRequest.getController();
		if(ctl != null) {
			ctl.init(request, response);
			String view = ctl.handleGet(ctlRequest.getAction(), ctlRequest.getId());
			if(view == null) {
				return;
			}
			ctl.forward(view);
		} else {
			String error = "RouterController could not find matching controller for request: " + ctlRequest.getPath();
			Logger.log(LogLevel.ERROR_CRITICAL, error);
			HttpStatusHelper.generateNotFound(request, response, ctlRequest.getPath());
		}
	}

	/**
	 * Handle a HTTP POST request
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ControllerRequest ctlRequest = this.handleRequest(request, response);
		if(ctlRequest == null) {
			return;
		}
		
		Controller ctl = ctlRequest.getController();
		if(ctl != null) {
			ctl.init(request, response);
			String view = ctl.handlePost(ctlRequest.getAction(), ctlRequest.getId());
			if(view == null) {
				return;
			}
			ctl.forward(view);
		} else {
			String error = "RouterController could not find matching controller for request: " + ctlRequest.getPath();
			Logger.log(LogLevel.ERROR_CRITICAL, error);
			HttpStatusHelper.generateNotFound(request, response, ctlRequest.getPath());
		}
	}
	
	/**
	 * Handle a HTTP PUT request
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ControllerRequest ctlRequest = this.handleRequest(request, response);
		if(ctlRequest == null) {
			return;
		}
		
		Controller ctl = ctlRequest.getController();
		if(ctl != null) {
			ctl.init(request, response);
			String view = ctl.handlePut(ctlRequest.getAction(), ctlRequest.getId());
			if(view == null) {
				return;
			}
			ctl.forward(view);
		} else {
			String error = "RouterController could not find matching controller for request: " + ctlRequest.getPath();
			Logger.log(LogLevel.ERROR_CRITICAL, error);
			HttpStatusHelper.generateNotFound(request, response, ctlRequest.getPath());
		}
	}
	
	/**
	 * Handle a HTTP Delete request
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ControllerRequest ctlRequest = this.handleRequest(request, response);
		if(ctlRequest == null) {
			return;
		}
		
		Controller ctl = ctlRequest.getController();
		if(ctl != null) {
			ctl.init(request, response);
			String view = ctl.handleDelete(ctlRequest.getAction(), ctlRequest.getId());
			if(view == null) {
				return;
			}
			ctl.forward(view);
		} else {
			String error = "RouterController could not find matching controller for request: " + ctlRequest.getPath();
			Logger.log(LogLevel.ERROR_CRITICAL, error);
			HttpStatusHelper.generateNotFound(request, response, ctlRequest.getPath());
		}
	}
}
