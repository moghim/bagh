package ir.ac.ut.ieproj.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Professor {
	
	@Id
	@Column(unique = true, nullable = false)
	private int id;
	private String firstName;
	private String lastName;
	
	public Professor() {
	}
	public Professor(int id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
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
	@Override
	public String toString() {
		return "Professor [id=" + id + ", firstName=" + firstName
				+ ", lastName=" + lastName + "]";
	}
}
