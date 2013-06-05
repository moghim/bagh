package ir.ac.ut.ieproj.controller;
import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class FrontController extends HttpServlet{

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String className = request.getServletPath().substring(1, request.getServletPath().indexOf(".action"));
		System.out.println("FrontController class name : "+className);
		try {
			Class<?> ctrlClass = null;
			if(className == null) {
				ctrlClass = Class.forName("ir.ac.ut.ieproj.controller.StudentLogin");
			}
			else {
				ctrlClass = Class.forName("ir.ac.ut.ieproj.controller." + className);
			}
			Method m = ctrlClass.getMethod("execute", HttpServletRequest.class, HttpServletResponse.class);
			String forward = (String)m.invoke(ctrlClass.newInstance(), request, response);
			request.getRequestDispatcher(forward).forward(request, response);
		} catch (Exception ex) {
			response.setContentType("text/html");
			response.getOutputStream().println("There is an error in loading the page .\nPlease contact +989354428397 ." + ex.getMessage());
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}