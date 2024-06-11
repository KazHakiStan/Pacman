import java.awt.*;

public class Player extends Entity {
    private int lives;

    public Player(int x, int y, int speed) {
        super(x, y, speed);
        this.lives = 3;
        setBounds(x, y, 30, 30);
    }

    public void go(int dx, int dy) {
        // System.out.println("current on " + x + " and " + y);
        x += dx * speed;
        y += dy * speed;
        setBounds(x, y, 30, 30);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        // System.out.println("painted" + x + " and " + y);
        super.paintComponent(g);
        g.setColor(Color.YELLOW);
        g.fillOval(x, y, 30, 30);
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }
}
