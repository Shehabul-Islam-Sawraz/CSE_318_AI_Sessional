package VariableOrderHeuristics;
import Utils.Cell;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class RandomOrdering implements VariableOrderHeuristics {
    @Override
    public Cell getNextVariable(Cell[][] latinSquare, ArrayList<Cell> unassignedCells) {
        int index = -1;
        ArrayList<Cell> temp = new ArrayList<>(unassignedCells);
        Collections.shuffle(temp);
        for(int i=0; i<temp.size(); i++) {
            if(temp.get(i).getValue() != 0) {
                continue;
            }
            index = i;
            break;
        }
        for(int i=0; i<unassignedCells.size(); i++) {
            if(unassignedCells.get(i).getCoordinate().equals(temp.get(index).getCoordinate())) {
                index = i;
                break;
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
