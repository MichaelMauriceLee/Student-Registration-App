import java.util.ArrayList;
import java.util.Scanner;

/***
 * This class contains the CourseRegApp class and its instance methods.
 * This class serves as the front end for the course registration app.
 *
 * @author Michael Lee
 * @version 1.0
 * @since 10/7/2019
 */


public class CourseRegApp {

    private Scanner userInput = new Scanner(System.in);

    public static void main(String[] args) {
        CourseRegApp courseRegApp = new CourseRegApp();
        CourseCat courseCat = new CourseCat();
        CourseOffering co1 = new CourseOffering(1, 100);
        co1.setTheCourse(courseCat.getCourseList().get(0));
        CourseOffering co2 = new CourseOffering(1, 75);
        co2.setTheCourse(courseCat.getCourseList().get(1));
        CourseOffering co3 = new CourseOffering(1, 50);
        co3.setTheCourse(courseCat.getCourseList().get(2));
        courseCat.getCourseList().get(0).addOffering(co1);
        courseCat.getCourseList().get(1).addOffering(co2);
        courseCat.getCourseList().get(2).addOffering(co3);

        Student s1 = new Student("Sara", 1);
        Student s2 = new Student("Michael", 2);
        Registration reg1 = new Registration();
        reg1.completeRegistration(s2, co1);
        reg1.setGrade('F');
        Student s3 = new Student("Jason", 3);
        Registration reg2 = new Registration();
        reg2.completeRegistration(s3, co1);
        reg2.setGrade('C');
        Registration reg3 = new Registration();
        reg3.completeRegistration(s3, co2);
        reg3.setGrade('A');

        ArrayList<Student> students = new ArrayList<Student>();
        students.add(s1);
        students.add(s2);
        students.add(s3);
        StudentList studentList = new StudentList(students);

        courseRegApp.printWelcomeMenu();
        courseRegApp.printUserSelectionMenu();
        String userSelection = courseRegApp.getUserInput();
        if (userSelection.contentEquals("1")) {
            courseRegApp.printAdminMenu(courseCat, studentList);
        }
        if (userSelection.contentEquals("2")) {
            Student user = courseRegApp.getStudentLogin(studentList);
            courseRegApp.printUserMenu(courseCat, user);
        }
    }

    private Student getStudentLogin(StudentList studentList) {
        Student user = null;
        while (user == null) {
            System.out.println("Welcome.  Please enter your name.");
            String name = this.getUserInput();
            System.out.println("Please enter your student id");
            int id = this.receiveNumberInput();
            user = studentList.findStudent(name, id);
        }
        return user;
    }

    private void printWelcomeMenu() {
        System.out.println("Welcome to the Michael University Registration App!");
    }

    private void printUserSelectionMenu() {
        System.out.println("Press 1 for admin and press 2 for student.");
    }

    private String getUserInput() {
        return userInput.nextLine();
    }

    private void printExitMenu() {
        System.out.println("Goodbye!  Thanks for using the app!");
    }

    private void printErrorMenu() {
        System.out.println("Invalid input.  Please check inputs and try again.");
    }

    private void printAdminMenu(CourseCat courseCat, StudentList studentList) {
        while (true) {
            printAdminMainMenu();
            String selection = getUserInput();
            switch (selection) {
                case "1":
                    this.printSearchCourseCatMenu(courseCat);
                    break;
                case "2":
                    this.printCheckMinStudentsAdmin(courseCat);
                    break;
                case "3":
                    this.printRegisterStudentAdmin(courseCat, studentList);
                    break;
                case "4":
                    this.printRemoveStudentAdmin(courseCat, studentList);
                    break;
                case "5":
                    this.printAddCourseMenu(courseCat);
                    break;
                case "6":
                    this.printRemoveCourseMenu(courseCat);
                    break;
                case "7":
                    this.printCatCourseMenu(courseCat);
                    break;
                case "8":
                    this.printStudentListMenu(courseCat);
                    break;
                case "9":
                    this.printStudentGradesAdminMenu(studentList);
                    break;
                case "10":
                    this.printExitMenu();
                    System.exit(0);
                default:
                    this.printErrorMenu();
                    break;
            }
        }
    }

    private void printAdminMainMenu() {
        System.out.println("Welcome Admin, please make a selection");
        System.out.println("1: Find a course offering");
        System.out.println("2: Check current course offerings registered student for less than min needed");
        System.out.println("3: Register student for a course offering");
        System.out.println("4: Remove student from a course offering");
        System.out.println("5: Add course and/or course offering to catalogue");
        System.out.println("6: Remove course and/or course offering to catalogue");
        System.out.println("7: View all courses in the catalogue");
        System.out.println("8: View all students in a course offering");
        System.out.println("9: View a student's grades");
        System.out.println("10: Quit");
    }

    private void printCheckMinStudentsAdmin(CourseCat courseCat) {
        courseCat.RemoveInsufficientCourseOrCO();
    }

    private void printRegisterStudentAdmin(CourseCat courseCat, StudentList studentList) {
        System.out.println("What is the name of the student?");
        String name = getUserInput();
        System.out.println("What is the id of the student?");
        int id = receiveNumberInput();
        Student student = studentList.findStudent(name, id);
        printRegisterStudent(courseCat, student);
    }

    private void printRemoveStudentAdmin(CourseCat courseCat, StudentList studentList) {
        System.out.println("What is the name of the student?");
        String name = getUserInput();
        System.out.println("What is the id of the student?");
        int id = receiveNumberInput();
        Student student = studentList.findStudent(name, id);
        this.printRemoveStudent(courseCat, student);
    }

    private void printAddCourseMenu(CourseCat courseCat) {
        System.out.println("What is the name of the course?");
        String cName = getUserInput();
        System.out.println("What is the id of the course?");
        int cNum = receiveNumberInput();
        System.out.println("What is the section number of the course?");
        int secNum = receiveNumberInput();
        System.out.println("What is the section capacity of the course?");
        int secCap = receiveNumberInput();
        courseCat.AddNewCourseOrCO(cName, cNum, secNum, secCap);
    }

    private void printRemoveCourseMenu(CourseCat courseCat) {
        System.out.println("What is the name of the course?" +
                " (Course will automatically delete when no offerings are available)");
        String cName = getUserInput();
        System.out.println("What is the id of the course?");
        int cNum = receiveNumberInput();
        System.out.println("What is the section number of the course?");
        int secNum = receiveNumberInput();
        courseCat.RemoveCourseOrCO(cName, cNum, secNum);
    }

    private void printStudentListMenu(CourseCat courseCat) {
        System.out.println();
    }

    private void printStudentGradesAdminMenu(StudentList studentList) {
        System.out.println("What is the name of the student?");
        String name = getUserInput();
        System.out.println("What is the id of the student?");
        int id = receiveNumberInput();
        Student student = studentList.findStudent(name, id);
        this.printStudentGradesMenu(student);
    }


    private void printUserMenu(CourseCat courseCat, Student student) {
        while (true) {
            printUserMainMenu(student);
            String selection = getUserInput();
            switch (selection) {
                case "1":
                    this.printSearchCourseCatMenu(courseCat);
                    break;
                case "2":
                    this.printRegisterStudent(courseCat, student);
                    break;
                case "3":
                    this.printRemoveStudent(courseCat, student);
                    break;
                case "4":
                    this.printCatCourseMenu(courseCat);
                    break;
                case "5":
                    this.printStudentCourseListMenu(student);
                    break;
                case "6":
                    this.printStudentGradesMenu(student);
                    break;
                case "7":
                    this.printExitMenu();
                    System.exit(0);
                default:
                    this.printErrorMenu();
                    break;

            }
        }

    }

    private void printUserMainMenu(Student student) {
        System.out.println(student.getName() + ", please make a selection");
        System.out.println("1: Find a course offering");
        System.out.println("2: Register for a course offering");
        System.out.println("3: Remove course offering from registered courses");
        System.out.println("4: View all courses in the catalogue");
        System.out.println("5: View current registered courses");
        System.out.println("6: View grades from completed courses");
        System.out.println("7: Quit");
    }

    private void printSearchCourseCatMenu(CourseCat courseCat) {
        System.out.println("What is the name of the course you are looking for?");
        String cName = userInput.nextLine();
        System.out.println("What is the number of the course you are looking for?");
        int cNum = receiveNumberInput();
        Course course = courseCat.searchCat(cName, cNum);
        if (course != null) {
            System.out.println(course.getCourseName() + " " + course.getCourseNum() + " is offered this semester.");
        }
    }

    private void printCatCourseMenu(CourseCat courseCat) {
        System.out.println(courseCat.toStringCourseCatStudent());
    }

    private void printRegisterStudent(CourseCat courseCat, Student student) {
        System.out.println("What is the name of the course you would like to register for?");
        String cName = userInput.nextLine();
        System.out.println("What is the number of the course you would like to register for?");
        int cNum = receiveNumberInput();
        System.out.println("What is the section number of the course you would like to register for?");
        int secNum = receiveNumberInput();
        if (student.registerForCourse(courseCat, cName, cNum, secNum)) {
            System.out.println("Successfully added course to your registered course list!");
        } else {
            System.out.println("Could not register for course offering.  Please ask system admin for assistance.");
        }
    }

    private void printRemoveStudent(CourseCat courseCat, Student student) {
        System.out.println("What is the name of the course you would like to remove?");
        String cName = userInput.nextLine();
        System.out.println("What is the number of the course you would like to remove?");
        int cNum = receiveNumberInput();
        System.out.println("What is the section number of the course you would like to remove?");
        int secNum = receiveNumberInput();
        if (student.removeFromCourse(courseCat, cName, cNum, secNum)) {
            System.out.println("Successfully removed course to your registered course list!");
        } else {
            System.out.println("Could remove registration.  Please ask system admin for assistance.");
        }
    }

    private void printStudentCourseListMenu(Student student) {
        System.out.println(student.printRegistrationList());
    }

    private void printStudentGradesMenu(Student student) {
        System.out.println(student.printGradeList());
    }

    private int receiveNumberInput() {
        // helper method to continue to prompt the user if he/she does not enter an proper input
        int number;
        while (true) {
            try {
                String temp = userInput.nextLine();
                number = Integer.parseInt(temp);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid Input.  Please enter a valid number.");
                continue;
            }

        }
        return number;
    }
}
