package Scheduler;

import Utils.Course;
import Utils.Student;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static void scheduler(ArrayList<Course> courses, ArrayList<Student> students){

    }

    private static void runInputFiles(String fileName) throws FileNotFoundException {
        ArrayList<Course> courses = new ArrayList<>();
        ArrayList<Student> students = new ArrayList<>();
        Scanner scanner;

        /* extracting input from .crs file */
        scanner = new Scanner(new File("input_data/"+fileName+".crs"));
        while(scanner.hasNextLine()) {
            String[] temp = scanner.nextLine().split(" ");
            courses.add(new Course(Integer.parseInt(temp[0]), Integer.parseInt(temp[1])));
        }
        scanner.close();

        /* preparing conflict matrix */
        int coursesSize = courses.size();
        int[][] conflictMatrix = new int[coursesSize][coursesSize];
        for(int i=0; i<coursesSize; i++) {
            for(int j=0; j<coursesSize; j++) {
                conflictMatrix[i][j] = 0;
            }
        }

        /* extracting input from .stu file */
        int student_count = 0;
        scanner = new Scanner(new File("input_data/"+fileName+".stu"));
        while(scanner.hasNextLine()) {
            /* preparing enrolled courses array */
            String[] temp = scanner.nextLine().split(" ");

            /* adding to enrolledCourses */
            Student student = new Student(student_count + 1);
            int tempLen = temp.length;
            for(int i=0; i<tempLen; i++) {
                //students.get(student_count).addEnrolledCourse(courses.get(tempInteger[i]-1));
                student.addEnrolledCourse(courses.get(Integer.parseInt(temp[i]) - 1));
            }
            students.add(student);
            student_count++;

            /* updating conflict matrix */
            for(int i=0; i<tempLen-1; i++) {
                int x = Integer.parseInt(temp[i]);
                for(int j=i+1; j<tempLen; j++) {
                    int y = Integer.parseInt(temp[j]);
                    if(conflictMatrix[x-1][y-1] == 0) {
                        conflictMatrix[x-1][y-1] = conflictMatrix[y-1][x-1] = 1;

                        courses.get(x-1).addConflictingCourse(courses.get(y-1));
                        courses.get(y-1).addConflictingCourse(courses.get(x-1));
                    }
                }
            }
        }
        scanner.close();

        // Calling scheduler
        scheduler(courses, students);
    }

    public static void main(String[] args) throws FileNotFoundException {
        runInputFiles("abc");
    }


}