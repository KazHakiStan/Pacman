
import java.awt.*;
import javax.swing.JLabel;

public abstract class Entity extends JLabel {
    protected volatile boolean interrupted = false;
    protected volatile int x;
    protected volatile int y;
    protected boolean outside = true;
    protected Thread thread;
    protected GameTimer timer;
    protected volatile boolean running = true;
    protected final static int defaultSpeed = 5;
    protected int speed = defaultSpeed;

    public Entity(int x, int y, Thread thread, GameTimer timer) {
        this.x = x;
        this.y = y;
        this.thread = thread;
        this.timer = timer;
    }

    @Override
    public synchronized int getX() {
        return x;
    }

    public synchronized void setX(int x) {
        this.x = x;
    }

    @Override
    public synchronized int getY() {
        return y;
    }

    public synchronized void setY(int y) {
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public synchronized Cell getCell(Entity e) {
        // Cell cell = null;    
    
        // for (Cell[] row : Board.cells) {
        //     for (Cell c : row) {
        //         Rectangle bounds = c.getBounds();
        //         Point p = new Point(e.getX(), e.getY());
        //         if (bounds.contains(p)) cell = c;
        //     }
        // }
        return Board.cells[e.getY() / Cell.length][e.getX() / Cell.length];
    }

    public Point getPortalEnd(Cell portalIn) {
        Point p = null;
        for (Cell portalOut : Cell.portals) {
            if (portalIn.getX() != portalOut.getX() && portalIn.getPortal().equals(portalOut.getPortal())) {
                p = new Point(portalOut.getX(), portalOut.getY());
            }
        }
        return p;
    }

    // public ImageIcon rotateImage(Image image, double angle) {
    //     BufferedImage rotated = new BufferedImage(Cell.length, Cell.length, BufferedImage.TYPE_INT_ARGB);
    //     Graphics2D g2d = rotated.createGraphics();

    //     AffineTransform at = new AffineTransform();
    //     at.rotate(angle, Cell.length / 2, Cell.length / 2);
    //     g2d.setTransform(at);
    //     g2d.drawImage(image, 0, 0, null);
    //     g2d.dispose();

    //     return new ImageIcon(rotated);
    // }

    protected synchronized boolean isOutside() {
        return this.outside;
    }

    protected synchronized void setOutside(boolean outside) {
        this.outside = outside;
    }
    
    protected Thread getThread() {
        return thread;
    }

    protected void setThread(Thread thread) {
        this.thread = thread;
    }

    protected void stopThread() {
        running = false;
    }

    protected void resumeThread() {
        running = true;
    }

    protected void interrupt() {
        this.interrupted = true;
        this.stopThread();
    }
}
