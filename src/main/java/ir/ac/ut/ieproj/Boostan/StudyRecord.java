package ir.ac.ut.ieproj.Boostan;


public class StudyRecord {
	
	private float grade;
	private Offering offering;
	private StudyStatus status;
	
	public StudyRecord() {		
	}
	public StudyRecord(float grade, StudyStatus status) {
		this.grade = grade;
		this.status = status;
	}
	public float getGrade() {
		return grade;
	}
	public void setGrade(float grade) {
		this.grade = grade;
	}
	public Offering getOffering() {
		return offering;
	}
	public void setOffering(Offering offering) {
		this.offering = offering;
	}
	public StudyStatus getStatus() {
		return status;
	}
	public void setStatus(StudyStatus status) {
		this.status = status;
	}
}
