package ir.ac.ut.ieproj.controller;

import ir.ac.ut.iecommon.exceptions.OfferingNotFoundException;
import ir.ac.ut.iecommon.exceptions.ProfNotFoundException;
import ir.ac.ut.iecommon.time.Clock;
import ir.ac.ut.ieproj.database.DBConnector;
import ir.ac.ut.ieproj.domain.Department;
import ir.ac.ut.ieproj.domain.Offering;
import ir.ac.ut.ieproj.domain.Professor;
import ir.ac.ut.ieproj.domain.Term;
import ir.ac.ut.ieproj.exception.termNotFoundException;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WithdrawResponseList {

	public String execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("WithrawResponseList.java : ");
		Professor p = null;
		Term t = null;
		Offering o = null;
		try {
			String sid = request.getParameter("sid");
			System.out.println("sid in WithrawResponseList : "+sid);
			p = Department.getProfessor(Integer.parseInt(sid));
			t = DBConnector.getCurrentTerm();
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
			System.out.println("choice for withdraw responsing .");
			if(!t.isWithrawResponseTime(new Date(Clock.getCurrentTimeMillis()))) {
				System.out.println("choice for withdraw response was bad.");
				request.setAttribute("teachingOffers", t.teachingOfferings(p));
				request.setAttribute("err", 1);
				request.setAttribute("errMessage", "Grade Submit time has been passed or not come yet .");
				return "withdraw-response-list.jsp";
			}
			else {
				System.out.println("choice for withdraw response grade was good .");
				request.setAttribute("students", Department.studentsInOfferingWaitingForWithdraw(o));
				request.setAttribute("offering", o.getId());
				request.setAttribute("err", 0);
				return "withdraw-response.jsp";
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  catch (termNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProfNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OfferingNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		System.out.println("Unknown error .");
		request.setAttribute("teachingOffers", t.teachingOfferings(p));
		request.setAttribute("err", "1");
		request.setAttribute("errMessage", "Unknown Error ... ");
		return "submit-grade-list.jsp";
	}
}
