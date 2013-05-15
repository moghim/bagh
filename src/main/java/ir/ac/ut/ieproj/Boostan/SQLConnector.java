package ir.ac.ut.ieproj.Boostan;

import ir.ac.ut.iecommon.exceptions.OfferingNotFoundException;
import ir.ac.ut.iecommon.exceptions.StudentNotFoundException;
import ir.ac.ut.iecommon.time.Clock;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Vector;

public class SQLConnector {

	private static Connection con;

	public SQLConnector(String user, String password) {
		// load the JDBC Driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException ex) {
			System.out.println("Unable to load MySQL JDBC driver");
		}

		// connecting to the database
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/boostandb", user, password);
		} catch (SQLException e) {
			System.out.println("sql error ...");
			e.printStackTrace();
		}
	}

	public static Student getStudent(String id) throws StudentNotFoundException {

		Statement st;
		try {

			st = con.createStatement();

			//System.out.println("getStudent come .");

			ResultSet rs = st.executeQuery("select * from student where id='"+id+"';");
			//System.out.println("select * from student where id='"+id+"';");;
			rs.next();
			//System.out.println("founded student : " + rs.getString("id") + '\t' + rs.getString("firstName") + '\t' + rs.getString("lastName") + '\t' + rs.getString("program"));

			ResultSet rr = st.executeQuery("select * from studyrecord where studentId='"+id+"';");
			//System.out.println("here 4");
			Vector<StudyRecord> records = new Vector<StudyRecord>();
			while(rr.next()) {
				//System.out.println("get student : add record : "+rr.getString("offering")+"\t"+StudyStatus.valueOf(rr.getString("status")));
				records.add(new StudyRecord(rr.getFloat("grade"), rr.getString("offering"), StudyStatus.valueOf(rr.getString("status"))));
			}


			rs = st.executeQuery("select * from student where id='"+id+"';");
			//System.out.println("select * from student where id='"+id+"';");;
			rs.next();

			return new Student(id, rs.getString("firstName"),  rs.getString("lastName"),  rs.getString("program"), records);

		} catch (SQLException e) {
			throw new StudentNotFoundException("student with id "+id+" not found in sql .");
		}
	}
	public static Term getOfferingTerm(String offeringID) {
		Statement st;
		try {

			st = con.createStatement();

			//System.out.println("get offering term : ...");
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

			ResultSet rs = st.executeQuery("select termId from offering where id='"+offeringID+"';");
			//System.out.println("here 14");
			rs.next();
			String termId = rs.getString("termId");
			ResultSet rr = st.executeQuery("select * from term where id='"+termId+"';");
			rr.next();
			//System.out.println("founded term : " + rr.getString("id") + '\t' + rr.getString("name") + '\t' + rr.getString("startDate") + '\t' + rr.getString("endDate"));

			ResultSet rp = st.executeQuery("select * from offering where termId='"+termId+"';");
			//System.out.println("select * from offering where termId='"+termId+"';");
			//System.out.println("here 15");
			Vector<Offering> offerings = new Vector<Offering>();
			while(rp.next()) {
				offerings.add(new Offering(rp.getString("id"), rp.getString("professor"), rp.getString("course"), rp.getInt("section"),
						rp.getInt("time"), rp.getInt("capacity"), rp.getInt("remainCapacity"), sdf.parse(rp.getString("examDate"))));
			}
			//System.out.println("here 16");
			rr = st.executeQuery("select * from term where id='"+termId+"';");
			rr.next();
			return new Term(termId, rr.getString("name"), sdf.parse(rr.getString("startDate")), sdf.parse(rr.getString("endDate")), sdf.parse(rr.getString("enrollmentStartDate"))
					, sdf.parse(rr.getString("enrollmentEndDate")), sdf.parse(rr.getString("addAndDropStartDate")), sdf.parse(rr.getString("addAndDropEndDate")), sdf.parse(rr.getString("withdrawStartDate"))
					, sdf.parse(rr.getString("withdrawEndDate")), sdf.parse(rr.getString("submitGradeStartDate")), sdf.parse(rr.getString("submitGradeEndDate")), offerings);

		} catch (ParseException e) {
			System.out.println("Time Parse Exceptin .");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("SQLException .");
			return null;
		}
		return null;
	}
	public static Offering getOffering(String offeringID) throws OfferingNotFoundException {
		Statement st;
		try {

			st = con.createStatement();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			//System.out.println("here !");

			ResultSet rs = st.executeQuery("select * from offering where id='"+offeringID+"';");
			//System.out.println("select * from student where id='"+id+"';");;
			rs.next();
			//System.out.println("founded offering : " + rs.getString("id") + '\t' + rs.getString("course") + '\t' + rs.getString("capacity") + '\t' + rs.getString("remainCapacity"));

			return new Offering(rs.getString("id"), rs.getString("professor"), rs.getString("course"), rs.getInt("section"),
					rs.getInt("time"), rs.getInt("capacity"), rs.getInt("remainCapacity"), sdf.parse(rs.getString("examDate")));

		} catch (SQLException e) {
			throw new OfferingNotFoundException("student with id "+offeringID+" not found in sql .");
		} catch (ParseException e) {
			System.out.println("Time Parse Exceptin .");
			e.printStackTrace();
		}
		return null;
	}
	public static Term getCurrentTerm() {
		try {
			Date now = new Date (Clock.getCurrentTimeMillis());
			Statement st = con.createStatement();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			//System.out.println("current time : "+now);
			String termID;

			ResultSet rs = st.executeQuery("select * from term;");
			while(rs.next()) {
				//System.out.println("In getCrrentTerm , in while, all terms : "+rs.getString("id")+"\t"+rs.getString("startDate")+"\t"+rs.getString("endDate"));
				if(sdf.parse(rs.getString("startDate")).before(now) && sdf.parse(rs.getString("endDate")).after(now)) {
					//System.out.println("Founded current term : "+rs.getString("id"));
					termID = rs.getString("id");
					ResultSet rp = st.executeQuery("select * from offering where termId='"+rs.getString("id")+"';");
					//System.out.println("here 3");
					Vector<Offering> offerings = new Vector<Offering>();
					while(rp.next()) {
						offerings.add(new Offering(rp.getString("id"), rp.getString("professor"), rp.getString("course"), rp.getInt("section"),
								rp.getInt("time"), rp.getInt("capacity"), rp.getInt("remainCapacity"), sdf.parse(rp.getString("examDate"))));
						//System.out.println("added offering id : "+rp.getString("id"));
					}
					rs = st.executeQuery("select * from term where id='"+termID+"';");
					rs.next();
					return new Term(rs.getString("id"), rs.getString("name"), sdf.parse(rs.getString("startDate")), sdf.parse(rs.getString("endDate")), sdf.parse(rs.getString("enrollmentStartDate"))
							, sdf.parse(rs.getString("enrollmentEndDate")), sdf.parse(rs.getString("addAndDropStartDate")), sdf.parse(rs.getString("addAndDropEndDate")), sdf.parse(rs.getString("withdrawStartDate"))
							, sdf.parse(rs.getString("withdrawEndDate")), sdf.parse(rs.getString("submitGradeStartDate")), sdf.parse(rs.getString("submitGradeEndDate")), offerings);

				}
			}
			con.close();
			//if (in.getStartDate().before(now) && in.getEndDate().after(now))
			//	return in;
			System.out.println("current time : "+now);
		} catch (SQLException e) {
			System.out.println("SQLExceptin .");
			e.printStackTrace();
		} catch (ParseException e) {
			System.out.println("Time Parse Exceptin .");
			e.printStackTrace();
		}
		return null;
	}


	public static void studentAddRecord(String studentID, String offeringID) {
		try {

			Statement st;
			st = con.createStatement();
			//System.out.println("insert into studyRecord values('0', '"+offeringID+"', 'INPROGRESS', '"+studentID+"');");
			/*int temp = */st.executeUpdate("insert into studyRecord(grade, offering, status, studentId) values('0', '"+offeringID+"', 'INPROGRESS', '"+studentID+"');");
			//System.out.println(temp+" row affected in add record .");
			//ResultSet rs = st.executeQuery("select * from studyRecord where offering='"+offeringID+"' and studentId='"+studentID+"';");
			//rs.next();
			//System.out.println("result = "+rs.getString("status"));
			st.closeOnCompletion();
		} catch (SQLException e) {
			System.out.println("Add Record Failed ...");
			e.printStackTrace();
		}
	}
	public static void decreaseRemainCapacity(String offeringID) {
		try {

			Statement st;
			st = con.createStatement();
			/*int temp = */st.executeUpdate("update offering set remainCapacity=remainCapacity-1 where id='"+offeringID+"';");
			//System.out.println(temp+" row affected in decrease remain capacity .");
			st.closeOnCompletion();
		} catch (SQLException e) {
			System.out.println("decrease capacity Failed ...");
			e.printStackTrace();
		}
	}
	public static void studentDeleteRecord(String studentID, String offeringID) {
		try {

			Statement st;
			st = con.createStatement();
			/*int temp = */st.executeUpdate("delete from studyRecord where offering='"+offeringID+"' and studentId='"+studentID+"';");
			//System.out.println(temp+" row affected in delete record .");
			st.closeOnCompletion();
		} catch (SQLException e) {
			System.out.println("delete Record Failed ...");
			e.printStackTrace();
		}
	}
	public static void increaseRemainCapacity(String offeringID) {
		try {

			Statement st;
			st = con.createStatement();
			/*int temp = */st.executeUpdate("update offering set remainCapacity=remainCapacity+1 where id='"+offeringID+"';");
			//System.out.println(temp+" row affected in increase remain capacity .");
			st.closeOnCompletion();
		} catch (SQLException e) {
			System.out.println("increase capacity Failed ...");
			e.printStackTrace();
		}
	}
}
