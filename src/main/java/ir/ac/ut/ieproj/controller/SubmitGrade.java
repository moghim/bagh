package ir.ac.ut.ieproj.controller;

import ir.ac.ut.iecommon.exceptions.OfferingNotFoundException;
import ir.ac.ut.iecommon.exceptions.ProfNotFoundException;
import ir.ac.ut.iecommon.exceptions.StudentNotFoundException;
import ir.ac.ut.iecommon.exceptions.SubmitGradeException;
import ir.ac.ut.ieproj.database.DBConnector;
import ir.ac.ut.ieproj.domain.Department;
import ir.ac.ut.ieproj.domain.Offering;
import ir.ac.ut.ieproj.domain.Professor;
import ir.ac.ut.ieproj.domain.Term;
import ir.ac.ut.ieproj.exception.termNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SubmitGrade {

	public String execute(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("SubmitGrade controller starts .");
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
			String offerID = temps[0].substring(1);
			o = Department.getOffering(Integer.parseInt(offerID));
			request.setAttribute("offering", offering);
			String student = request.getParameter("student");
			if (student != null){
				System.out.println("choice for submit grading .");
				String[] temps1 = student.split(",");
				String studentID = temps1[0].substring(1);
				String grade = request.getParameter("grade");
				Department.submitGrade(Integer.parseInt(studentID), p.getId(), Integer.parseInt(offerID), Float.parseFloat(grade));
			}
			
			/*
			String choice = request.getParameter("choice");
			if(choice!=null && choice.equals("home")) {
				request.setAttribute("teachingOffers", t.teachingOfferings(p));
				return "professor-main.jsp";
			}
			String student = request.getParameter("student");	
			if (student != null){
				String[] temps = student.split(",");
				String studentID = temps[0].substring(1);
				//System.out.println("data : drop : sid="+sid+",offerID="+offerID+"#");
				String offeringID = request.getParameter("offering");
				String grade = request.getParameter("grade");
				o = Department.getOffering(Integer.parseInt(offeringID));
				Department.submitGrade(Integer.parseInt(studentID), p.getId(), Integer.parseInt(offeringID), Float.parseFloat(grade));
			}
			else {
				System.out.println("data : not student is recognized in submit grade !!!");
				throw new requestException("No chioce has been recognized .");
			}
			*/
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
		} catch (SubmitGradeException e) {
			request.setAttribute("err", 1);
			request.setAttribute("errMessage", e.getMessage());
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (StudentNotFoundException e) {
			request.setAttribute("err", 1);
			request.setAttribute("errMessage", e.getMessage());
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		request.setAttribute("students", Department.studentsInOffering(o));
		//request.setAttribute("offering", o.getId());
		return "/WEB-INF/prof/SubmitGrade.jsp";
	}
}
