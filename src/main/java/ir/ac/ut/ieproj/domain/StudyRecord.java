package ir.ac.ut.ieproj.domain;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.CascadeType;
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
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "studyrecord", cascade = CascadeType.ALL)
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
