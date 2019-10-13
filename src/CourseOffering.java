import java.util.ArrayList;
import java.util.ListIterator;

/***
 * This class contains the CourseOffering class and its instance methods.
 *
 * @author Michael Lee
 * @version 1.0
 * @since 10/7/2019
 */

public class CourseOffering {
	private int secNum;
	private int secCap;
	private ArrayList <Registration> studentRegList;
	private Course theCourse;

	
	public CourseOffering (int secNum, int secCap) {
		this.setSecNum(secNum);
		this.secCap = secCap;
		studentRegList = new ArrayList<Registration>();
	}
	public CourseOffering () {

	}

	public void addRegistration (Registration reg) {
		//We can add logic to ensure the requirements for the number of students
		//is met
		studentRegList.add(reg);
	}

	public void removeRegistration (Registration reg) {
		studentRegList.remove(reg);
	}
	public int getSecNum() {
		return secNum;
	}
	public void setSecNum(int secNum) {
		this.secNum = secNum;
	}

	public int getSecCap() {
		return secCap;
	}

	public Course getTheCourse() {
		return theCourse;
	}
	public void setTheCourse(Course theCourse) {
		this.theCourse = theCourse;
	}
	@Override
	public String toString() {
		String st = "";
		st += "Section id: " + secNum + ", Section cap: " + secCap + "\n\n";
		st += "Students in this section are: \n\n";
		for (Registration r : studentRegList) {
			st += r.getTheStudent();
			st += "\n\n";
		}
		return st;
	}

	public ArrayList<Registration> getStudentRegList() {
		return studentRegList;
	}

	public void removeAllRegistrationsBelowMinAmt() {
		ListIterator<Registration> i = getStudentRegList().listIterator();
		while (i.hasNext()) {
			i.next();
			if (getStudentRegList().size() <= 8) {
				i.remove();
				System.out.println("Deregistered a student!");
			}
		}
	}
}
