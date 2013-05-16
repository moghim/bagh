package ir.ac.ut.ieproj.controller;

import ir.ac.ut.iecommon.exceptions.DeptLoadException;
import ir.ac.ut.ieproj.Boostan.Department;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class FrontController extends HttpServlet{
// extends HttpServlet
	// URLs must have the form /polling/ControllerClass.action
	// the execute() method of the ControllerClass will be called
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("salam man inja hastam .");
		//String path = getServletContext().getInitParameter("samplePath");
		//String user = getServletContext().getInitParameter("user");
		//String password = getServletContext().getInitParameter("password");
		//System.out.println("user: "+user+" password: "+password);
		//System.out.println("path : "+path);
		try {
			@SuppressWarnings("unused")
			Department d = Department.getInstance();
		} catch (DeptLoadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String className = request.getServletPath().substring(1, request.getServletPath().indexOf(".action"));
		//System.out.println("class name : "+className);
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