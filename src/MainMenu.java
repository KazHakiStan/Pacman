import java.awt.event.*;
import javax.swing.*;

public class MainMenu extends JFrame {
    public MainMenu() {
        setTitle("Pacman");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        getContentPane().add(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JButton newGameButton = new JButton("New Game");
        newGameButton.addActionListener((ActionEvent event) -> {
            newGame();
        });

        // JButton scoresButton = new JButton("High Scores");
        // scoresButton.addActionListener((ActionEvent event) -> {
        //     showScores();
        // });

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener((ActionEvent event) -> {
            System.exit(0);
        });

        panel.add(newGameButton);
        // panel.add(scoresButton);
        panel.add(exitButton);
    }

    private void newGame() {
        Game game = new Game();
        game.setVisible(true);
        this.setVisible(false);
    }

    // private void showScores() {
    //     Scores scores = new Scores();
    //     scores.setVisible(true);
    // }
}
