package ir.ac.ut.ieproj.Boostan;
import java.util.Vector;


public class Package {
	
	private String name;
	private Vector<Course> courses;
	
	public Package() {
	}
	public Package(String name, Vector<Course> courses) {
		super();
		this.name = name;
		this.courses = courses;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Vector<Course> getCourses() {
		return courses;
	}
	public void setCourses(Vector<Course> courses) {
		this.courses = courses;
	}
}
