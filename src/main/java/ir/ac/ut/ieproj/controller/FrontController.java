package ir.ac.ut.ieproj.controller;
import ir.ac.ut.ieproj.domain.Department;
import ir.ac.ut.ieproj.domain.Person;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class FrontController extends HttpServlet{

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {	
			String path = request.getServletPath();
			int jspIndex = path.indexOf(".jsp");
			int actionIndex = path.indexOf(".action");
			String className = null;
			if(jspIndex != -1) {
				className = path.substring(1, jspIndex);
			}
			else if(actionIndex != -1) {
				className = path.substring(1, actionIndex);
			}
			else {
				throw new Exception("Not good path in URL .");
			}
			System.out.println("FrontController class name : "+className);
			Class<?> ctrlClass = null;
			ctrlClass = Class.forName("ir.ac.ut.ieproj.controller." + className);
			Method m = ctrlClass.getMethod("execute", HttpServletRequest.class, HttpServletResponse.class);

			if(request.getSession().getAttribute("name") == null) {
				Person p = Department.getPerson(Integer.parseInt(request.getUserPrincipal().getName()));
				request.getSession().setAttribute("name", p.getFirstName()+" "+p.getLastName());
			}

			String forward = (String)m.invoke(ctrlClass.newInstance(), request, response);
			request.getRequestDispatcher(forward).forward(request, response);
		} catch (Exception ex) {
			response.setContentType("text/html");
			response.getOutputStream().println("The site is under construction , Please try later !" + ex.getMessage());
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}