package ir.ac.ut.ieproj.database;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import ir.ac.ut.iecommon.exceptions.OfferingNotFoundException;
import ir.ac.ut.iecommon.exceptions.ProfNotFoundException;
import ir.ac.ut.iecommon.exceptions.StudentNotFoundException;
import ir.ac.ut.ieproj.domain.*;

public class DBConnector {

	private static Session session = HibernateUtil.getSessionFactory().openSession();
	
	public static Student getStudent(int studentId) throws StudentNotFoundException {
		Student student = (Student) session.get(Student.class, studentId);
		if(student == null)
			throw new StudentNotFoundException("Student not found in database .");
		return student;
	}
	public static Professor getProfessor(int professorID) throws ProfNotFoundException {
		Professor professor = (Professor) session.get(Professor.class, professorID);
		if(professor == null)
			throw new ProfNotFoundException("Professor not found in database .");
		return professor;
	}
	public static Offering getOffering(int offeringID) throws OfferingNotFoundException {
		Offering offering = (Offering) session.get(Offering.class, offeringID);
		if(offering == null)
			throw new OfferingNotFoundException("Professor not found in database .");
		return offering;
	}
	public static Term getCurrentTerm() throws Exception {
		session.beginTransaction();
		Query query = session.createQuery("From Term where endDate >= :now and startDate <= :now");
		query.setParameter("now", new Date());
		@SuppressWarnings("unchecked")
		List<Term> resultList = query.list();
		session.getTransaction().commit();
		if(resultList.isEmpty())
			throw new Exception("Current term does not exist .");
		if(resultList.size() > 1)
			throw new Exception("More than 1 term has conditions to be current term .");
		return resultList.get(0);
	}
	public static Term getPreviosTerm() throws Exception {
		Term currentTerm = getCurrentTerm();
		session.beginTransaction();
		Query query = session.createQuery("From Term where id="+(currentTerm.getId()-1));
		@SuppressWarnings("unchecked")
		List<Term> resultList = query.list();
		session.getTransaction().commit();
		if(resultList.isEmpty())
			throw new Exception("Previos term does not exist .");
		if(resultList.size() > 1)
			throw new Exception("More than 1 term has conditions to be previos term .");
		return resultList.get(0);
	}
	public static void saveStudent(Student s) {
		session.beginTransaction();
		session.save(s);
		session.getTransaction().commit();
	}
	public static void saveOffering(Offering o) {
		session.beginTransaction();
		session.save(o);
		session.getTransaction().commit();
	}
}
