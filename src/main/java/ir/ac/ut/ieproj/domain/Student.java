package ir.ac.ut.ieproj.domain;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table
public class Student {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(unique = true, nullable = false)
	private int id;
	private String firstName;
	private String lastName;
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Program program;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<StudyRecord> studyRecord;

	public Student(){
	}
	public Student(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.studyRecord = new HashSet<StudyRecord>(); 
	}
	public Program getProgram() {
		return program;
	}
	public void setProgram(Program program) {
		this.program = program;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
	public Set<StudyRecord> getStudyRecord() {
		return studyRecord;
	}
	public void setStudyRecord(Set<StudyRecord> studyRecord) {
		this.studyRecord = studyRecord;
	}
	public void addStudyRecord(StudyRecord studyRecord) {
		this.studyRecord.add(studyRecord);
	}

	public int FindNumberOfPassedCourse() {
		int num=0;
		for(StudyRecord sr:studyRecord){
			if(sr.getStatus()==StudyStatus.PASSED)
				num++;
		}
		return num;
	}
	public int findNumberOfCourseTaken() {
		int num=0;
		for(StudyRecord sr :studyRecord){
			if(sr.getStatus()==StudyStatus.PASSED||sr.getStatus()==StudyStatus.INPROGRESS||sr.getStatus()==StudyStatus.WAITINGFORWITHRAWACCEPT)
				num++;
		}
		return num;
	}
	public boolean isInSameTimeOffering(Offering o) {
		for (StudyRecord sr: studyRecord) {
			if(sr.getOffering().getTime() == o.getTime())
				return true;
		}
		return false;
	}
	public boolean isInSameTimeExam(Offering o) {
		for (StudyRecord sr: studyRecord) {
			if(sr.getOffering().getExamDate().equals(o.getExamDate()))
				return true;
		}
		return false;
	}
	public StudyStatus offeringStatus(String OfferingID) {
		for(StudyRecord sr : studyRecord) {
			if(sr.getOffering().equals(OfferingID))
				return sr.getStatus();
		}
		return null;
	}	
	public boolean isPassedReq() {
		return program.isPassedReq(this);
	}
	public boolean hasPassedCourse(Course c) {
		for(StudyRecord sr : studyRecord) {
			if(sr.getOffering().getCourse().getId() == c.getId())
				return true;
		}
		return false;
	}
	public float TermAverage(Term t) {
		float sum = 0;
		int num = 0;
		for(StudyRecord sr : studyRecord) {
			if((sr.getStatus() == StudyStatus.PASSED || sr.getStatus() == StudyStatus.FAILED) && t.hasOffering(sr.getOffering())) {
				sum += sr.getGrade();
				num ++;
			}
		}
		return sum/num;
	}
	public int inProgressUnits() {
		int result = 0;
		for(StudyRecord sr : studyRecord) {
			if(sr.getStatus() == StudyStatus.INPROGRESS) {
				result += sr.getOffering().getCourse().getUnits();
			}
		}
		return result;
	}
	public boolean isPassedCourse(Course c) {
		for(StudyRecord sr : studyRecord) {
			if(sr.getOffering().getCourse().getId() == c.getId() && sr.getStatus() == StudyStatus.PASSED) 
				return true;
		}
		return false;
	}
	public boolean isInProgresCourse(Course c) {
		for(StudyRecord sr : studyRecord) {
			if(sr.getOffering().getCourse().getId() == c.getId() && sr.getStatus() == StudyStatus.INPROGRESS) 
				return true;
		}
		return false;
	}
	public int gradPassedCourses() {
		int result = 0;
		for(StudyRecord sr : studyRecord) {
			if(sr.getStatus() == StudyStatus.PASSED && sr.getOffering().getCourse().getLevel() == Level.GRAD)
				result ++;
		}
		return result;
	}
	public Set<Course> AllPassedCourses() {
		Set<Course> result = new HashSet<Course>();
		for(StudyRecord sr : studyRecord) {
			if(sr.getStatus() == StudyStatus.PASSED)
				result.add(sr.getOffering().getCourse());
		}
		return result;
	}
	public boolean hasOffering(Offering o) {
		for(StudyRecord sr : studyRecord) {
			if(sr.getOffering().getId() == o.getId())
				return true;
		}
		return false;
	}
	public StudyStatus offeringStatus(Offering o) {
		for(StudyRecord sr : studyRecord) {
			if(o.getId() == sr.getOffering().getId())
				sr.getStatus();
		}
		return null;
	}
	public void changeRecordToWithrawn(Offering o) {
		for(StudyRecord sr : studyRecord) {
			if(sr.getOffering().getId() == o.getId()) {
				sr.setStatus(StudyStatus.WITHRAW);
			}
		}
	}
	public void changeRecordToWaitingForWithraws(Offering o) {
		for(StudyRecord sr : studyRecord) {
			if(sr.getOffering().getId() == o.getId()) {
				sr.setStatus(StudyStatus.WAITINGFORWITHRAWACCEPT);
			}
		}
	}
	public void changeRecordToInProgress(Offering o) {
		for(StudyRecord sr : studyRecord) {
			if(sr.getOffering().getId() == o.getId()) {
				sr.setStatus(StudyStatus.INPROGRESS);
			}
		}
	}
	public void setOfferingGrade(float grade, Offering o) {
		for(StudyRecord sr : studyRecord) {
			if(sr.getOffering().getId() == o.getId()) {
				sr.setGrade(grade);
			}
		}
	}
	public void addRecord(Offering o) {
		studyRecord.add(new StudyRecord(0, o, StudyStatus.INPROGRESS));
	}
	public void deleteRecord(Offering o) {
		studyRecord.remove(o);
	}
}
