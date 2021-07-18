public class Game {
    protected Cell[][] grid;
    protected int size;

    Game(int size) {
        this.size = size;
        this.grid = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = new Cell(i,j);
            }
        }
    }

    public String toString() {
        String s = "";
        // Print top border
        for (int i = 0; i < size+2; i++) {
            s += '*';
        }
        s += '\n';

        // Print content of grid
        for (int i = 0; i < size; i++) {
            s += '*';
            for (int j = 0; j < size; j++) {
                if (grid[i][j].isAlive) {
                    s += 'o';
                } else {
                    s += '-';
                }
            }
            s += "*\n";
        }

        // Print bottom border
        for (int i = 0; i < size+2; i++) {
            s += '*';
        }
        s += '\n';

        return s;
    }

    protected void updateAll() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j].updateCell();
            }
        }
    }

    protected void checkAll() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j].checkCell();
            }
        }
    }

    public class Cell {
        private final int X;
        private final int Y;
        int neighboursCount = 0;
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

        Cell(int x, int y, int neighboursCount, boolean isAlive, boolean needUpdate) {
            this.X = x;
            this.Y = y;
            this.neighboursCount = neighboursCount;
            this.isAlive = isAlive;
            this.needUpdate = needUpdate;
        }

        public String toString() {
            return  "Position: (" + this.X + "," + this.Y + ")\n" +
                    "Neighbours: " + this.neighboursCount + '\n' +
                    "Alive: " + this.isAlive + '\n' +
                    "Update: " + this.needUpdate + '\n';
        }

        protected void updateCell() {
            // Check if need to change state
            if (needUpdate) {
                needUpdate = false;
                // Alive cell to dead cell
                if (isAlive) {
                    isAlive = false;
                    // Update neighbouring cells
                    for (int i = X - 1; i <= X + 1; i++) {
                        if (i < 0 || i > grid.length) {
                            continue;
                        } // Skip if outside border
                        for (int j = Y - 1; j <= Y + 1; j++) {
                            if (j < -0 || j > grid.length) {
                                continue;
                            }   // Skip if outside border
                            else {
                                grid[i][j].neighboursCount--;
                            }
                        }
                    }
                }
                // Otherwise, dead cell to live cell
                else {
                    isAlive = true;
                    // Update neighbouring cells
                    for (int i = X - 1; i <= X + 1; i++) {
                        if (i < 0 || i > grid.length) {
                            continue;
                        } // Skip if outside border
                        for (int j = Y - 1; j <= Y + 1; j++) {
                            if (j < -0 || j > grid.length) {
                                continue;
                            }   // Skip if outside border
                            else {
                                grid[i][j].neighboursCount++;
                            }
                        }
                    }
                }
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
    }

}
