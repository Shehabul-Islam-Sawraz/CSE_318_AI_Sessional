package Utils;

import java.util.ArrayList;

public class Student {
    private int studentId;
    private ArrayList<Course> enrolledCourses;

    public Student(int studentId) {
        this.studentId = studentId;
        this.enrolledCourses = new ArrayList<>();
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public ArrayList<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void setEnrolledCourses(ArrayList<Course> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }
}
