package PerburtiveHeuristics;

import Scheduler.ExamScheduler;
import Utils.Course;
import Utils.Student;

import java.util.ArrayList;
import java.util.Random;

public class KempeChainInterchange implements PerburtiveHeuristic{
    @Override
    public void doPenaltyReduction(ArrayList<Course> courses, ArrayList<Student> students) {
        Random random = new Random(System.currentTimeMillis());
        for(int i=0; i<50; i++){
            int current = random.nextInt(courses.size());
            Course c1 = courses.get(current);
            for(Course c2 : c1.getConflictingCourses()){
                KempeChain kempeChain = new KempeChain(c1, c2);
                double prev_penalty = ExamScheduler.calculateAvgPenalty(students);
                kempeChain.interchangeTimeSlots();
                double new_penalty = ExamScheduler.calculateAvgPenalty(students);
                if(new_penalty > prev_penalty) {
                    kempeChain.interchangeTimeSlots();
                }
            }
        }
    }
}
