package ir.ac.ut.ieproj.database;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import ir.ac.ut.iecommon.exceptions.OfferingNotFoundException;
import ir.ac.ut.iecommon.exceptions.ProfNotFoundException;
import ir.ac.ut.iecommon.exceptions.StudentNotFoundException;
import ir.ac.ut.ieproj.domain.*;
import ir.ac.ut.ieproj.exception.PersonNotFoundException;
import ir.ac.ut.ieproj.exception.termNotFoundException;

public class DBConnector {

	//private static Session session = HibernateUtil.getSessionFactory().openSession();

	public static Person getPerson(int personID) throws PersonNotFoundException {
		Session session = Context.getSession();
		Person person = (Person) session.get(Person.class, personID);
		if(person == null)
			throw new PersonNotFoundException("Person not found in database .");
		return person;
	}
	public static Student getStudent(int studentId) throws StudentNotFoundException {
		Session session = Context.getSession();
		Student student = (Student) session.get(Student.class, studentId);
		if(student == null)
			throw new StudentNotFoundException("Student not found in database .");
		return student;
	}
	public static Professor getProfessor(int professorID) throws ProfNotFoundException {
		Session session = Context.getSession();
		Professor professor = (Professor) session.get(Professor.class, professorID);
		if(professor == null)
			throw new ProfNotFoundException("Professor not found in database .");
		return professor;
	}
	public static Offering getOffering(int offeringID) throws OfferingNotFoundException {
		Session session = Context.getSession();
		Offering offering = (Offering) session.get(Offering.class, offeringID);
		if(offering == null)
			throw new OfferingNotFoundException("Professor not found in database .");
		return offering;
	}
	public static Term getCurrentTerm() throws termNotFoundException {
		Session session = Context.getSession();
		session.beginTransaction();
		Query query = session.createQuery("From Term where endDate >= :now and startDate <= :now");
		query.setParameter("now", new Date());
		@SuppressWarnings("unchecked")
		List<Term> resultList = query.list();
		session.getTransaction().commit();
		if(resultList.isEmpty())
			throw new termNotFoundException("Current term does not exist .");
		if(resultList.size() > 1)
			throw new termNotFoundException("More than 1 term has conditions to be current term .");
		return resultList.get(0);
	}
	public static Term getPreviosTerm() throws termNotFoundException {
		Session session = Context.getSession();
		Term currentTerm = getCurrentTerm();
		session.beginTransaction();
		Query query = session.createQuery("From Term where id="+(currentTerm.getId()-1));
		@SuppressWarnings("unchecked")
		List<Term> resultList = query.list();
		session.getTransaction().commit();
		if(resultList.isEmpty())
			throw new termNotFoundException("Previos term does not exist .");
		if(resultList.size() > 1)
			throw new termNotFoundException("More than 1 term has conditions to be previos term .");
		return resultList.get(0);
	}
	public static List<Student> getStudentsInOffering(Offering offering) {
		Session session = Context.getSession();
		System.out.println("getStudentsInOffering in DBconnector : ");
		session.beginTransaction();
		Query query = session.createQuery("select s from Student as s inner join s.studyRecord ss with ss.offering.id="+offering.getId());
		//System.out.println("query has no error or exception !!!");
		//Query query = session.createQuery("From Student s where s.id="+810190420);
		//Query query = session.createQuery("from Student s where exists (from StudyRecord ss where exists (from Offering o where o.id="
		//+offering.getId()+" and ss.offering=o)");
		@SuppressWarnings("unchecked")
		List<Student> resultList = query.list();
		session.getTransaction().commit();
		//System.out.println("students in offering size : "+resultList.size());
		//System.out.println("first student : "+resultList.get(0));
		//System.out.println("first student id : "+resultList.get(0).getId());
		return resultList;
	}
	public static List<Student> getStudentsInOfferingWaitingForWithdraw(Offering offering) {
		Session session = Context.getSession();
		System.out.println("getStudentsInOfferingWaitingForWithdraw in DBconnector : ");
		session.beginTransaction();
		Query query = session.createQuery("select s from Student as s inner join s.studyRecord ss with ss.offering.id="+offering.getId()+" and ss.status='WAITINGFORWITHRAWACCEPT'");
		@SuppressWarnings("unchecked")
		List<Student> resultList = query.list();
		session.getTransaction().commit();
		return resultList;
	}

	public static void saveStudent(Student s) {
		Session session = Context.getSession();
		session.beginTransaction();
		session.update(s);
		session.getTransaction().commit();
	}
	public static void saveOffering(Offering o) {
		Session session = Context.getSession();
		session.beginTransaction();
		session.update(o);
		session.getTransaction().commit();
	}
	public static void deleteStudyRecord(StudyRecord studyRecord) {
		Session session = Context.getSession();
		session.beginTransaction();
		session.delete(studyRecord);
		session.getTransaction().commit();
	}
	public static void saveStudyRecord(StudyRecord sr) {
		Session session = Context.getSession();
		session.beginTransaction();
		session.update(sr);
		session.getTransaction().commit();
	}
}
