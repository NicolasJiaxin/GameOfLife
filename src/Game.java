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

    public void printGrid() {
        // Print top border
        for (int i = 0; i < size+2; i++) {
            System.out.print("*");
        }
        System.out.println();

        // Print content of grid
        for (int i = 0; i < size; i++) {
            System.out.print('*');
            for (int j = 0; j < size; j++) {
                if (grid[i][j].isAlive) {
                    System.out.print('X');
                } else {
                    System.out.print('-');
                }
            }
            System.out.println('*');
        }

        // Print bottom border
        for (int i = 0; i < size+2; i++) {
            System.out.print("*");
        }
        System.out.println();
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

    protected void updateNeighboursAll() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j].updateNeighbours(this.grid);
            }
        }
    }

}
