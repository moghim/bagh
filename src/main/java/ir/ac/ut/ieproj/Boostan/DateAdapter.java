package ir.ac.ut.ieproj.Boostan;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateAdapter extends XmlAdapter<String, Date> {
	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

	@Override
	public String marshal(Date d) throws Exception {
		
		return sdf.format(d); 
	}

	@Override
	public Date unmarshal(String d) throws Exception {
		
		return sdf.parse(d);
	}
	
}
