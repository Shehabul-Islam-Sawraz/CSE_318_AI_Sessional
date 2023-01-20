package ConstructiveHeuristics;

import Utils.Course;

import java.util.Comparator;

public class LargestEnrollmentFirst implements Comparator<Course> {
    @Override
    public int compare(Course c1, Course c2) {
        int sz1 = c1.getNoOfStudents();
        int sz2 = c2.getNoOfStudents();
        if(sz1 != sz2) {
            return sz2 - sz1; // Choose the course having the largest no of enrollments
        }
        return c2.getConflictingCourses().size() - c1.getConflictingCourses().size(); // For tie breaking, choose the course having most no of conflicts
    }
}
