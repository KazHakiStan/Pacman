import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class Game extends JFrame {
    private Board board;
    private static Thread gameThread;
    private final MainMenu mainMenu;
    
    public Game(MainMenu mainMenu, String selectedMap) {
        this.mainMenu = mainMenu;
        
        initialize(selectedMap);
    }

    private void initialize(String selectedMap) {
        setTitle("Pacman");
        setSize(800, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton backButton = new JButton("Back");
        
        board = new Board(backButton, selectedMap);
        gameThread = new Thread(board);
        gameThread.start();
        getContentPane().add(board);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                board.keyPressed(e);
            }
        });
        setFocusable(true);
        pack();

        backButton.addActionListener((ActionEvent event) -> {
            board.interrupt();
            mainMenu.setVisible(true);
            this.dispose();
        });
    }
    
}
