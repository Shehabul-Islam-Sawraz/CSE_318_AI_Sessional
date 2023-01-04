package Solver;
import Utils.Cell;
import Utils.GroupCell;
import java.util.LinkedList;

public class SolveChecker {
    // Checks whether the value we are using for a variable
    // is the first priority of the domain of another variable
    // or not! If yes, then remove that domain from the second
    // variable.
    // For backtracking, the first param is always the unassigned
    // cells to whom we have assigned a value already.
    // For forwardChecking, the first param is the cells that we
    // haven't assigned a value yet
    private static boolean revise(Cell vi, Cell vj) {
        boolean isDeleted = false;
        for(int i=0; i<vi.getDomainSize(); i++) {
            if(vi.getDomainAt(i) == 0) {
                continue;
            }
            boolean isConsistent = false;
            for(int j=0; j<vj.getDomainSize(); j++) {
                if(vj.getDomainAt(j)!=0 && vi.getDomainAt(i)!=vj.getDomainAt(j)) {
                    isConsistent = true;
                    break;
                }
            }
            if(!isConsistent) {
                vi.removeDomainAt(i);
                isDeleted = true;
            }
        }
        return isDeleted;
    }

    public static boolean doConsistencyChecking(boolean isForwardChecking, Cell variable, Cell[][] latinSquare) {
        LinkedList<GroupCell> queue = new LinkedList<>();

        int squareLen = latinSquare.length;
        int varX = variable.getCoordinate().getX();
        int varY = variable.getCoordinate().getY();

        // For backtracking, make groups of all the unassigned set's cells of same the column of
        // variable having already an assigned value for revise with current variable
        // For forwardChecking, make groups of all the unassigned set's cells of same
        // the column of variable for revise with current variable
        for(int row=0; row<squareLen; row++) {
            if(!isForwardChecking && row!=variable.getCoordinate().getX() && latinSquare[row][varY].getValue()!=0) { // For backtracking
                queue.offer(new GroupCell(latinSquare[row][varY], variable));
            } else if(isForwardChecking && latinSquare[row][varY].getValue()==0) { // For forwardchecking
                queue.offer(new GroupCell(latinSquare[row][varY], variable));
            }
        }

        // For backtracking, make groups of all the unassigned set's cells of same the row of
        // variable having already an assigned value for revise with current variable
        // For forwardChecking, make groups of all the unassigned set's cells of same
        // the row of variable for revise with current variable
        for(int col=0; col<squareLen; col++) {
            if(!isForwardChecking && col!=variable.getCoordinate().getY() && latinSquare[varX][col].getValue()!=0) {
                queue.offer(new GroupCell(latinSquare[varX][col], variable));
            } else if(isForwardChecking && latinSquare[varX][col].getValue()==0) {
                queue.offer(new GroupCell(latinSquare[varX][col], variable));
            }
        }
        boolean isConsistent = true;

        while(!queue.isEmpty() && isConsistent) {
            GroupCell groupCell = queue.poll();
            if(!isForwardChecking) { // If we don't have to remove domain for cell1(C1), then consistent assignment
                isConsistent = !(revise(groupCell.getC1(), groupCell.getC2()));
            } else {
                if(revise(groupCell.getC1(), groupCell.getC2())) { // If we remove domain from C1, and it's domain size is not 0, then consistent assignment
                    isConsistent = (groupCell.getC1().getPossibleDomainSize()!=0);
                }
            }
        }
        return isConsistent;
    }
}
