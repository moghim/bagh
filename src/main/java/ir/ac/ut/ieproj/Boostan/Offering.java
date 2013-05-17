package ir.ac.ut.ieproj.Boostan;

import ir.ac.ut.iecommon.exceptions.DeptLoadException;
import ir.ac.ut.iecommon.exceptions.TakeException;

import java.util.Date;

public class Offering {
	private int id;
	private Professor professor;
	private Course course;
	private int section;
	private int time;
	private int capacity;
	boolean isRemainCapacitySet;
	private int remainCapacity ;
	private Date examDate;
	
	public Offering() {
	}
	public Offering(Professor professor, Course course, int section,
			int time, int capacity, int remainCapacity, Date examDate) {
		this.professor = professor;
		this.course = course;
		this.section = section;
		this.time = time;
		this.capacity = capacity;
		this.remainCapacity = remainCapacity;
		this.examDate = examDate;
		isRemainCapacitySet = false;
	}
	public int findNumberOfStudentInOfferring() throws DeptLoadException{
		int num=0;
		Department dep = Department.getInstance();
		for (Student s:dep.getStudents()){
			if (s.hasOffering(Integer.toString(this.id)))
				num++;
		}
		return num;
	}
	public int findRemainCapacity() throws DeptLoadException {
	//	System.out.println("capacity"+capacity);
		
		if (!isRemainCapacitySet){
			remainCapacity = capacity - findNumberOfStudentInOfferring();
			isRemainCapacitySet = true;
		}
	//	System.out.println("remain capacity"+remainCapacity);
		return remainCapacity;
	}
	public synchronized void decRemainCapacity() throws TakeException, DeptLoadException{
		remainCapacity = findRemainCapacity();
		if (remainCapacity == 0)
			throw new TakeException("Error from take function: there is no capacity for offering with id "+id);
		remainCapacity--;
	}
	public synchronized void incRemainCapacity() throws TakeException, DeptLoadException{
		remainCapacity = findRemainCapacity();
		remainCapacity++;
	}
	public void setCapacity(int capacity) {
		this.remainCapacity = capacity;
		this.capacity = capacity;
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
}
