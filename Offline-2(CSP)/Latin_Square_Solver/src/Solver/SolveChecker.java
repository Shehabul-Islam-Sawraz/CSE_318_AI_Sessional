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
    public static boolean doConsistencyChecking(boolean isForwardChecking, Cell variable, Cell[][] latinSquare){
        boolean isConsistent = true;
        int varX = variable.getCoordinate().getX();
        int varY = variable.getCoordinate().getY();
        int len = latinSquare.length;
        for(int i=0;i<len;i++){
            if(i!=varX &&latinSquare[i][varY].getValue()==0 ){
                latinSquare[i][varY].removeFromDomain(variable.getValue());
                if(latinSquare[i][varY].getPossibleDomainSize()==0){
                    isConsistent = false;
                }
            }
            if(i != varY &&latinSquare[varX][i].getValue()==0){
                latinSquare[varX][i].removeFromDomain(variable.getValue());
                if(latinSquare[varX][i].getPossibleDomainSize()==0){
                    isConsistent = false;
                }
            }
        }
        return isConsistent;
    }
}
