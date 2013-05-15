package ir.ac.ut.ieproj;
import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import ir.ac.ut.iecommon.exceptions.DeptLoadException;
import ir.ac.ut.iecommon.exceptions.DeptSaveException;
import ir.ac.ut.iecommon.interfaces.DepartmentI;
import ir.ac.ut.iecommon.interfaces.DeptRepoI;
import ir.ac.ut.ieproj.Boostan.*;


public class DeptRepo implements DeptRepoI{

	private Department d;
	@Override
	public DepartmentI load(String arg0) throws DeptLoadException {

		try {
			
			File file = new File(arg0);
			JAXBContext j = JAXBContext.newInstance(Department.class,SimpleElectivePolicy.class,PackagedElectivePolicy.class);
			Unmarshaller jm = j.createUnmarshaller();
			d = (Department) jm.unmarshal(file);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
		return d;
	}

	@Override
	public void save(DepartmentI arg0, String arg1) throws DeptSaveException {
		try {

			File file = new File(arg1);
			JAXBContext j = JAXBContext.newInstance(Department.class,SimpleElectivePolicy.class,PackagedElectivePolicy.class);
			Marshaller jm = j.createMarshaller();
			jm.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jm.marshal(arg0, file);
			//jm.marshal(arg0, System.out);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
