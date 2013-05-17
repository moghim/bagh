package ir.ac.ut.ieproj.database;

import org.hibernate.Session;

import ir.ac.ut.iecommon.exceptions.OfferingNotFoundException;
import ir.ac.ut.iecommon.exceptions.StudentNotFoundException;
import ir.ac.ut.ieproj.domain.*;

public class DBConnector {

	public static Student getStudent(int studentId) {
		Session session = (Session) HibernateUtil.getSessionFactory();
		session.beginTransaction();
		Student student = (Student) session.get(Student.class, studentId);
		session.close();
		return student;
	}
	public static Student getStudent(String id) throws StudentNotFoundException {
		// TODO
		return null;
	}
	public static Term getOfferingTerm(String offeringID) {
		// TODO
		return null;
	}
	public static Offering getOffering(String offeringID) throws OfferingNotFoundException {
		// TODO
		return null;
	}
	public static Term getCurrentTerm() {
		// TODO
		return null;
	}
	public static void studentAddRecord(String studentID, String offeringID) {
		// TODO
	}
	public static void decreaseRemainCapacity(String offeringID) {
		// TODO
	}
	public static void studentDeleteRecord(String studentID, String offeringID) {
		// TODO
	}
	public static void increaseRemainCapacity(String offeringID) {
		// TODO
	}
}
