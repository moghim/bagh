package ir.ac.ut.ieproj.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

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
public class Offering {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(unique = true, nullable = false)
	private int id;
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Professor professor;
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Course course;
	private int section;
	private int time;
	private int capacity;
	private int remainCapacity;
	private Date examDate;
	
	public Offering() {
	}
	public Offering(Professor professor, Course course, int section,
			int time, int capacity, Date examDate) {
		this.professor = professor;
		this.course = course;
		this.section = section;
		this.time = time;
		this.capacity = capacity;
		this.setRemainCapacity(capacity);
		this.examDate = examDate;
	}
	public synchronized void decRemainCapacity() {
		setRemainCapacity(getRemainCapacity() - 1);
	}
	public synchronized void incRemainCapacity() {
		setRemainCapacity(getRemainCapacity() + 1);
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getExamDate() {
		return examDate;
	}
	public void setExamDate(Date examDate) {
		this.examDate =examDate;
	}
	public Professor getProfessor() {
		return professor;
	}
	public void setProfessor(Professor professor) {
		this.professor = professor;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	public int getSection() {
		return section;
	}
	public void setSection(int section) {
		this.section = section;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.setRemainCapacity(capacity);
		this.capacity = capacity;
	}
	public int getRemainCapacity() {
		return remainCapacity;
	}
	public void setRemainCapacity(int remainCapacity) {
		this.remainCapacity = remainCapacity;
	}
	@Override
	public boolean equals(Object o) {
		if(o == null)
			return false;
		if(!(o instanceof Offering))
			return false;
		Offering oo = (Offering) o;
		if(oo.getId() != this.getId())
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Offering [id=" + id + ", professor=" + professor + ", course="
				+ course + ", section=" + section + ", time=" + time
				+ ", capacity=" + capacity + ", remainCapacity="
				+ remainCapacity + ", examDate=" + examDate + "]";
	}
}
