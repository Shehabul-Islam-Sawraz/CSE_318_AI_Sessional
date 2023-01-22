package PerburtiveHeuristics;

import Utils.Course;
import Utils.Student;

import java.util.ArrayList;

public interface PerburtiveHeuristic {
    public void doPenaltyReduction(ArrayList<Course> courses, ArrayList<Student> students);
}
