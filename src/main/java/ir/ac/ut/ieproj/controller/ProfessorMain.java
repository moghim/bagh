package ir.ac.ut.ieproj.controller;

import ir.ac.ut.iecommon.exceptions.ProfNotFoundException;
import ir.ac.ut.ieproj.database.DBConnector;
import ir.ac.ut.ieproj.domain.Department;
import ir.ac.ut.ieproj.domain.Professor;
import ir.ac.ut.ieproj.domain.Term;
import ir.ac.ut.ieproj.exception.termNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProfessorMain {

	public String execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("ProfessorMain.java start .");
		Professor p = null;
		Term t = null;
		try {
			p = Department.getProfessor(Integer.parseInt(request.getUserPrincipal().getName()));
			t = DBConnector.getCurrentTerm();
			/*String choice = request.getParameter("choice");
			request.setAttribute("name", p.getFirstName()+" "+p.getLastName());
			if(choice != null && choice.equals("submitGrade")) {
				System.out.println("choice for submitGrade .");
				if(!t.isSubmitGradeTime(new Date(Clock.getCurrentTimeMillis()))) {
					System.out.println("choice for submitGrade was bad .");
					request.setAttribute("teachingOffers", t.teachingOfferings(p));
					request.setAttribute("err", "1");
					request.setAttribute("errMessage", "Submit grade time is not came yet or has benn passed .");
					return "professor-main.jsp";
				}
				else {
					System.out.println("choice for submitGrade was good .");
					request.setAttribute("teachingOffers", t.teachingOfferings(p));
					request.setAttribute("err", 0);
					return "submit-grade-list.jsp";
				}
			}
			else if(choice.equals("withdraw")) {
				System.out.println("choice for Withdraw .");
				if(!t.isWithrawResponseTime(new Date(Clock.getCurrentTimeMillis()))) {
					System.out.println("choice for Withdraw was bad.");
					request.setAttribute("teachingOffers", t.teachingOfferings(p));
					request.setAttribute("err", 1);
					request.setAttribute("errMessage", "Term is not started or has been ended .");
					return "professor-main.jsp";
				}
				else {
					System.out.println("choice for Withdraw was good .");
					request.setAttribute("teachingOffers", t.teachingOfferings(p));
					request.setAttribute("err", 0);
					return "withdraw-response-list.jsp";
				}
				
			}*/
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  catch (termNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProfNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("Unknown error .");
		request.setAttribute("teachingOffers", t.teachingOfferings(p));
		request.setAttribute("err", "0");
		//request.setAttribute("errMessage", "Unknown Error ... ");
		
		return "/WEB-INF/prof/ProfessorMain.jsp";
	}
}