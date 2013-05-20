package ir.ac.ut.ieproj.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
@DiscriminatorValue("Professor")
public class Professor extends Person {

	
	public Professor() {
		super();
	}

	public Professor(int id, String password, String firstName, String lastName) {
		super(id, password, firstName, lastName);
	}

	@Override
	public String toString() {
		return "Professor [id=" + id + ", firstName=" + firstName
				+ ", lastName=" + lastName + "]";
	}
}
