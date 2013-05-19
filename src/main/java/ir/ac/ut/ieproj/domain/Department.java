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
		if(!s.hasOffering(o))
			throw new AcceptWithdrawException("Student with id "+studentID+" has no offering with id "+offeringID+" .");
		if(o.getProfessor().getId() != professorID)
			throw new ProfNotFoundException("Offering with id "+offeringID+"'s professor is not this professor with id "+professorID+" .");
		if(s.offeringStatus(o) != StudyStatus.WAITINGFORWITHRAWACCEPT)
			throw new AcceptWithdrawException("Offering with id "+offeringID+"is not in good status="+s.offeringStatus(o)+" .");
		s.changeRecordToWithrawn(o);
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
		if(!s.hasOffering(o))
			throw new DropException("Offering with id="+ offeringID +" has not taken by this student .");
		if(s.offeringStatus(o) != StudyStatus.INPROGRESS)
			throw new DropException("Offering with id="+ offeringID +" for student with id="+studentID+" is not in good status="+s.offeringStatus(o)+" .");
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
		s.deleteRecord(o);
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
		if(!s.hasOffering(o))
			throw new RejectWithdrawException("Student with id "+studentID+" has no offering with id "+offeringID+" .");
		if(o.getProfessor().getId() != professorID)
			throw new ProfNotFoundException("Offering with id "+offeringID+"'s professor is not this professor with id "+professorID+" .");
		if(s.offeringStatus(o) != StudyStatus.WAITINGFORWITHRAWACCEPT)
			throw new RejectWithdrawException("Offering was not waiting for withraw .");
		
		s.changeRecordToInProgress(o);
		DBConnector.saveStudent(s);		
	}
	public static void submitGrade(int studentID, int professorID, int offeringID, float grade)
			throws SubmitGradeException, StudentNotFoundException,
			OfferingNotFoundException, ProfNotFoundException, Exception {
		
		Student s = DBConnector.getStudent(studentID);
		Offering o = DBConnector.getOffering(offeringID);
		Professor p = DBConnector.getProfessor(professorID);
		Term t = DBConnector.getCurrentTerm();
		if(!t.hasOffering(o))
			throw new SubmitGradeException("The offering with id= "+offeringID+" does not belongs to this term .");
		if(!s.hasOffering(o))
			throw new SubmitGradeException("Student with id "+studentID+" has no offering with id "+offeringID+" .");
		if(o.getProfessor().getId() != p.getId())
			throw new ProfNotFoundException("Offering with id "+offeringID+"'s professor is not this professor with id "+professorID+" .");
		if(s.offeringStatus(o) != StudyStatus.INPROGRESS)
			throw new SubmitGradeException("Offering was not in progress .");
		if(grade>20 || grade<0)
			throw new SubmitGradeException("Grade should be in 0 to 20 range .");
		Date now = new Date(Clock.getCurrentTimeMillis());
		if (!(t.getSubmitGradeEndDate().after(now) && t.getSubmitGradeStartDate().before(now)))
			throw new SubmitGradeException("This is not an appropriate time for grade submitions .");
		
		s.setOfferingGrade(grade, o);
		DBConnector.saveStudent(s);
	}
	public static void take(int studentID, int offeringID) throws TakeException,
	StudentNotFoundException, OfferingNotFoundException, Exception {
	
		Student s = DBConnector.getStudent(studentID);
		Offering o = DBConnector.getOffering(offeringID);
		Term t = DBConnector.getCurrentTerm();
		Term preTerm = DBConnector.getPreviosTerm();
		if(!t.hasOffering(o))
			throw new TakeException("Offering with id="+ offeringID +" is not in current term .");
		if(s.hasOffering(o))
			throw new TakeException("Offering with id="+ offeringID +" has taken by this student .");
		Date now = new Date(Clock.getCurrentTimeMillis());
		if (!(t.getEnrollmentStartDate().before(now) && t.getEnrollmentEndDate().after(now) ||
				t.getAddAndDropStartDate().before(now) && t.getAddAndDropEndDate().after(now)))
			throw new TakeException("Student with id="+studentID+" is not in take time .");
		if(o.getRemainCapacity() <= 0)
			throw new TakeException("Offering with id="+offeringID+" has no more capacity .");
		if(s.hasPassedCourse(o.getCourse()))
			throw new TakeException("Student with id="+studentID+" has passed course of offering with id="+offeringID+" .");
		// TODO : take a course which you have in progress in another offering .
		if(!o.getCourse().isPrequIsPassedByStudent(s))
			throw new TakeException("Prequisite condition for course of offering with id="+offeringID+" is not observed by student with id="+studentID+" .");
		if(!o.getCourse().isCorequIsPassedOrInProgressByStudent(s))
			throw new TakeException("Corequisite condition for course of offering with id="+offeringID+" is not observed by student with id="+studentID+" .");
		if(s.isInSameTimeOffering(o))
			throw new TakeException("Student with id="+studentID+" has offering in same time .");
		if(s.isInSameTimeExam(o))
			throw new TakeException("Student with id="+studentID+" has exam in same time .");
		// TODO an student with was not in university for one term is not supported ...
		if(preTerm!=null  && s.TermAverage(preTerm)<10 && s.inProgressUnits()+o.getCourse().getUnits()>14)
			throw new TakeException("Student with id="+studentID+" has below 10 average and can't take more than 14 units .");
		if(preTerm!=null && s.TermAverage(preTerm)<17 && s.inProgressUnits()+o.getCourse().getUnits()>20)
			throw new TakeException("Student with id="+studentID+" has below 17 average and can't take more than 20 units .");
		if(preTerm==null && s.inProgressUnits()+o.getCourse().getUnits()>20)
			throw new TakeException("Student with id="+studentID+" is in his first term and can't take more than 20 units .");
		if(s.TermAverage(t)+o.getCourse().getUnits()>24)
			throw new TakeException("Student with id="+studentID+" has good average but can't take more than 24 units .");
		
		o.decRemainCapacity();
		s.addRecord(o);
		DBConnector.saveStudent(s);
		DBConnector.saveOffering(o);	
	}	
	public static void withdraw(int studentID, int offeringID) throws WithdrawException,
	StudentNotFoundException, OfferingNotFoundException, Exception {

		Student s = DBConnector.getStudent(studentID);
		Offering o = DBConnector.getOffering(offeringID);
		if(!s.hasOffering(o))
			throw new WithdrawException("Offering with id="+ offeringID +" has not taken by this student .");
		if(s.offeringStatus(o) != StudyStatus.INPROGRESS)
			throw new WithdrawException("Offering with id="+ offeringID +" for student with id="+studentID+"is not in good status="+s.offeringStatus(o)+" .");
		Term t = DBConnector.getCurrentTerm();
		if(!t.hasOffering(o))
			throw new WithdrawException("Offering with id="+ offeringID +" is not in current term .");
		if(s.hasWaitingForWithrawOfferingInTerm(t))
			throw new WithdrawException("Student with id="+studentID+"  has another waiting for withraw offering in this term .");
		Date now = new Date(Clock.getCurrentTimeMillis());
		//System.out.println("now : "+now);
		//System.out.println("withraw start and end : "+t.getWithdrawStartDate()+" "+t.getWithdrawEndDate());
		if (!(t.getWithdrawStartDate().before(now) && t.getWithdrawEndDate().after(now)))
			throw new WithdrawException("Student with id="+studentID+" is not in withraw time .");
		if(s.inProgressUnits()-o.getCourse().getUnits() < 12)
			throw new WithdrawException("Student with id="+studentID+" will have less than 12 units after withrawing and can't withraw .");
		
		s.changeRecordToWaitingForWithraws(o);
	}
	
	public static Student getStudent(int studentID) throws StudentNotFoundException {
		return DBConnector.getStudent(studentID);
	}
	public static Term getCurrentTerm() throws Exception {
		return DBConnector.getCurrentTerm();
	}
}
