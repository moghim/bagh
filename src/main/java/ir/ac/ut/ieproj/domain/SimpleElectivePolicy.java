package ir.ac.ut.ieproj.domain;

import ir.ac.ut.ieproj.database.DBConnector;

import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
@DiscriminatorValue("S")
public class SimpleElectivePolicy extends ElectivePolicy {

	@Override
	public boolean isPassedReq(Student s, Set<Course> electives, Set<Course> mandatories) {
		int electivePassed = 0;
		for(Course c : electives) {
			if(s.hasPassedCourse(c))
				electivePassed ++;
		}
		if(electivePassed >= 5)
			return true;
		return false;
	}
	@Override
	public boolean canPassCourses(Student student, Course course, Set<Course> electives, Set<Course> mandatories) {
		if(course.getLevel() == Level.UNDERGRAD) {
			boolean isInAllowedCourse = false;
			for(Course c : electives) {
				if(course.getId() == c.getId())
					isInAllowedCourse = true;
			}
			for(Course c : mandatories) {
				if(course.getId() == c.getId())
					isInAllowedCourse = true;
			}
			if(!isInAllowedCourse)
				return false;
			return true;
		}
		else {
			if(student.gradPassedCourses()>=2)
				return false;
			if(student.gradPassedCourses()==1 && student.TermAverage(DBConnector.getPreviosTerm())<18)
				return false;
			return true;
		}
	}
}
