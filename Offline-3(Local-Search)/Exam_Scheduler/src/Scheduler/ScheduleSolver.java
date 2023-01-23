package Scheduler;

import ConstructiveHeuristics.LargestConflictFirst;
import ConstructiveHeuristics.LargestDegreeFirst;
import ConstructiveHeuristics.LargestEnrollmentFirst;
import PerburtiveHeuristics.KempeChainInterchange;
import PerburtiveHeuristics.PairSwapOperator;
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
    private int constructiveHeuristic;
    //private ExamScheduler examScheduler;
    KempeChainInterchange chainInterchange;
    PairSwapOperator swapOperator;

    public ScheduleSolver(ArrayList<Course> courses, ArrayList<Student> students, int constructiveHeuristic) {
        this.courses = courses;
        this.students = students;
        this.constructiveHeuristic = constructiveHeuristic;
        chainInterchange = new KempeChainInterchange();
        swapOperator = new PairSwapOperator();
    }

    private int chooseScheduler() {
        switch (constructiveHeuristic) {
            case 1:
                return scheduleByLargestDegree();
            case 2:
                return scheduleBySaturationDegree();
            case 3:
                return scheduleByLargestEnrollment();
            case 4:
                return scheduleByRandomOrdering();
            default:
                return 0;
        }
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
                    courses.get(maxIndex).updateDegreeSaturation();
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
        int sz = students.size();
        for(int i=0; i<sz; i++) {
            HashSet<Course> courseArrayList = students.get(i).getEnrolledCourses();
            for(Course course : courseArrayList) {
                course.setConflict(course.getConflict()+(courseArrayList.size()==1? 0: 1));
            }
        }
        Collections.sort(courses, new LargestConflictFirst());
        return ExamScheduler.examScheduling(courses);
    }

    public void solve() {
        long runtime = System.currentTimeMillis();
        int totalSlot = chooseScheduler();
        System.out.println("Total Slot Required: " + totalSlot);
        System.out.println("Average Penalty: " + ExamScheduler.calculateAvgPenalty(students));

        chainInterchange.doPenaltyReduction(courses, students);
        System.out.println("After KempeChainInterchange Average Penalty: " + ExamScheduler.calculateAvgPenalty(students));

        swapOperator.doPenaltyReduction(courses, students);
        System.out.println("After PairSwapOperator Average Penalty: " + ExamScheduler.calculateAvgPenalty(students));
        runtime = System.currentTimeMillis() - runtime;
    }
}
