import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame{
    protected JPanel[][] panelCell;
    public int size;

    public MyFrame(int size) {
        this.size = size;
        // Basic setup
        this.setSize(800,800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("GAME OF LIFE");

        // Setup grid
        this.setLayout(new GridLayout(size,size,3,3));
        panelCell = new JPanel[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                panelCell[i][j] = new JPanel();
                panelCell[i][j].setBackground(Color.BLACK);
                panelCell[i][j].setVisible(true);
                this.add(panelCell[i][j]);
            }
        }

        // Set visible for frame last
        this.setVisible(true);
    }
}
