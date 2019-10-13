
/***
 * This class contains the Registration class and its instance methods.
 *
 * @author Michael Lee
 * @version 1.0
 * @since 10/7/2019
 */

public class Registration {
	private Student theStudent;
	private CourseOffering theOffering;
	private char grade = '\u0000';

	/////////instance methods
	public void completeRegistration (Student theStudent, CourseOffering theOffering) {
		this.theStudent = theStudent;
		this.theOffering = theOffering;
		addRegistration ();
	}
	public void removeRegistration (Student theStudent, CourseOffering theOffering){
		deleteRegistration();
	}
	@Override
	public String toString () {
		return "The student info:\n" + theStudent;
	}
	//////// helper methods
	private void addRegistration() {
		theStudent.addRegistration(this);
		theOffering.addRegistration(this);
	}
	private void deleteRegistration() {
		theStudent.removeRegistration(this);
		theOffering.removeRegistration(this);
	}


	/////getters and setters
	public Student getTheStudent() {
		return theStudent;
	}
	public void setTheStudent(Student theStudent) {
		this.theStudent = theStudent;
	}
	public CourseOffering getTheOffering() {
		return theOffering;
	}
	public void setTheOffering(CourseOffering theOffering) {
		this.theOffering = theOffering;
	}
	public char getGrade() {
		return grade;
	}
	public void setGrade(char grade) {
		this.grade = grade;
	}


}
