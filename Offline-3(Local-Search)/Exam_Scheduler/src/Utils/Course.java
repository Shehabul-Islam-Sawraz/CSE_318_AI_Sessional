package Utils;

import java.util.ArrayList;
import java.util.HashSet;

public class Course {
    private int courseId;
    private int noOfStudents;
    private int timeSlot;
    private ArrayList<Course> conflictingCourses;
    private int conflict; // This stores no of students associated with conflict for the course
    private int degreeSaturation;

    public Course(int courseId, int noOfStudents) {
        this.courseId = courseId;
        this.noOfStudents = noOfStudents;
        this.timeSlot = -1;
        this.conflict = 0;
        this.degreeSaturation = 0;
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

    public void addConflictingCourse(Course course) {
        this.conflictingCourses.add(course);
    }

    public int getConflict() {
        return conflict;
    }

    public void setConflict(int conflict) {
        this.conflict = conflict;
    }

    public int getDegreeSaturation() {
        return degreeSaturation;
    }

    public void setDegreeSaturation(int degreeSaturation) {
        this.degreeSaturation = degreeSaturation;
    }

    public void updateDegreeSaturation() {
        for(Course course : conflictingCourses) {
            if(course.getTimeSlot() == -1){ // If the adjacent is an uncoloured vertex, then it's saturation will increase
                course.setDegreeSaturation(course.getDegreeSaturation() + 1);
            }
        }
    }

    public HashSet<Integer> getConflictingTimeSlots() {
        HashSet<Integer> slots = new HashSet<>();
        for(Course course : conflictingCourses){
            if(course.getTimeSlot() != -1) {
                slots.add(course.getTimeSlot());
            }
        }
        return slots;
    }
}
