
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.*;
import javax.swing.*;

public class MainMenu extends JFrame {
    public MainMenu() {
        setTitle("Pacman");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        getContentPane().add(panel);
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(10, 10, 10, 10);
        // panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JButton newGameButton = new JButton("New Game");
        newGameButton.addActionListener((ActionEvent event) -> {
            newGame();
        });

        JButton scoresButton = new JButton("High Scores");
        scoresButton.addActionListener((ActionEvent event) -> {
            showScores();
        });

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener((ActionEvent event) -> {
            System.exit(0);
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(newGameButton, gbc);

        gbc.gridy = 1;
        panel.add(scoresButton, gbc);

        gbc.gridy = 2;
        panel.add(exitButton, gbc);
    }

    private void newGame() {
        String[] maps = {"default", "defaultNoPortal", "map 3", "map 4", "map 5"};

        String selectedMap = (String) JOptionPane.showInputDialog(
                this, 
                "Select a map", 
                "Map Selection", 
                JOptionPane.PLAIN_MESSAGE, 
                null, 
                maps, 
                maps[0]
        );

        if (selectedMap != null) {
            Game game = new Game(this, selectedMap);
            game.setVisible(true);
            this.setVisible(false);
        }

    }

    private void showScores() {
        ScoresMenu scoresMenu = new ScoresMenu(this);
        scoresMenu.setVisible(true);
    }
}
