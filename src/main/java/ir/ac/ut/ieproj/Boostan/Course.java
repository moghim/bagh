package ir.ac.ut.ieproj.Boostan;
import java.util.Vector;

public class Course {
	
	private int id;
	private String name;
	private int units;
	private Level level;
	private Vector<Course> prerequisite;
	private Vector<Course> corequisite;
	
	public Course() {
	}
	public Course(String name, int units, Level level,
			Vector<Course> prerequisite, Vector<Course> corequisite) {
		this.name = name;
		this.units = units;
		this.level = level;
		this.prerequisite = prerequisite;
		this.corequisite = corequisite;
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
	public Vector<Course> getPrerequisite() {
		return prerequisite;
	}
	public void setPrerequisite(Vector<Course> prerequisite) {
		this.prerequisite = prerequisite;
	}
	public Vector<Course> getCorequisite() {
		return corequisite;
	}
	public void setCorequisite(Vector<Course> corequisite) {
		this.corequisite = corequisite;
	}
}
