package Solver;

import Utils.Cell;
import Utils.Coordinate;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static void solver(Cell[][] latinSquare, ArrayList<Cell> unassignedCells, ArrayList<Cell> assignedCells , int variableHeuristic, int typeOfChecking){

    }

    private static void runDefaultInputFiles() throws FileNotFoundException {
        int order = -1;
        Cell[][] latinSquare = null;
        ArrayList<Cell> assignedCells = new ArrayList<>();
        ArrayList<Cell> unassignedCells = new ArrayList<>();

        File folder = new File("input-data");
        String[] files = folder.list();

        // Running for all files in input-data folder
        for(int index=0; index< files.length; index++){
            /* reading from input file and preparing containers */
            Scanner scanner = new Scanner(new File("input-data/"+files[index]));
            for(int j=0; j<2; j++) {
                String scannedLine = scanner.nextLine();
                if(j == 0) {
                    order = Integer.parseInt(scannedLine.substring(2, scannedLine.length()-1));
                    latinSquare = new Cell[order][order];
                }
            }
            for(int i=0; i<order; i++) {
                String[] temp = scanner.nextLine().split(", ");
                for(int j=0; j<temp.length; j++) {
                    latinSquare[i][j] = new Cell(new Coordinate(i, j), order); // Initializing each index as Cell
                    // Assigning values in each cell
                    latinSquare[i][j].setValue((j<temp.length-1? Integer.parseInt(temp[j]): Integer.parseInt(temp[j].substring(0, temp[j].indexOf(" ")))));
                    // For unassigned cells, value is 0
                    if(latinSquare[i][j].getValue() == 0) {
                        unassignedCells.add(latinSquare[i][j]);
                    } else {
                        assignedCells.add(latinSquare[i][j]);
                    }
                }
            }
            scanner.close();

            /* finalizing domains of unassigned cells by initial inference */
            for(int i=0, x, y; i<assignedCells.size(); i++) {
                x = assignedCells.get(i).getCoordinate().getX();
                y = assignedCells.get(i).getCoordinate().getY();
                for(int row=0; row<order; row++) {
                    if(latinSquare[row][y].getValue() == 0) {
                        latinSquare[row][y].removeDomainAt(assignedCells.get(i).getValue()-1);
                    }
                }
                for(int col=0; col<order; col++) {
                    if(latinSquare[x][col].getValue() == 0) {
                        latinSquare[x][col].removeDomainAt(assignedCells.get(i).getValue()-1);
                    }
                }
            }
        }
    }
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("input_data/input.txt"));
//        for(int i=0; i<3;i++){
//            System.out.println(scanner.nextLine());
//        }
//        for(int i=0; i<10;i++){
//            String[] numbers = scanner.nextLine().split(", ");
//            for(int j=0;j<10;j++){
//                System.out.print(numbers[j]+" ");
//            }
//            System.out.println("\n");
//        }

        //runDefaultInputFiles(); // chng korte hbe
    }
}