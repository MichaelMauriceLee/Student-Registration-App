import java.util.ArrayList;
import java.util.ListIterator;

/***
 * This class contains the Course class and its instance methods.
 *
 * @author Michael Lee
 * @version 1.0
 * @since 10/7/2019
 */

public class Course {
	
	private String courseName;
	private int courseNum;
	private ArrayList <Course> preReqList;
	private ArrayList <CourseOffering> courseOffering;

	public Course (String courseName, int courseNum) {
		this.courseName = courseName;
		this.courseNum = courseNum;
		preReqList = new ArrayList<Course>();
		courseOffering = new ArrayList <CourseOffering>();
	}
	public void addOffering (CourseOffering offering) {
		if (offering.getTheCourse() == null)
			offering.setTheCourse(this);
		if (!offering.getTheCourse().getCourseName().equals(courseName) ||
			offering.getTheCourse().getCourseNum() != courseNum) {
			System.err.println("ERROR! This section belongs to another course!");
			return;
		}
		courseOffering.add(offering);
	}
	public void removeOffering (CourseOffering offering) {
		if (!offering.getTheCourse().getCourseName().equals(courseName) ||
				offering.getTheCourse().getCourseNum() != courseNum) {
			System.err.println("ERROR! Wrong course offering!");
		}
		courseOffering.remove(offering);
	}
	public void addPreReq(ArrayList<Course> preReq){
		this.preReqList = preReq;
	}
	public boolean checkPreReq (Student student){
		if (preReqList.size() == 0){
			return true;
		}
		for (Course preReq : preReqList){
			for (Registration reg : student.getStudentRegList()) {
				if (reg.getTheOffering().getTheCourse().getCourseName() == preReq.getCourseName() &&
						reg.getTheOffering().getTheCourse().getCourseNum() == preReq.getCourseNum() &&
						reg.getGrade() <= 'D' && reg.getGrade() >= 'A') {
					return true;
				}
			}
		}
		System.out.println("Prerequisites not met.");
		return false;
	}
	
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public int getCourseNum() {
		return courseNum;
	}
	public void setCourseNum(int courseNum) {
		this.courseNum = courseNum;
	}
	public ArrayList<CourseOffering> getCourseOffering () {
		return courseOffering;//not safe, should return a copy of this instead
		//will do later
	}
	public CourseOffering getCourseOfferingAt(int index) {
		return courseOffering.get(index);//what if index is out of bounds??
	}
	@Override
	public String toString() {
		String st = "";
		st += "\n";
		st += courseName + " " + courseNum;
		st += "\n";
		for (CourseOffering c: courseOffering)
			st += c;
		return st;
	}

	public void RemoveCourseOffering() {
		ListIterator<CourseOffering> courseOfferingListIterator = getCourseOffering().listIterator();
		while (courseOfferingListIterator.hasNext()) {
			CourseOffering temp = courseOfferingListIterator.next();
			if (temp.getStudentRegList().size() <= 8) {   //if below 8, deregisters everyone
				System.out.println("Less than 8 students, deregistering everyone and removing course offering");
				temp.removeAllRegistrationsBelowMinAmt();
			}
			courseOfferingListIterator.remove(); // removes course offering from course list
		}
	}
}
