package Utils;

public class Cell {
    private Coordinate coordinate;
    int[] domain;
    int staticDegree;
    int value;

    public Cell(Coordinate coordinate, int staticDegree) {
        this.coordinate = coordinate;
        this.staticDegree = staticDegree;

        // Setting up possible values for every cell
        domain = new int[staticDegree];
        for(int i=0; i<staticDegree; i++){
            domain[i] = i+1;
        }
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public int[] getDomain() {
        return domain;
    }

    public void setDomain(int[] domain) {
        this.domain = domain;
    }

    public int getStaticDegree() {
        return staticDegree;
    }

    public void setStaticDegree(int staticDegree) {
        this.staticDegree = staticDegree;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        // If value of a cell is already given then the domain of the cell is only that value
        if(value != 0){
            for(int i=0; i<staticDegree; i++){
                domain[i] = ((i+1) == value? value: 0);
            }
        }
    }

    // Returns the size of the domain array
    public int getDomainSize(){
        return domain.length;
    }

    // Returns the size of possible no of domain values
    public int getPossibleDomainSize(){
        int sz = 0;
        for(int i=0; i<domain.length;i++){
            sz += ((domain[i]!=0) ? 1: 0);
        }
        return sz;
    }

    public int getDynamicDegree(Cell[][] latinSquare){
        int rowSize = latinSquare.length;
        int colSize = latinSquare[0].length;
        int dynamicDegree = 0;
        // Calculating no of 0 or unassigned value in the column of current pos Y
        for(int row=0; row<rowSize; row++) {
            if(row!=coordinate.getX() && latinSquare[row][coordinate.getY()].getValue()==0) {
                dynamicDegree++;
            }
        }
        // Calculating no of 0 or unassigned value in the row of current pos X
        for(int col=0; col<colSize; col++) {
            if(col!=coordinate.getY() && latinSquare[coordinate.getX()][col].getValue()==0) {
                dynamicDegree++;
            }
        }
        return dynamicDegree;
    }
}
