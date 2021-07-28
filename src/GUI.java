import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;


public class GUI {
    public static MyFrame frame;
    public static Game game;
    public static void main(String[] args) {
        int size = 20;

        frame = new MyFrame(size);
        game = new Game(size);

        game.switchState(0,2);
        game.switchState(1,2);
        game.switchState(2,2);
        game.switchState(2,1);
        game.switchState(1,0);

        while (game.generationsCount < 31) {
            updateBoard();
            try {
                TimeUnit.MILLISECONDS.sleep(game.delayMilli);
            }
            catch (InterruptedException e) {
                System.out.println(e);
            }
            game.doOneGen();
        }

    }

    public static void updateBoard() {
        for (int i = 0; i < game.size; i++) {
            for (int j = 0; j <game.size; j++) {
                if (game.grid[i][j].isAlive) {
                    frame.panelCell[i][j].setBackground(Color.WHITE);
                }
                else {
                    frame.panelCell[i][j].setBackground(Color.BLACK);
                }
            }
        }
    }
}
