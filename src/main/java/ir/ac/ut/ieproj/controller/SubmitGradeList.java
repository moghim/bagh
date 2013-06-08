package ir.ac.ut.ieproj.controller;

import java.util.Date;

import ir.ac.ut.iecommon.exceptions.ProfNotFoundException;
import ir.ac.ut.iecommon.time.Clock;
import ir.ac.ut.ieproj.database.DBConnector;
import ir.ac.ut.ieproj.domain.Department;
import ir.ac.ut.ieproj.domain.Professor;
import ir.ac.ut.ieproj.domain.Term;
import ir.ac.ut.ieproj.exception.termNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SubmitGradeList {

	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		System.out.println("SubmitGradeList.java starts .");
		Professor p = null;
		Term t = null;
		request.setAttribute("err", "0");
		try {
			p = Department.getProfessor(Integer.parseInt(request.getUserPrincipal().getName()));
			t = DBConnector.getCurrentTerm();
			if(!t.isSubmitGradeTime(new Date(Clock.getCurrentTimeMillis()))) {
				System.out.println("choice for SubmitGrade was bad.");
				request.setAttribute("teachingOffers", t.teachingOfferings(p));
				request.setAttribute("err", 1);
				request.setAttribute("errMessage", "Grade Submit time has been passed or not come yet .");
				return "/WEB-INF/prof/ProfessorMain.jsp";
			}
			/*
			String choice = request.getParameter("choice");
			request.setAttribute("sid", p.getId());
			request.setAttribute("name", p.getFirstName()+" "+p.getLastName());
			if(choice != null && choice.equals("home")) {
				System.out.println("choice for home .");
				request.setAttribute("teachingOffers", t.teachingOfferings(p));
				request.setAttribute("err", "0");
				return "professor-main.jsp";
			}
			String offer = request.getParameter("offer");
			String[] temps = offer.split(",");
			String offerID = temps[0].substring(1);
			o = Department.getOffering(Integer.parseInt(offerID));
			System.out.println("choice for submit grading .");
			if(!t.isSubmitGradeTime(new Date(Clock.getCurrentTimeMillis()))) {
				System.out.println("choice for submit was bad.");
				request.setAttribute("teachingOffers", t.teachingOfferings(p));
				request.setAttribute("err", 1);
				request.setAttribute("errMessage", "Grade Submit time has been passed or not come yet .");
				return "submit-grade-list.jsp";
			}
			else {
				System.out.println("choice for submit grade was good .");
				request.setAttribute("students", Department.studentsInOffering(o));
				request.setAttribute("offering", o.getId());
				request.setAttribute("err", 0);
				return "submit-grade.jsp";
			}
		*/
		} catch (NumberFormatException e) {
			request.setAttribute("err", 1);
			request.setAttribute("errMessage", e.getMessage());
			e.printStackTrace();
		}  catch (termNotFoundException e) {
			request.setAttribute("err", 1);
			request.setAttribute("errMessage", e.getMessage());
			e.printStackTrace();
		} catch (ProfNotFoundException e) {
			request.setAttribute("err", 1);
			request.setAttribute("errMessage", e.getMessage());
			e.printStackTrace();
		}
		System.out.println("SubmitGradeList set data .");
		request.setAttribute("teachingOffers", t.teachingOfferings(p));
		//request.setAttribute("errMessage", "Unknown Error ... ");
		return "/WEB-INF/prof/SubmitGradeList.jsp";
	}
}