import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame{
    protected JPanel[][] panelCell;
    protected Game game;

    public MyFrame() {
        // Basic setup
        this.setSize(800,800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("GAME OF LIFE");
        this.setVisible(true);

        // Setup grid
        this.game = new Game(10);
        this.setLayout(new GridLayout(10,10,3,3));
        panelCell = new JPanel[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                panelCell[i][j] = new JPanel();
                panelCell[i][j].setBackground(Color.BLACK);
                panelCell[i][j].setVisible(true);
                this.add(panelCell[i][j]);
            }
        }
    }
}
