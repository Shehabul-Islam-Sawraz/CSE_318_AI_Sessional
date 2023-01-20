package ConstructiveHeuristics;

import Utils.Course;

import java.util.Comparator;

public class SmallerTimeSlotFirst implements Comparator<Course> {
    @Override
    public int compare(Course c1, Course c2) {
        return c1.getTimeSlot() - c2.getTimeSlot();
    }
}
