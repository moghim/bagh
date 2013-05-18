package ir.ac.ut.ieproj.domain;
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
import javax.persistence.Table;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table
public class Course {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(unique = true, nullable = false)
	private int id;
	private String name;
	private int units;
	private Level level;
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "pre", joinColumns = {@JoinColumn(name = "first")}, inverseJoinColumns = {@JoinColumn(name = "last")})
	private Set<Course> prerequisite;
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "co", joinColumns = {@JoinColumn(name = "first")}, inverseJoinColumns = {@JoinColumn(name = "last")})
	private Set<Course> corequisite;
	
	public Course() {
		prerequisite = new HashSet<Course>();
		corequisite = new HashSet<Course>();
	}
	public Course(String name, int units, Level level) {
		this.name = name;
		this.units = units;
		this.level = level;
		prerequisite = new HashSet<Course>();
		corequisite = new HashSet<Course>();
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
	public int getUnits() {
		return units;
	}
	public void setUnits(int units) {
		this.units = units;
	}
	public Level getLevel() {
		return level;
	}
	public void setLevel(Level level) {
		this.level = level;
	}
	public Set<Course> getPrerequisite() {
		return prerequisite;
	}
	public void setPrerequisite(Set<Course> prerequisite) {
		this.prerequisite = prerequisite;
	}
	public Set<Course> getCorequisite() {
		return corequisite;
	}
	public void setCorequisite(Set<Course> corequisite) {
		this.corequisite = corequisite;
	}
	public void addPrerequisite(Course course) {
		prerequisite.add(course);
	}
	public void addCorequisite(Course course) {
		corequisite.add(course);
	}
	public boolean isPrequIsPassedByStudent(Student s) {
		for(Course c : prerequisite) {
			if(!s.isPassedCourse(c))
				return false; 
		}
		return true;
	}
	public boolean isCorequIsPassedOrInProgressByStudent(Student s) {
		for(Course c : corequisite) {
			if(!(s.isPassedCourse(c)||s.isInProgresCourse(c)))
				return false; 
		}
		return true;
	}
}