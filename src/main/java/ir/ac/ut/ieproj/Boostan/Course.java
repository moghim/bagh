package ir.ac.ut.ieproj.Boostan;
import java.util.Vector;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"id", "units", "level", "name",
						"prerequisite", "corequisite"})
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
	@XmlAttribute
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	@XmlAttribute
	public void setName(String name) {
		this.name = name;
	}
	public int getUnits() {
		return units;
	}
	@XmlAttribute
	public void setUnits(int units) {
		this.units = units;
	}
	@XmlAttribute
	public Level getLevel() {
		return level;
	}
	public void setLevel(Level level) {
		this.level = level;
	}
	public Vector<String> getPrerequisite() {
		return prerequisite;
	}
	@XmlElement(name="pre")
	public void setPrerequisite(Vector<String> prerequisite) {
		this.prerequisite = prerequisite;
	}
	public Vector<String> getCorequisite() {
		return corequisite;
	}
	@XmlElement(name="co")
	public void setCorequisite(Vector<String> corequisite) {
		this.corequisite = corequisite;
	}
}
