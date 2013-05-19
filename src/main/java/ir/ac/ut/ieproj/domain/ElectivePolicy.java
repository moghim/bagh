package ir.ac.ut.ieproj.domain;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Set;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.DiscriminatorType;

@Entity
@Table
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
	    name="discriminator",
	    discriminatorType = DiscriminatorType.STRING
	)
	@DiscriminatorValue(value="E")
public abstract class ElectivePolicy {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public abstract boolean isPassedReq(Student s, Set<Course> electives, Set<Course> mandatories);
	public abstract boolean canPassCourses(Student s, Course c, Set<Course> electives, Set<Course> mandatories) throws Exception;
	
}
	