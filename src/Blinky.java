import java.awt.*;

public class Blinky extends Entity implements Runnable {
    private final Player player;

    public Blinky(int x, int y, int speed, Player player) {
        super(x, y, speed);
        this.player = player;
        setBounds(x, y, 30, 30);
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (true) { 
            go();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void go() {
        pathFinder();
        // System.out.println("blinky " + x + " and " + y);
        setBounds(x, y, 30, 30);
        repaint();
    }

    private void pathFinder() {
        int tar_x = this.player.getX();
        int tar_y = this.player.getY();
        
        if (Math.abs(tar_x - x) > Math.abs(tar_y - y)) {
            if (tar_x - x > 0) {
                x += speed;
            } else {
                x -= speed;
            }
        } else {
            if (tar_y - y > 0) {
                y += speed;
            } else {
                y -= speed;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.fillOval(x, y, 30, 30);
    }
}
