package PerburtiveHeuristics;

import Utils.Course;

import java.util.HashSet;

public class KempeChain {
    private HashSet<Course> hashSet1;
    private HashSet<Course> hashSet2;
    private int timeSlot1;
    private int timeSlot2;

    public KempeChain(Course course1, Course course2) {
        this.hashSet1 = new HashSet<>();
        this.hashSet2 = new HashSet<>();
        this.timeSlot1 = course1.getTimeSlot(); // 1
        this.timeSlot2 = course2.getTimeSlot(); // 5

        createChain(course1);
    }

    private void createChain(Course course) {
        if (course.getTimeSlot() == timeSlot1) {
            hashSet1.add(course);
        } else if (course.getTimeSlot() == timeSlot2) {
            hashSet2.add(course);
        }
        else{
            return;
        }

        for(Course c : course.getConflictingCourses()){
            if(!hashSet1.contains(c) && !hashSet2.contains(c)){
                createChain(c);
            }
        }
    }

    public void interchangeTimeSlots() {
        for (Course c : hashSet1) {
            c.setTimeSlot(timeSlot2);
        }
        for (Course c : hashSet2) {
            c.setTimeSlot(timeSlot1);
        }
    }

    private boolean hasOneCourse() {
        // This checks if there are only one member in the Kempe Chain or not!
        return hashSet1.size()==1 && hashSet2.size()==0;
        //return hashSet2.size()==0;
    }

    public boolean isPairSwappable(KempeChain chain) {
        if (!this.hasOneCourse() || !chain.hasOneCourse()) {
            return false;
        }
        return true;
    }

    public void swapPair(KempeChain chain) {
        this.interchangeTimeSlots();
        chain.interchangeTimeSlots();
    }
}
