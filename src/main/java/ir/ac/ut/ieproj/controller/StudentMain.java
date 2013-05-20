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

	public String execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("StudentMain.java : ");
		Student s= null;
		Term t = null;
		try {
			String sid = request.getParameter("sid");
			System.out.println("sid in StudentMain : "+sid);
			s = Department.getStudent(Integer.parseInt(sid));
			t = DBConnector.getCurrentTerm();
			String choice = request.getParameter("choice");
			request.setAttribute("sid", s.getId());
			request.setAttribute("sname", s.getFirstName()+" "+s.getLastName());
			if(choice.equals("CourseSelect")) {
				System.out.println("choice for CourseSelect .");
				if(!t.isTakeTime(new Date(Clock.getCurrentTimeMillis()))) {
					System.out.println("choice for CourseSelect was bad .");
					request.setAttribute("inProgressOffers", t.inProgressOfferings(s));
					request.setAttribute("err", "1");
					request.setAttribute("errMessage", "Your take time is not came yet or has benn passed .");
					return "student-main.jsp";
				}
				else {
					System.out.println("choice for CourseSelect was good .");
					request.setAttribute("inProgressOffers", t.inProgressOfferings(s));
					request.setAttribute("otherOffers", t.notInProgressOfferings(s));
					request.setAttribute("err", 0);
					return "course-select.jsp";
				}
			}
			else if(choice.equals("Withdraw")) {
				System.out.println("choice for Withdraw .");
				if(!t.isWithrawTime(new Date(Clock.getCurrentTimeMillis()))) {
					System.out.println("choice for Withdraw was bad.");
					request.setAttribute("inProgressOffers", t.inProgressOfferings(s));
					request.setAttribute("err", 1);
					request.setAttribute("errMessage", "Your take withdraw is not came yet or has benn passed .");
					return "student-main.jsp";
				}
				else {
					System.out.println("choice for Withdraw was good .");
					request.setAttribute("inProgressOffers", t.inProgressOfferings(s));
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
		System.out.println("Unknown error .");
		request.setAttribute("inProgressOffers", t.inProgressOfferings(s));
		request.setAttribute("err", "1");
		request.setAttribute("errMessage", "Unknown Error ... ");
		return "student-main.jsp";
	}
}
