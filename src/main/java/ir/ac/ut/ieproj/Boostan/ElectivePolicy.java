package ir.ac.ut.ieproj.Boostan;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Vector;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public abstract class ElectivePolicy {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private int id;
	public abstract boolean ispass(Department dep, Vector<String> mandatories,Vector<String> electives,Student s); 
	public abstract boolean canPass(Department dep, Vector<String> mandatories,Vector<String> electives,Student s,Course co);
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
	