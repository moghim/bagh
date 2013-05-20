package ir.ac.ut.ieproj.controller;

import ir.ac.ut.iecommon.exceptions.OfferingNotFoundException;
import ir.ac.ut.iecommon.exceptions.StudentNotFoundException;
import ir.ac.ut.iecommon.exceptions.WithdrawException;
import ir.ac.ut.ieproj.database.DBConnector;
import ir.ac.ut.ieproj.domain.Department;
import ir.ac.ut.ieproj.domain.Student;
import ir.ac.ut.ieproj.domain.Term;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Withdraw {

	public String execute(HttpServletRequest request,
			HttpServletResponse response) {

		Student s = null;
		String sid = null;
		Term t = null;
		try {
			request.setAttribute("err", "0");
			sid = request.getParameter("sid");
			t = DBConnector.getCurrentTerm();
			s = Department.getStudent(Integer.parseInt(sid));
			request.setAttribute("sid", s.getId());
			request.setAttribute("sname",
					s.getFirstName() + " " + s.getLastName());
			String choice = request.getParameter("choice");
			if (choice != null && choice.equals("home")) {
				request.setAttribute("inProgressOffers",
						t.inProgressOfferings(s));
				return "student-main.jsp";
			}
			String withdrawOffer = request.getParameter("withdraw");
			if (withdrawOffer != null) {
				String[] temps = withdrawOffer.split(",");
				String offerID = temps[0].substring(1);
				System.out.println("data : withdraw : sid=" + sid + ",offerID="
						+ offerID + "#");
				Department.withdraw(Integer.parseInt(sid),
						Integer.parseInt(offerID));
			} else {
				System.out.println("data : not withdraw !!!");
				throw new Exception("No chioce has been recognized .");
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StudentNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OfferingNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WithdrawException e) {
			request.setAttribute("err", 1);
			request.setAttribute("errMessage", e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("inProgressOffers", t.inProgressOfferings(s));
		return "withdraw.jsp";
	}
}
