package Scheduler;

import Utils.Course;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ExamScheduler {
    public static int examScheduling(ArrayList<Course> courses) {
        int totalTimeSlot = 0;
        int sz = courses.size();
        for(int i = 0; i < sz; i++) {
            //ArrayList<Course> conflictingCourses = courses.get(i).getConflictingCourses();
            ArrayList<Integer> conflictingSlots = new ArrayList<>(courses.get(i).getConflictingTimeSlots());
            Collections.sort(conflictingSlots);

            int suitableSlot = 0;
            int slotSize = conflictingSlots.size();
            for(int j = 0; j < slotSize; j++){
                if(suitableSlot < conflictingSlots.get(j)){
                    courses.get(i).setTimeSlot(suitableSlot);
                    break;
                }
                else if(suitableSlot == conflictingSlots.get(j)) {
                    suitableSlot++;
                }
            }
            if(courses.get(i).getTimeSlot() == -1){ // Time slot not assigned yet
                if(suitableSlot == totalTimeSlot) {
                    courses.get(i).setTimeSlot(totalTimeSlot++);
                } else {
                    courses.get(i).setTimeSlot(suitableSlot);
                }
            }
        }
        return totalTimeSlot;
    }
}
