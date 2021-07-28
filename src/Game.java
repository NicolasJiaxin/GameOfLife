import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Game {
    protected Cell[][] grid;
    protected int size;
    protected int aliveCellsCount = 0;
    protected int generationsCount = 0;
    protected ArrayList<Cell> needUpdateCells = new ArrayList<>(10);
    public int delayMilli = 500;

    Game(int size) {
        this.size = size;
        this.grid = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = new Cell(i,j);
            }
        }
    }

    public boolean equals(Object o) {
        if (o instanceof Game) {
            Game other = (Game) o;
            boolean equal = true;
            if (this.size != other.size) { return false; }
            if (this.aliveCellsCount != other.aliveCellsCount) { return false; }
            else {
                for (int i = 0; i < this.size; i++) {
                    for (int j = 0; j < this.size; j++) {
                        if ( !(this.grid[i][j].equals(other.grid[i][j])) ) {
                            equal = false;
                        }
                    }
                }
            }
            return equal;
        }
        return false;
    }

    public String toString() {
        String s = "========= Generation " + generationsCount + " =========\n";
        // Print top border
        for (int i = 0; i < size+2; i++) {
            s += "@ ";
        }
        s += '\n';

        // Print content of grid
        for (int i = 0; i < size; i++) {
            s += '@';
            for (int j = 0; j < size; j++) {
                if (grid[i][j].isAlive) {
                    s += " o";
                } else {
                    s += " —";
                }
            }
            s += " @\n";
        }

        // Print bottom border
        for (int i = 0; i < size+2; i++) {
            s += "@ ";
        }
        s += "\n\n";

        return s;
    }

    public void switchState(int x, int y) {
        grid[x][y].needUpdate = true;
        grid[x][y].updateCell();
    }

    protected void checkAll() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j].checkCell();
            }
        }
    }

    protected void updateAll() {
        for (Cell cell : needUpdateCells) {
            cell.updateCell();
        }
        // Remove all cells from list
        needUpdateCells.clear();
    }

    public void start() {
        System.out.print(this);
        generationsCount++;
        while (aliveCellsCount > 0) {
            try {
                TimeUnit.MILLISECONDS.sleep(delayMilli);
            }
            catch (InterruptedException e) {
                System.out.println(e);
            }
            doOneGen();
            System.out.print(this);
        }
    }

    public void start(int maxGen) {
        System.out.print(this);
        generationsCount++;
        while (aliveCellsCount > 0 && generationsCount <= maxGen) {
            try {
                TimeUnit.MILLISECONDS.sleep(delayMilli);
            }
            catch (InterruptedException e) {
                System.out.println(e);
            }
            doOneGen();
            System.out.print(this);
        }
    }

    public void doOneGen() {
        // First, check cells to updated
        checkAll();
        // Then, update cells
        updateAll();
        generationsCount++;
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

        public boolean equals(Object o) {
            if (o == this) { return true; }
            if (!(o instanceof Cell)) { return false; }
            Cell other = (Cell) o;
            return  this.X == other.X &&
                    this.Y == other.Y &&
                    this.neighboursCount == other.neighboursCount &&
                    this.isAlive == other.isAlive &&
                    this.needUpdate == other.needUpdate;
        }   // equals

        public String toString() {
            return  "Position: (" + this.X + "," + this.Y + ")\n" +
                    "Neighbours: " + this.neighboursCount + '\n' +
                    "Alive: " + this.isAlive + '\n' +
                    "Update: " + this.needUpdate + '\n';
        }   // toString

        protected void updateCell() {
            // Check if need to change state
            if (needUpdate) {
                needUpdate = false;
                // Alive cell to dead cell
                if (isAlive) {
                    isAlive = false;
                    aliveCellsCount--;
                    // Update neighbouring cells
                    for (int i = X - 1; i <= X + 1; i++) {
                        if (i < 0 || i > size - 1) { continue; } // Skip if outside border
                        for (int j = Y - 1; j <= Y + 1; j++) {
                            if (j < -0 || j > size - 1 ) { continue; }   // Skip if outside border
                            else if (i == this.X && j == this.Y) { continue; }  // Skip if current cell
                            else {
                                grid[i][j].neighboursCount--;
                            }
                        }
                    }
                }
                // Otherwise, dead cell to live cell
                else {
                    isAlive = true;
                    aliveCellsCount++;
                    // Update neighbouring cells
                    for (int i = X - 1; i <= X + 1; i++) {
                        if (i < 0 || i > size - 1) { continue; } // Skip if outside border
                        for (int j = Y - 1; j <= Y + 1; j++) {
                            if (j < -0 || j > size - 1) { continue; }   // Skip if outside border
                            else if (i == this.X && j == this.Y) { continue; }  // Skip if current cell
                            else {
                                grid[i][j].neighboursCount++;
                            }
                        }
                    }
                }
            }
        }   // updateCell

        protected void checkCell() {
            if (isAlive) {  // Alive cell
                if (neighboursCount < 2 || neighboursCount > 3) {
                    needUpdate = true;
                    // Add to list of cells to update
                    needUpdateCells.add(this);
                }
            }
            else if (neighboursCount == 3) {    // Dead cell
                needUpdate = true;
                // Add to list of cells to update
                needUpdateCells.add(this);
            }
        }   // checkCell

    }   // Cell

}   // Game
