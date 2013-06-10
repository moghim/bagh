package ir.ac.ut.ieproj.controller;

import ir.ac.ut.iecommon.exceptions.ProfNotFoundException;
import ir.ac.ut.iecommon.time.Clock;
import ir.ac.ut.ieproj.database.DBConnector;
import ir.ac.ut.ieproj.domain.Department;
import ir.ac.ut.ieproj.domain.Professor;
import ir.ac.ut.ieproj.domain.Term;
import ir.ac.ut.ieproj.exception.termNotFoundException;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WithdrawResponseList {

	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		System.out.println("WithdrawResponseList.java starts .");
		Professor p = null;
		Term t = null;
		request.setAttribute("err", "0");
		try {
			p = Department.getProfessor(Integer.parseInt(request.getUserPrincipal().getName()));
			t = DBConnector.getCurrentTerm();
			if(!t.isWithrawResponseTime(new Date(Clock.getCurrentTimeMillis()))) {
				System.out.println("choice for Withdraw was bad.");
				request.setAttribute("teachingOffers", t.teachingOfferings(p));
				request.setAttribute("err", 1);
				request.setAttribute("errMessage", "Withdraw response time has been passed or not come yet .");
				return "/WEB-INF/prof/ProfessorMain.jsp";
			}
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
		System.out.println("WithdrawResponseList set data .");
		request.setAttribute("teachingOffers", t.teachingOfferings(p));
		return "/WEB-INF/prof/WithdrawResponseList.jsp";
	}
}
