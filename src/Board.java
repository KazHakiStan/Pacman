import java.awt.*;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class Board extends JPanel {
    private Player player;
    private Blinky blinky;
    private int width = 28;
    private int height = 31;
    private String[][] board = new String[][]{
        {"lt2ib", "t2b", "t2b", "t2b", "t2b", "t2b", "t2b", "t2b", "t2b", "t2b", "t2b", "t2b", "t2b", "rt2ib", "lt2ib", "t2b", "t2b", "t2b", "t2b", "t2b", "t2b", "t2b", "t2b", "t2b", "t2b", "t2b", "t2b", "rt2ib"},
        {"l2b", "dot r,d", "dot r,l", "dot r,l", "dot r,l", "dot r,l", "dot r,l,d", "dot r,l", "dot r,l", "dot r,l", "dot r,l", "dot r,l", "dot l,d", "r2b", "l2b", "dot r,d", "dot r,l", "dot r,l", "dot r,l", "dot r,l", "dot r,l", "dot r,l,d", "dot r,l", "dot r,l", "dot r,l", "dot r,l", "dot l,d", "r2b"},
        {"l2b", "dot u,d", "rb1b", "b1b", "b1b", "lb1b", "void", "rb1b", "b1b", "b1b", "b1b", "lb1b", "void", "r2b", "l2b", "void", "rb1b", "b1b", "b1b", "b1b", "lb1b", "void", "rb1b", "b1b", "b1b", "lb1b", "void", "r2b"},
        {"l2b", "enrgzr u,d", "r1b", "void", "void", "l1b", "dot u,d", "r1b", "void", "void", "void", "l1b", "dot u,d", "r2b", "l2b", "dot u,d", "r1b", "void", "void", "void", "l1b", "dot u,d", "r1b", "void", "void", "l1b", "enrgzr u,d", "r2b"},
        {"l2b", "dot u,d", "rb1b", "t1b", "t1b", "lt1b", "dot u,d", "rt1b", "t1b", "t1b", "t1b", "lt1b", "dot u,d", "r2b", "l2b", "dot u,d", "rt1b", "t1b", "t1b", "t1b", "lt1b", "dot u,d", "rt1b", "t1b", "t1b", "lt1b", "dot u,d", "r2b"},
        {"l2b", "dot r,u,d", "dot r,l", "dot r,l", "dot r,l", "dot r,l", "dot r,l,u,d", "dot r,l", "dot r,l", "dot r,l,d", "dot r,l", "dot r,l", "dot r,l,u", "dot r,l", "dot r,l", "dot r,l,u", "dot r,l", "dot r,l", "dot r,l,d", "dot r,l", "dot r,l", "dot r,l,u,d", "dot r,l", "dot r,l", "dot r,l", "dot r,l", "dot r,u,d", "r2b"},
        {"l2b", "dot u,d", "rb1b", "b1b", "b1b", "lb1b", "dot u,d", "rb1b", "lb1b", "dot u,d", "rb1b", "b1b", "b1b", "b1b", "b1b", "b1b", "b1b", "lb1b", "dot u,d", "rb1b", "lb1b", "dot u,d", "rb1b", "b1b", "b1b", "lb1b", "dot u,d", "r2b"},
        {"l2b", "dot u,d", "rt1b", "t1b", "t1b", "lt1b", "dot u,d", "r1b", "l1b", "dot u,d", "rt1b", "t1b", "t1b", "rt1ib", "lt1ib", "t1b", "t1b", "lt1b", "dot u,d", "r1b", "l1b", "dot u,d", "rt1b", "t1b", "t1b", "lt1b", "dot u,d", "r2b"},
        {"l2b", "dot r,u", "dot r,l", "dot r,l", "dot r,l", "dot r,l", "dot l,u,d", "r1b", "l1b", "dot r,u", "dot r,l", "dot r,l", "dot l,d", "r1b", "l1b", "dot r,d", "dot r,l", "dot r,l", "dot l,u", "r1b", "l1b", "dot r,u,d", "dot r,l", "dot r,l", "dot r,l", "dot r,l", "dot l,u", "r2b"},
        {"lb2ib", "b2b", "b2b", "b2b", "b2b", "lb2b", "dot u,d", "r1b", "lb1ib", "b1b", "b1b", "lb1b", "void u,d", "r1b", "l1b", "void u,d", "rb1b", "b1b", "b1b", "rb1ib", "l1b", "dot u,d", "rb2b", "b2b", "b2b", "b2b", "b2b", "rb2ib"},
        {"void", "void", "void", "void", "void", "l2b", "dot u,d", "r1b", "lt1ib", "t1b", "t1b", "lt1b", "void u,d", "rt1b", "lt1b", "void u,d", "rt1b", "t1b", "t1b", "rt1ib", "l1b", "dot u,d", "r2b", "void", "void", "void", "void", "void"},
        {"void", "void", "void", "void", "void", "l2b", "dot u,d", "r1b", "l1b", "void r,d", "void r,l", "void r,l", "void r,l,u", "void r,l,d", "void r,l,d", "void r,l,u", "void r,l", "void r,l", "void l,d", "r1b", "l1b", "dot u,d", "r2b", "void", "void", "void", "void", "void"},
        {"void", "void", "void", "void", "void", "l2b", "dot u,d", "r1b", "l1b", "void u,d", "rb2b", "b2b", "b2b", "gate u,d", "gate u,d", "b2b", "b2b", "lb2b", "void u,d", "r1b", "l1b", "dot u,d", "r2b", "void", "void", "void", "void", "void"},
        {"t2b", "t2b", "t2b", "t2b", "t2b", "lt2b", "dot u,d", "rt1b", "lt1b", "void u,d", "r2b", "void r,d", "void r,l,d", "void r,l,u,d", "void r,l,u,d", "void r,l,d", "void l,d", "l2b", "void u,d", "rt1b", "lt1b", "dot u,d", "rt2b", "t2b", "t2b", "t2b", "t2b", "t2b"},
        {"tnl r,l", "tnl r,l", "tnl r,l", "tnl r,l", "tnl r,l", "tnl r,l", "dot r,l,u,d", "void r,l", "void r,l", "void l,u,d", "r2b", "void r,u,d", "void r,l,u,d", "void r,l,u,d", "void r,l,u,d", "void r,l,u,d", "void l,u,d", "l2b", "void u,d", "void l,r", "void l,r", "dot l,r,u,d", "tnl r,l", "tnl r,l", "tnl r,l", "tnl r,l", "tnl r,l", "tnl r,l"},
        {"b2b", "b2b", "b2b", "b2b", "b2b", "lb2b", "dot u,d", "rb1b", "lb1b", "void u,d", "r2b", "void r,u", "void r,l,u", "void r,l,u", "void r,l,u", "void r,l,u", "void l,u", "l2b", "void u,d", "rb1b", "lb1b", "dot u,d", "rb2b", "b2b", "b2b", "b2b", "b2b", "b2b"},
        {"void", "void", "void", "void", "void", "l2b", "dot u,d", "r1b", "l1b", "void u,d", "rt2b", "t2b", "t2b", "t2b", "t2b", "t2b", "t2b", "lt2b", "void u,d", "r1b", "l1b", "dot u,d", "r2b", "void", "void", "void", "void", "void"},
        {"void", "void", "void", "void", "void", "l2b", "dot u,d", "r1b", "l1b", "void u,d", "void r,l", "void r,l", "void r,l", "void r,l", "void r,l", "void r,l", "void r,l", "void r,l", "void l,u,d", "r1b", "l1b", "dot u,d", "r2b", "void", "void", "void", "void", "void"},
        {"void", "void", "void", "void", "void", "l2b", "dot u,d", "r1b", "l1b", "void r,u,d", "rb1b", "b1b", "b1b", "b1b", "b1b", "b1b", "b1b", "lb1b", "void u,d", "r1b", "l1b", "dot u,d", "r2b", "void", "void", "void", "void", "void"},
        {"lt2ib", "t2b", "t2b", "t2b", "t2b", "lt2b", "dot u,d", "rt1b", "lt1b", "void u,d", "rt1b", "t1b", "t1b", "rt1ib", "lt1ib", "t1b", "t1b", "lt1b", "void u,d", "rt1b", "lt1b", "dot u,d", "rt2b", "t2b", "t2b", "t2b", "t2b", "rt2b"},
        {"l2b", "dot r,d", "dot r,l", "dot r,l", "dot r,l", "dot r,l", "dot r,l,u,d", "dot r,l", "dot r,l", "dot r,l,u", "dot r,l", "dot r,l", "dot l,d", "r1b", "l1b", "dot r,d", "dot r,l", "dot r,l", "dot r,l,u", "dot r,l", "dot r,l", "dot r,l,u,d", "dot r,l", "dot r,l", "dot r,l", "dot r,l", "dot l,d", "r2b"},
        {"l2b", "dot u,d", "rb1b", "b1b", "b1b", "lb1b", "dot u,d", "rb1b", "b1b", "b1b", "b1b", "lb1b", "dot u,d", "r1b", "l1b", "dot u,d", "rb1b", "b1b", "b1b", "b1b", "lb1b", "dot u,d", "rb1b", "b1b", "b1b", "lb1b", "dot u,d", "r2b"},
        {"l2b", "dot u,d", "rt1b", "t1b", "rt1ib", "l1b", "dot u,d", "rt1b", "t1b", "t1b", "t1b", "lt1b", "dot u,d", "rt1b", "lt1b", "dot u,d", "rt1b", "t1b", "t1b", "t1b", "lt1b", "dot u,d", "r1b", "lt1ib", "t1b", "lt1b", "dot u,d", "r2b"},
        {"l2b", "enrgzr r,u", "dot r,l", "dot l,d", "r1b", "l1b", "dot r,u,d", "dot r,l", "dot r,l", "dot r,l,d", "dot r,l", "dot r,l", "dot r,l,u", "dot r,l", "dot r,l", "dot r,l,u", "dot r,l", "dot r,l", "dot r,l,d", "dot r,l", "dot r,l", "dot l,u,d", "r1b", "l1b", "dot r,d", "dot r,l", "enrgzr l,u", "r2b"},
        {"lb2ib", "b2b", "lb2b", "dot u,d", "r1b", "l1b", "dot u,d", "rb1b", "lb1b", "dot u,d", "rb1b", "b1b", "b1b", "b1b", "b1b", "b1b", "b1b", "lb1b", "dot u,d", "rb1b", "lb1b", "dot u,d", "r1b", "l1b", "dot u,d", "rb2b", "b2b", "rb2ib"},
        {"lt2ib", "t2b", "lt2b", "dot u,d", "rt1b", "lt1b", "dot u,d", "r1b", "l1b", "dot u,d", "rt1b", "t1b", "t1b", "rt1ib", "lt1ib", "t1b", "t1b", "lt1b", "dot u,d", "r1b", "l1b", "dot u,d", "rt1b", "lt1b", "dot u,d", "rt2b", "t2b", "rt2ib"},
        {"l2b", "dot r,d", "dot r,l", "dot r,l,u", "dot r,l", "dot r,l", "dot l,u", "r1b", "l1b", "dot r,u", "dot r,l", "dot r,l", "dot l,d", "r1b", "l1b", "dot r,d", "dot r,l", "dot r,l", "dot l,u", "r1b", "l1b", "dot r,u", "dot r,l", "dot r,l", "dot r,l,u", "dot r,l", "dot l,d", "r2b"},
        {"l2b", "dot u,d", "rb1b", "b1b", "b1b", "b1b", "b1b", "rb1ib", "lb1ib", "b1b", "b1b", "lb1b", "dot u,d", "r1b", "l1b", "dot u,d", "rb1b", "b1b", "b1b", "rb1ib", "lb1ib", "b1b", "b1b", "b1b", "b1b", "lb1b", "dot u,d", "r2b"},
        {"l2b", "dot u,d", "rt1b", "t1b", "t1b", "t1b", "t1b", "t1b", "t1b", "t1b", "t1b", "lt1b", "dot u,d", "rt1b", "lt1b", "dot u,d", "rt1b", "t1b", "t1b", "t1b", "t1b", "t1b", "t1b", "t1b", "t1b", "lt1b", "dot u,d", "r2b"},
        {"l2b", "dot r,u", "dot r,l", "dot r,l", "dot r,l", "dot r,l", "dot r,l", "dot r,l", "dot r,l", "dot r,l", "dot r,l", "dot r,l", "dot r,l,u", "r2b", "l2b", "dot r,l,u", "dot r,l", "dot r,l", "dot r,l", "dot r,l", "dot r,l", "dot r,l", "dot r,l", "dot r,l", "dot r,l", "dot r,l", "dot l,u", "r2b"},
        {"lt2ib", "b2b", "b2b", "b2b", "b2b", "b2b", "b2b", "b2b", "b2b", "b2b", "b2b", "b2b", "b2b", "b2b", "b2b", "b2b", "b2b", "b2b", "b2b", "b2b", "b2b", "b2b", "b2b", "b2b", "b2b", "b2b", "b2b", "rb2ib"}
    };
    protected Cell[][] cells = new Cell[height][width];

    public Board() {
        initializeBoard();
    }

    private void initializeBoard() {
        setPreferredSize(new Dimension(width * Cell.length, height * Cell.length));
        setBackground(Color.BLACK);
        setLayout(null);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cells[i][j] = new Cell(j * Cell.length, i * Cell.length, board[i][j]);
                add(cells[i][j]);
            }
        }
        this.setPlayer(new Player(400, 400, 5));
        this.setBlinky(new Blinky(100, 100, 1, this.getPlayer()));
        add(this.player);
        add(this.blinky);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                if (!cell.getImagePath().equals("")) {
                    cell.paintComponent(g);
                }
            }
        }
        player.paintComponent(g);
        blinky.paintComponent(g);
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            // System.out.println("left");
            this.player.go(-1, 0);
        }

        if (key == KeyEvent.VK_RIGHT) {
            // System.out.println("right");
            this.player.go(1, 0);
        }

        if (key == KeyEvent.VK_UP) {
            // System.out.println("up");
            this.player.go(0, -1);
        }

        if (key == KeyEvent.VK_DOWN) {
            // System.out.println("down");
            this.player.go(0, 1);
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

    public Cell getCell(Entity e) {
        Cell cell = null;    
    
        for (Cell[] row : cells) {
            for (Cell c : row) {
                Rectangle bounds = c.getBounds();
                Point p = new Point(e.getX(), e.getY());
                if (bounds.contains(p)) cell = c;
            }
        }
        return cell;
    }
}
