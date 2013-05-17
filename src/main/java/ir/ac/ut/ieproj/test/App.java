package ir.ac.ut.ieproj.test;

import ir.ac.ut.ieproj.Boostan.*;
import ir.ac.ut.ieproj.database.HibernateUtil;

import org.hibernate.Session;

public class App 
{
    public static void main( String[] args ) throws Exception
    {
    	System.out.println("Hello World !");
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
		
		// making needed data
		Professor p1 = new Professor("Ramtin", "Khosravi");
		Professor p2 = new Professor("Ahmad", "Khonsari");
		Professor p3 = new Professor("Hesham", "Faili");
		Professor p4 = new Professor("Fatemeh", "Ghasemi");
		Professor p5 = new Professor("Mahmood", "Kharat");
		Professor p6 = new Professor("Mahmoudreza", "Hashemi");
		Professor p7 = new Professor("Fattaneh", "Taghiyareh");
		Professor p8 = new Professor("Azadeh", "Shakery");
		Professor p9 = new Professor("Siamak", "Mohammadi");
		
		// begin transaction
		Session session = (Session) HibernateUtil.getSessionFactory();
		session.beginTransaction();
		
		// make data persistent
		session.save(p1);
		session.save(p2);
		session.save(p3);
		session.save(p4);
		session.save(p5);
		session.save(p6);
		session.save(p7);
		session.save(p8);
		session.save(p9);
		
		// closing connection
		session.close();
	}
}
