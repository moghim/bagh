package ir.ac.ut.ieproj.controller;

import ir.ac.ut.iecommon.exceptions.AcceptWithdrawException;
import ir.ac.ut.iecommon.exceptions.OfferingNotFoundException;
import ir.ac.ut.iecommon.exceptions.ProfNotFoundException;
import ir.ac.ut.iecommon.exceptions.RejectWithdrawException;
import ir.ac.ut.iecommon.exceptions.StudentNotFoundException;
import ir.ac.ut.ieproj.database.DBConnector;
import ir.ac.ut.ieproj.domain.Department;
import ir.ac.ut.ieproj.domain.Offering;
import ir.ac.ut.ieproj.domain.Professor;
import ir.ac.ut.ieproj.domain.Term;
import ir.ac.ut.ieproj.exception.requestException;
import ir.ac.ut.ieproj.exception.termNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WithdrawResponse {

	public String execute(HttpServletRequest request, HttpServletResponse response) {

		Professor p = null;
		Offering o = null;
		String sid = null;
		Term t = null;

		try {
			request.setAttribute("err", "0");
			sid = request.getParameter("sid");
			t = DBConnector.getCurrentTerm();
			p = Department.getProfessor(Integer.parseInt(sid));
			request.setAttribute("sid", p.getId());
			request.setAttribute("name", (p.getFirstName()+" "+p.getLastName()));
			String choice = request.getParameter("choice");
			if(choice!=null && choice.equals("home")) {
				request.setAttribute("teachingOffers", t.teachingOfferings(p));
				return "professor-main.jsp";
			}
			String student = request.getParameter("student");

			if (student != null){
				String[] temps = student.split(",");
				String studentID = temps[0].substring(1);
				String offeringID = request.getParameter("offering");
				o = Department.getOffering(Integer.parseInt(offeringID));
				String accept = request.getParameter("accept");
				String reject = request.getParameter("reject");
				if(accept!=null && accept.equals("accept")) {
					Department.acceptWithdraw(Integer.parseInt(studentID), Integer.parseInt(offeringID), Integer.parseInt(sid));
				}
				else if(reject!=null && reject.equals("reject")) {
					Department.rejectWithdraw(Integer.parseInt(studentID), Integer.parseInt(offeringID), Integer.parseInt(sid));
				}
				else {
					System.out.println("data : not choice is recognized in WithdrawResponse.java controller .");
					throw new requestException("No chioce has been recognized .");
				}
			}
			else {
				System.out.println("student not found in withdrawResponse.java controller .");
				throw new requestException("No student has been recognized .");
			}
		} catch (NumberFormatException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (ProfNotFoundException e) {
			request.setAttribute("err", 1);
			request.setAttribute("errMessage", e.getMessage());
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (OfferingNotFoundException e) {
			request.setAttribute("err", 1);
			request.setAttribute("errMessage", e.getMessage());
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (termNotFoundException e) {
			System.out.println(e.getMessage());
			request.setAttribute("err", 1);
			request.setAttribute("errMessage", e.getMessage());
			e.printStackTrace();
		} catch (requestException e) {
			System.out.println(e.getMessage());
			request.setAttribute("err", 1);
			request.setAttribute("errMessage", e.getMessage());
			e.printStackTrace();
		} catch (AcceptWithdrawException e) {
			System.out.println(e.getMessage());
			request.setAttribute("err", 1);
			request.setAttribute("errMessage", e.getMessage());
			e.printStackTrace();
		} catch (StudentNotFoundException e) {
			System.out.println(e.getMessage());
			request.setAttribute("err", 1);
			request.setAttribute("errMessage", e.getMessage());
			e.printStackTrace();
		} catch (RejectWithdrawException e) {
			System.out.println(e.getMessage());
			request.setAttribute("err", 1);
			request.setAttribute("errMessage", e.getMessage());
			e.printStackTrace();
		}
		request.setAttribute("students", Department.studentsInOfferingWaitingForWithdraw(o));
		request.setAttribute("offering", o.getId());
		return "withdraw-response.jsp";
	}
}
