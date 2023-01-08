package VariableOrderHeuristics;

import Utils.Cell;

import java.util.ArrayList;

public class MinimumSDMDRatio implements VariableOrderHeuristics {
    @Override
    public Cell getNextVariable(Cell[][] latinSquare, ArrayList<Cell> unassignedCells) {
        int index = -1;
        double minRatio = Double.MAX_VALUE;
        int unassignedCellSize = unassignedCells.size();
        for(int i=0; i<unassignedCellSize; i++) {
            if(unassignedCells.get(i).getValue() != 0) {
                continue;
            }
            int domainSize = unassignedCells.get(i).getPossibleDomainSize();
            int degree = unassignedCells.get(i).getDynamicDegree(latinSquare);
            double ratio = (double) (domainSize*1.0/degree);
            if(ratio < minRatio) {
                minRatio = ratio;
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
