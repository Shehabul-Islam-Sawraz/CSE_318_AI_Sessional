package VariableOrderHeuristics;
import Utils.Cell;
import java.util.ArrayList;
import java.util.Random;

public class RandomOrdering implements VariableOrderHeuristics {

    private Random random;

    public RandomOrdering() {
        random = new Random(System.currentTimeMillis());
    }

    @Override
    public int getNextVariable(Cell[][] latinSquare, ArrayList<Cell> unassignedCells) {
        return random.nextInt(unassignedCells.size());
    }
}
