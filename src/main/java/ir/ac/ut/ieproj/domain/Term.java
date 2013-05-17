package ir.ac.ut.ieproj.Boostan;
import static javax.persistence.GenerationType.IDENTITY;
import ir.ac.ut.iecommon.time.Clock;

import java.util.Date;

import java.util.Vector;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "term")
	private Vector<Offering> offerings = new Vector<Offering>();

	public Term() {	
	}
	public Term(String name, Date startDate, Date endDate,
			Date enrollmentStartDate, Date enrollmentEndDate,
			Date addAndDropStartDate, Date addAndDropEndDate,
			Date withdrawStartDate, Date withdrawEndDate,
			Date submitGradeStartDate, Date submitGradeEndDate,
			Vector<Offering> offerings) {
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
		this.offerings = offerings;
	}
	private Date withdrawStartDate;
	private Date withdrawEndDate;
	private Date submitGradeStartDate;
	private Date submitGradeEndDate;


	public boolean isTakeTime() {
		Date now = new Date (Clock.getCurrentTimeMillis());
		if (this.getEnrollmentStartDate().before(now) && this.getEnrollmentEndDate().after(now))
			return true;
		return false;
	}
	public Offering findoOffering(int offeringID){
		for (Offering o : offerings){
			if (o.getId() == offeringID) {
				return o;
			}
		}
		return null;
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
	public Vector<Offering> getOfferings() {
		return offerings;
	}
	public void setOfferings(Vector<Offering> offerings) {
		this.offerings = offerings;
	}
}
