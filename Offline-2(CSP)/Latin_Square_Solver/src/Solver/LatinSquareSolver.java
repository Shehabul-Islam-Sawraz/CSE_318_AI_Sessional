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
    private int nodeVisited = -1, backtracks = -1, assignedValues = 0, order;

    public LatinSquareSolver(int order, Cell[][] latinSquare, ArrayList<Cell> unassignedCells, ArrayList<Cell> assignedCells,
                                int variableHeuristic, int typeOfChecking) {
        this.latinSquare = latinSquare;
        this.unassignedCells = unassignedCells;
        this.assignedCells = assignedCells;
        this.variableHeuristic = variableHeuristic;
        this.typeOfChecking = typeOfChecking;
        this.assignedValues = assignedCells.size();
        this.order = order;
    }

    private Cell getNextVariable(){
        switch (this.variableHeuristic){
            case 1:
                SmallestDomainFirst sdf = new SmallestDomainFirst();
                return sdf.getNextVariable(latinSquare, unassignedCells);
            case 2:
                MaximumDegreeFirst mdf = new MaximumDegreeFirst();
                return mdf.getNextVariable(latinSquare, unassignedCells);
            case 3:
                SmallestDomainMaximumDegree sdmd = new SmallestDomainMaximumDegree();
                return sdmd.getNextVariable(latinSquare, unassignedCells);
            case 4:
                MinimumSDMDRatio minSdmd = new MinimumSDMDRatio();
                return minSdmd.getNextVariable(latinSquare, unassignedCells);
            case 5:
                RandomOrdering random = new RandomOrdering();
                return random.getNextVariable(latinSquare, unassignedCells);
            default:
                return null;
        }
    }

    private boolean chooseSolver(){
        nodeVisited = 0;
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
        nodeVisited++;
        boolean isSolved = true;

        if(assignedValues != order*order){
            isSolved = false;
        }
        if(isSolved) {
            return true;
        }

        // Get the next variable using heuristic
        Cell variable = getNextVariable();
        if (variable==null){
            return false;
        }

        int[][] domains = new int[unassignedCells.size()][];
        LeastConstraintValue leastConstraintValue = new LeastConstraintValue(latinSquare, variable, unassignedCells);
        leastConstraintValue.setVariable();

        // Run loop for all the values in the domain of the unassigned cell until it is solved
        while (leastConstraintValue.hasNext() && !isSolved) {
            // Getting value using value heuristic
            int value = leastConstraintValue.getNext();
            // Checking just for error purpose
            if(value == 0) {
                continue;
            }
            // Storing the domains of unassigned cells before value assignment
            for(int j=0; j<domains.length; j++) {
                domains[j] = unassignedCells.get(j).copyDomain();
            }

            // Setting value to variable
            variable.setValue(value);
            assignedValues++;
            boolean isConsistent = SolveChecker.doConsistencyChecking(isForwardChecking, variable, latinSquare);

            if(!isForwardChecking){
                isSolved = backtracking(false); // Call backtracking
            }
            else{
                if(isConsistent){
                    isSolved = backtracking(true); // Call Forward Checking
                }
            }

            if(!isSolved) {
                variable.setValue(0); // If not solved then make the variable an unassigned cell again
                // Reset the domains of unassigned cells with the domains before the assignment
                assignedValues--;
                for(int j=0; j<unassignedCells.size(); j++) {
                    unassignedCells.get(j).setDomain(domains[j]);
                }
            }
        }
        if(!isSolved){
            backtracks++;
        }
        return isSolved;
    }

    public void solve(){
        long runtime = System.currentTimeMillis();
        boolean isSolved = chooseSolver();
        runtime = System.currentTimeMillis() - runtime;
        if(isSolved){
            //printResult();
            System.out.println("Runtime: " + runtime + "ms\nBacktracks: " + backtracks + "\nNode Visited: " + nodeVisited);
            System.out.println("AssignedCells: " + assignedValues);
        }
        else{
            System.out.println("Latin Square couldn't be solved!!");
        }
    }

    private void printResult() {
        for(int i=0;i<latinSquare.length;i++){
            for(int j=0;j<latinSquare[0].length;j++){
                System.out.print(latinSquare[i][j].getValue()+ ", ");
            }
            System.out.println();
        }
    }
}
