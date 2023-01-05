package VariableOrderHeuristics;

import Utils.Cell;

import java.util.ArrayList;

public class SmallestDomainMaximumDegree implements VariableOrderHeuristics {
    @Override
    public Cell getNextVariable(Cell[][] latinSquare, ArrayList<Cell> unassignedCells) {
        int smallestDomain = Integer.MAX_VALUE;
        int maxForwardDegree = Integer.MIN_VALUE;
        int index = -1, degree = -1;
        int unassignedCellSize = unassignedCells.size();
        for(int i=0; i<unassignedCellSize; i++) {
            if(unassignedCells.get(i).getValue() != 0) {
                continue;
            }
            int domainSize = unassignedCells.get(i).getPossibleDomainSize();
            degree = unassignedCells.get(i).getDynamicDegree(latinSquare);
            if(domainSize < smallestDomain) {
                smallestDomain = domainSize;
                maxForwardDegree = degree;
                index = i;
            }
            else if(domainSize == smallestDomain){
                if(degree > maxForwardDegree) {
                    maxForwardDegree = degree;
                    index = i;
                }
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
