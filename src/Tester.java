class CellToStringTest implements Runnable {
    @Override
    public void run() {
        Game game = new Game(3);
        Game.Cell c1 = game.new Cell(0,0,0,true,false);
        Game.Cell c2 = game.new Cell(2,3,3,false,true);

        boolean string1 = c1.toString().equals("Position: (0,0)\n" +
                "Neighbours: 0\n" +
                "Alive: true\n" +
                "Update: false\n");
        boolean string2 = c2.toString().equals("Position: (2,3)\n"+
                "Neighbours: 3\n" +
                "Alive: false\n" +
                "Update: true\n");
        if (!string1 || !string2) {
            throw new AssertionError("Cell toString does not work.\n Got:n\n" +
                    c1.toString() + c2.toString() +
                    "Expected: " + "Position: (0,0)\n" +
                    "Neighbours: 0\n" +
                    "Alive: true\n" +
                    "Update: false\n" + "Position: (2,3)\n"+
                    "Neighbours: 3\n" +
                    "Alive: false\n" +
                    "Update: true\n");
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

        boolean string = game.toString().equals("*****\n*o--*\n*-o-*\n*o-o*\n*****\n");
        if (!string) {
            throw new AssertionError("Grid toString does not work.\nGot:\n" + game.toString() +
                    "Expected:\n*****\n*o--*\n*-o-*\n*o-o*\n*****\n");
        }
    }
}


public class Tester {
    static String[] tests = {
            "CellToStringTest",
            "GridToStringTest"
    };
    public static void main(String[] args) {
        int numPassed = 0;
        for (String test : tests) {
            System.out.println("====== " + test + " ======\n");
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
