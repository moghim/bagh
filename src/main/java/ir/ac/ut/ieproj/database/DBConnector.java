package ir.ac.ut.ieproj.database;

import org.hibernate.Session;

import ir.ac.ut.ieproj.Boostan.*;

public class DBConnector {

	public static Student getStudent(int studentId) {
		Session session = (Session) HibernateUtil.getSessionFactory();
		session.beginTransaction();
		Student student = (Student) session.get(Student.class, studentId);
		session.close();
		return student;
	}
}
