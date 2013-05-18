package ir.ac.ut.ieproj.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
@DiscriminatorValue("P")
public class PackagedElectivePolicy extends ElectivePolicy {
	
	@OneToMany(fetch = FetchType.LAZY)
	private Set<Package> packages;
	
	public PackagedElectivePolicy() {
		packages = new HashSet<Package>();
	}
	public PackagedElectivePolicy(Set<Package> packages) {
		super();
		this.packages = packages;
	}
	@Override
	public boolean ispass(Department dep, Vector<String> mandatories,Vector<String> electives,Student s) {
		// TODO
		/*
		int findCourse=0;
		for (String man : mandatories){
			boolean find = false;
			for (StudyRecord sr : s.getStudyRecord()){
				if(sr.getStatus() == StudyStatus.PASSED){
					String c = dep.findOffering(sr.getOffering()).getCourse();
					if(c.equals(man)){
						findCourse++;
						find=true;
						break;
					}		
				}
			}
			if (find==false)
				return false;
		}
		Vector<Integer> numPassedOfPackages = new Vector<Integer>(packages.size(), 0);
		for (int i=0;i<packages.size();i++) {
			Package p = packages.get(i);
			for (String stu : p.getCourses()) {
				if(s.isPassedCourse(stu, dep)){
					numPassedOfPackages.set(i, numPassedOfPackages.get(i)+1);
					findCourse++;
				}
			}
		}
		int num2 = 0;
		int num1 = 0;
		for (int i=0;i<numPassedOfPackages.size();i++) {
			if(numPassedOfPackages.get(i) > 2)
				return false;
			if(numPassedOfPackages.get(i) == 2)
				num2 ++;
			if(numPassedOfPackages.get(i) == 1)
				num1 ++;
		}
		if(num1==1 && num2==1 && findCourse==s.FindNumberOfPassedCourse()+1)
			return true;
		*/
		return false;
	}
	@Override
	public boolean canPass(Department dep, Vector<String> mandatories,Vector<String> electives,Student s,Course co) {
		// TODO
		/*
		int findCourse=0;
		for (String man : mandatories){
			//boolean find = false;
			if (man.equals(co.getId()))
				return true;
		}
		for (String man : mandatories){
			for (StudyRecord sr : s.getStudyRecord()){
				if(sr.getStatus() == StudyStatus.PASSED||sr.getStatus() == StudyStatus.INPROGRESS||sr.getStatus() == StudyStatus.WAITINGFORWITHRAWACCEPT){
					String c = dep.findOffering(sr.getOffering()).getCourse();
					if(c.equals(man)){
						findCourse++;
						break;
					}		
				}
			}
		}
		Vector<Integer> numPassedOfPackages = new Vector<Integer>(packages.size(), 0);
		boolean isInP=false;
		for (int i=0;i<packages.size();i++) {
			Package p = packages.get(i);
			for (String stu : p.getCourses()) {
				if(stu.equals(co.getId())){
					numPassedOfPackages.set(i, numPassedOfPackages.get(i)+1);
					findCourse++;
					isInP=true;
				}
				if(s.isPassedCourse(stu, dep)){
					numPassedOfPackages.set(i, numPassedOfPackages.get(i)+1);
					findCourse++;
				}
			}
		}
		int num2 = 0;
		int num1 = 0;
		for (int i=0;i<numPassedOfPackages.size();i++) {
			if(numPassedOfPackages.get(i) > 2)
				return false;
			if(numPassedOfPackages.get(i) == 2)
				num2 ++;
			if(numPassedOfPackages.get(i) == 1)
				num1 ++;
		}
		if((num1<=1 && num2<=1 &&isInP)||(num1<=1 && num2<=1 &&findCourse-s.findNumberOfCourseTaken()==0))
			return true;
		*/
		return false;
	}
	public Set<Package> getPackages() {
		return packages;
	}
	public void setPackages(Set<Package> packages) {
		this.packages = packages;
	}
	public void addPackage(Package package1) {
		packages.add(package1);
	}
}
