package ir.ac.ut.ieproj.controller;

import ir.ac.ut.iecommon.exceptions.DeptLoadException;
import ir.ac.ut.iecommon.exceptions.DropException;
import ir.ac.ut.iecommon.exceptions.OfferingNotFoundException;
import ir.ac.ut.iecommon.exceptions.StudentNotFoundException;
import ir.ac.ut.iecommon.exceptions.TakeException;
import ir.ac.ut.ieproj.Boostan.Department;
import ir.ac.ut.ieproj.Boostan.Offering;
import ir.ac.ut.ieproj.Boostan.Student;

import java.text.SimpleDateFormat;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CourseSelect {

	public String execute(HttpServletRequest request, HttpServletResponse response) throws DeptLoadException {
		
		Student s = null;
		Department d = null;
		String sid = null;
		Vector<Vector<String>> dataInprogress = new Vector<Vector<String>>(); 
		Vector<Vector<String>> data = new Vector<Vector<String>>(); 
		
		try {
			request.setAttribute("err", "0");
			d = Department.getInstance();
			sid = request.getParameter("sid");
			String dropOffer = request.getParameter("hasOffer");
			String takeOffer = request.getParameter("canOffer");
			//System.out.println("data : sid="+sid+",dropOffer="+dropOffer+",takeOffer="+takeOffer+"#");
			//System.out.println("course id : #"+courseID+"#");
			//System.out.println("data : student name : "+s.getFirstName()+" "+s.getLastName());
			if (dropOffer != null){
				String[] temps = dropOffer.split(",");
				String offerID = temps[0].substring(1);
				//System.out.println("data : drop : sid="+sid+",offerID="+offerID+"#");
				d.drop(sid,	offerID);
			}
			else if (takeOffer != null){
				String[] temps = takeOffer.split(",");
				String offerID = temps[0].substring(1);
				//System.out.println("data : take : sid="+sid+",offerID="+offerID+"#");
				d.take(sid, offerID);
			}
			else {
				System.out.println("data : not drop nor take !!!");
			}
		} catch (DropException e) {
			request.setAttribute("err", "1");
			request.setAttribute("errMessage", e.getMessage());
			e.printStackTrace();
		} catch (StudentNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OfferingNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TakeException e) {
			request.setAttribute("err", "1");
			request.setAttribute("errMessage", e.getMessage());
			e.printStackTrace();
		} catch (DeptLoadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		s = d.findStudent(sid);
		//System.out.println("after findSt in course select .");
		//System.out.println("after catches in course select .");
		//System.out.println("this term offerings size : "+d.findCurrentTerm().getOfferings().size());
		for (int i = 0; i < d.findCurrentTerm().getOfferings().size(); i++) {
			Offering o = d.findCurrentTerm().getOfferings().get(i);
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			Vector<String> temp = new Vector<String>();	
			temp.add(Integer.toString(o.getId()));
			temp.add(d.findCourse(o.getCourse()).getName());		
			temp.add(Integer.toString(d.findCourse(o.getCourse()).getUnits()));
			temp.add(Integer.toString(o.getTime()));
			temp.add(d.findProf(o.getProfessor()).getFirstName()+" "+d.findProf(o.getProfessor()).getLastName());
			temp.add(sdf.format(o.getExamDate()));
			temp.add(Integer.toString(o.findRemainCapacity()));
			temp.add(Integer.toString(o.getCapacity()));
			//System.out.println("in for in course select : offering id="+o.getId()+" status: "+((s.inProgressOffering(o.getId()))?"INPROGRESS":"ELSE"));
			if (s.inProgressOffering(Integer.toString(o.getId())))
				dataInprogress.add(temp);
			else
				data.add(temp);
		}
		//Vector<Course> courses = d.getCourses();
		//request.setAttribute("courses", courses);
		//System.out.println("asb : "+request.getParameter("asb"));

		//System.out.println("after catch ... ");
		request.setAttribute("sid", s.getId());
		request.setAttribute("sname", s.getFirstName()+" "+s.getLastName());
		request.setAttribute("inprogressOffer", dataInprogress);
		request.setAttribute("offers", data);
		return "course-select.jsp";
	}
}
