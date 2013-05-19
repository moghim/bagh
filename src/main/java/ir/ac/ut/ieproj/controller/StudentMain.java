package ir.ac.ut.ieproj.controller;

import java.util.Date;

import ir.ac.ut.iecommon.exceptions.StudentNotFoundException;
import ir.ac.ut.iecommon.time.Clock;
import ir.ac.ut.ieproj.database.DBConnector;
import ir.ac.ut.ieproj.domain.Department;
import ir.ac.ut.ieproj.domain.Student;
import ir.ac.ut.ieproj.domain.Term;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StudentMain {

	public String excecute(HttpServletRequest request, HttpServletResponse response) {
		Student s= null;
		Term t = null;
		try {
			String sid = request.getParameter("sid");
			s = Department.getStudent(Integer.parseInt(sid));
			t = DBConnector.getCurrentTerm();
			String choice = request.getParameter("choice");
			request.setAttribute("sid", s.getId());
			request.setAttribute("sname", s.getFirstName()+" "+s.getLastName());
			if(choice.equals("CourseSelect")) {
				if(!t.isTakeTime(new Date(Clock.getCurrentTimeMillis()))) {
					request.setAttribute("inprogressOffers", t.inProgressOfferings(s));
					request.setAttribute("err", "1");
					request.setAttribute("errMessage", "Your take time is not came yet or has benn passed .");
					return "student-main.jsp";
				}
				else {
					request.setAttribute("inprogressOffers", t.inProgressOfferings(s));
					request.setAttribute("offers", t.notInProgressOfferings(s));
					request.setAttribute("err", 0);
					return "course-select.jsp";
				}
			}
			else if(choice.equals("Withdraw")) {
				if(!t.isWithrawTime(new Date(Clock.getCurrentTimeMillis()))) {
					request.setAttribute("inprogressOffers", t.inProgressOfferings(s));
					request.setAttribute("err", "1");
					request.setAttribute("errMessage", "Your take withdraw is not came yet or has benn passed .");
					return "student-main.jsp";
				}
				else {
					request.setAttribute("canWithdrawOffers", s.canWithdrawOfferings(t));
					request.setAttribute("err", 0);
					return "withdraw.jsp";
				}
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StudentNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("inprogressOffers", t.inProgressOfferings(s));
		request.setAttribute("err", "1");
		request.setAttribute("errMessage", "Unknown Error ... ");
		return "student-main.jsp";
	}
}
