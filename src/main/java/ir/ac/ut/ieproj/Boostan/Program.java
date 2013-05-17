package ir.ac.ut.ieproj.Boostan;
import java.util.Vector;


public class Program {
	
	private int id;
	private String name;
	private Vector<Offering> mandatories;
	private Vector<Offering> electives;
	private ElectivePolicy electivePolicy;
	
	public Program() {
	}
	public Program(String name, Vector<Offering> mandatories,
			Vector<Offering> electives, ElectivePolicy electivePolicy) {
		super();
		this.name = name;
		this.mandatories = mandatories;
		this.electives = electives;
		this.electivePolicy = electivePolicy;
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
	public Vector<Offering> getMandatories() {
		return mandatories;
	}
	public void setMandatories(Vector<Offering> mandatories) {
		this.mandatories = mandatories;
	}
	public Vector<Offering> getElectives() {
		return electives;
	}
	public void setElectives(Vector<Offering> electives) {
		this.electives = electives;
	}
	public ElectivePolicy getElectivePolicy() {
		return electivePolicy;
	}
	public void setElectivePolicy(ElectivePolicy electivePolicy) {
		this.electivePolicy = electivePolicy;
	} 	
}
