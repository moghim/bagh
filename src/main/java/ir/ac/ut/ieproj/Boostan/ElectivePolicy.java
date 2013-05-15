package ir.ac.ut.ieproj.Boostan;

import java.util.Vector;
//import javax.xml.bind.annotation.XmlSeeAlso;

//@XmlSeeAlso({PackagedElectivePolicy.class,SimpleElectivePolicy.class  })
public abstract class ElectivePolicy {	
	public abstract boolean ispass(Department dep, Vector<String> mandatories,Vector<String> electives,Student s); 
	public abstract boolean canPass(Department dep, Vector<String> mandatories,Vector<String> electives,Student s,Course co);

}
	