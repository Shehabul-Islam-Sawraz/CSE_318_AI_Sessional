package Solver;
import Utils.Cell;
import VariableOrderHeuristics.*;
import ValueOrderHeuristics.*;
import java.util.ArrayList;

public class LatinSquareSolver {
    private Cell[][] latinSquare;
    private ArrayList<Cell> unassignedCells;
    private ArrayList<Cell> assignedCells;
    private int variableHeuristic, typeOfChecking;
    private int nodeVisited = -1, backtracks = -1;

    public LatinSquareSolver(Cell[][] latinSquare, ArrayList<Cell> unassignedCells, ArrayList<Cell> assignedCells ,
                                int variableHeuristic, int typeOfChecking) {
        this.latinSquare = latinSquare;
        this.unassignedCells = unassignedCells;
        this.assignedCells = assignedCells;
        this.variableHeuristic = variableHeuristic;
        this.typeOfChecking = typeOfChecking;
    }

    private Cell getNextVariable(){
        switch (this.variableHeuristic){
            case 1:
                SmallestDomainFirst sdf = new SmallestDomainFirst();
                return unassignedCells.get(sdf.getNextVariable(latinSquare, unassignedCells));
            case 2:
                MaximumDegreeFirst mdf = new MaximumDegreeFirst();
                return unassignedCells.get(mdf.getNextVariable(latinSquare, unassignedCells));
            case 3:
                SmallestDomainMaximumDegree sdmd = new SmallestDomainMaximumDegree();
                return unassignedCells.get(sdmd.getNextVariable(latinSquare, unassignedCells));
            case 4:
                MinimumSDMDRatio minSdmd = new MinimumSDMDRatio();
                return unassignedCells.get(minSdmd.getNextVariable(latinSquare, unassignedCells));
            case 5:
                RandomOrdering random = new RandomOrdering();
                return unassignedCells.get(random.getNextVariable(latinSquare, unassignedCells));
            default:
                return null;
        }
    }

    private boolean chooseSolver(){
        nodeVisited = 1;
        backtracks = 0;
        switch (this.typeOfChecking){
            case 1:
                return backtracking(false);
            case 2:
                return forwardChecking();
            default:
                return false;
        }
    }

    private boolean forwardChecking() {
        return backtracking(true);
    }

    private boolean backtracking(boolean isForwardChecking) {
        boolean isSolved = true;
        // Checking whether all unassigned cells have got value. If yes, then solved, else not.
        for(Cell cell: unassignedCells) {
            if(cell.getValue() == 0) {
                isSolved = false;
                break;
            }
        }
        if(isSolved) {
            return true;
        }

        Cell variable = getNextVariable(); // Get the next variable using heuristic
        int[][] domains = new int[unassignedCells.size()][];
        LeastConstraintValue leastConstraintValue = new LeastConstraintValue(latinSquare, variable, unassignedCells);
        leastConstraintValue.setVariable();
        // Run loop for all the values in the domain of the unassigned cell until it is solved
        while (leastConstraintValue.hasNext() && !isSolved) {
            // Getting value using heuristic
            int value = leastConstraintValue.getNext();
            // Checking just for error purpose
            if(value == 0) {
                continue;
            }
            nodeVisited++;
            for(int j=0; j<domains.length; j++) {
                domains[j] = unassignedCells.get(j).copyDomain();
            }

            if (variable != null) {
                variable.setValue(value);
            }

            // If Latin Square is consistent with the assignment of the variable, then continue backtracking
            if(SolveChecker.doConsistencyChecking(isForwardChecking, variable, latinSquare)) {
                isSolved = backtracking(isForwardChecking);
            } else {
                backtracks++;
            }
            if(!isSolved) {
                variable.setValue(0); // If not solved then make the variable an unassigned cell again
                // Reset the domains of unassigned cells with the domains before the assignment
                for(int j=0; j<unassignedCells.size(); j++) {
                    unassignedCells.get(j).setDomain(domains[j]);
                }
            }
        }
        return isSolved;
    }

    public void solve(){
        long runtime = System.currentTimeMillis();
        boolean isSolved = chooseSolver();
        runtime = System.currentTimeMillis() - runtime;
        if(isSolved){
            System.out.println("Runtime: " + runtime + "ms\nBacktracks: " + backtracks + "\nNode Visited: " + nodeVisited);
        }
        else{
            System.out.println("Latin Square couldn't be solved!!");
        }
    }
}
