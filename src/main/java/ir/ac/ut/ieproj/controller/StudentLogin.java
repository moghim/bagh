package ir.ac.ut.ieproj.controller;

import ir.ac.ut.ieproj.domain.Department;
import ir.ac.ut.ieproj.domain.Person;
import ir.ac.ut.ieproj.domain.Professor;
import ir.ac.ut.ieproj.domain.Student;
import ir.ac.ut.ieproj.domain.Term;
import ir.ac.ut.ieproj.exception.PersonNotFoundException;
import ir.ac.ut.ieproj.exception.autenticationException;
import ir.ac.ut.ieproj.exception.termNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;

public class StudentLogin {

	public String execute(HttpServletRequest request, HttpServletResponse response) {
		//System.out.println("StudentLogin controller come .");
		//System.out.println("sid : "+request.getParameter("sid"));
		
		Person person = null;
		Term t= null;
		try {
			String sid = request.getParameter("sid");
			String password = request.getParameter("password");
			request.setAttribute("sid",sid);
			person = Department.getPerson(Integer.parseInt(sid));
			t = Department.getCurrentTerm();
			if(!person.getPassword().equals(password))
				throw new autenticationException("Username or password is incorrect .");
		}
		catch (PersonNotFoundException e) {
			e.printStackTrace();
			request.setAttribute("hasError", 1);
			request.setAttribute("errMessage", e.getMessage());
			return "student-login.jsp";
			
		} catch (termNotFoundException e) {
			e.printStackTrace();
			request.setAttribute("hasError", 1);
			request.setAttribute("errMessage", e.getMessage());
			return "student-login.jsp";
		} catch (autenticationException e) {
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
		Student s = null;
		Professor p = null;
		request.setAttribute("name", (person.getFirstName()+" "+person.getLastName()));
		request.setAttribute("hasError", 0);
		if(person instanceof Student) {
			s = (Student) person;
			request.setAttribute("inProgressOffers", t.inProgressOfferings(s));
			return "student-main.jsp";
		}
		else {
			p = (Professor) person;
			request.setAttribute("teachingOffers", t.teachingOfferings(p));
			return "professor-main.jsp";
		}
	}
}
