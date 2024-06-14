import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public abstract class Entity extends JLabel {
    protected int x;
    protected int y;
    protected int speed;
    protected boolean outside = true;

    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public Cell getCell(Entity e) {
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

    public ImageIcon rotateImage(Image image, double angle) {
        BufferedImage rotated = new BufferedImage(Cell.length, Cell.length, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotated.createGraphics();

        AffineTransform at = new AffineTransform();
        at.rotate(angle, Cell.length / 2, Cell.length / 2);
        g2d.setTransform(at);
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        return new ImageIcon(rotated);
    }
}
