package VariableOrderHeuristics;

import Utils.Cell;

import java.util.ArrayList;

public interface VariableOrderHeuristics {
    Cell getNextVariable(Cell[][] latinSquare, ArrayList<Cell> unassignedCells);
}
