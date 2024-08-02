public enum Grid {
    A (0, 0),
    B (0, 1),
    C (0, 2),
    D (1, 0),
    E (1, 1),
    F (1, 2),
    G (2, 0),
    H (2, 1),
    I (2, 2);   

    private final int row;
    private final int col;

    Grid(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
