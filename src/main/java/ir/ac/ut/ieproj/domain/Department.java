package ir.ac.ut.ieproj.domain;

import java.util.Date;

import ir.ac.ut.iecommon.exceptions.*;
import ir.ac.ut.iecommon.time.Clock;

import ir.ac.ut.ieproj.database.DBConnector;

public class Department {
	
	public static void acceptWithdraw(int studentID, int offeringID, int professorID)
			throws AcceptWithdrawException, StudentNotFoundException,
			OfferingNotFoundException, ProfNotFoundException, Exception {
		
		Student s = DBConnector.getStudent(studentID);
		Offering o = DBConnector.getOffering(offeringID);
		@SuppressWarnings("unused")
		Professor p = DBConnector.getProfessor(professorID);
		Term t = DBConnector.getCurrentTerm();
		if(!t.hasOffering(o)) {
			throw new AcceptWithdrawException("The offering with id= "+offeringID+" does not belongs to this term .");
		}
		if(!s.hasOffering(offeringID))
			throw new AcceptWithdrawException("Student with id "+studentID+" has no offering with id "+offeringID+" .");
		if(o.getProfessor().getId() != professorID)
			throw new ProfNotFoundException("Offering with id "+offeringID+"'s professor is not this professor with id "+professorID+" .");
		if(s.offeringStatus(offeringID) != StudyStatus.WAITINGFORWITHRAWACCEPT)
			throw new AcceptWithdrawException("Offering was not waiting for withraw .");
		
		s.changeRecordToWithrawn(offeringID);
		DBConnector.saveStudent(s);
	}
	public static void checkDegreeReq(int studentID) throws CheckDegreeReqException,
	StudentNotFoundException {
		
		Student s = DBConnector.getStudent(studentID);
		if(!s.isPassedReq())
			throw new CheckDegreeReqException("Student with id "+studentID+" doesn't pass enough courses .");
	}
	public static void drop(int studentID, int offeringID) throws DropException,
	StudentNotFoundException, OfferingNotFoundException, Exception {
		
		Student s = DBConnector.getStudent(studentID);
		Offering o = DBConnector.getOffering(offeringID);
		if(!s.hasOffering(offeringID))
			throw new DropException("Offering with id="+ offeringID +" has not taken by this student .");
		if(s.offeringStatus(offeringID) != StudyStatus.INPROGRESS)
			throw new DropException("Offering with id="+ offeringID +" for student with id="+studentID+"is not in good status="+s.offeringStatus(offeringID)+" .");
		Term t = DBConnector.getCurrentTerm();
		if(!t.hasOffering(o))
			throw new DropException("Offering with id="+ offeringID +" is not in current term .");
		Date now = new Date(Clock.getCurrentTimeMillis());
		if (!(t.getEnrollmentStartDate().before(now) && t.getEnrollmentEndDate().after(now) ||
				t.getAddAndDropStartDate().before(now) && t.getAddAndDropEndDate().after(now)))
			throw new DropException("Student with id="+studentID+" is not in drop time .");
		// TODO : drop when you have less than 12 units .
		// TODO : drop when you have some course that is corecuesti...
		o.incRemainCapacity();
		s.deleteRecord(offeringID);
		DBConnector.saveStudent(s);
		DBConnector.saveOffering(o);
	}
	public static void rejectWithdraw(int studentID, int offeringID, int professorID)
			throws RejectWithdrawException, StudentNotFoundException,
			OfferingNotFoundException, ProfNotFoundException, Exception {
		Student s = DBConnector.getStudent(studentID);
		Offering o = DBConnector.getOffering(offeringID);
		@SuppressWarnings("unused")
		Professor p = DBConnector.getProfessor(professorID);
		Term t = DBConnector.getCurrentTerm();
		if(!t.hasOffering(o)) {
			throw new RejectWithdrawException("The offering with id= "+offeringID+" does not belongs to this term .");
		}
		if(!s.hasOffering(offeringID))
			throw new RejectWithdrawException("Student with id "+studentID+" has no offering with id "+offeringID+" .");
		if(o.getProfessor().getId() != professorID)
			throw new ProfNotFoundException("Offering with id "+offeringID+"'s professor is not this professor with id "+professorID+" .");
		if(s.offeringStatus(offeringID) != StudyStatus.WAITINGFORWITHRAWACCEPT)
			throw new RejectWithdrawException("Offering was not waiting for withraw .");
		
		s.changeRecordToInProgress(offeringID);
		DBConnector.saveStudent(s);		
	}
	public static void submitGrade(int studentID, int professorID, int offeringID, float grade)
			throws SubmitGradeException, StudentNotFoundException,
			OfferingNotFoundException, ProfNotFoundException, Exception {
		
		Student s = DBConnector.getStudent(studentID);
		Offering o = DBConnector.getOffering(offeringID);
		@SuppressWarnings("unused")
		Professor p = DBConnector.getProfessor(professorID);
		Term t = DBConnector.getCurrentTerm();
		if(!t.hasOffering(o)) {
			throw new SubmitGradeException("The offering with id= "+offeringID+" does not belongs to this term .");
		}
		if(!s.hasOffering(offeringID))
			throw new SubmitGradeException("Student with id "+studentID+" has no offering with id "+offeringID+" .");
		if(o.getProfessor().getId() != professorID)
			throw new ProfNotFoundException("Offering with id "+offeringID+"'s professor is not this professor with id "+professorID+" .");
		if(s.offeringStatus(offeringID) != StudyStatus.INPROGRESS)
			throw new SubmitGradeException("Offering was not in progress .");
		if(grade>20 || grade<0)
			throw new SubmitGradeException("Grade should be in 0 to 20 range .");
		Date now = new Date(Clock.getCurrentTimeMillis());
		if (!(t.getSubmitGradeEndDate().after(now) && t.getSubmitGradeStartDate().before(now)))
			throw new SubmitGradeException("This is not an appropriate time for grade submitions .");
		
		s.setOfferingGrade(grade, offeringID);
		DBConnector.saveStudent(s);
	}
	public static void take(int studentID, int offeringID) throws TakeException,
	StudentNotFoundException, OfferingNotFoundException, Exception {
	
		Student s = DBConnector.getStudent(studentID);
		Offering o = DBConnector.getOffering(offeringID);
		Term t = DBConnector.getCurrentTerm();
		if(!t.hasOffering(o))
			throw new TakeException("Offering with id="+ offeringID +" is not in current term .");
		if(s.hasOffering(offeringID))
			throw new TakeException("Offering with id="+ offeringID +" has taken by this student .");
		Date now = new Date(Clock.getCurrentTimeMillis());
		if (!(t.getEnrollmentStartDate().before(now) && t.getEnrollmentEndDate().after(now) ||
				t.getAddAndDropStartDate().before(now) && t.getAddAndDropEndDate().after(now)))
			throw new TakeException("Student with id="+studentID+" is not in take time .");
		if(o.getRemainCapacity() <= 0)
			throw new TakeException("Offering with id="+offeringID+" has no more capacity .");
		if(s.hasPassedCourse(o.getCourse()))
			throw new TakeException("Student with id="+studentID+" has passed course of offering with id="+offeringID+" .");
		if(!o.getCourse().isPrequIsPassedByStudent(s))
			throw new TakeException("Prequisite condition for course of offering with id="+offeringID+" is not observed by student with id="+studentID+" .");
		if(!o.getCourse().isCorequIsPassedOrInProgressByStudent(s))
			throw new TakeException("Corequisite condition for course of offering with id="+offeringID+" is not observed by student with id="+studentID+" .");
		if(s.lastTermAverage()<10 && s.inProgressUnits()+o.getCourse().getUnits()>14)
			throw new TakeException("Student with id= has below 10 average and can't take more than 14 units .");
		if(s.lastTermAverage()<17 && s.inProgressUnits()+o.getCourse().getUnits()>20)
			throw new TakeException("Student with id= has below 17 average and can't take more than 20 units .");
		if(s.inProgressUnits()+o.getCourse().getUnits()>24)
			throw new TakeException("Student with id= has good average but can't take more than 24 units .");
		
		o.decRemainCapacity();
		s.addRecord(offeringID);
		DBConnector.saveStudent(s);
		DBConnector.saveOffering(o);	
	}	
	public static void withdraw(int studentID, int offeringID) throws WithdrawException,
	StudentNotFoundException, OfferingNotFoundException, Exception {

		Student s = DBConnector.getStudent(studentID);
		Offering o = DBConnector.getOffering(offeringID);
		if(!s.hasOffering(offeringID))
			throw new WithdrawException("Offering with id="+ offeringID +" has not taken by this student .");
		if(s.offeringStatus(offeringID) != StudyStatus.INPROGRESS)
			throw new WithdrawException("Offering with id="+ offeringID +" for student with id="+studentID+"is not in good status="+s.offeringStatus(offeringID)+" .");
		Term t = DBConnector.getCurrentTerm();
		if(!t.hasOffering(o))
			throw new WithdrawException("Offering with id="+ offeringID +" is not in current term .");
		Date now = new Date(Clock.getCurrentTimeMillis());
		if (!(t.getWithdrawStartDate().before(now) && t.getWithdrawEndDate().after(now)))
			throw new WithdrawException("Student with id="+studentID+" is not in withraw time .");
		if(s.inProgressUnits()-o.getCourse().getUnits() < 12)
			throw new WithdrawException("Student with id="+studentID+" will have less than 12 units after withrawing and can't withraw .");
		
		s.changeRecordToWaitingForWithraws(offeringID);
	}
}
