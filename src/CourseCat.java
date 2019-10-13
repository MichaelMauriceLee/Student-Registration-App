import java.util.ArrayList;
import java.util.ListIterator;

/***
 * This class contains the CourseCat class and its instance methods.
 *
 * @author Michael Lee
 * @version 1.0
 * @since 10/7/2019
 */

public class CourseCat {
	
	private ArrayList <Course> courseList;
	
	
	public CourseCat () {	
		this.courseList = loadFromDB();
	}
	////////Class Methods
	private static ArrayList<Course> loadFromDB () {
		//In real life courses are loaded from the database, or
	    //at least a file on disk. Here we are simulating that!
		//Imagine!! this is being loaded from the database!
		ArrayList<Course> courseList = new ArrayList<Course>();
		courseList.add(new Course("ENGG", 233));
		courseList.add(new Course("PHYS", 259));
		courseList.add(new Course("ENSF", 400));

		ArrayList<Course> preReq1= new ArrayList<Course>();
		preReq1.add(new Course("ENGG", 233));
		preReq1.add(new Course("PHYS", 259));
		courseList.get(2).addPreReq(preReq1);

		return courseList;
	}
	//Instance methods
	public String toStringCourseCatStudent(){
		String st = "";
		for (Course course: courseList){
			st += "\n";
			st += course.getCourseName()+ " " + course.getCourseNum();
			st += "\n";
			for (CourseOffering c: course.getCourseOffering())
				st += "Section id: " + c.getSecNum() + ", Current cap: " + c.getStudentRegList().size() +
						", Section cap: " + c.getSecCap() + "\n\n";
		}
		return st;
	}

	public Course searchCat (String courseName, int courseNum) {
		for (Course c: courseList) {
			if (courseName.equals(c.getCourseName()) 
					&& c.getCourseNum() == courseNum) {
				return c;
			}
		}
		System.err.println("ERROR! Course: " + courseName + " " + courseNum + " does NOT exit!");
		return null;
	}
	public CourseOffering searchOffering (Course selectedCourse, int secNum) {
		for (CourseOffering co: selectedCourse.getCourseOffering()) {
			if (co.getSecNum() == secNum)
				return co;
		}
		return null;	
	}

	public void createCourseOffering (Course c, int secNum, int secCap) {
		if (c != null) {
			CourseOffering co = new CourseOffering(secNum, secCap);
			c.addOffering(co);
		}
	}

	public void removeCourseOffering (Course c, int secNum, int secCap) {
		if (c != null) {
			CourseOffering co = new CourseOffering(secNum, secCap);
			c.addOffering(co);
		}
	}
	public ArrayList<Course> getCourseList(){
		return courseList;
	}
	@Override
	public String toString(){
		String st = "";
		for (Course course: courseList){
			st+= course + "\n";
		}
		return st;
	}

	public void RemoveInsufficientCourseOrCO() {
		ListIterator<Course> courseListIterator = getCourseList().listIterator(); // remove course if 0 course offerings
		while (courseListIterator.hasNext()) {
			Course course = courseListIterator.next();
			course.RemoveCourseOffering();
			if (course.getCourseOffering().size() == 0) {// remove course if 0 course offerings
				courseListIterator.remove();
				System.out.println("No course offerings in courses, removing course!");
			}
		}
	}

	public void AddNewCourseOrCO(String cName, int cNum, int secNum, int secCap) {
		boolean isNewCourse = checkNewCourse(cName, cNum);
		if (isNewCourse) {
			addNewCourse(cName, cNum, secNum, secCap);
		} else {
			boolean isUniqueSecNum = checkUniqueSecNum(cName, cNum, secNum);
			if (isUniqueSecNum) {
				addNewCourseOffering(cName, cNum, secNum, secCap);
			}
			else{
				System.out.println("Must be a unique id when adding in a new section");
			}
		}
	}

	private boolean checkUniqueSecNum(String cName, int cNum, int secNum) {
		boolean isUniqueSecNum = true;
		for (CourseOffering courseOffering : searchCat(cName, cNum).getCourseOffering()) {
			if (courseOffering.getSecNum() == secNum) {
				isUniqueSecNum = false;
			}
		}
		return isUniqueSecNum;
	}

	private void addNewCourseOffering(String cName, int cNum, int secNum, int secCap) {
		CourseOffering newCourseOffering = new CourseOffering(secNum, secCap);
		newCourseOffering.setTheCourse(searchCat(cName, cNum));
		System.out.println("Successfully added new course offering to existing course!");
	}

	private void addNewCourse(String cName, int cNum, int secNum, int secCap) {
		CourseOffering newCourseOffering = new CourseOffering(secNum, secCap);
		Course newCourse = new Course(cName, cNum);
		newCourse.addOffering(newCourseOffering);
		newCourseOffering.setTheCourse(newCourse);
		getCourseList().add(newCourse);
		System.out.println("Successfully added new course!");
	}

	private boolean checkNewCourse(String cName, int cNum) {
		boolean isNewCourse = true;
		for (Course course : getCourseList()) {
			if (course.getCourseName().contentEquals(cName) && course.getCourseNum() == cNum) {
				isNewCourse = false;
			}
		}
		return isNewCourse;
	}

	public void RemoveCourseOrCO(String cName, int cNum, int secNum) {
		ArrayList<CourseOffering> temp = searchCat(cName, cNum).getCourseOffering();
		CourseOffering targetCO = findCourseOffering(secNum, temp);
		removeAllRegistrations(targetCO.getStudentRegList());//remove registrations
		targetCO.getTheCourse().removeOffering(targetCO); // remove course offering from course
		System.out.println("Removed course offering!");
		removeEmptyCourse();
	}

	private void removeEmptyCourse() {
		ListIterator<Course> i2 = getCourseList().listIterator();
		while (i2.hasNext()) {
			if (i2.next().getCourseOffering().size() == 0) {
				i2.remove();
				System.out.println("No course offerings in courses, removing course!");
			}
		}
	}

	private CourseOffering findCourseOffering(int secNum, ArrayList<CourseOffering> temp) {
		CourseOffering targetCO = new CourseOffering();
		for (CourseOffering offering : temp) {
			if (offering.getSecNum() == secNum) {
				targetCO = offering;
			}
		}
		return targetCO;
	}

	public void removeAllRegistrations(ArrayList<Registration> tempRegList) {
		ListIterator<Registration> i = tempRegList.listIterator();
		while (i.hasNext()) {
			i.next();
			i.remove();
			System.out.println("Deregistered a student!");
		}
	}
}
