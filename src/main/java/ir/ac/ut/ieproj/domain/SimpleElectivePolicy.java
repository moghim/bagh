package ir.ac.ut.ieproj.domain;

import java.util.Vector;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class SimpleElectivePolicy extends ElectivePolicy {

	@Override
	public boolean ispass(Department dep, Vector<String> mandatories,Vector<String> electives,Student s) {
		// TODO
		/*
		for (String man : mandatories){
			boolean find = false;
			
			for (StudyRecord sr : s.getStudyRecord()){
				if(sr.getStatus() == StudyStatus.PASSED){
					String c = dep.findOffering(sr.getOffering()).getCourse();
					if(c.equals(man)){
						find=true;
						break;
					}		
				}
			}
			if (find==false)
				return false;		
		}
		int numofel=0;
		int gr=0;
		for (String el :electives){
			boolean find=false;
			for (StudyRecord sr : s.getStudyRecord()){
				if(sr.getStatus() == StudyStatus.PASSED){
					Course c = dep.findCourse(dep.findOffering(sr.getOffering()).getCourse());
					if(c.getId().equals(el)){
						if(c.getLevel() == Level.GRAD)
							gr ++;
						find=true;
						break;
					}		
				}
			}
			if (find == true)
				numofel++;
		}
		float avg = s.getLastTermAverage(dep);
		if (numofel == 5){
			if (avg>18 && gr<=2)
				return true;
			if (gr < 2)
				return true;
		} 
		*/
		return false;
	}
	@Override
	public boolean canPass(Department dep, Vector<String> mandatories,Vector<String> electives,Student s,Course co) {
		// TODO
		/*
		for (String man :mandatories){
			if (man.equals(co.getId())){
				return true;
			}
		}
		int gr=0;
		int numofel=0;
		boolean isFindInEl=false;
		for (String el :electives){
			if (el.equals(co.getId())){
				isFindInEl=true;
				if (co.getLevel()==Level.GRAD)
					gr++;
			}
			boolean find=false;
			for (StudyRecord sr:s.getStudyRecord()){
				if(sr.getStatus()==StudyStatus.PASSED||sr.getStatus()==StudyStatus.INPROGRESS||sr.getStatus()==StudyStatus.WAITINGFORWITHRAWACCEPT){
					Course c =dep.findCourse(dep.findOffering(sr.getOffering()).getCourse());
					if(c.getId().equals(el)){
						find=true;
						if (c.getLevel()==Level.GRAD){
							gr++;
						}
						break;
					}		
				}
			}
			if (find==true)
				numofel++;
		}
		float avg=s.getLastTermAverage(dep);
		if (isFindInEl&&numofel<5){
			if (avg>18&&gr<=2)
				return true;
			if (gr<2)
				return true;
		} 
		*/
		return false;	
	}
}
