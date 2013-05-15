package ir.ac.ut.ieproj.Boostan;


public class StudyRecord {
	
	private float grade;
	private String offering;
	private StudyStatus status;
	
	public StudyRecord() {
		
	}
	
	public StudyRecord(float grade, String offering, StudyStatus status) {
		super();
		this.grade = grade;
		this.offering = offering;
		this.status = status;
	}
	public float getGrade() {
		return grade;
	}

	public void setGrade(float grade) {
		this.grade = grade;
	}
	public String getOffering() {
		return offering;
	}
	public void setOffering(String offering) {
		this.offering = offering;
	}
	public StudyStatus getStatus() {
		//
		return status;
	}

	public void setStatus(StudyStatus status) {
		this.status = status;
	}


}
