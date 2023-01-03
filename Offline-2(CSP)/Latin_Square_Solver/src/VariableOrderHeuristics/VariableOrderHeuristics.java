package VariableOrderHeuristics;

import Utils.Cell;

import java.util.ArrayList;

public interface VariableOrderHeuristics {
    int getNextVariable(Cell[][] latinSquare, ArrayList<Cell> unassignedCells);
}
