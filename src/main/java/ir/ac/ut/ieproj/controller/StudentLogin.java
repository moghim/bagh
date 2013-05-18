package ir.ac.ut.ieproj.controller;

import ir.ac.ut.iecommon.exceptions.DeptLoadException;
import ir.ac.ut.ieproj.domain.Department;
import ir.ac.ut.ieproj.domain.Offering;
import ir.ac.ut.ieproj.domain.Student;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StudentLogin {

	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, DeptLoadException {
		//System.out.println("sid : "+request.getParameter("sid"));
		//System.out.println("before find");
		Student s = Department.findStudent(request.getParameter("sid"));
		boolean hasError = false;
		if(s == null) {
			request.setAttribute("err", "1");

			hasError = true;
		}
		else if(!Department.findCurrentTerm().isTakeTime()) {
			request.setAttribute("err", "2");
			hasError = true;
		}
		if(hasError)
			return "student-login.jsp";
		
	//	request.setAttribute("sname", s.getFirstName()+" "+s.getLastName());
		Vector<Vector<String>> dataInprogress = new Vector<Vector<String>>(); 
		Vector<Vector<String>> data = new Vector<Vector<String>>(); 
		for (int i = 0; i < Department.findCurrentTerm().getOfferings().size(); i++) {
			Offering o = Department.findCurrentTerm().getOfferings().iterator().next(); // TODO
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			Vector<String> temp = new Vector<String>();	
			temp.add(Integer.toString(o.getId()));
			// TODO temp.add(d.findCourse(o.getCourse()).getName());		
			// TODO temp.add(Integer.toString(d.findCourse(o.getCourse()).getUnits()));
			temp.add(Integer.toString(o.getTime()));
			// TODO temp.add(d.findProf(o.getProfessor()).getFirstName()+" "+d.findProf(o.getProfessor()).getLastName());
			temp.add(sdf.format(o.getExamDate()));
			temp.add(Integer.toString(o.findRemainCapacity()));
			temp.add(Integer.toString(o.getCapacity()));
			//System.out.println("in for in student login : offering id="+o.getId()+" status: "+((s.inProgressOffering(o.getId()))?"INPROGRESS":"ELSE"));
			if (s.inProgressOffering(Integer.toString(o.getId())))
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
}
