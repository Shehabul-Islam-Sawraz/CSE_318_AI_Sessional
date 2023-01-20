package Utils;

import ConstructiveHeuristics.SmallerTimeSlotFirst;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.TreeSet;

public class Student {
    private int studentId;
    private HashSet<Course> enrolledCourses;

    public Student(int studentId) {
        this.studentId = studentId;
        this.enrolledCourses = new HashSet<>();
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public HashSet<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void setEnrolledCourses(HashSet<Course> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

    public void addEnrolledCourse(Course course) {
        this.enrolledCourses.add(course);
    }

    public double getStudentPenalty(boolean isLinearStrategy) {
        double penalty = 0.0;
        ArrayList<Course> courseArrayList = new ArrayList<>(enrolledCourses);
        Collections.sort(courseArrayList, new SmallerTimeSlotFirst());
        //TreeSet<Course> courseArrayList = new TreeSet<>(enrolledCourses);
        int sz = courseArrayList.size();
        for(int i = 0; i < sz; i++) {
            for(int j = i + 1; j < sz; j++){
                int x = courseArrayList.get(j).getTimeSlot() - courseArrayList.get(i).getTimeSlot();
                if(x < 6){
                    if(isLinearStrategy){
                        penalty += 2 * (5-x);
                    }
                    else{
                        penalty += Math.pow(2.0, (5-x)*1.0);
                    }
                }
            }
        }
        return penalty;
    }
}
