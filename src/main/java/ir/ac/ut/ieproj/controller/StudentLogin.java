package ir.ac.ut.ieproj.controller;

import ir.ac.ut.iecommon.exceptions.StudentNotFoundException;
import ir.ac.ut.ieproj.domain.Department;
import ir.ac.ut.ieproj.domain.Student;
import ir.ac.ut.ieproj.domain.Term;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StudentLogin {

	public String execute(HttpServletRequest request, HttpServletResponse response) {
		//System.out.println("StudentLogin controller come .");
		//System.out.println("sid : "+request.getParameter("sid"));
		Student s = null;
		Term t= null;
		try {
			String sid = request.getParameter("sid");
			s = Department.getStudent(Integer.parseInt(sid));
			t = Department.getCurrentTerm();
		}
		catch (StudentNotFoundException e) {
			e.printStackTrace();
			request.setAttribute("hasError", 1);
			request.setAttribute("errMessage", e.getMessage());
			return "student-login.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("hasError", 1);
			request.setAttribute("errMessage", e.getMessage());
			return "student-login.jsp";
		}
		request.setAttribute("name", (s.getFirstName()+" "+s.getLastName()));
		request.setAttribute("inProgress", t.inProgressOfferings(s));
		//request.setAttribute("canTake", t.notInProgressOfferings(s));
		request.setAttribute("hasError", 0);
		return "student-main.jsp";
	}
}
