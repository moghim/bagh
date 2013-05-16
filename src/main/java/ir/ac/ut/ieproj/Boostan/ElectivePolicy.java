package ir.ac.ut.ieproj.Boostan;

import java.util.Vector;

public abstract class ElectivePolicy {	
	public abstract boolean ispass(Department dep, Vector<String> mandatories,Vector<String> electives,Student s); 
	public abstract boolean canPass(Department dep, Vector<String> mandatories,Vector<String> electives,Student s,Course co);
}
	