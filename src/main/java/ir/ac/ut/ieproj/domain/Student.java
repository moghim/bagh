package ir.ac.ut.ieproj.domain;

import ir.ac.ut.iecommon.exceptions.DropException;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table
@DiscriminatorValue("Student")
public class Student extends Person {

	@OneToOne(fetch = FetchType.LAZY)
	private Program program;
	@OneToMany(fetch = FetchType.LAZY, orphanRemoval=true, cascade=javax.persistence.CascadeType.ALL)
	//@Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
	@JoinColumn(name = "student_id")
	private Set<StudyRecord> studyRecord;

	public Student() {
		super();
		this.studyRecord = new HashSet<StudyRecord>();
	}
	public Student(int id, String password, String firstName, String lastName, String roleName) {
		super(id, password, firstName, lastName, roleName);
		this.studyRecord = new HashSet<StudyRecord>();
	}
	public Program getProgram() {
		return program;
	}
	public void setProgram(Program program) {
		this.program = program;
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
	public boolean isInSameTimeExam(Offering o, Term t) {
		for (StudyRecord sr: studyRecord) {
			if(sr.getOffering().getExamDate().equals(o.getExamDate()) && t.hasOffering(sr.getOffering()))
				return true;
		}
		return false;
	}
	public boolean isPassedReq() {
		return program.isPassedReq(this);
	}
	public boolean hasPassedCourse(Course c) {
		//System.out.println("Student hasPassesCourse : s="+this+" course="+c);
		for(StudyRecord sr : studyRecord) {
			if(sr.getOffering().getCourse().getId() == c.getId() && sr.getStatus() != StudyStatus.FAILED)
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
		if(num == 0)
			return 0;
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
		//System.out.println("in has offering for student : "+this.id+" and offering : "+o.getId());
		for(StudyRecord sr : studyRecord) {
			//System.out.println("in for : "+sr.getOffering()+" "+sr.getStatus());
			if(sr.getOffering().getId() == o.getId())
				return true;
		}
		return false;
	}
	public StudyStatus offeringStatus(Offering o) {
		for(StudyRecord sr : studyRecord) {
			if(o.getId() == sr.getOffering().getId())
				return sr.getStatus();
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
				if(grade >= 10)
					sr.setStatus(StudyStatus.PASSED);
				else
					sr.setStatus(StudyStatus.FAILED);
			}
		}
	}
	public void addRecord(Offering o) {
		studyRecord.add(new StudyRecord(0, o, StudyStatus.INPROGRESS));
	}
	public void deleteRecord(Offering o) throws DropException {
		boolean isDeleted = false;
		StudyRecord s = null;
		for(StudyRecord sr : studyRecord) {
			if(sr.getOffering().getId() == o.getId())
				s = sr;
		}
		isDeleted = studyRecord.remove(s);
		if(!isDeleted)
			throw new DropException("Delete was not successful .");
	}
	@Override
	public String toString() {
		String result = "";
		result +=  "Student [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + " studyRecords=";
		for (StudyRecord sr : studyRecord) {
			result += sr + " ";
		}
		result += "]";
		return result;
	}
	public boolean hasWaitingForWithrawOfferingInTerm(Term t) {
		for(StudyRecord sr : studyRecord) {
			if(sr.getStatus() == StudyStatus.WAITINGFORWITHRAWACCEPT && t.hasOffering(sr.getOffering()))
				return true;
		}
		return false;
	}
	public boolean isInProgressOffering(Offering o) {
		for(StudyRecord sr : studyRecord) {
			if(o.getId() == sr.getOffering().getId() && sr.getStatus() == StudyStatus.INPROGRESS)
				return true;
		}
		return false;
	}
	public StudyStatus getStatus(Offering o) {
		for(StudyRecord sr : studyRecord) {
			if(o.getId() == sr.getOffering().getId())
				return sr.getStatus();
		}
		return null;
	}
	public float getGrade(Offering o) {
		for(StudyRecord sr : studyRecord) {
			if(o.getId() == sr.getOffering().getId())
				return sr.getGrade();
		}
		return -1;
	}
	public StudyRecord getStudyRecordOfOffering(Offering o) {
		for(StudyRecord sr : studyRecord) {
			if(o.getId() == sr.getOffering().getId())
				return sr;
		}
		return null;
	}
}
