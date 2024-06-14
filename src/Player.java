import java.awt.*;
import javax.swing.ImageIcon;

public class Player extends Entity implements Runnable {
    private int lives;
    private String[] queue = new String[2];
    private ImageIcon pacmanClosed = new ImageIcon(
                                        new ImageIcon(".\\src\\resources\\assets\\pacmanClosed.png")
                                            .getImage()
                                            .getScaledInstance(Cell.length, Cell.length, Image.SCALE_SMOOTH));
    private ImageIcon pacmanQuar = new ImageIcon(
                                        new ImageIcon(".\\src\\resources\\assets\\pacmanQuar.png")
                                            .getImage()
                                            .getScaledInstance(Cell.length, Cell.length, Image.SCALE_SMOOTH));
    private ImageIcon pacmanThird = new ImageIcon(
                                        new ImageIcon(".\\src\\resources\\assets\\pacmanThird.png")
                                            .getImage()
                                            .getScaledInstance(Cell.length, Cell.length, Image.SCALE_SMOOTH));
    
    private String pacmanHalf = ".\\src\\resources\\assets\\pacmanHalf.png";
    private String pacmanDyingHalf = ".\\src\\resources\\assets\\pacmanDyingHalf.png";
    private String pacmanDying = ".\\src\\resources\\assets\\pacmanDying.png";
    private String pacmanPoof = ".\\src\\resources\\assets\\pacmanPoof.png";

    public Player(int x, int y) {
        super(x, y);
        this.lives = 3;
        this.speed = 10;
        setIcon(pacmanClosed);
        setBounds(x, y, Cell.length, Cell.length);
    }

    public void go(int dx, int dy, Cell currCell) {
        // System.out.println(this.x + ", " + this.y);
        if (currCell.checkPortal() && validJump(currCell)) {
            x = dx;
            y = dy;
            setLocation(x, y);
            return;
        }
        System.out.println("current on " + x + " and " + y);
        System.out.println("Direction " + this.queue[0] + " alt, " + this.queue[1]);
        // System.out.println("now on " + currCell.getX() + ", " + currCell.getY() + currCell.getType());
        if (((dx == 1 && currCell.getMoveR()) || (dx == -1 && currCell.getMoveL())) && validPosY()) {
            x += dx;
        } else if (((dy == 1 && currCell.getMoveD()) || (dy == -1 && currCell.getMoveU())) && validPosX()) {
            y += dy;
        }
        setLocation(x, y);
    }

    public boolean validJump(Cell currCell) {
        boolean flag = false;
        if (!currCell.getMoveR() && this.queue[0].equals("r")) { flag = true; }
        else if (!currCell.getMoveL() && this.queue[0].equals("l")) { flag = true; }
        else if (!currCell.getMoveD() && this.queue[0].equals("d")) { flag = true; }
        else if (!currCell.getMoveU() && this.queue[0].equals("u")) { flag = true; }
        return flag;
    }

    @Override
    public void run() {
        while (true) {
            Point p;
            Cell currCell = getCell(this);
            // System.out.println("cell x: " + currCell.getX() + ", y: " + currCell.getY());
            if (checkQueue(currCell) && validPosX() && validPosY()) {
                p = getDirCoords(currCell, this.queue[1]);
                this.queue[0] = this.queue[1];
                this.queue[1] = null;
            } else {
                p = getDirCoords(currCell, this.queue[0]);
            }
            go(p.x, p.y, currCell);
            // double angle;
            if (this.x % Cell.length / 2 == 0 && this.y % Cell.length / 2 == 0) {
                // angle = (this.queue[0] != null) ? switch (this.queue[0]) {
                //     case "r" -> 0;
                //     case "l" -> 180;
                //     case "u" -> -90;
                //     default -> 90;
                // } : 0;
                // System.out.println(angle);
                if (getIcon() == pacmanClosed) {
                    // pacmanQuar = rotateImage(pacmanQuar.getImage(), angle);
                    setIcon(pacmanQuar);
                } else if (getIcon() == pacmanQuar) {
                    // pacmanThird = rotateImage(pacmanThird.getImage(), angle);
                    setIcon(pacmanThird);
                } else if (getIcon() == pacmanThird) {
                    // pacmanClosed = rotateImage(pacmanClosed.getImage(), angle);
                    setIcon(pacmanClosed);
                }
            }
            
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean validPosX() {
        return this.getX() % Cell.length == 0;
    }

    public boolean validPosY() {
        return this.getY() % Cell.length == 0;
    }

    public boolean checkQueue(Cell currCell) {
        String direction = this.queue[1];
        if (direction == null) {
            return false;
        }
        boolean flag = false;
        switch (direction) {
            case "r" -> {
                if (currCell.getMoveR()) {
                    flag = true;
                }
            }
            case "l" -> {
                if (currCell.getMoveL()) {
                    flag = true;
                }
            }
            case "u" -> {
                if (currCell.getMoveU()) {
                    flag = true;
                }
            }
            case "d" -> {
                if (currCell.getMoveD()) {
                    flag = true;
                }
            }
        }
        return flag;
    }

    public Point getDirCoords(Cell currCell, String direction) {
        Point p = new Point(0, 0);
        if (direction == null) {
            return p;
        }
        switch (direction) {
            case "r" -> {
                if (!currCell.getMoveR() && currCell.checkPortal() && currCell.getType() != Cell.CellType.GATE) {
                    p = getPortalEnd(currCell);
                } else if (currCell.getMoveR() && (currCell.checkTunnel() || currCell.checkPortal())) {
                    p = new Point(1, 0);
                } else if (currCell.getMoveR()) {
                    p = new Point(1, 0);
                } else {
                    this.queue[0] = this.queue[1];
                    this.queue[1] = null;
                }
            }
            case "l" -> {
                if (!currCell.getMoveL() && currCell.checkPortal()) {
                    p = getPortalEnd(currCell);
                } else if (currCell.getMoveL() && (currCell.checkTunnel() || currCell.checkPortal())) {
                    p = new Point(-1, 0);
                } else if (currCell.getMoveL()) {
                    p = new Point(-1, 0);
                } else {
                    this.queue[0] = this.queue[1];
                    this.queue[1] = null;
                }
            }
            case "u" -> {
                if (!currCell.getMoveU() && currCell.checkPortal()) {
                    p = getPortalEnd(currCell);
                } else if (currCell.getMoveU() && (currCell.checkTunnel() || currCell.checkPortal())) {
                    p = new Point(0, -1);
                } else if (currCell.getMoveU()) {
                    p = new Point(0, -1);
                } else {
                    this.queue[0] = this.queue[1];
                    this.queue[1] = null;
                }
            }
            case "d" -> {
                if (!currCell.getMoveD() && currCell.checkPortal()) {
                    p = getPortalEnd(currCell);
                } else if (currCell.getMoveD() && (currCell.checkTunnel() || currCell.checkPortal())) {
                    p = new Point(0, 1);
                } else if (currCell.getMoveD()) {
                    p = new Point(0, 1);
                } else {
                    this.queue[0] = this.queue[1];
                    this.queue[1] = null;
                }
            }
        }
        return p;
    }

    

    @Override
    public Cell getCell(Entity e) {
        Cell cell = null;    
    
        for (Cell[] row : Board.cells) {
            for (Cell c : row) {
                Rectangle bounds = c.getBounds();
                Point p;
                if (this.queue[0] == null || this.queue[0].equals("r") || this.queue[0].equals("d")) {
                    p = new Point(e.getX(), e.getY());
                } else {
                    p = new Point(e.getX() + Cell.length - 1, e.getY() + Cell.length - 1);
                }
                if (bounds.contains(p)) cell = c;
            }
        }
        return cell;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public String[] getQueue() {
        return this.queue;
    }

    public void setQueue(String direction) {
        if (this.queue[0] == null) {
            this.queue[0] = direction;
        } else {
            this.queue[1] = this.queue[0].equals(direction) ? null : direction;
        }
    }
}
