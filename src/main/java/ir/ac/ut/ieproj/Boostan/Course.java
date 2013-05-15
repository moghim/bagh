package ir.ac.ut.ieproj.Boostan;
import java.util.Vector;




public class Course {
	
	private String id;
	private String name;
	private int units;
	private Level level;
	private Vector<String> prerequisite = new Vector<String>();
	private Vector<String> corequisite = new Vector<String>();
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
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
	public Vector<String> getPrerequisite() {
		return prerequisite;
	}
	public void setPrerequisite(Vector<String> prerequisite) {
		this.prerequisite = prerequisite;
	}
	public Vector<String> getCorequisite() {
		return corequisite;
	}
	public void setCorequisite(Vector<String> corequisite) {
		this.corequisite = corequisite;
	}
}
