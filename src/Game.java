import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;
// import java.awt.*;

public class Game extends JFrame {
    private Board board;
    
    public Game() {
        initialize();
    }

    private void initialize() {
        setTitle("Pacman");
        setSize(800, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        board = new Board();
        getContentPane().add(board);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                board.keyPressed(e);
            }

            // @Override
            // public void keyReleased(KeyEvent e) {
            //     board.keyReleased(e);
            // }
        });
        setFocusable(true);
        pack();
    }
}
