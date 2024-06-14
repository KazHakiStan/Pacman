import java.awt.*;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class Board extends JPanel {
    private Player player;
    private Blinky blinky;
    private Pinky pinky;
    private Inky inky;
    private Clyde clyde;
    private int width = 28;
    private int height = 31;
    private String[][] board = new String[][]{
        {"rb2b", "t2b", "t2b", "t2b", "t2b", "t2b", "t2b", "t2b", "t2b", "t2b", "t2b", "t2b", "t2b", "lb2b", "rb2b", "t2b", "t2b", "t2b", "t2b", "t2b", "t2b", "t2b", "t2b", "t2b", "t2b", "t2b", "t2b", "lb2b"},
        {"l2b", "dot r,d", "dot r,l", "dot r,l", "dot r,l", "dot r,l", "dot r,l,d", "dot r,l", "dot r,l", "dot r,l", "dot r,l", "dot r,l", "dot l,d", "r2b", "l2b", "dot r,d", "dot r,l", "dot r,l", "dot r,l", "dot r,l", "dot r,l", "dot r,l,d", "dot r,l", "dot r,l", "dot r,l", "dot r,l", "dot l,d", "r2b"},
        {"l2b", "dot u,d", "rb1b", "b1b", "b1b", "lb1b", "dot u,d", "rb1b", "b1b", "b1b", "b1b", "lb1b", "dot u,d", "r2b", "l2b", "dot u,d", "rb1b", "b1b", "b1b", "b1b", "lb1b", "dot u,d", "rb1b", "b1b", "b1b", "lb1b", "dot u,d", "r2b"},
        {"l2b", "enrgzr u,d", "r1b", "void", "void", "l1b", "dot u,d", "r1b", "void", "void", "void", "l1b", "dot u,d", "r2b", "l2b", "dot u,d", "r1b", "void", "void", "void", "l1b", "dot u,d", "r1b", "void", "void", "l1b", "enrgzr u,d", "r2b"},
        {"l2b", "dot u,d", "rt1b", "t1b", "t1b", "lt1b", "dot u,d", "rt1b", "t1b", "t1b", "t1b", "lt1b", "dot u,d", "rt2b", "lt2b", "dot u,d", "rt1b", "t1b", "t1b", "t1b", "lt1b", "dot u,d", "rt1b", "t1b", "t1b", "lt1b", "dot u,d", "r2b"},
        {"l2b", "dot r,u,d", "dot r,l", "dot r,l", "dot r,l", "dot r,l", "dot r,l,u,d", "dot r,l", "dot r,l", "dot r,l,d", "dot r,l", "dot r,l", "dot r,l,u", "dot r,l", "dot r,l", "dot r,l,u", "dot r,l", "dot r,l", "dot r,l,d", "dot r,l", "dot r,l", "dot r,l,u,d", "dot r,l", "dot r,l", "dot r,l", "dot r,l", "dot r,u,d", "r2b"},
        {"l2b", "dot u,d", "rb1b", "b1b", "b1b", "lb1b", "dot u,d", "rb1b", "lb1b", "dot u,d", "rb1b", "b1b", "b1b", "b1b", "b1b", "b1b", "b1b", "lb1b", "dot u,d", "rb1b", "lb1b", "dot u,d", "rb1b", "b1b", "b1b", "lb1b", "dot u,d", "r2b"},
        {"l2b", "dot u,d", "rt1b", "t1b", "t1b", "lt1b", "dot u,d", "r1b", "l1b", "dot u,d", "rt1b", "t1b", "t1b", "rt1ib", "lt1ib", "t1b", "t1b", "lt1b", "dot u,d", "r1b", "l1b", "dot u,d", "rt1b", "t1b", "t1b", "lt1b", "dot u,d", "r2b"},
        {"l2b", "dot r,u", "dot r,l", "dot r,l", "dot r,l", "dot r,l", "dot l,u,d", "r1b", "l1b", "dot r,u", "dot r,l", "dot r,l", "dot l,d", "r1b", "l1b", "dot r,d", "dot r,l", "dot r,l", "dot l,u", "r1b", "l1b", "dot r,u,d", "dot r,l", "dot r,l", "dot r,l", "dot r,l", "dot l,u", "r2b"},
        {"rt2b", "b2b", "b2b", "b2b", "b2b", "lb2b", "dot u,d", "r1b", "lb1ib", "b1b", "b1b", "lb1b", "void u,d", "r1b", "l1b", "void u,d", "rb1b", "b1b", "b1b", "rb1ib", "l1b", "dot u,d", "rb2b", "b2b", "b2b", "b2b", "b2b", "lt2b"},
        {"void", "void", "void", "void", "void", "l2b", "dot u,d", "r1b", "lt1ib", "t1b", "t1b", "lt1b", "void u,d", "rt1b", "lt1b", "void u,d", "rt1b", "t1b", "t1b", "rt1ib", "l1b", "dot u,d", "r2b", "void", "void", "void", "void", "void"},
        {"void", "void", "void", "void", "void", "l2b", "dot u,d", "r1b", "l1b", "void r,d", "void r,l", "void r,l", "void r,l,u", "void r,l,d", "void r,l,d", "void r,l,u", "void r,l", "void r,l", "void l,d", "r1b", "l1b", "dot u,d", "r2b", "void", "void", "void", "void", "void"},
        {"void", "void", "void", "void", "void", "l2b", "dot u,d", "r1b", "l1b", "void u,d", "rb2b", "b2b", "b2b", "gate r,u,d", "gate l,u,d", "b2b", "b2b", "lb2b", "void u,d", "r1b", "l1b", "dot u,d", "r2b", "void", "void", "void", "void", "void"},
        {"t2b", "t2b", "t2b", "t2b", "t2b", "lt2b", "dot u,d", "rt1b", "lt1b", "void u,d", "r2b", "void r,d", "void r,l,d", "void r,l,u,d", "void r,l,u,d", "void r,l,d", "void l,d", "l2b", "void u,d", "rt1b", "lt1b", "dot u,d", "rt2b", "t2b", "t2b", "t2b", "t2b", "t2b"},
        {"tnl r j0", "tnl r,l", "tnl r,l", "tnl r,l", "tnl r,l", "tnl r,l", "dot r,l,u,d", "void r,l", "void r,l", "void l,u,d", "r2b", "void r,u,d", "void r,l,u,d", "void r,l,u,d", "void r,l,u,d", "void r,l,u,d", "void l,u,d", "l2b", "void r,u,d", "void l,r", "void l,r", "dot l,r,u,d", "tnl r,l", "tnl r,l", "tnl r,l", "tnl r,l", "tnl r,l", "tnl l j0"},
        {"b2b", "b2b", "b2b", "b2b", "b2b", "lb2b", "dot u,d", "rb1b", "lb1b", "void u,d", "r2b", "void r,u", "void r,l,u", "void r,l,u", "void r,l,u", "void r,l,u", "void l,u", "l2b", "void u,d", "rb1b", "lb1b", "dot u,d", "rb2b", "b2b", "b2b", "b2b", "b2b", "b2b"},
        {"void", "void", "void", "void", "void", "l2b", "dot u,d", "r1b", "l1b", "void u,d", "rt2b", "t2b", "t2b", "t2b", "t2b", "t2b", "t2b", "lt2b", "void u,d", "r1b", "l1b", "dot u,d", "r2b", "void", "void", "void", "void", "void"},
        {"void", "void", "void", "void", "void", "l2b", "dot u,d", "r1b", "l1b", "void r,u,d", "void r,l", "void r,l", "void r,l", "void r,l", "void r,l", "void r,l", "void r,l", "void r,l", "void l,u,d", "r1b", "l1b", "dot u,d", "r2b", "void", "void", "void", "void", "void"},
        {"void", "void", "void", "void", "void", "l2b", "dot u,d", "r1b", "l1b", "void u,d", "rb1b", "b1b", "b1b", "b1b", "b1b", "b1b", "b1b", "lb1b", "void u,d", "r1b", "l1b", "dot u,d", "r2b", "void", "void", "void", "void", "void"},
        {"rb2b", "t2b", "t2b", "t2b", "t2b", "lt2b", "dot u,d", "rt1b", "lt1b", "void u,d", "rt1b", "t1b", "t1b", "rt1ib", "lt1ib", "t1b", "t1b", "lt1b", "void u,d", "rt1b", "lt1b", "dot u,d", "rt2b", "t2b", "t2b", "t2b", "t2b", "lb2b"},
        {"l2b", "dot r,d", "dot r,l", "dot r,l", "dot r,l", "dot r,l", "dot r,l,u,d", "dot r,l", "dot r,l", "dot r,l,u", "dot r,l", "dot r,l", "dot l,d", "r1b", "l1b", "dot r,d", "dot r,l", "dot r,l", "dot r,l,u", "dot r,l", "dot r,l", "dot r,l,u,d", "dot r,l", "dot r,l", "dot r,l", "dot r,l", "dot l,d", "r2b"},
        {"l2b", "dot u,d", "rb1b", "b1b", "b1b", "lb1b", "dot u,d", "rb1b", "b1b", "b1b", "b1b", "lb1b", "dot u,d", "r1b", "l1b", "dot u,d", "rb1b", "b1b", "b1b", "b1b", "lb1b", "dot u,d", "rb1b", "b1b", "b1b", "lb1b", "dot u,d", "r2b"},
        {"l2b", "dot u,d", "rt1b", "t1b", "rt1ib", "l1b", "dot u,d", "rt1b", "t1b", "t1b", "t1b", "lt1b", "dot u,d", "rt1b", "lt1b", "dot u,d", "rt1b", "t1b", "t1b", "t1b", "lt1b", "dot u,d", "r1b", "lt1ib", "t1b", "lt1b", "dot u,d", "r2b"},
        {"l2b", "enrgzr r,u", "dot r,l", "dot l,d", "r1b", "l1b", "dot r,u,d", "dot r,l", "dot r,l", "dot r,l,d", "dot r,l", "dot r,l", "dot r,l,u", "dot r,l", "dot r,l", "dot r,l,u", "dot r,l", "dot r,l", "dot r,l,d", "dot r,l", "dot r,l", "dot l,u,d", "r1b", "l1b", "dot r,d", "dot r,l", "enrgzr l,u", "r2b"},
        {"rt2b", "b2b", "lb2b", "dot u,d", "r1b", "l1b", "dot u,d", "rb1b", "lb1b", "dot u,d", "rb1b", "b1b", "b1b", "b1b", "b1b", "b1b", "b1b", "lb1b", "dot u,d", "rb1b", "lb1b", "dot u,d", "r1b", "l1b", "dot u,d", "rb2b", "b2b", "lt2b"},
        {"rb2b", "t2b", "lt2b", "dot u,d", "rt1b", "lt1b", "dot u,d", "r1b", "l1b", "dot u,d", "rt1b", "t1b", "t1b", "rt1ib", "lt1ib", "t1b", "t1b", "lt1b", "dot u,d", "r1b", "l1b", "dot u,d", "rt1b", "lt1b", "dot u,d", "rt2b", "t2b", "lb2b"},
        {"l2b", "dot r,d", "dot r,l", "dot r,l,u", "dot r,l", "dot r,l", "dot l,u", "r1b", "l1b", "dot r,u", "dot r,l", "dot r,l", "dot l,d", "r1b", "l1b", "dot r,d", "dot r,l", "dot r,l", "dot l,u", "r1b", "l1b", "dot r,u", "dot r,l", "dot r,l", "dot r,l,u", "dot r,l", "dot l,d", "r2b"},
        {"l2b", "dot u,d", "rb1b", "b1b", "b1b", "b1b", "b1b", "rb1ib", "lb1ib", "b1b", "b1b", "lb1b", "dot u,d", "r1b", "l1b", "dot u,d", "rb1b", "b1b", "b1b", "rb1ib", "lb1ib", "b1b", "b1b", "b1b", "b1b", "lb1b", "dot u,d", "r2b"},
        {"l2b", "dot u,d", "rt1b", "t1b", "t1b", "t1b", "t1b", "t1b", "t1b", "t1b", "t1b", "lt1b", "dot u,d", "rt1b", "lt1b", "dot u,d", "rt1b", "t1b", "t1b", "t1b", "t1b", "t1b", "t1b", "t1b", "t1b", "lt1b", "dot u,d", "r2b"},
        {"l2b", "dot r,u", "dot r,l", "dot r,l", "dot r,l", "dot r,l", "dot r,l", "dot r,l", "dot r,l", "dot r,l", "dot r,l", "dot r,l", "dot r,l,u", "dot r,l", "dot r,l", "dot r,l,u", "dot r,l", "dot r,l", "dot r,l", "dot r,l", "dot r,l", "dot r,l", "dot r,l", "dot r,l", "dot r,l", "dot r,l", "dot l,u", "r2b"},
        {"rt2b", "b2b", "b2b", "b2b", "b2b", "b2b", "b2b", "b2b", "b2b", "b2b", "b2b", "b2b", "b2b", "b2b", "b2b", "b2b", "b2b", "b2b", "b2b", "b2b", "b2b", "b2b", "b2b", "b2b", "b2b", "b2b", "b2b", "lt2b"}
    };
    protected static Cell[][] cells;

    public Board() {
        initializeBoard();
    }

    private void initializeBoard() {
        setPreferredSize(new Dimension(width * Cell.length, height * Cell.length));
        setBackground(Color.BLACK);
        setLayout(null);
        cells = new Cell[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cells[i][j] = new Cell(j * Cell.length, i * Cell.length, board[i][j]);
                if (cells[i][j].getType() == Cell.CellType.PORTAL) { Cell.portals.add(cells[i][j]); }
                // add(cells[i][j]);
            }
        }
        this.setPlayer(new Player(cells[23][13].getX(), cells[23][13].getY()));
        this.setBlinky(new Blinky(cells[11][13].getX(), cells[11][13].getY(), this.getPlayer()));
        this.setPinky(new Pinky(cells[13][15].getX(), cells[13][15].getY(), this.getPlayer()));
        this.setInky(new Inky(cells[13][13].getX(), cells[13][13].getY(), this.getPlayer()));
        this.setClyde(new Clyde(cells[13][14].getX(), cells[13][14].getY(), this.getPlayer()));
        add(this.player);
        add(this.blinky);
        add(this.pinky);
        add(this.inky);
        add(this.clyde);
        new Thread(this.player).start();
        new Thread(this.blinky).start();
        new Thread(this.pinky).start();
        new Thread(this.inky).start();
        new Thread(this.clyde).start();
        for (Cell[] row : cells) {
            for (Cell c : row) add(c);
        }
    }

    // @Override
    // protected void paintComponent(Graphics g) {
    //     super.paintComponent(g);
    //     for (Cell[] row : cells) {
    //         for (Cell cell : row) {
    //             if (!cell.getImagePath().equals("")) {
    //                 cell.paintComponent(g);
    //             }
    //         }
    //     }
    //     player.paintComponent(g);
    //     blinky.paintComponent(g);
    // }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            // System.out.println("left");
            this.player.setQueue("l");
        }

        if (key == KeyEvent.VK_RIGHT) {
            // System.out.println("right");
            this.player.setQueue("r");
        }

        if (key == KeyEvent.VK_UP) {
            // System.out.println("up");
            this.player.setQueue("u");
        }

        if (key == KeyEvent.VK_DOWN) {
            // System.out.println("down");
            this.player.setQueue("d");
        }
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

    
}
