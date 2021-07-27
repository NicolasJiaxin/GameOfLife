import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;


public class GUI {
    public static MyFrame frame;
    public static void main(String[] args) {
        frame = new MyFrame();

        frame.game.switchState(0,2);
        frame.game.switchState(1,2);
        frame.game.switchState(2,2);
        frame.game.switchState(2,1);
        frame.game.switchState(1,0);

        while (frame.game.generationsCount < 31) {
            updateBoard();
            try {
                TimeUnit.MILLISECONDS.sleep(frame.game.delayMilli);
            }
            catch (InterruptedException e) {
                System.out.println(e);
            }
            frame.game.doOneGen();
        }
    }

    public static void updateBoard() {
        for (int i = 0; i < frame.game.size; i++) {
            for (int j = 0; j <frame.game.size; j++) {
                if (frame.game.grid[i][j].isAlive) {
                    frame.panelCell[i][j].setBackground(Color.WHITE);
                }
                else {
                    frame.panelCell[i][j].setBackground(Color.BLACK);
                }
            }
        }
    }
}
