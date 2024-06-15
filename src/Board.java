import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.*;

public class Board extends JPanel implements Runnable {
    private Player player;
    private Blinky blinky;
    private Pinky pinky;
    private Inky inky;
    private Clyde clyde;
    private GameTimer gameTimer = new GameTimer();
    private Thread timer;
    private JLabel timerLabel = new JLabel(gameTimer.toString());
    private JLabel livesLabel;
    private JLabel scoreLabel;
    private JLabel levelLabel;
    private volatile int score = 0;
    private volatile int dotCount = 0;
    private volatile int level = 1;
    private int width;
    private int height;
    protected static Cell[][] cells;
    protected static ArrayList<Cell> markedCells = new ArrayList<>();
    protected static Cell spawn;
    private String selectedMap;
    private volatile int upgradeTimestamp = 5;
    private int speedTimestamp = -1;
    private int immortalityTimestamp = -1;
    private int doubleTimestamp = -1;
    private int scoreMultiplier = 1;

    public Board(JButton backButton, String selectedMap) {
        this.selectedMap = selectedMap;
        String[][] board = loadMap(selectedMap);
        initializeBoard(board);
        backButton.setBounds(0, height * Cell.length + 40 , 100, 50);
        add(backButton);
    }

    private void initializeBoard(String[][] board) {
        setPreferredSize(new Dimension(width * Cell.length, height * Cell.length + 80));
        setBackground(Color.BLACK);
        setLayout(null);
        cells = new Cell[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cells[i][j] = new Cell(j * Cell.length, i * Cell.length, board[i][j]);
                if (cells[i][j].getType() == Cell.CellType.PORTAL) { Cell.portals.add(cells[i][j]); }
                if (cells[i][j].getType() == Cell.CellType.SPAWN) { spawn = cells[i][j]; }
                if (cells[i][j].getType() == Cell.CellType.DOT) { dotCount += 1; }
            }
        }
        
        this.setPlayer(new Player(cells[23][13].getX(), cells[23][13].getY(), null, gameTimer));
        this.setBlinky(new Blinky(cells[11][13].getX(), cells[11][13].getY(), null, gameTimer, this.getPlayer()));
        this.setPinky(new Pinky(cells[13][15].getX(), cells[13][15].getY(), null, gameTimer, this.getPlayer()));
        this.setInky(new Inky(cells[13][13].getX(), cells[13][13].getY(), null, gameTimer, this.getPlayer()));
        this.setClyde(new Clyde(cells[13][14].getX(), cells[13][14].getY(), null, gameTimer, this.getPlayer()));
        player.setThread(new Thread(this.player));
        blinky.setThread(new Thread(this.blinky));
        pinky.setThread(new Thread(this.pinky));
        inky.setThread(new Thread(this.inky));
        clyde.setThread(new Thread(this.clyde));
        
        markCells(0, 0);
        markCells(0, height - 3);
        markCells(width - 3, 0);
        markCells(width - 3, height - 3);
        markCells(width / 2, height / 2);
        markCells(0, height / 2);
        markCells(width / 2, 0);
        markCells(width - 3, height / 2);
        markCells(width / 2, height - 3);
        
        Font font = new Font("Serif", Font.BOLD, 32);
        timerLabel.setFont(font);
        timerLabel.setBounds(0, height * Cell.length, 200, 40);
        livesLabel = new JLabel("Lives " + String.valueOf(player.getLives()));
        livesLabel.setFont(font);
        livesLabel.setBounds(200, height * Cell.length, 200, 40);
        scoreLabel = new JLabel("Score " + String.valueOf(score));
        scoreLabel.setFont(font);
        scoreLabel.setBounds(400, height * Cell.length, 200, 40);

        add(this.player);
        add(this.blinky);
        add(this.pinky);
        add(this.inky);
        add(this.clyde);
        add(this.timerLabel);
        add(this.livesLabel);
        add(this.scoreLabel);
        for (Cell[] row : cells) {
            for (Cell c : row) add(c);
        }

        timer = new Thread(gameTimer);
        timer.start();
        player.getThread().start();
        blinky.getThread().start();
        pinky.getThread().start();
        inky.getThread().start();
        clyde.getThread().start();

    }

    @Override
    public void run() {
        while(true) {
            if (player.isDead() || dotCount == 0) {
                String nickname = askNickname();
                ScoresMenu.addScore(score, gameTimer.getTotalTime(), nickname, selectedMap);
                break;
            }
            if (doubleTimestamp != -1 && gameTimer.getTotalTime() >= doubleTimestamp) {
                doubleTimestamp = -1;
                scoreMultiplier = 1;
            }
            if (immortalityTimestamp != -1 && gameTimer.getTotalTime() >= immortalityTimestamp) {
                immortalityTimestamp = -1;
                player.setImmortal(false);
            }
            if (speedTimestamp != -1 && gameTimer.getTotalTime() >= speedTimestamp) {
                speedTimestamp = -1;
                player.setSpeed(Ghost.defaultSpeed);
            }
            if (upgradeTimestamp <= gameTimer.getTotalTime()) {
                System.out.println(upgradeTimestamp);
                System.out.println("bum");
                upgradeTimestamp = gameTimer.getTotalTime() + 5;
                Random random = new Random();
                Ghost[] ghosts = new Ghost[]{blinky, pinky, inky, clyde};
                for (Ghost ghost : ghosts) {
                    Cell cell = cells[ghost.getY() / Cell.length][ghost.getX() / Cell.length];
                    if (!cell.isContent() && random.nextDouble() >= 0.25 && cell.getType() != Cell.CellType.GATE) {
                        String[] upgrades = ghost.getUpgrades();
                        if (random.nextDouble() <= Double.parseDouble(upgrades[2])) {
                            cell.setUpgrade(upgrades[0]);
                        } else {
                            cell.setUpgrade(upgrades[1]);
                        }
                        cell.setContent(true);
                        cell.updateContent();
                        break;
                    }
                }
            }
            livesLabel.setText("Lives " + String.valueOf(player.getLives()));
            scoreLabel.setText("Score " + String.valueOf(score));
            timerLabel.setText(gameTimer.toString());
            checkCollisions();
        }
    }

    private synchronized void checkCollisions() {
        Cell cellPlayer = cells[player.getY() / Cell.length][player.getX() / Cell.length];
        Ghost[] ghosts = new Ghost[]{blinky, pinky, inky, clyde}; 

        for (Ghost ghost : ghosts) {
            Cell ghostCell = cells[ghost.getY() / Cell.length][ghost.getX() / Cell.length];

            if (ghostCell == cellPlayer && ghost.isPanic) {
                ghost.setDead(true);
                score += 200 * scoreMultiplier;
            } else if (ghostCell == cellPlayer && !ghost.isDead() && !player.isImmortal()) {
                if (player.getLives() == 0) {
                    for (Ghost g : ghosts) {
                        g.stopThread();
                        gameTimer.stopTimer();
                    }
                }
                player.die();
            }
        }

        if (cellPlayer.isContent()) {
            Cell.CellType type = cellPlayer.getType();
            cellPlayer.setContent(false);
            cellPlayer.updateContent();
            
            String upgrade = cellPlayer.getUpgrade();
            if (upgrade != null) {
                switch (upgrade) {
                    case "food" -> {
                        player.setLives(player.getLives() + 1);
                    }
                    case "speed" -> {
                        player.setSpeed(Ghost.deadSpeed);
                        speedTimestamp = gameTimer.getTotalTime() + 5;
                    }
                    case "double" -> {
                        this.scoreMultiplier = 2;
                        doubleTimestamp = gameTimer.getTotalTime() + 5;
                    }
                    case "immortality" -> {
                        player.setImmortal(true);
                        immortalityTimestamp = gameTimer.getTotalTime() + 10;
                    }
                    case "enrgzr" -> {
                        
                    }
                    default -> throw new AssertionError();
                }
            }
            
            if (type == Cell.CellType.ENERGIZER || (upgrade != null && upgrade.equals("enrgzr"))) {
                for (Ghost ghost : ghosts) {
                    ghost.setPanic(true);
                }
                score += 50 * scoreMultiplier;
            } else {
                score += 10 * scoreMultiplier;
                if (cellPlayer.getUpgrade() == null) dotCount -= 1;
            }
            cellPlayer.setUpgrade(null);
        }
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            this.player.setQueue("l");
        }

        if (key == KeyEvent.VK_RIGHT) {
            this.player.setQueue("r");
        }

        if (key == KeyEvent.VK_UP) {
            this.player.setQueue("u");
        }

        if (key == KeyEvent.VK_DOWN) {
            this.player.setQueue("d");
        }
    }

    private static void markCells(int m, int n) {
        for (int i = n; i < n + 3; i++) {
            for (int j = m; j < m + 3; j++) {
                if (cells[i][j].getType() == Cell.CellType.DOT || cells[i][j].getType() == Cell.CellType.ENERGIZER) {
                    markedCells.add(cells[i][j]);
                    return;
                }
            }
        }
    }

    private void stopGame() {
        Entity[] entities = new Entity[]{player, blinky, pinky, inky, clyde};
        gameTimer.interrupt();
        for (Entity e : entities) {
            e.interrupt();
        }
    }

    private synchronized void resetBoard() {
        level += 1;

        Entity[] entities = new Entity[]{player, blinky, pinky, inky, clyde};

        for (Entity e : entities) {
            e.interrupt();
        }

        Ghost[] ghosts = new Ghost[]{blinky, pinky, inky, clyde};

        for (Ghost ghost : ghosts) {
            ghost.setDead(false);
            ghost.setPanic(false);
            ghost.setIcon(ghost.defaultFirst);
            ghost.setPath(null);
        }

        player.setThread(new Thread(player));
        blinky.setThread(new Thread(blinky));
        pinky.setThread(new Thread(pinky));
        inky.setThread(new Thread(inky));
        clyde.setThread(new Thread(clyde));

        player.setX(cells[23][13].getX());
        player.setY(cells[23][13].getY());
        blinky.setX(cells[11][13].getX());
        blinky.setY(cells[11][13].getY());
        pinky.setX(cells[13][15].getX());
        pinky.setY(cells[13][15].getY());
        inky.setX(cells[13][13].getX());
        inky.setY(cells[13][13].getY());
        clyde.setX(cells[13][14].getX());
        clyde.setY(cells[13][14].getY());

        pinky.setOutside(false);
        inky.setOutside(false);
        clyde.setOutside(false);

        for (Cell[] row : cells) {
            for (Cell cell : row) {
                if (cell.getType() == Cell.CellType.DOT || cell.getType() == Cell.CellType.ENERGIZER) {
                    cell.setContent(true);
                    dotCount += 1;
                }
            }
        }

        for (Entity e : entities) {
            e.getThread().start();
            e.resumeThread();
        }
        gameTimer.resumeTimer();
    }

    private String[][] loadMap(String selectedMap) {
        String filename = "./src/resources/maps/" + selectedMap + ".txt";
        List<String[]> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] cls = line.split("; ");
                lines.add(cls);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        width = lines.get(0).length;
        height = lines.size() - 1;
        String[][] map = new String[height][];
        for (int i = 0; i < height; i++) {
            map[i] = lines.get(i);
        }
        return map;
    }

    private String askNickname() {
        return JOptionPane.showInputDialog(this, "Enter your nickname:", "Nickname", JOptionPane.PLAIN_MESSAGE);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Blinky getBlinky() {
        return blinky;
    }

    public void setBlinky(Blinky blinky) {
        this.blinky = blinky;
    }

    public Pinky getPinky() {
        return pinky;
    }

    public void setPinky(Pinky pinky) {
        this.pinky = pinky;
    }

    public Inky getInky() {
        return inky;
    }

    public void setInky(Inky inky) {
        this.inky = inky;
    }

    public Clyde getClyde() {
        return clyde;
    }

    public void setClyde(Clyde clyde) {
        this.clyde = clyde;
    }

    protected void interrupt() {
        Thread[] threads = new Thread[]{
            player.getThread(),
            blinky.getThread(),
            pinky.getThread(), 
            inky.getThread(), 
            clyde.getThread(), 
            timer
        };

        for (Thread t : threads) {
            t.interrupt();
        }
    }
}
