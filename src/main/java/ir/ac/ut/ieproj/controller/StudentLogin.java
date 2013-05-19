package ir.ac.ut.ieproj.controller;

import ir.ac.ut.iecommon.exceptions.StudentNotFoundException;
import ir.ac.ut.iecommon.time.Clock;
import ir.ac.ut.ieproj.domain.Department;
import ir.ac.ut.ieproj.domain.Offering;
import ir.ac.ut.ieproj.domain.Student;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StudentLogin {

	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("StudentLogin controller come .");
		System.out.println("sid : "+request.getParameter("sid"));
		try {
			Student s = Department.getStudent(Integer.parseInt(request.getParameter("sid")));
			boolean hasError = false;
			if(s == null) {
				request.setAttribute("err", "1");
				hasError = true;
			}
			else if(!Department.getCurrentTerm().isTakeTime(new Date(Clock.getCurrentTimeMillis()))) {
				request.setAttribute("err", "2");
				hasError = true;
			}
			if(hasError)
				return "student-login.jsp";
			Vector<Vector<String>> dataInprogress = new Vector<Vector<String>>(); 
			Vector<Vector<String>> data = new Vector<Vector<String>>(); 
			for (Offering o : Department.getCurrentTerm().getOfferings()) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				Vector<String> temp = new Vector<String>();	
				temp.add(Integer.toString(o.getId()));
				temp.add(o.getCourse().getName());		
				temp.add(Integer.toString(o.getCourse().getUnits()));
				temp.add(Integer.toString(o.getTime()));
				temp.add((o.getProfessor()).getFirstName()+" "+o.getProfessor().getLastName());
				temp.add(sdf.format(o.getExamDate()));
				temp.add(Integer.toString(o.getRemainCapacity()));
				temp.add(Integer.toString(o.getCapacity()));

				if (s.isInProgressOffering(o))
					dataInprogress.add(temp);
				else
					data.add(temp);
			}
			request.setAttribute("sid", s.getId());
			request.setAttribute("sname", s.getFirstName()+" "+s.getLastName());
			request.setAttribute("inprogressOffer", dataInprogress);
			request.setAttribute("offers", data);
			request.setAttribute("err", "0");
			return "course-select.jsp";
		}
		catch (StudentNotFoundException e) {
			e.printStackTrace();
			request.setAttribute("err", 1);
			return "student-login.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("err", 3);
			return "student-login.jsp";
		}
	}
}
