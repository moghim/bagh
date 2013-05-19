package ir.ac.ut.ieproj.controller;

import ir.ac.ut.iecommon.exceptions.DropException;
import ir.ac.ut.iecommon.exceptions.OfferingNotFoundException;
import ir.ac.ut.iecommon.exceptions.StudentNotFoundException;
import ir.ac.ut.iecommon.exceptions.TakeException;
import ir.ac.ut.ieproj.database.DBConnector;
import ir.ac.ut.ieproj.domain.Department;
import ir.ac.ut.ieproj.domain.Student;
import ir.ac.ut.ieproj.domain.Term;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CourseSelect {

	public String execute(HttpServletRequest request, HttpServletResponse response) {

		Student s = null;
		String sid = null;
		Term t = null;

		try {
			request.setAttribute("err", "0");
			sid = request.getParameter("sid");
			String dropOffer = request.getParameter("drop");
			String takeOffer = request.getParameter("take");
			if (dropOffer != null){
				String[] temps = dropOffer.split(",");
				String offerID = temps[0].substring(1);
				//System.out.println("data : drop : sid="+sid+",offerID="+offerID+"#");
				Department.drop(Integer.parseInt(sid), Integer.parseInt(offerID));
			}
			else if (takeOffer != null){
				String[] temps = takeOffer.split(",");
				String offerID = temps[0].substring(1);
				//System.out.println("data : take : sid="+sid+",offerID="+offerID+"#");
				Department.take(Integer.parseInt(sid), Integer.parseInt(offerID));
			}
			else {
				System.out.println("data : not drop nor take !!!");
				throw new Exception("No chioce has been recognized .");
			}

		} catch (TakeException e) {
			request.setAttribute("err", "1");
			request.setAttribute("errMessage", e.getMessage());
			e.printStackTrace();
		} catch (DropException e) {
			request.setAttribute("err", "1");
			request.setAttribute("errMessage", e.getMessage());
			e.printStackTrace();
		} catch (StudentNotFoundException e) {
			System.out.println("Student not found in CourseSelect.java controller .");
			e.printStackTrace();
		} catch (OfferingNotFoundException e) {
			System.out.println("Offering not found in CourseSelect.java controller .");
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			t = DBConnector.getCurrentTerm();
			s = Department.getStudent(Integer.parseInt(sid));
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
		Vector<Vector<String>> dataInprogress = t.inProgressOfferings(s); 
		Vector<Vector<String>> data = t.notInProgressOfferings(s); 
		request.setAttribute("sid", s.getId());
		request.setAttribute("sname", s.getFirstName()+" "+s.getLastName());
		request.setAttribute("inprogressOffer", dataInprogress);
		request.setAttribute("offers", data);
		request.setAttribute("err", "0");
		return "course-select.jsp";
	}
}
