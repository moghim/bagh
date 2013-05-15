package ir.ac.ut.ieproj.Boostan;
import java.util.Vector;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"courses", "name"})
public class Package {
	
	private String name;
	private Vector<String> courses = new Vector<String>();
	public String getName() {
		return name;
	}
	@XmlElement
	public void setName(String name) {
		this.name = name;
	}
	public Vector<String> getCourses() {
		return courses;
	}
	@XmlElement(name="course")
	public void setCourses(Vector<String> courses) {
		this.courses = courses;
	}
}
