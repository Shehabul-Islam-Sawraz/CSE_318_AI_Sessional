package ValueOrderHeuristics;

import Utils.Cell;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class LeastConstraintValue implements ValueOrderHeuristics {

    private PriorityQueue<Integer> values;
    private Cell variable;
    private Cell[][] latinSquare;
    private ArrayList<Cell> unassignedCells;

    public LeastConstraintValue(Cell[][] latinSquare, Cell variable, ArrayList<Cell> unassignedCells) {
        this.latinSquare = latinSquare;
        this.variable = variable;
        this.unassignedCells = unassignedCells;
        this.values = null;
    }

    @Override
    public void setVariable() {
        HashMap<Integer, Integer> valueDegree = new HashMap<>();
        int x = variable.getCoordinate().getX();
        int y = variable.getCoordinate().getY();

        // min priority queue on the basis of degree
        values = new PriorityQueue<>(Comparator.comparing(valueDegree::get));

        int squareLen = latinSquare.length;

        // If no. of unassigned variables is less than twice the square length
        // then calculate the degree of each value of the domain of the variable
        // otherwise calculate it from the associated variables of the variable
        // this takes O(n^2) time
        if(unassignedCells.size() <= 2 * squareLen) {
            for (int value : variable.getDomain()) {
                if(value == 0){
                    continue;
                }
                valueDegree.put(value, 0);
                for (Cell cell : unassignedCells) {
                    if ((cell.getCoordinate().getX() == x || cell.getCoordinate().getY() == y) && cell.domainContains(value)) {
                        valueDegree.put(value, valueDegree.get(value) + 1);
                    }
                }
                values.add(value);
            }
        } else {
            for(int value : variable.getDomain()) {
                if(value == 0){
                    continue;
                }
                valueDegree.put(value, 0);
                for(int i = 0; i < squareLen; i++) {
                    Cell cell = latinSquare[i][y];
                    if(i != x && unassignedCells.contains(cell) && cell.domainContains(value)) {
                        valueDegree.put(value, valueDegree.get(value) + 1);
                    }
                    cell = latinSquare[x][i];
                    if(i != y && unassignedCells.contains(cell) && cell.domainContains(value)) {
                        valueDegree.put(value, valueDegree.get(value) + 1);
                    }
                }
                values.add(value);
            }
        }
    }

    @Override
    public Integer getNext() {
        if(variable == null) {
            return null;
        }
        return values.poll();
    }

    @Override
    public boolean hasNext() {
        if(variable == null) {
            return false;
        }
        return !values.isEmpty();
    }
}
