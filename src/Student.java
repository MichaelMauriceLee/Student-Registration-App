import java.util.ArrayList;
import java.util.Iterator;

/***
 * This class contains the Student class and its instance methods.
 *
 * @author Michael Lee
 * @version 1.0
 * @since 10/7/2019
 */


public class Student {
    private String name;
    private int id;
    private final int maxCourseCount = 6;
    private ArrayList<Registration> studentRegList;

    public Student(String name, int id) {
        this.name = name;
        this.id = id;
        studentRegList = new ArrayList<>();
    }


    public boolean registerForCourse(CourseCat cat, String cName, int cNum, int secNum) {
        Course newCourse = searchCat(cat, cName, cNum);
        if (checkCourseCount() && checkSameCourse(cName) == true) {
            if (newCourse != null) {
                CourseOffering co = cat.searchOffering(newCourse, secNum);
                if (co != null && newCourse.checkPreReq(this) && co.getStudentRegList().size() < co.getSecCap()) {
                    Registration r = new Registration();
                    r.completeRegistration(this, co);
                    if (r.getTheOffering().getStudentRegList().size() < 8) {
                        System.out.println("WARNING: LESS THAN 8 PEOPLE, " +
                                "THIS COURSE OFFERING MAY BE CANCELLED BY THE ADMIN AT ANY TIME");
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public boolean removeFromCourse(CourseCat cat, String cName, int cNum, int secNum) {
        Course newCourse = searchCat(cat, cName, cNum);
        if (checkSameCourseForRemoveCO(cName)) {
            if (newCourse != null) {
                CourseOffering co = cat.searchOffering(newCourse, secNum);
                if (co != null && checkDoesNotHaveGrade(co)) {
					if (removeRegistration(co)) return true;
				} else {
                    System.out.println("Cannot remove courses that already have been completed.");
                }
            }
        }
        return false;
    }

	public boolean removeRegistration(CourseOffering co) {
		Iterator<Registration> registrationIterator = studentRegList.listIterator();
		while (registrationIterator.hasNext()){
			Registration reg = registrationIterator.next();
			if(reg.getTheOffering() == co && reg.getTheStudent() == this){
				registrationIterator.remove();
				return true;
			}
		}
		return false;
	}


	public boolean checkDoesNotHaveGrade(CourseOffering co) {
        for (Registration reg : studentRegList) {
            if (reg.getTheOffering() == co && reg.getGrade() != '\u0000') {
                return false;
            }
        }
        return true;
    }

    //helper function
    private Course searchCat(CourseCat cat, String cName, int cNum) {
        return cat.searchCat(cName, cNum);
    }

    private boolean checkCourseCount() {
        if (studentRegList.size() > maxCourseCount) {
            System.out.println("Cannot register more than " + maxCourseCount + " courses");
            return false;
        }
        return true;
    }

    private boolean checkSameCourse(String cName) {
        for (Registration reg : studentRegList) {
            if (reg.getTheOffering().getTheCourse().getCourseName().contentEquals(cName)) {
                System.out.println("Already registered in " + cName);
                return false;
            }
        }
        return true;
    }

    private boolean checkSameCourseForRemoveCO(String cName) {
        for (Registration reg : studentRegList) {
            if (reg.getTheOffering().getTheCourse().getCourseName().contentEquals(cName)) {
                return true;
            }
        }
        return true;
    }

    public String printRegistrationList() {
        String st = "";
        for (Registration reg : studentRegList) {
            st += "Course Name: " + reg.getTheOffering().getTheCourse().getCourseName() +
                    " " + reg.getTheOffering().getTheCourse().getCourseNum() + " Sec Num: "
                    + reg.getTheOffering().getSecNum() + "\n";
        }
        return st;
    }

    public String printGradeList() {
        String st = "";
        for (Registration reg : studentRegList) {
            st += "Course Name: " + reg.getTheOffering().getTheCourse().getCourseName() +
                    " " + reg.getTheOffering().getTheCourse().getCourseNum() + " Grade: "
                    + reg.getGrade() + "\n";
        }
        return st;
    }

    //adding one Registration to studentRegList
    public void addRegistration(Registration reg) {
        studentRegList.add(reg);
    }

    //removing one Registration to studentRegList
    public void removeRegistration(Registration reg) {
        studentRegList.remove(reg);
    }

    @Override
    public String toString() {
        return "Student Name: " + name + ", id: " + id;
    }

    //setters and getters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Registration> getStudentRegList() {
        return studentRegList;
    }
}
