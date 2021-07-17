public class Grid {
    Cell[][] grid;
    int size;
    Cell[] affectedCells;

    Grid(int size) {
        this.size = size;
        this.grid = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = new Cell(i,j);
            }
        }
    }
}
