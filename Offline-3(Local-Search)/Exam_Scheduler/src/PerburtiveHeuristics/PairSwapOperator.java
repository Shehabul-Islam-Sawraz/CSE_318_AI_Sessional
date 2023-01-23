package PerburtiveHeuristics;

import Scheduler.ExamScheduler;
import Utils.Course;
import Utils.Student;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class PairSwapOperator implements PerburtiveHeuristic{

    private static void doPairSwapOperator(ArrayList<Student> students, Course u, Course v) {
        int u_time_slot=u.getTimeSlot(), v_time_slot=v.getTimeSlot();

        /* checking if pair swapping is possible */
        if(u_time_slot == v_time_slot) {
            return ;
        }

        HashSet<Course> overlappingCourses = u.getConflictingCourses();
        for(Course c : overlappingCourses) {
            if(c.getTimeSlot() == v_time_slot) {
                return ;
            }
        }

        overlappingCourses = v.getConflictingCourses();
        for(Course c : overlappingCourses) {
            if(c.getTimeSlot() == u_time_slot) {
                return ;
            }
        }

        /* do pair swap */
        double current_penalty = ExamScheduler.calculateAvgPenalty(students);
        u.setTimeSlot(v_time_slot);
        v.setTimeSlot(u_time_slot);

        /* comparing obtained penalty with current penalty */
        if(current_penalty <= ExamScheduler.calculateAvgPenalty(students)) {
            /* undoing pair swap */
            u.setTimeSlot(u_time_slot);
            v.setTimeSlot(v_time_slot);
        }
    }

    @Override
    public void doPenaltyReduction(ArrayList<Course> courses, ArrayList<Student> students) {
        int penaltyReduction = 0;
        int sameSlot = 0;
        Random random = new Random(System.currentTimeMillis());
        for(int i=0; i<3000; i++) {
            Course c1 = courses.get(random.nextInt(courses.size()));
            //random = new Random(System.currentTimeMillis());
            Course c2 = courses.get(random.nextInt(courses.size()));

            if(c1.getTimeSlot() == c2.getTimeSlot()){
                sameSlot++;
                continue;
            }

            /*KempeChain kempeChain1 = new KempeChain(c1, c2);
            KempeChain kempeChain2 = new KempeChain(c2,c1);

            if(kempeChain1.isPairSwappable(kempeChain2)) {
                penaltyReduction++;
                double prev_penalty = ExamScheduler.calculateAvgPenalty(students);
                kempeChain1.swapPair(kempeChain2);
                double new_penalty = ExamScheduler.calculateAvgPenalty(students);

                if(new_penalty > prev_penalty) {
                    System.out.println("Age: " + ExamScheduler.calculateAvgPenalty(students));
                    kempeChain2.swapPair(kempeChain1);
                    System.out.println("Pore: " + ExamScheduler.calculateAvgPenalty(students));
                    System.out.println("\n\n");
                }
            }*/

            doPairSwapOperator(students, c1, c2);
        }
        System.out.println("Penalty Reduction tried: " + penaltyReduction + " times");
        System.out.println("Same slot: " + sameSlot);
    }
}
