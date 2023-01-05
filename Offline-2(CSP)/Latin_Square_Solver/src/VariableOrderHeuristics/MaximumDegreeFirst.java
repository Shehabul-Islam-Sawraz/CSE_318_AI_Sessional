package VariableOrderHeuristics;
import Utils.Cell;
import java.util.ArrayList;

public class MaximumDegreeFirst implements VariableOrderHeuristics {
    @Override
    public Cell getNextVariable(Cell[][] latinSquare, ArrayList<Cell> unassignedCells) {
        int maxForwardDegree = Integer.MIN_VALUE;
        int index = -1, degree = -1;
        int unassignedCellSize = unassignedCells.size();
        for(int i=0; i<unassignedCellSize; i++) {
            if(unassignedCells.get(i).getValue() != 0) {
                continue;
            }
            degree = unassignedCells.get(i).getDynamicDegree(latinSquare);
            if(degree > maxForwardDegree) {
                maxForwardDegree = degree;
                index = i;
            }
        }
        if(index!=-1){
            return unassignedCells.get(index);
        }
        else {
            return null;
        }
    }
}
