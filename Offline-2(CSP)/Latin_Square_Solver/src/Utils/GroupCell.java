package Utils;

public class GroupCell {
    private Cell c1;
    private Cell c2;

    public GroupCell(Cell ci, Cell cj) {
        this.c1 = ci;
        this.c2 = cj;
    }

    public Cell getC1() {
        return c1;
    }

    public void setC1(Cell c1) {
        this.c1 = c1;
    }

    public Cell getC2() {
        return c2;
    }

    public void setC2(Cell c2) {
        this.c2 = c2;
    }
}
