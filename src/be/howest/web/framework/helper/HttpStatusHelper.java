package be.howest.web.framework.helper;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import be.howest.web.framwork.log.Logger;

/**
 * Helper methods to generate error pages. 
 * @author Daan Pape
 */
public class HttpStatusHelper {
	
	/**
	 * Generate an internal server error page. 
	 * @param request the HTTP request.
	 * @param response the HTTP response.
	 * @param ex the exception that cause the internal server error. 
	 * @throws ServletException
	 * @throws IOException
	 */
	public static void generateInternalServerError(HttpServletRequest request, HttpServletResponse response, Throwable ex) throws ServletException, IOException {
		request.setAttribute("error", HttpFormatHelper.newLineToBr(Logger.exceptionToString(ex)));
		RequestDispatcher dispatcher = request.getRequestDispatcher("/howestFramework/500.jsp");
		dispatcher.forward(request, response);
    }
    
	/**
	 * Generate a bad request error page. 
	 * @param request the HTTP request.
	 * @param response the HTTP response.
	 * @param info information about why this is a bad request. 
	 * @param ex the exception that caused this to be a bad request.
	 * @throws ServletException
	 * @throws IOException
	 */
    public static void generateBadRequest(HttpServletRequest request, HttpServletResponse response, String info, Throwable ex) throws ServletException, IOException {
    	response.setStatus(400);
    	request.setAttribute("info", info);
    	request.setAttribute("error", HttpFormatHelper.newLineToBr(Logger.exceptionToString(ex)));
		RequestDispatcher dispatcher = request.getRequestDispatcher("/howestFramework/400.jsp");
		dispatcher.forward(request, response);
    }
    
    /**
	 * Generate a bad request error page. 
	 * @param request the HTTP request.
	 * @param response the HTTP response.
	 * @param info information about why this is a bad request.
	 * @throws ServletException
	 * @throws IOException
	 */
    public static void generateBadRequest(HttpServletRequest request, HttpServletResponse response, String info) throws ServletException, IOException {
    	response.setStatus(400);
    	request.setAttribute("info", info);
    	request.setAttribute("error", "");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/howestFramework/400.jsp");
		dispatcher.forward(request, response);
    }
    
    /**
     * Generate a resource not found error page.
     * @param request the HTTP request.
     * @param response the HTTP response.
     * @param uri what wasn't found. 
     * @throws ServletException
     * @throws IOException
     */
    public static void generateNotFound(HttpServletRequest request, HttpServletResponse response, String uri) throws ServletException, IOException {
    	response.setStatus(404);
    	request.setAttribute("uri", uri);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/howestFramework/404.jsp");
		dispatcher.forward(request, response);
    }
}
