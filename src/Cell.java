public class Cell {
    private int x;
    private int y;
    protected boolean isAlive = false;
    protected boolean needUpdate = false;

    Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    Cell(int x, int y, boolean isAlive) {
        this.x = x;
        this.y = y;
        this.isAlive = isAlive;
    }

    public void updateCell() {
        if (needUpdate) {
            needUpdate = false;
            isAlive = !isAlive;
        }
    }
}
