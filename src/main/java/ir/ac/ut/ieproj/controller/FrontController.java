package ir.ac.ut.ieproj.controller;
import ir.ac.ut.ieproj.database.DBConnector;
import ir.ac.ut.ieproj.domain.Department;
import ir.ac.ut.ieproj.domain.Person;
import ir.ac.ut.ieproj.domain.Professor;
import ir.ac.ut.ieproj.domain.Student;
import ir.ac.ut.ieproj.exception.requestException;

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
			System.out.println("FrontController start : ");
			//String path = request.getServletPath();
			//System.out.println("path:"+path+"#");
			String url = request.getRequestURI();
			//System.out.println("url:"+url+"#");
			int jspIndex = url.indexOf(".jsp");
			int actionIndex = url.indexOf(".action");
			String className = null;
			if(jspIndex != -1) {
				System.out.println("jsp ast");
				className = url.substring(11, jspIndex);
			}
			else if(actionIndex != -1) {
				System.out.println("action ast");
				className = url.substring(11 , actionIndex);
			}
			else {
				//throw new Exception("Not good path in URL .");
				if (request.getUserPrincipal()!=null){
					Person p = DBConnector.getPerson(Integer.parseInt(request.getUserPrincipal().getName()));
					if (p instanceof Student)
						className = "StudentMain";
					else if(p instanceof Professor)
						className = "ProfessorMain";
					else {
						throw new requestException("Not professor , nor student !!!");
					}
				}
				else 
					className = "StudentMain";
			}
			System.out.println("FrontController class name : "+className);
			Class<?> ctrlClass = null;
			ctrlClass = Class.forName("ir.ac.ut.ieproj.controller." + className);
			Method m = ctrlClass.getMethod("execute", HttpServletRequest.class, HttpServletResponse.class);
			if(request.getSession().getAttribute("name") == null && request.getUserPrincipal()!=null) {
				Person p = Department.getPerson(Integer.parseInt(request.getUserPrincipal().getName()));
				request.getSession().setAttribute("name", p.getFirstName()+" "+p.getLastName());
			}			
			String forward = (String)m.invoke(ctrlClass.newInstance(), request, response);
			System.out.println("#before forward to : "+forward);
			request.getRequestDispatcher(forward).forward(request, response);
			System.out.println("After All !!!");
		} catch (Exception ex) {
			response.setContentType("text/html");
			response.getOutputStream().println("Url Pattern is not valid , Please try later !" + ex.getMessage());
			ex.printStackTrace();
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("hooshang");
		doGet(request, response);
	}
}