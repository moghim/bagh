package ir.ac.ut.ieproj.Boostan;
import ir.ac.ut.iecommon.exceptions.TakeException;

import java.util.Vector;

public class Student {
	
	private String id;
	private String firstName;
	private String lastName;
	private String program;
	private Vector<StudyRecord> studyRecord = new Vector<StudyRecord>();
	public Student(){
	}
	public Student(String id, String firstName, String lastName,
			String program, Vector<StudyRecord> studyRecord) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.program = program;
		this.studyRecord = studyRecord;
	}
	public String getProgram() {
		return program;
	}
	public void setProgram(String program) {
		this.program = program;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Vector<StudyRecord> getStudyRecord() {
		return studyRecord;
	}
	public void setStudyRecord(Vector<StudyRecord> studyRecord) {
		this.studyRecord = studyRecord;
	}
	
	public void addRecord(String offeringID){
		StudyRecord sr = new StudyRecord();
		sr.setOffering(offeringID);
		sr.setStatus(StudyStatus.INPROGRESS);
		studyRecord.add(sr);
	}
	public void deleteRecord(String offeringID){
		for (int i=0;i<studyRecord.size();i++){
			if (studyRecord.get(i).getOffering().equals(offeringID)){
				studyRecord.remove(i);
				break;
			}
		}
	}
	public void changeRecordToWithrawn(String offeringID) {
		for (int i=0;i<studyRecord.size();i++){
			if (studyRecord.get(i).getOffering().equals(offeringID)){
				studyRecord.get(i).setStatus(StudyStatus.WITHRAW);
				break;
			}
		}
	}
	public void changeRecordToWaitingForWithraws(String offeringID) {
		for (int i=0;i<studyRecord.size();i++){
			if (studyRecord.get(i).getOffering().equals(offeringID)){
				studyRecord.get(i).setStatus(StudyStatus.WAITINGFORWITHRAWACCEPT);
				break;
			}
		}
	}
	public void changeRecordToInProgress(String offeringID) {
		for (int i=0;i<studyRecord.size();i++){
			if (studyRecord.get(i).getOffering().equals(offeringID)){
				studyRecord.get(i).setStatus(StudyStatus.INPROGRESS);
				break;
			}
		}
	}
 	public boolean hasOffering(String offeringID){
		for (StudyRecord sr :studyRecord){
			if (sr.getOffering().equals(offeringID))
				return true;
		}
		return false;
	}
	public void setOfferingGrade(float grade,String offeringID){
		for (int i=0;i<studyRecord.size();i++){
			if (studyRecord.get(i).getOffering().equals(offeringID)){
				studyRecord.get(i).setGrade(grade);
				if(grade<10)
					studyRecord.get(i).setStatus(StudyStatus.FAILED);
				else
					studyRecord.get(i).setStatus(StudyStatus.PASSED);
				break;
			}
		}
	}
	public boolean isPassedCourse(String courseID, Department dep){
		for(StudyRecord sr : studyRecord){
			if(sr.getStatus() == StudyStatus.PASSED){
				Offering of = dep.findOffering(sr.getOffering());
				if (of.getCourse().equals(courseID))
					return true;
			}
		}
		return false;
	}
	public int FindNumberOfPassedCourse(){
		int num=0;
		for(StudyRecord sr :studyRecord){
			if(sr.getStatus()==StudyStatus.PASSED)
				num++;
		}
		return num;
	}
	public int findNumberOfCourseTaken(){
		int num=0;
		for(StudyRecord sr :studyRecord){
			if(sr.getStatus()==StudyStatus.PASSED||sr.getStatus()==StudyStatus.INPROGRESS||sr.getStatus()==StudyStatus.WAITINGFORWITHRAWACCEPT)
				num++;
		}
		return num;
	}
	public boolean isInprogressOrPassCourse(String courseID,Department dep){
		for(StudyRecord sr :studyRecord){
			if(sr.getStatus()==StudyStatus.PASSED||sr.getStatus()==StudyStatus.INPROGRESS){
				Offering of=dep.findOffering(sr.getOffering());	
				if (of.getCourse().equals(courseID))
					return true;
			}
		}
		return false;
	}
	public boolean isPassedOffering(String offeringID) throws TakeException{
		for(StudyRecord sr : studyRecord){
			if(sr.getStatus()==StudyStatus.PASSED && sr.getOffering().equals(offeringID))
				return true;
		}
		return false;
	}
	public boolean inProgressOffering(String offeringID) {
		//System.out.println("inProgressOffering : ");
		for(StudyRecord sr :studyRecord){
			//System.out.println("*"+sr.getOffering()+"* *"+sr.getStatus()+"*");
			if(sr.getStatus()==StudyStatus.INPROGRESS && sr.getOffering().equals(offeringID))
				return true;
		}
		return false;
	}
	public float getLastTermAverage(Department dep){
		//System.out.println("getLastTermAverage come .");
		int lastTermID = -1;
		for(StudyRecord sr : studyRecord) {
			//System.out.println("offeringId = "+sr.getOffering());
			String temp = dep.findOfferingTerm(sr.getOffering()).getId();
			//System.out.println("term = "+temp);
			if(!dep.findCurrentTerm().equals(temp) && Integer.parseInt(temp) > lastTermID)
				lastTermID = Integer.parseInt(temp);
		}
		if(lastTermID == -1) {
			//System.out.println("terme avali");
			return 17;
		}
		String lastTerm = Integer.toString(lastTermID);
		float avg = 0;
		int numOfCourse = 0;
		for(StudyRecord sr : studyRecord){
			if (sr.getStatus() == StudyStatus.PASSED || sr.getStatus() == StudyStatus.FAILED){
				Term ofTerm = dep.findOfferingTerm(sr.getOffering());
			//	System.out.println("badbakht : "+Integer.getInteger(ofTerm.getId()));
				if (!ofTerm.getId().equals(lastTerm)){
					numOfCourse += dep.findCourse(sr.getOffering()).getUnits();
					avg += sr.getGrade()*dep.findCourse(sr.getOffering()).getUnits();
				}
			}
		}
		if (numOfCourse != 0)
			avg = avg/numOfCourse;
		//System.out.println("moadel : "+avg);
		return avg;
	}
	public int getCurrentTermUnit(Department dep) {
		//System.out.println("getCurrentTerm Unit come .");
		int unitNum = 0;
		for(StudyRecord sr :studyRecord) {
			if (sr.getStatus() == StudyStatus.INPROGRESS) {
				Term ofTerm = dep.findOfferingTerm(sr.getOffering());
				Term currentTerm = dep.findCurrentTerm();
				if (ofTerm.getId().equals(currentTerm.getId())) {
					Offering of = dep.findOffering(sr.getOffering());
					Course co = dep.findCourse(of.getCourse());
					unitNum += co.getUnits();
				}
			}
		}
		return unitNum;
	}
	public boolean hasWithrownOrWaitingOffering(Department dep) {
		Term thisTerm = dep.findCurrentTerm();
		for(StudyRecord sr : studyRecord){
			//Offering o = dep.findOffering(sr.getOffering());
			Term t = dep.findOfferingTerm(sr.getOffering());
			if(thisTerm.getId().equals(t.getId())) {
				if(sr.getStatus() == StudyStatus.WAITINGFORWITHRAWACCEPT 
				|| sr.getStatus()== StudyStatus.WITHRAW ) {
					return true;
				}
			}
		}
		return false;
	}
	public boolean isInSameTimeCourses(Offering of,Department dep){
		for (StudyRecord sr: studyRecord){	
			Term currentTerm=dep.findCurrentTerm();
			Term termSR=dep.findOfferingTerm(sr.getOffering());
			if(termSR.getId().equals(currentTerm.getId())){
				Offering depOf=dep.findOffering(sr.getOffering());
				if (depOf.getTime()==of.getTime())
					return true;
			}	
		}
		return false;
	}
	public boolean isInSameTimeExam(Offering of,Department dep){
		for (StudyRecord sr: studyRecord){	
			Term currentTerm=dep.findCurrentTerm();
			Term termSR=dep.findOfferingTerm(sr.getOffering());
			if(termSR.getId().equals(currentTerm.getId())){
				Offering depOf=dep.findOffering(sr.getOffering());
				if (depOf.getExamDate().equals(of.getExamDate()))
					return true;
			}
			
		}
		return false;
	}
	public Program findProgram(Vector<Program> Prs){
		for (Program p:Prs){
			if(p.getId().equals(program))
				return p;
		}
		return null;
	}
	public boolean isPreconPass(Course co, Department dep){
		//System.out.println("isPreconPass come .");
		//Course toCheck=null;
		//System.out.println("finded course :"+co.getId());
		for (String a:co.getPrerequisite()){
			//System.out.println("here");
			if (!isPassedCourse(a, dep))
				return false;
		}
		return true;
	}
	public boolean isCoreconPass(Course co,Department dep){
		//Course toCheck=null;
		//if(co.getCorequisite()==null)
			//System.out.println("babbakht shodim ...");
		//System.out.println("isCoreconPass come .");
		for (String a:co.getCorequisite()){
			if (!isInprogressOrPassCourse(a, dep))
				return false;
		}
		return true;
	}
	public StudyStatus offeringStatus(String OfferingID) {
		for(StudyRecord sr : studyRecord) {
			if(sr.getOffering().equals(OfferingID))
				return sr.getStatus();
		}
		return null;
	}
}
