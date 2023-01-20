package ConstructiveHeuristics;

import Utils.Course;

import java.util.Comparator;

public class LargestDegreeFirst implements Comparator<Course> {
    @Override
    public int compare(Course c1, Course c2) {
        int sz1 = c1.getConflictingCourses().size();
        int sz2 = c2.getConflictingCourses().size();
        if(sz1 != sz2) {
            return sz2 - sz1; // Choose the course having the largest conflicting no of courses
        }
        return c2.getNoOfStudents() - c1.getNoOfStudents(); // For tie breaking, choose the course having most no of students
    }
}
