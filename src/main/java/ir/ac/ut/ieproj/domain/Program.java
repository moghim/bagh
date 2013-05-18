package ir.ac.ut.ieproj.domain;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "program_mandatory", joinColumns = {@JoinColumn(name = "program")}, inverseJoinColumns = {@JoinColumn(name = "mandatorycourse")})
	private Set<Course> mandatories;
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "program_elective", joinColumns = {@JoinColumn(name = "program")}, inverseJoinColumns = {@JoinColumn(name = "electivecourse")})
	private Set<Course> electives;
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private ElectivePolicy electivePolicy;
	
	public Program() {
		this.mandatories = new HashSet<Course>();
		this.electives = new HashSet<Course>();
	}
	public Program(String name) {
		super();
		this.name = name;
		this.mandatories = new HashSet<Course>();
		this.electives = new HashSet<Course>();
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
	public Set<Course> getMandatories() {
		return mandatories;
	}
	public void setMandatories(Set<Course> mandatories) {
		this.mandatories = mandatories;
	}
	public Set<Course> getElectives() {
		return electives;
	}
	public void setElectives(Set<Course> electives) {
		this.electives = electives;
	}
	public ElectivePolicy getElectivePolicy() {
		return electivePolicy;
	}
	public void setElectivePolicy(ElectivePolicy electivePolicy) {
		this.electivePolicy = electivePolicy;
	}
	public void addMandatory(Course course) {
		mandatories.add(course);
	}
	public void addElective(Course course) {
		electives.add(course);
	}
}
