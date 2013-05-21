package ir.ac.ut.ieproj.domain;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table
public class StudyRecord {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(unique = true, nullable = false)
	private int id;
	private float grade;
	@OneToOne(fetch = FetchType.LAZY)
	private Offering offering;
	private StudyStatus status;
	
	public StudyRecord() {		
	}
	public StudyRecord(float grade, StudyStatus status) {
		this.grade = grade;
		this.status = status;
	}
	public StudyRecord(float grade, Offering offering, StudyStatus status) {
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "[id=" + id + ", grade=" + grade  + ", status=" + status + "]";
	}
	@Override
	public boolean equals(Object obj) {
		if(obj == null) 
			return false;
		if(!(obj instanceof StudyRecord))
			return false;
		StudyRecord s = (StudyRecord) obj;
		if(!s.getOffering().equals(this.getOffering()))
			return false;
		return true;
	}
}
