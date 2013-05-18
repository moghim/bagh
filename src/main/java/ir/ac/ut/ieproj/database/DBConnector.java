package ir.ac.ut.ieproj.database;

import org.hibernate.Session;

import ir.ac.ut.iecommon.exceptions.OfferingNotFoundException;
import ir.ac.ut.iecommon.exceptions.ProfNotFoundException;
import ir.ac.ut.iecommon.exceptions.StudentNotFoundException;
import ir.ac.ut.ieproj.domain.*;

public class DBConnector {

	public static Student getStudent(int studentId) throws StudentNotFoundException {
		Session session = (Session) HibernateUtil.getSessionFactory();
		session.beginTransaction();
		Student student = (Student) session.get(Student.class, studentId);
		session.close();
		if(student == null)
			throw new StudentNotFoundException("Student not found .");
		return student;
	}
	public static Professor getProfessor(int professorID) throws ProfNotFoundException {
		// TODO
		return null;
	}
	public static Term getTerm(int TermID) {
		// TODO
		return null;
	}
	public static Offering getOffering(int offeringID) throws OfferingNotFoundException {
		// TODO
		return null;
	}
	public static Course getCourse() {
		// TODO
		return null;
	}
	public static Term getCurrentTerm() throws Exception {
		// TODO
		return null;
	}
	public static void saveStudent(Student s) {
		// TODO Auto-generated method stub
	}
	public static void saveOffering(Offering o) {
		// TODO Auto-generated method stub	
	}
	public static Term getPreviosTerm() {
		// TODO Auto-generated method stub
		return null;
	}
}
