public class Cell {
    private final int X;
    private final int Y;
    protected int neighboursCount = 0;
    protected boolean isAlive = false;
    protected boolean needUpdate = false;

    Cell(int x, int y) {
        this.X = x;
        this.Y = y;
    }

    Cell(int x, int y, boolean isAlive) {
        this.X = x;
        this.Y = y;
        this.isAlive = isAlive;
    }

    protected void updateCell() {
        if (needUpdate) {
            needUpdate = false;
            isAlive = !isAlive;
        }
    }

    protected void checkCell() {
        if (isAlive) {  // Alive cell
            if (neighboursCount < 2 || neighboursCount > 3) {
                needUpdate = true;
            }
        }
        else if (neighboursCount == 3) {    // Dead cell
            needUpdate = true;
        }
    }

    protected void updateNeighbours(Cell[][] grid) {
        neighboursCount = 0;
        for (int i = X-1; i <= X+1; i++) {
            if (i < 0 || i > grid.length) { continue; } // Skip if outside border
            for (int j = Y-1; j <= Y+1; j++) {
                if (j < - 0 || j > grid.length) { continue; }   // Skip if outside border
                else if (grid[i][j].isAlive) {
                    neighboursCount++;
                }
            }
        }
    }
}
