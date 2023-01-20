package Utils;

import java.util.ArrayList;

public class Course {
    private int courseId;
    private int noOfStudents;
    private int timeSlot;
    private ArrayList<Course> conflictingCourses;
    private int conflict; // This stores no of students associated with conflict for the course

    public Course(int courseId, int noOfStudents) {
        this.courseId = courseId;
        this.noOfStudents = noOfStudents;
        this.timeSlot = -1;
        this.conflict = 0;
        this.conflictingCourses = new ArrayList<>();
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getNoOfStudents() {
        return noOfStudents;
    }

    public void setNoOfStudents(int noOfStudents) {
        this.noOfStudents = noOfStudents;
    }

    public int getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(int timeSlot) {
        this.timeSlot = timeSlot;
    }

    public ArrayList<Course> getConflictingCourses() {
        return conflictingCourses;
    }

    public void setConflictingCourses(ArrayList<Course> conflictingCourses) {
        this.conflictingCourses = conflictingCourses;
    }

    public int getConflict() {
        return conflict;
    }

    public void setConflict(int conflict) {
        this.conflict = conflict;
    }
}
