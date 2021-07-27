class CellToStringTest implements Runnable {
    @Override
    public void run() {
        Game game = new Game(3);
        Game.Cell c1 = game.new Cell(0,0,0,true,false);
        Game.Cell c2 = game.new Cell(2,3,3,false,true);

        String expected1 = "Position: (0,0)\n" +
                            "Neighbours: 0\n" +
                            "Alive: true\n" +
                            "Update: false\n";
        String expected2 =  "Position: (2,3)\n"+
                            "Neighbours: 3\n" +
                            "Alive: false\n" +
                            "Update: true\n";
        boolean string1 = c1.toString().equals(expected1);
        boolean string2 = c2.toString().equals(expected2);
        if (!string1 || !string2) {
            throw new AssertionError("Cell toString does not work.\n Got:n\n" +
                    c1.toString() + c2.toString() +
                    "Expected:\n" + expected1 + expected2);
        }
    }
}

class CellEqualTest implements Runnable {
    @Override
    public void run() {
        Game game = new Game(3);
        Game.Cell c1 = game.new Cell(0,0,5,false,true);
        Game.Cell c2 = game.new Cell(0,0,5,false,true);
        Game.Cell c3= game.new Cell(2,0,5,false,true);

        boolean b1 = c1.equals(c2);
        boolean b2 = c2.equals(c1);
        boolean b3 = !c1.equals(c3);
        boolean b4 = !c2.equals(c3);
        boolean b5 = c1.equals(c1);
        if ( !(b1 && b2 && b3 && b4 && b5) ) {
            throw new AssertionError("Equals method does not work. Got: " + b1 + ", " + b2 + ", " + b3 + ", " + b4 + ", " + b5 + ".\n");
        }
    }
}

class GridToStringTest implements Runnable {
    @Override
    public void run() {
        Game game = new Game(3);
        game.grid[0][0] = game.new Cell(0,0,true);
        game.grid[1][1] = game.new Cell(1,1,true);
        game.grid[2][0] = game.new Cell(2,0,true);
        game.grid[2][2] = game.new Cell(2,2,true);

        String expected = "========= Generation 0 =========\n@ @ @ @ @ \n@ o — — @\n@ — o — @\n@ o — o @\n@ @ @ @ @ \n\n";
        boolean string = game.toString().equals(expected);
        if (!string) {
            throw new AssertionError("Grid toString does not work.\nGot:\n" + game.toString() +
                    "Expected:\n" + expected);
        }
    }
}
class switchStateTest implements Runnable {
    @Override
    public void run() {
        Game game = new Game(3);
        game.switchState(0,0);

        Game expected = new Game(3);
        expected.grid[0][0].isAlive = true;
        expected.grid[0][1].neighboursCount++;
        expected.grid[1][0].neighboursCount++;
        expected.grid[1][1].neighboursCount++;
        expected.aliveCellsCount++;
        boolean equal1 = game.equals(expected);

        game.switchState(0,2);
        expected.grid[0][2].isAlive = true;
        expected.grid[0][1].neighboursCount++;
        expected.grid[1][1].neighboursCount++;
        expected.grid[1][2].neighboursCount++;
        expected.aliveCellsCount++;
        boolean equal2 = game.equals(expected);

        game.switchState(0,2);
        expected.grid[0][2].isAlive = false;
        expected.grid[0][1].neighboursCount--;
        expected.grid[1][1].neighboursCount--;
        expected.grid[1][2].neighboursCount--;
        expected.aliveCellsCount--;
        boolean equal3 = game.equals(expected);

        if ( !(equal1 && equal2 && equal3)) {
            throw new AssertionError("Got wrong result.");
        }
    }
}

class checkAllTest implements Runnable {
    @Override
    public void run() {
        Game game = new Game(3);
        game.switchState(0,2);
        game.switchState(1,2);
        game.switchState(2,2);
        for (int i = 0; i < game.size; i++) {
            for (int j = 0; j < game.size; j++) {
                game.grid[i][j].checkCell();
            }
        }

        Game expected = new Game(3);
        expected.switchState(0,2);
        expected.switchState(1,2);
        expected.switchState(2,2);
        expected.grid[0][2].needUpdate = true;
        expected.grid[1][1].needUpdate = true;
        expected.grid[2][2].needUpdate = true;
        boolean equal = game.equals(expected);

        if (!equal) {
            throw new AssertionError("Got wrong result.");
        }
    }
}


public class Tester {
    static String[] tests = {
            "CellToStringTest",
            "CellEqualTest",
            "GridToStringTest",
            "switchStateTest",
            "checkAllTest"
    };
    public static void main(String[] args) {
        int numPassed = 0;
        for (String test : tests) {
            System.out.println("\n====== " + test + " ======\n");
            try {
                Runnable testCase = (Runnable) Class.forName(test).getDeclaredConstructor().newInstance();
                testCase.run();
                numPassed++;
                System.out.println("Test passed.");
            }
            catch (AssertionError e) {
                System.out.println(e);
            }
            catch (Exception e) {
                e.printStackTrace(System.out);
            }
        }

        System.out.printf("\n\n%d out of %d tests passed. ", numPassed, tests.length);
        if (numPassed == tests.length) {
            System.out.println("All tests passed.");
        }
    }
}
