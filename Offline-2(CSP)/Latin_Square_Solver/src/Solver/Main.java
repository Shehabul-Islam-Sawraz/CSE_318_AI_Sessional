package Solver;

import Utils.Cell;
import Utils.Coordinate;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static void solver(int order, Cell[][] latinSquare, ArrayList<Cell> unassignedCells, ArrayList<Cell> assignedCells, int variableHeuristic, int typeOfChecking){
        LatinSquareSolver latinSquareSolver = new LatinSquareSolver(order, latinSquare, unassignedCells, assignedCells, variableHeuristic, typeOfChecking);
        latinSquareSolver.solve();
    }

    private static void runDefaultInputFiles() throws FileNotFoundException {
        File folder = new File("input_data");
        String[] files = folder.list();
        for(int index=0; index< files.length; index++){
            for(int varHeuristic=1; varHeuristic<=5; varHeuristic++){
                for(int typeOfCheck=1; typeOfCheck<=2; typeOfCheck++){
                    int order = -1;
                    Cell[][] latinSquare = null;
                    ArrayList<Cell> assignedCells = new ArrayList<>();
                    ArrayList<Cell> unassignedCells = new ArrayList<>();

                    // Running for all files in input-data folder
                    /* reading from input file and preparing containers */
                    Scanner scanner = new Scanner(new File("input_data/"+files[index]));
                    for(int j=0; j<3; j++) {
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

                    // Finalizing domains of unassigned cells by initial inference
                    // Remove the assigned value from the domain of all the neighbours
                    // of an assigned cell
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
                    System.out.println("====================================");
                    System.out.println("For file: " + files[index] + "\nVariable Heuristic: " + varHeuristic + "\nType of checking: "+ typeOfCheck);
                    solver(order, latinSquare, unassignedCells, assignedCells, varHeuristic, typeOfCheck);
                    System.out.println("====================================\n\n");
                }
            }
        }
    }
    public static void main(String[] args) throws FileNotFoundException {
        runDefaultInputFiles();
    }
}