package ir.ac.ut.ieproj;

import ir.ac.ut.ieproj.Boostan.*;
import ir.ac.ut.ieproj.database.HibernateUtil;

import org.hibernate.Session;

public class App 
{
    public static void main( String[] args ) throws Exception
    {
    	initialize();
		
		// date = "04-02-2013"
		//d.take("810190421", "8");
		//d.take("810190421", "8");
		//d.drop("810190420", "9");
		//d.drop("810190421", "8");
		//d.withdraw("810190420", "9");
		//d.withdraw("810190421", "6");
		//d.withdraw("810190421", "7");
		
		//System.out.println(d.findStudent("810190421").getLastTermAverage(d));
		
		//d.acceptWithdraw("810190421", "7", "9");
		//d.rejectWithdraw("810190421", "6", "1");
		
		//d.submitGrade("810190421", "9", "7", 0);
		//d.checkDegreeReq("810190420");
		
		//System.out.println("\n");
    }

	private static void initialize() {
		Student s1 = new Student();
		
		Session session = (Session) HibernateUtil.getSessionFactory();
		session.beginTransaction();
		session.save(s1);
	}
}
