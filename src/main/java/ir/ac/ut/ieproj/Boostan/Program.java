package ir.ac.ut.ieproj.Boostan;
import java.util.Vector;


public class Program {
	
	private int id;
	private String name;
	private Vector<String> mandatories = new Vector<String>();
	private Vector<String> electives = new Vector<String>();
	private ElectivePolicy electivePolicy;
	
	public Program() {
	}
	public Program(String name, Vector<String> mandatories,
			Vector<String> electives, ElectivePolicy electivePolicy) {
		super();
		this.name = name;
		this.mandatories = mandatories;
		this.electives = electives;
		this.electivePolicy = electivePolicy;
	}
	public boolean canPass(Department dep,Student s,Course co) {
		return electivePolicy.canPass(dep, mandatories, electives, s, co);
	}
	public boolean isPass(Department dep,Student s) {
		return electivePolicy.ispass(dep, mandatories, electives, s);
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
	public Vector<String> getMandatories() {
		return mandatories;
	}
	public void setMandatories(Vector<String> mandatories) {
		this.mandatories = mandatories;
	}
	public Vector<String> getElectives() {
		return electives;
	}
	public void setElectives(Vector<String> electives) {
		this.electives = electives;
	}
	public ElectivePolicy getElectivePolicy() {
		return electivePolicy;
	}
	public void setElectivePolicy(ElectivePolicy electivePolicy) {
		this.electivePolicy = electivePolicy;
	} 	
}
