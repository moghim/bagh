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
		System.out.println("WithdrawResponse controller starts .");
		Professor p = null;
		Offering o = null;
		@SuppressWarnings("unused")
		Term t = null;

		try {
			request.setAttribute("err", "0");
			t = DBConnector.getCurrentTerm();
			p = Department.getProfessor(Integer.parseInt(request.getUserPrincipal().getName()));
			String offering = request.getParameter("offering");
			System.out.println("offering in SubmitGrade.java : # "+offering+" #");
			String[] temps = offering.split(",");
			String offeringID = temps[0].substring(1);
			o = Department.getOffering(Integer.parseInt(offeringID));
			request.setAttribute("offering", offering);
			String student = request.getParameter("student");
			if (student != null){
				System.out.println("choice for Withdraw responsing .");
				String[] temps1 = student.split(",");
				String studentID = temps1[0].substring(1);
				
				String accept = request.getParameter("accept");
				String reject = request.getParameter("reject");
				if(accept!=null && accept.equals("accept")) {
					System.out.println("Accept withdraw .");
					Department.acceptWithdraw(Integer.parseInt(studentID), Integer.parseInt(offeringID), p.getId());
				}
				else if(reject!=null && reject.equals("reject")) {
					System.out.println("Reject withdraw .");
					Department.rejectWithdraw(Integer.parseInt(studentID), Integer.parseInt(offeringID), p.getId());
				}
				else {
					System.out.println("data : not choice is recognized in WithdrawResponse.java controller .");
					throw new requestException("No chioce has been recognized in withdraw response .");
				}
			}
		} catch (NumberFormatException e) {
			request.setAttribute("err", 1);
			request.setAttribute("errMessage", e.getMessage());
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
		//request.setAttribute("offering", o.getId());
		return "/WEB-INF/prof/WithdrawResponse.jsp";
	}
}
