package ir.ac.ut.ieproj.domain;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table
public class Program {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(unique = true, nullable = false)
	private int id;
	private String name;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "program")
	private Vector<Offering> mandatories;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "program")
	private Vector<Offering> electives;
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "program", cascade = CascadeType.ALL)
	private ElectivePolicy electivePolicy;
	
	public Program() {
	}
	public Program(String name, Vector<Offering> mandatories,
			Vector<Offering> electives, ElectivePolicy electivePolicy) {
		super();
		this.name = name;
		this.mandatories = mandatories;
		this.electives = electives;
		this.electivePolicy = electivePolicy;
	}
	public boolean canPass(Department dep,Student s,Course co) {
		// TODO
		//return electivePolicy.canPass(dep, mandatories, electives, s, co);
		return false;
	}
	public boolean isPass(Department dep,Student s) {
		// TODO
		//return electivePolicy.ispass(dep, mandatories, electives, s);
		return false;
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
	public Vector<Offering> getMandatories() {
		return mandatories;
	}
	public void setMandatories(Vector<Offering> mandatories) {
		this.mandatories = mandatories;
	}
	public Vector<Offering> getElectives() {
		return electives;
	}
	public void setElectives(Vector<Offering> electives) {
		this.electives = electives;
	}
	public ElectivePolicy getElectivePolicy() {
		return electivePolicy;
	}
	public void setElectivePolicy(ElectivePolicy electivePolicy) {
		this.electivePolicy = electivePolicy;
	} 	
}
