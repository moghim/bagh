package ir.ac.ut.ieproj.domain;
import static javax.persistence.GenerationType.IDENTITY;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class Term {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(unique = true, nullable = false)
	private int id;
	private String name;
	private Date startDate;
	private Date endDate;
	private Date enrollmentStartDate;
	private Date enrollmentEndDate;
	private Date addAndDropStartDate;
	private Date addAndDropEndDate;
	private Date withdrawStartDate;
	private Date withdrawEndDate;
	private Date submitGradeStartDate;
	private Date submitGradeEndDate;
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "term_id")
	private Set<Offering> offerings;
	public Term() {	
	}
	public Term(String name, Date startDate, Date endDate,
			Date enrollmentStartDate, Date enrollmentEndDate,
			Date addAndDropStartDate, Date addAndDropEndDate,
			Date withdrawStartDate, Date withdrawEndDate,
			Date submitGradeStartDate, Date submitGradeEndDate) {
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.enrollmentStartDate = enrollmentStartDate;
		this.enrollmentEndDate = enrollmentEndDate;
		this.addAndDropStartDate = addAndDropStartDate;
		this.addAndDropEndDate = addAndDropEndDate;
		this.withdrawStartDate = withdrawStartDate;
		this.withdrawEndDate = withdrawEndDate;
		this.submitGradeStartDate = submitGradeStartDate;
		this.submitGradeEndDate = submitGradeEndDate;
		this.offerings = new HashSet<Offering>();
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Date getEnrollmentStartDate() {
		return enrollmentStartDate;
	}	
	public void setEnrollmentStartDate(Date enrollmentStartDate) {
		this.enrollmentStartDate = enrollmentStartDate;
	}
	public Date getEnrollmentEndDate() {
		return enrollmentEndDate;
	}
	public void setEnrollmentEndDate(Date enrollmentEndDate) {
		this.enrollmentEndDate = enrollmentEndDate;
	}
	public Date getAddAndDropStartDate() {
		return addAndDropStartDate;
	}
	public void setAddAndDropStartDate(Date addAndDropStartDate) {
		this.addAndDropStartDate = addAndDropStartDate;
	}
	public Date getAddAndDropEndDate() {
		return addAndDropEndDate;
	}
	public void setAddAndDropEndDate(Date addAndDropEndDate) {
		this.addAndDropEndDate = addAndDropEndDate;
	}
	public Date getWithdrawStartDate() {
		return withdrawStartDate;
	}
	public void setWithdrawStartDate(Date withdrawStartDate) {
		this.withdrawStartDate = withdrawStartDate;
	}
	public Date getWithdrawEndDate() {
		return withdrawEndDate;
	}
	public void setWithdrawEndDate(Date withdrawEndDate) {
		this.withdrawEndDate = withdrawEndDate;
	}
	public Date getSubmitGradeStartDate() {
		return submitGradeStartDate;
	}
	public void setSubmitGradeStartDate(Date submitGradeStartDate) {
		this.submitGradeStartDate = submitGradeStartDate;
	}
	public Date getSubmitGradeEndDate() {
		return submitGradeEndDate;
	}
	public void setSubmitGradeEndDate(Date submitGradeEndDate) {
		this.submitGradeEndDate = submitGradeEndDate;
	}
	public Set<Offering> getOfferings() {
		return offerings;
	}
	public void setOfferings(Set<Offering> offerings) {
		this.offerings = offerings;
	}
	public void addOffering(Offering offering) {
		offerings.add(offering);
	}
	public boolean hasOffering(Offering offering) {
		for (Offering o : offerings) {
			if(offering.getId() == o.getId())
				return true;
		}
		return false;
	}
	@Override
	public String toString() {
		return "Term [id=" + id + ", name=" + name + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", enrollmentStartDate="
				+ enrollmentStartDate + ", enrollmentEndDate="
				+ enrollmentEndDate + ", addAndDropStartDate="
				+ addAndDropStartDate + ", addAndDropEndDate="
				+ addAndDropEndDate + ", withdrawStartDate="
				+ withdrawStartDate + ", withdrawEndDate=" + withdrawEndDate
				+ ", submitGradeStartDate=" + submitGradeStartDate
				+ ", submitGradeEndDate=" + submitGradeEndDate + "]";
	}
	public boolean isTakeTime(Date now) {
		if(now.after(enrollmentStartDate) && now.before(enrollmentEndDate)
				|| now.after(addAndDropStartDate) && now.before(addAndDropEndDate))
			return true;
		return false;
	}
	public Vector<Vector<String>> inProgressOfferings(Student s) {
		Vector<Vector<String>> dataInprogress = new Vector<Vector<String>>();  
		for (Offering o : this.getOfferings()) {
			if(s.isInProgressOffering(o)) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				Vector<String> temp = new Vector<String>();	
				temp.add(Integer.toString(o.getId()));
				temp.add(o.getCourse().getName());		
				temp.add(Integer.toString(o.getCourse().getUnits()));
				temp.add(Integer.toString(o.getTime()));
				temp.add((o.getProfessor()).getFirstName()+" "+o.getProfessor().getLastName());
				temp.add(sdf.format(o.getExamDate()));
				temp.add(Integer.toString(o.getRemainCapacity()));
				temp.add(Integer.toString(o.getCapacity()));
				dataInprogress.add(temp);
			}
		}
		return dataInprogress;
	}
	public Vector<Vector<String>> notInProgressOfferings(Student s) {
		Vector<Vector<String>> dataInprogress = new Vector<Vector<String>>();  
		for (Offering o : this.getOfferings()) {
			if(!s.isInProgressOffering(o)) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				Vector<String> temp = new Vector<String>();	
				temp.add(Integer.toString(o.getId()));
				temp.add(o.getCourse().getName());		
				temp.add(Integer.toString(o.getCourse().getUnits()));
				temp.add(Integer.toString(o.getTime()));
				temp.add((o.getProfessor()).getFirstName()+" "+o.getProfessor().getLastName());
				temp.add(sdf.format(o.getExamDate()));
				temp.add(Integer.toString(o.getRemainCapacity()));
				temp.add(Integer.toString(o.getCapacity()));
				dataInprogress.add(temp);
			}
		}
		return dataInprogress;
	}
	public Vector<Vector<String>> teachingOfferings(Professor p) {
		// TODO : is it true ?
		Vector<Vector<String>> dataInprogress = new Vector<Vector<String>>();  
		for (Offering o : this.getOfferings()) {
			if(o.getProfessor().getId() == p.getId()) {
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				Vector<String> temp = new Vector<String>();	
				temp.add(Integer.toString(o.getId()));
				temp.add(o.getCourse().getName());		
				temp.add(Integer.toString(o.getCourse().getUnits()));
				temp.add(Integer.toString(o.getTime()));
				temp.add((o.getProfessor()).getFirstName()+" "+o.getProfessor().getLastName());
				temp.add(sdf.format(o.getExamDate()));
				temp.add(Integer.toString(o.getRemainCapacity()));
				temp.add(Integer.toString(o.getCapacity()));
				dataInprogress.add(temp);
			}
		}
		return dataInprogress;
	}
	public boolean isWithrawTime(Date date) {
		if(date.after(withdrawStartDate) && date.before(withdrawEndDate))
			return true;
		return false;
	}
	public boolean isSubmitGradeTime(Date date) {
		if(date.after(submitGradeStartDate) && date.before(submitGradeEndDate))
			return true;
		return false;
	}
	public boolean isWithrawResponseTime(Date date) {
		// TODO : is time good ?  
		if(date.after(startDate) && date.before(endDate))
			return true;
		return false;
	}
}