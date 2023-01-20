package Scheduler;

import ConstructiveHeuristics.LargestConflictFirst;
import ConstructiveHeuristics.LargestDegreeFirst;
import ConstructiveHeuristics.LargestEnrollmentFirst;
import Utils.Course;
import Utils.Student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import Scheduler.ExamScheduler;

public class ScheduleSolver {
    private ArrayList<Course> courses;
    private ArrayList<Student> students;
    private int perturbativeHeuristic;
    private int constructiveHeuristic;
    //private ExamScheduler examScheduler;

    public ScheduleSolver(ArrayList<Course> courses, ArrayList<Student> students, int constructiveHeuristic, int perturbativeHeuristic) {
        this.courses = courses;
        this.students = students;
        //examScheduler = new ExamScheduler();
        this.constructiveHeuristic = constructiveHeuristic;
        this.perturbativeHeuristic = perturbativeHeuristic;
    }

    private int scheduleByLargestDegree() {
        Collections.sort(courses, new LargestDegreeFirst());
        return ExamScheduler.examScheduling(courses);
    }

    private int scheduleByLargestEnrollment() {
        Collections.sort(courses, new LargestEnrollmentFirst());
        return ExamScheduler.examScheduling(courses);
    }

    private int scheduleBySaturationDegree() {
        Collections.sort(courses, new LargestDegreeFirst());
        courses.get(0).setTimeSlot(0); // Scheduling first course's slot as all course has same saturation degree at start

        int totalSlot = 1;
        int sz = courses.size();
        for(int i = 1; i < sz; i++){
            HashSet<Integer> temp, selected = null;
            int maxSaturation = -1, maxIndex = -1;

            for(int j = 0; j < sz; j++) {
                if(courses.get(j).getTimeSlot() == -1){
                    temp = new HashSet<>();
                    temp = courses.get(j).getConflictingTimeSlots();
                    if((temp.size() > maxSaturation) || (temp.size() == maxSaturation && courses.get(j).getConflictingCourses().size() > courses.get(maxIndex).getConflictingCourses().size())){
                        maxSaturation = temp.size();
                        maxIndex = j;
                        selected = temp;
                    }
                }
            }

            int suitableSlot = 0;
            while (courses.get(maxIndex).getTimeSlot() == -1) {
                if(!selected.contains(suitableSlot)) {
                    courses.get(maxIndex).setTimeSlot(suitableSlot);
                    if(suitableSlot == totalSlot) {
                        totalSlot++;
                    }
                } else {
                    suitableSlot++;
                }
            }
        }

        return totalSlot;
    }

    private int scheduleByRandomOrdering() {
        Collections.sort(courses, new LargestConflictFirst());
        return ExamScheduler.examScheduling(courses);
    }

    public void solve() {
        long runtime = System.currentTimeMillis();
        // boolean isSolved = chooseSolver();
        runtime = System.currentTimeMillis() - runtime;
    }
}
