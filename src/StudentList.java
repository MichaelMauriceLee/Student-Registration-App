import java.util.ArrayList;

/***
 * This class contains the StudentList class and its instance methods.
 * This class exists so that way it is possible to choose a student user in the app.
 *
 * @author Michael Lee
 * @version 1.0
 * @since 10/7/2019
 */


public class StudentList {
    private ArrayList<Student> studentList;

    public StudentList(ArrayList<Student> studentList){
        this.studentList = studentList;
    }

    public Student findStudent(String name, int id){
        for (Student student : studentList){
            if (student.getName().contentEquals(name) && student.getId() == id){
                return student;
            }
        }
        System.out.println("Invalid credentials, please check inputs and/or contact system admin");
        return null;
    }
}
