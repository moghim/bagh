package ir.ac.ut.ieproj.Boostan;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"firstName", "lastName", "id"})
public class Professor {
	
	private String id;
	private String firstName;
	private String lastName;
	
	public String getId() {
		return id;
	}
	@XmlAttribute
	public void setId(String id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	@XmlAttribute
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	@XmlAttribute
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
