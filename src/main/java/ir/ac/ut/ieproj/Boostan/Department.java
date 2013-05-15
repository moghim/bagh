package ir.ac.ut.ieproj.Boostan;

import ir.ac.ut.iecommon.exceptions.AcceptWithdrawException;

import ir.ac.ut.iecommon.exceptions.CheckDegreeReqException;
import ir.ac.ut.iecommon.exceptions.DeptLoadException;
import ir.ac.ut.iecommon.exceptions.DropException;
import ir.ac.ut.iecommon.exceptions.OfferingNotFoundException;
import ir.ac.ut.iecommon.exceptions.ProfNotFoundException;
import ir.ac.ut.iecommon.exceptions.RejectWithdrawException;
import ir.ac.ut.iecommon.exceptions.StudentNotFoundException;
import ir.ac.ut.iecommon.exceptions.SubmitGradeException;
import ir.ac.ut.iecommon.exceptions.TakeException;
import ir.ac.ut.iecommon.exceptions.WithdrawException;
import ir.ac.ut.iecommon.interfaces.DepartmentI;
import ir.ac.ut.iecommon.time.Clock;
import ir.ac.ut.ieproj.DeptRepo;

//import java.sql.Time;
import java.util.Date;
import java.util.Vector;

//import javax.swing.plaf.metal.MetalIconFactory.FolderIcon16;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
//import javax.xml.bind.annotation.XmlType;

//import org.omg.CORBA.Current;
//import org.omg.CORBA.SystemException;
// Payam is gav

@XmlRootElement(name = "dept")
//@XmlType(propOrder = {"name", "courses", "programs", "professors"})
public class Department implements DepartmentI{

	private Department() throws DeptLoadException {
		// Exists only to defeat instantiation.	
	}
	private static Department OneDept = null;
	@SuppressWarnings("unused")
	private static SQLConnector sql = null;
	public static Department getInstance() throws DeptLoadException {
		//System.out.println("getInstance Department without argument .");
		/*if(OneDept == null) {
			DeptRepo d = new DeptRepo();
			//System.out.println("Department was null and was created .");
			OneDept = (Department)d.load("sample.xml");
		}*/
		return OneDept;
	}
	public static Department getInstance(String path, String user, String password) throws DeptLoadException {
		//System.out.println("getInstance Department with argument : "+path);
		if(OneDept == null) {
			DeptRepo d = new DeptRepo();
			//System.out.println("Department was null and was created .");
			sql = new SQLConnector(user, password);
			OneDept = (Department)d.load(path);
		}
		return OneDept;
	}
	//my comment
	private String name;
	private Vector<Student> students = new Vector<Student>();
	private Vector<Course> courses = new Vector<Course>();
	private Vector<Program> programs = new Vector<Program>();
	private Vector<Term> terms = new Vector<Term>();
	private Vector<Professor> professors = new Vector<Professor>();

	@Override
	public void acceptWithdraw(String studentID, String offeringID, String professorID)
			throws AcceptWithdrawException, StudentNotFoundException,
			OfferingNotFoundException, ProfNotFoundException {

		Student s = findStudent(studentID);
		Offering o = findOffering(offeringID);
		Term t = findOfferingTerm(offeringID);
		Professor p = findProf(professorID);

		if(s==null)
			throw new StudentNotFoundException("Student with id "+studentID+" not found .");
		if(o==null)
			throw new OfferingNotFoundException("Offering with id "+offeringID+" not found .");
		if(p==null)
			throw new ProfNotFoundException("Professor with id "+professorID+" not found .");
		if(t==null)
			throw new OfferingNotFoundException("Offering with id "+offeringID+" does not belongs to any term .");
		if(!t.getId().equals(findCurrentTerm().getId()))
			throw new OfferingNotFoundException("Offering with id "+offeringID+" does not belongs to this term .");
		if(!s.hasOffering(offeringID))
			throw new OfferingNotFoundException("Student with id "+studentID+" has no offering with id "+offeringID+" .");
		if(!o.getProfessor().equals(professorID))
			throw new ProfNotFoundException("Offering with id "+offeringID+"'s professor is not this professor with id "+professorID+" .");
		if(s.offeringStatus(offeringID)!=StudyStatus.WAITINGFORWITHRAWACCEPT)
			throw new AcceptWithdrawException("Offering was not waiting for withraw .");
		s.changeRecordToWithrawn(offeringID);
	}
	@Override
	public void checkDegreeReq(String studentID) throws CheckDegreeReqException,
	StudentNotFoundException {
		Student findSt = findStudent(studentID);
		if (findSt == null)
			throw new StudentNotFoundException("Student with id "+studentID+" not found .");
		Program findPr = findSt.findProgram(programs);
		if(!findPr.isPass(this, findSt)){
			throw new CheckDegreeReqException("Student with id "+studentID+" doesn't pass enough courses .");
		}
		//System.out.println("check degree succsesful .");
	}
	@Override
	public void drop(String StudentID, String offeringID) throws DropException,
	StudentNotFoundException, OfferingNotFoundException {
		Student findSt = findStudent(StudentID);
		if (findSt == null)
			throw new StudentNotFoundException("Error from drop function: Student with id "+StudentID+" not found .");
		Term findOfTerm = findOfferingTerm(offeringID);
		if (findOfTerm == null)
			throw new OfferingNotFoundException("Error from drop function: Offering with id "+offeringID+" not found .");
		Date now = new Date(Clock.getCurrentTimeMillis());
		Term CurrentTerm = findCurrentTerm();
		if (!(CurrentTerm.getId().equals(findOfTerm.getId())))
			throw new DropException("Error from drop function: offering with id "+ offeringID +" has not taken in current term .");
		if(!findSt.hasOffering(offeringID)) {
			//System.out.println("drop : has Offering Exception : offeringID="+offeringID+",studentID="+StudentID+",hasOffering 4="+findSt.hasOffering("4")+"#");
			throw new DropException("Error from drop function: offering with id "+ offeringID +" has not taken by this student .");
		}
		if ((CurrentTerm.getEnrollmentStartDate().before(now) && CurrentTerm.getEnrollmentEndDate().after(now))||
				CurrentTerm.getAddAndDropStartDate().before(now) && CurrentTerm.getAddAndDropEndDate().after(now)) {
			try {
				findOffering(offeringID).incRemainCapacity();
			} catch (TakeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DeptLoadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			findSt.deleteRecord(offeringID);
			SQLConnector.studentDeleteRecord(StudentID, offeringID);
			SQLConnector.increaseRemainCapacity(offeringID);
		}
		else {
			throw new DropException("Error from drop function: drop in wrong time. ");
		}
	}
	@Override
	public void rejectWithdraw(String studentID, String offeringID, String professorID)
			throws RejectWithdrawException, StudentNotFoundException,
			OfferingNotFoundException, ProfNotFoundException {
		Student s = findStudent(studentID);
		Offering o = findOffering(offeringID);
		Term t = findOfferingTerm(offeringID);
		Professor p = findProf(professorID);

		if(s==null)
			throw new StudentNotFoundException("Student with id "+studentID+" not found .");
		if(o==null)
			throw new OfferingNotFoundException("Offering with id "+offeringID+" not found .");
		if(p==null)
			throw new ProfNotFoundException("Professor with id "+professorID+" not found .");
		if(t==null)
			throw new OfferingNotFoundException("Offering with id "+offeringID+" does not belongs to any term .");
		if(!t.getId().equals( findCurrentTerm().getId()))
			throw new OfferingNotFoundException("Offering with id "+offeringID+" does not belongs to this term .");
		if(!s.hasOffering(offeringID))
			throw new OfferingNotFoundException("Student with id "+studentID+" has no offering with id "+offeringID+" .");
		if(!o.getProfessor().equals(professorID))
			throw new ProfNotFoundException("Offering with id "+offeringID+"'s professor is not this professor with id "+professorID+" .");
		if(s.offeringStatus(offeringID)!=StudyStatus.WAITINGFORWITHRAWACCEPT)
			throw new RejectWithdrawException("Offering was not waiting for withraw .");
		s.changeRecordToInProgress(offeringID);

	}
	@Override
	public void submitGrade(String StudentID, String ProfID, String OfferingID, float Grade)
			throws SubmitGradeException, StudentNotFoundException,
			OfferingNotFoundException, ProfNotFoundException {
		Student findSt=findStudent(StudentID);
		if (findSt==null)
			throw new StudentNotFoundException("Error from submitGrade function: Student with id "+StudentID+" not found .");
		Offering findOf = findOffering(OfferingID);
		if (findOf==null)
			throw new OfferingNotFoundException("Error from submitGrade function: Offering with id "+OfferingID+" not found .");
		Date now=new Date(Clock.getCurrentTimeMillis());
		Term CurrentTerm = findCurrentTerm();
		Professor findPr = findProf(ProfID);
		if (findPr==null)
			throw new ProfNotFoundException("Error from submitGrade function: Professor with id "+ProfID+" not found .");
		if (!findOf.getProfessor().equals(ProfID))
			throw new SubmitGradeException("Error from submitGrade function: not same professor to submit this grade .");
		Term findOfTerm=findOfferingTerm(OfferingID);
		if (!findOfTerm.getId().equals(CurrentTerm.getId()))
			throw new SubmitGradeException("Error from submitGrade function: this offering is not belong to currnet term .");
		if (!findSt.hasOffering(OfferingID))
			throw new SubmitGradeException("Error from submitGrade function: this student has not taken this offering .");
		if(Grade>20 || Grade<0)
			throw new SubmitGradeException("Error from submitGrade function: Grade should be in 0 to 20 range .");
		if (!(CurrentTerm.getSubmitGradeEndDate().after(now) && CurrentTerm.getSubmitGradeStartDate().before(now)))
			throw new SubmitGradeException("Error from submitGrade function: this is not an appropriate time for grade submitions .");
		findSt.setOfferingGrade(Grade, OfferingID);
	}
	@Override
	public void take(String StudentID, String offeringID) throws TakeException,
	StudentNotFoundException, OfferingNotFoundException {
		//System.out.println("here 11");
		Student findSt = findStudent(StudentID);
		if (findSt == null)
			throw new StudentNotFoundException("Error from take function: Student with id "+StudentID+" not found .");
		Offering findOf = findOffering(offeringID);
		//System.out.println("here 10");
		if (findOf == null)
			throw new OfferingNotFoundException("Error from take function: Offering with id "+offeringID+" not found .");
		Course findCo = findCourse(findOf.getCourse());
		Date now = new Date(Clock.getCurrentTimeMillis());
		Term CurrentTerm = findCurrentTerm();
		//System.out.println("current term : "+CurrentTerm.getId()+" ");
		//if(findOfferingTerm(offeringID)==null)
			//System.out.println("findOfferingTerm(offeringID)==null");
		//System.out.println("here 9");
		if(CurrentTerm == null)
			throw new TakeException("Error from take function: Term not found .");
		//System.out.println("currentTerm: #"+CurrentTerm.getId()+"# offering term : #"+findOfferingTerm(offeringID).getId()+"#");
		if(!CurrentTerm.getId().equals(findOfferingTerm(offeringID).getId()))
			throw new TakeException("Error from take function: you can't take an offering which is not in this term .");
		if (findSt.isPassedCourse(findCo.getId(), this))
			throw new TakeException("Error from take function: you can't take a course which you have passed before .");
		if (findSt.inProgressOffering(offeringID)){
			throw new TakeException("Error from take function: you can't take a course which is in progress before .");
		}
		//System.out.println("here 8");
		if (!findSt.isCoreconPass(findCo, this))
			throw new TakeException("Error from take function: Corequisite condition for this course is not observe .");
		if (!findSt.isPreconPass(findCo, this))
			throw new TakeException("Error from take function: prequisite condition for this course is not observe .");
		float avgGrade = findSt.getLastTermAverage(this);
		int passedUnit=findSt.getCurrentTermUnit(this);
		//System.out.println("here 7");
		//System.out.println("passed units : "+passedUnit);
		if (avgGrade<12 && passedUnit+findCo.getUnits()>14)
			throw new TakeException("Error from take function: your last term avrage grade is less than 14 because of that you cant get more than 14 unit .");
		if (avgGrade<18 && passedUnit+findCo.getUnits()>20)
			throw new TakeException("Error from take function: your last term avrage grade is less than 18 because of that you cant get more than 20 unit .");
		//System.out.println("here 6");
		if ( passedUnit+findCo.getUnits()>24)
			throw new TakeException("Error from take function: you cant get more than 24 unit .");
		if (findSt.isInSameTimeCourses(findOf, this))
			throw new TakeException("Error from take function: you can't take this course because you take another course in this time .");	
		if(findSt.isInSameTimeExam(findOf, this))
			throw new TakeException("Error from take function: you can't take this course because one of your course exam is in this course exam .");	
		Program findstProgram=findSt.findProgram(programs);
		if (!findstProgram.canPass(this, findSt, findCo))
			throw new TakeException("Error from take function: you cant take this course because of your elective policy .");

		if ((CurrentTerm.getEnrollmentStartDate().before(now)&&CurrentTerm.getEnrollmentEndDate().after(now))||
				CurrentTerm.getAddAndDropStartDate().before(now)&&CurrentTerm.getAddAndDropEndDate().after(now)) {
			try {
				findOf.decRemainCapacity();
			} catch (DeptLoadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			findSt.addRecord(offeringID);
			SQLConnector.studentAddRecord(StudentID, offeringID);
			SQLConnector.decreaseRemainCapacity(offeringID);
			//System.out.println("take : success : StudentID="+StudentID+",offeringID="+offeringID+"#");
			//System.out.println("take : success : has offering 4="+findSt.hasOffering("4"));
			return;
		}
		throw new TakeException("Error from take function: take in wrong time. ");	
	}	
	@Override
	public void withdraw(String studentID, String offeringID) throws WithdrawException,
	StudentNotFoundException, OfferingNotFoundException {

		Student s = findStudent(studentID);
		Offering o = findOffering(offeringID);
		Term t = findOfferingTerm(offeringID);

		if(s==null)
			throw new StudentNotFoundException("Student with id "+studentID+" not found .");
		if(o==null)
			throw new OfferingNotFoundException("Offering with id "+offeringID+" not found .");
		if(t==null)
			throw new OfferingNotFoundException("Offering with id "+offeringID+" does not belongs to any term .");
		if(!t.getId().equals(findCurrentTerm().getId()))
			throw new OfferingNotFoundException("Offering with id "+offeringID+" does not belongs to this term .");
		if(!s.hasOffering(offeringID))
			throw new OfferingNotFoundException("Student with id "+studentID+" has no offering with id "+offeringID+" .");
		Date now = new Date(Clock.getCurrentTimeMillis());
		if(now.before(t.getWithdrawStartDate()) ||
				now.after(t.getWithdrawEndDate()))
			throw new WithdrawException("The withrow time is passed or not come yet .");
		if(s.hasWithrownOrWaitingOffering(this)) {
			throw new WithdrawException("Only one offering can be withrawn in a term .");
		}
		s.changeRecordToWaitingForWithraws(offeringID);
	}

	public Term findOfferingTerm(String offeringID){
		/*for (Term t : terms ){
			Offering findOff=t.findoOffering(offeringID);
			if (findOff!=null)
				return t;
		}*/
		try {
			return SQLConnector.getOfferingTerm(offeringID);
		} catch (Exception e) {
			System.out.println("term not found Exception .!.!.");
			e.printStackTrace();
		}
		return null;
	}
	public Professor findProf(String profID){
		for (Professor pr :professors){
			if (pr.getId().equals(profID))
				return pr;
		}
		return null;
	}
	public Offering findOffering(String OfferingID){
		/*for (Term t : terms ){
			Offering findOff=t.findoOffering(OfferingID);
			if (findOff!=null)
				return findOff;
		}
		return null;*/
		try {
			return SQLConnector.getOffering(OfferingID);
		} catch (OfferingNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	public Student findStudent(String studentID) {
		/*for (Student s : students){
			if (s.getId().equals(studentID))
				return s;
		}
		return null;
		*/
		try {
			//System.out.println("findStudent come .");
			return SQLConnector.getStudent(studentID);
		} catch (StudentNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	public Course findCourse(String CourseID){
		for (Course c :courses ){
			if (c.getId().equals(CourseID))
				return c;
		}
		return null;
	}

	public Term findCurrentTerm() {
		/*Date now = new Date (Clock.getCurrentTimeMillis());
		System.out.println("current time : "+now);
		for (Term in: terms){
			//System.out.println("terms : "+in.getStartDate()+" *** "+in.getEndDate());
			if (in.getStartDate().before(now) && in.getEndDate().after(now))
				return in;
		}*/
		return SQLConnector.getCurrentTerm();
	}

	public String getName() {
		return name;
	}
	@XmlAttribute
	public void setName(String name) {
		this.name = name;
	}
	public Vector<Student> getStudents() {
		return students;
	}
	public void setStudents(Vector<Student> students) {
		this.students = students;
	}
	public Vector<Course> getCourses() {
		return courses;
	}
	@XmlElementWrapper(name = "courses")
	@XmlElement(name = "course")
	public void setCourses(Vector<Course> courses) {
		this.courses = courses;
	}
	public Vector<Program> getPrograms() {
		return programs;
	}
	@XmlElementWrapper(name = "programs")
	@XmlElement(name = "program")
	public void setPrograms(Vector<Program> programs) {
		this.programs = programs;
	}
	public Vector<Professor> getProfessors() {
		return professors;
	}
	@XmlElementWrapper(name = "profs")
	@XmlElement(name = "prof")
	public void setProfessors(Vector<Professor> professors) {
		this.professors = professors;
	}
	public Vector<Term> getTerms() {
		return terms;
	}
	public void setTerms(Vector<Term> terms) {
		this.terms = terms;
	}
}
