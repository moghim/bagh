package ir.ac.ut.ieproj.test;

import java.util.Date;

import ir.ac.ut.ieproj.domain.*;
import ir.ac.ut.ieproj.domain.Package;
import ir.ac.ut.ieproj.database.DBConnector;
import ir.ac.ut.ieproj.database.HibernateUtil;

import org.hibernate.Session;

public class App 
{
	public static void main( String[] args ) throws Exception
	{
		//Logger.getLogger("").setLevel(org.apache.log4j.Level.WARN);
		System.out.println("Hello World !");
		initialize();

		// SubSytem Tests :

		// DBConnector test :
		Student s1 = DBConnector.getStudent(810190420);
		Student s2 = DBConnector.getStudent(810190421);
		System.out.println("Student test 1 : "+s1);
		System.out.println("Student test 2 : "+s2);

		Professor p1 = DBConnector.getProfessor(1);
		Professor p2 = DBConnector.getProfessor(5);
		System.out.println("Professor test 1 : "+p1);
		System.out.println("Professor test 2 : "+p2);

		Offering o1 = DBConnector.getOffering(1);
		Offering o2 = DBConnector.getOffering(6);
		System.out.println("Offering test 1 : "+o1);
		System.out.println("Offering test 2 : "+o2);

		Term t1 = DBConnector.getCurrentTerm();
		Term t2 = DBConnector.getPreviosTerm();
		System.out.println("Term test 1 : "+t1);
		System.out.println("Term test 2 : "+t2);

		// System Tests :

		Department.drop(810190420, 9);
		System.out.println("After drop student : "+DBConnector.getStudent(810190420));
		//Department.take(810190420, 9);
		//Department.take(810190421, 8);
		//Department.drop(810190421, 8);
		//Department.withdraw(810190420, 9);
		//Department.withdraw(810190421, 6);
		//Department.withdraw(810190421, 7);

		//System.out.println(d.findStudent("810190421").getLastTermAverage(d));

		//d.acceptWithdraw("810190421", "7", "9");
		//d.rejectWithdraw("810190421", "6", "1");

		//d.submitGrade("810190421", "9", "7", 0);
		//d.checkDegreeReq("810190420");

		//System.out.println("\n");
	}

	@SuppressWarnings("deprecation")
	private static void initialize() {
		System.out.println("Initializing and mproduce initiate data ...");

		int year = 1900;
		int month = 1;

		// making needed data
		Professor p1 = new Professor(1,"Ramtin", "Khosravi");
		Professor p2 = new Professor(2,"Ahmad", "Khonsari");
		Professor p3 = new Professor(3,"Hesham", "Faili");
		Professor p4 = new Professor(4,"Fatemeh", "Ghasemi");
		Professor p5 = new Professor(5,"Mahmood", "Kharat");
		Professor p6 = new Professor(6,"Mahmoudreza", "Hashemi");
		Professor p7 = new Professor(7,"Fattaneh", "Taghiyareh");
		Professor p8 = new Professor(8,"Azadeh", "Shakery");
		Professor p9 = new Professor(9,"Siamak", "Mohammadi");

		Course c1 = new Course("Fundamentals of Programming", 4, Level.UNDERGRAD);
		Course c2 = new Course("Advanced Programming", 3, Level.UNDERGRAD);
		Course c3 = new Course("Discrete Mathematics", 3, Level.UNDERGRAD);
		Course c4 = new Course("Data Structures", 3, Level.UNDERGRAD);
		Course c5 = new Course("Database Systems", 3, Level.UNDERGRAD);
		Course c6 = new Course("Internet Engineering", 3, Level.UNDERGRAD);
		Course c7 = new Course("Formal Methods", 3, Level.GRAD);
		Course c8 = new Course("Advanced Networks", 3, Level.GRAD);
		Course c9 = new Course("Fundamentals of Management", 3, Level.UNDERGRAD);
		Course c10 = new Course("Fundamentals of IT", 3, Level.UNDERGRAD);
		Course c11 = new Course("Multimedia", 3, Level.UNDERGRAD);
		Course c12 = new Course("Network Security", 3, Level.UNDERGRAD);
		Course c13 = new Course("Elearning", 3, Level.UNDERGRAD);

		c2.addPrerequisite(c1);
		c3.addPrerequisite(c1);
		c5.addPrerequisite(c2);
		c5.addPrerequisite(c4);
		c6.addPrerequisite(c2);
		c6.addPrerequisite(c4);
		c6.addCorequisite(c5);

		Offering o1 = new Offering(p6, c1, 1, 1, 10,  new Date(2013-year, 1-month, 20));
		//System.out.println("Date example : "+(new Date(2013-year, 1-month, 20)));
		Offering o2 = new Offering(p1, c2, 1, 1, 10, new Date(2013-year, 1-month, 20));
		Offering o3 = new Offering(p9, c3, 1, 2, 10, new Date(2013-year, 1-month, 20));
		Offering o4 = new Offering(p3, c4, 1, 3, 10, new Date(2013-year, 1-month, 20));
		Offering o5 = new Offering(p1, c7, 1, 6, 10, new Date(2013-year, 1-month, 20));
		Offering o6 = new Offering(p1, c2, 1, 5, 10, new Date(2013-year, 5-month, 20));
		Offering o7 = new Offering(p9, c3, 1, 6, 10, new Date(2013-year, 5-month, 20));
		Offering o8 = new Offering(p4, c8, 1, 6, 10, new Date(2013-year, 5-month, 20));
		Offering o9 = new Offering(p2, c1, 1, 1, 10, new Date(2013-year, 5-month, 20));
		Offering o10 = new Offering(p2, c9, 1, 1, 10, new Date(2013-year, 5-month, 21));

		//new Term(name, startDate, endDate, enrollmentStartDate
		//, enrollmentEndDate, addAndDropStartDate, addAndDropEndDate, withdrawStartDate
		//, withdrawEndDate, submitGradeStartDate, submitGradeEndDate)
		Term t1 = new Term("Fall-12", new Date(2012-year, 1-month, 1), new Date(2013-year, 1-month, 2), new Date(2012-year, 9-month, 8)
		, new Date(2012-year, 9-month, 19), new Date(2012-year, 9-month, 22), new Date(2012-year, 9-month, 25), new Date(2012-year, 11-month, 26)
		, new Date(2012-year, 11-month, 28), new Date(2012-year, 11-month, 28), new Date(2012-year, 12-month, 15));
		Term t2 = new Term("Spring-13", new Date(2013-year, 1-month, 1), new Date(2014-year, 5-month, 29), new Date(2013-year, 5-month, 1)
		, new Date(2013-year, 6-month, 1), new Date(2013-year, 2-month, 2), new Date(2013-year, 2-month, 6), new Date(2013, 4-month, 20)
		, new Date(2013-year, 4-month, 22), new Date(2013-year, 6-month, 23), new Date(2013-year, 6-month, 29));

		t1.addOffering(o1);
		t1.addOffering(o2);
		t1.addOffering(o3);
		t1.addOffering(o4);
		t1.addOffering(o5);
		t2.addOffering(o6);
		t2.addOffering(o7);
		t2.addOffering(o8);
		t2.addOffering(o9);
		t2.addOffering(o10);

		Program pp1 = new Program("Sotfware");
		Program pp2 = new Program("IT");

		Package pack1 = new Package("Network");
		Package pack2 = new Package("Elearning");
		pack1.addCourse(c8);
		pack1.addCourse(c12);
		pack2.addCourse(c13);

		PackagedElectivePolicy pa1 = new PackagedElectivePolicy();
		pa1.addPackage(pack1);
		pa1.addPackage(pack2);
		SimpleElectivePolicy pa2 = new SimpleElectivePolicy();

		pp1.addMandatory(c1);
		pp1.addMandatory(c2);
		pp1.addMandatory(c3);
		pp1.addMandatory(c4);
		pp1.addMandatory(c5);
		pp1.addMandatory(c6);
		pp1.addMandatory(c7);
		pp1.addMandatory(c8);
		pp1.addElective(c7);
		pp1.addElective(c8);
		pp1.setElectivePolicy(pa2);
		pp2.addMandatory(c1);
		pp2.addMandatory(c2);
		pp2.addMandatory(c3);
		pp2.addMandatory(c4);
		pp2.addMandatory(c9);
		pp2.addMandatory(c10);
		pp2.addMandatory(c11);
		pp2.addElective(c8);
		pp2.addElective(c12);
		pp2.addElective(c13);
		pp2.setElectivePolicy(pa1);

		Student s1 = new Student(810190420, "Gholam", "Patoobaf");
		Student s2 = new Student(810190421, "Ghamar", "Aghrabparast");

		s1.setProgram(pp1);
		s2.setProgram(pp2);

		StudyRecord ss1 = new StudyRecord(9, o1, StudyStatus.FAILED);
		StudyRecord ss2 = new StudyRecord(0, o9, StudyStatus.INPROGRESS);
		StudyRecord ss3 = new StudyRecord(16, o1, StudyStatus.PASSED);
		StudyRecord ss4 = new StudyRecord(0, o6, StudyStatus.INPROGRESS);
		StudyRecord ss5 = new StudyRecord(0, o7, StudyStatus.INPROGRESS);

		s1.addStudyRecord(ss1);
		s1.addStudyRecord(ss2);
		s2.addStudyRecord(ss3);
		s2.addStudyRecord(ss4);
		s2.addStudyRecord(ss5);

		ss1.getOffering().decRemainCapacity();
		ss2.getOffering().decRemainCapacity();
		ss3.getOffering().decRemainCapacity();
		ss4.getOffering().decRemainCapacity();
		ss5.getOffering().decRemainCapacity();


		// begin transacton
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();

		// make data persistent
		// professors :
		session.save(p1);
		session.save(p2);
		session.save(p3);
		session.save(p4);
		session.save(p5);
		session.save(p6);
		session.save(p7);
		session.save(p8);
		session.save(p9);
		// courses : 
		session.save(c1);
		session.save(c2);
		session.save(c3);
		session.save(c4);
		session.save(c5);
		session.save(c6);
		session.save(c7);
		session.save(c8);
		session.save(c9);
		session.save(c10);
		session.save(c11);
		session.save(c12);
		session.save(c13);
		// Offerings :
		session.save(o1);
		session.save(o2);
		session.save(o3);
		session.save(o4);
		session.save(o5);
		session.save(o6);
		session.save(o7);
		session.save(o8);
		session.save(o9);
		session.save(o10);
		// StudyRecords :

		session.save(ss1);
		session.save(ss2);
		session.save(ss3);
		session.save(ss4);
		session.save(ss5);

		// Packages : 
		session.save(pack1);
		session.save(pack2);
		// ElectivePolicies
		session.save(pa1);
		session.save(pa2);
		// Programs :
		session.save(pp1);
		session.save(pp2);
		// Students :
		session.save(s1);
		session.save(s2);
		// Terms : 
		session.save(t1);
		session.save(t2);

		// committing changes
		session.getTransaction().commit();

		// closing connection
		session.close();

		System.out.println("Initializing ended succesfully .");
	}
}
