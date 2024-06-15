import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class ScoresMenu extends JFrame {
    private static final String FILE_NAME = "./src/resources/highscores.txt";
    private JList<String> scoreList;
    private MainMenu mainMenu;

    public ScoresMenu(MainMenu mainMenu) {
        this.mainMenu = mainMenu;

        setTitle("High Scores");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        

        scoreList = updateScores();
        scoreList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(scoreList);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JButton backButton = new JButton("Back");
        backButton.addActionListener((ActionEvent event) -> {
            this.mainMenu.setVisible(true);
            this.dispose();
        });

        panel.add(scrollPane);
        panel.add(backButton);

        getContentPane().add(panel);
    }

    private static List<Score> loadScores() {
        List<Score> scores = new ArrayList<>();
        Path path = Paths.get(FILE_NAME);
        if (Files.exists(path)) {
            try (BufferedReader reader = Files.newBufferedReader(path)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 4) {
                        int score = Integer.parseInt(parts[0]);
                        int time = Integer.parseInt(parts[1]);
                        String nickname = parts[2];
                        String map = parts[3];
                        scores.add(new Score(score, time, nickname, map));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return scores;
    }

    public static void addScore(int score, int time, String nickname, String map) {
        try (FileWriter fw = new FileWriter(FILE_NAME, true)) {
            fw.write(score + "," + time + "," + nickname + "," + map + "\n");
            updateScores();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JList<String> updateScores() {
        DefaultListModel<String> scores = new DefaultListModel<>();
        for (Score score : loadScores()) {
            scores.addElement(score.toString());
        }

        return new JList<>(scores);
    }
}
