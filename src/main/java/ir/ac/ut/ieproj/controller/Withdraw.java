package ir.ac.ut.ieproj.controller;

import java.util.Date;

import ir.ac.ut.iecommon.exceptions.OfferingNotFoundException;
import ir.ac.ut.iecommon.exceptions.StudentNotFoundException;
import ir.ac.ut.iecommon.exceptions.WithdrawException;
import ir.ac.ut.iecommon.time.Clock;
import ir.ac.ut.ieproj.database.DBConnector;
import ir.ac.ut.ieproj.domain.Department;
import ir.ac.ut.ieproj.domain.Student;
import ir.ac.ut.ieproj.domain.Term;
import ir.ac.ut.ieproj.exception.termNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Withdraw {

	public String execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("Withdraw start .");
		
		Student s = null;
		Term t = null;
		try {
			request.setAttribute("err", "0");
			t = DBConnector.getCurrentTerm();
			s = Department.getStudent(Integer.parseInt(request.getUserPrincipal().getName()));
		
			if(!t.isWithrawTime(new Date(Clock.getCurrentTimeMillis()))) {
				System.out.println("It is not withdraw time .");
				request.setAttribute("inProgressOffers", t.inProgressOfferings(s));
				request.setAttribute("err", 1);
				request.setAttribute("errMessage", "Your take withdraw is not came yet or has been passed .");
				return "/WEB-INF/stud/StudentMain.jsp";
			}
			
			String withdrawOffer = request.getParameter("withdraw");
			if (withdrawOffer != null) {
				String[] temps = withdrawOffer.split(",");
				String offerID = temps[0].substring(1);
				Department.withdraw(Integer.parseInt(request.getUserPrincipal().getName()),
						Integer.parseInt(offerID));
			} 
		} catch (NumberFormatException e) {
			request.setAttribute("err", 1);
			request.setAttribute("errMessage", e.getMessage());
			e.printStackTrace();
		} catch (StudentNotFoundException e) {
			request.setAttribute("err", 1);
			request.setAttribute("errMessage", e.getMessage());
			e.printStackTrace();
		} catch (OfferingNotFoundException e) {
			request.setAttribute("err", 1);
			request.setAttribute("errMessage", e.getMessage());
			e.printStackTrace();
		} catch (WithdrawException e) {
			request.setAttribute("err", 1);
			request.setAttribute("errMessage", e.getMessage());
			e.printStackTrace();
		} catch (termNotFoundException e) {
			request.setAttribute("err", 1);
			request.setAttribute("errMessage", e.getMessage());
			e.printStackTrace();
		}
		
		request.setAttribute("inProgressOffers", t.inProgressOfferings(s));
		return "/WEB-INF/stud/Withdraw.jsp";
	}
}
