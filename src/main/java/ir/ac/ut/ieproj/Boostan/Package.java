package ir.ac.ut.ieproj.Boostan;
import java.util.Vector;


public class Package {
	
	private String name;
	private Vector<String> courses = new Vector<String>();
	
	public Package() {
	}
	public Package(String name, Vector<String> courses) {
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
	public Vector<String> getCourses() {
		return courses;
	}
	public void setCourses(Vector<String> courses) {
		this.courses = courses;
	}
}
