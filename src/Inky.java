import java.awt.Image;
import javax.swing.ImageIcon;

public class Inky extends Ghost {
    public Inky(int x, int y, Player player) {
        super(x, y, player);
        this.mode = Mode.CORNER;
        this.defaultFirst = new ImageIcon(
            new ImageIcon(".\\src\\resources\\assets\\inky1.png")
                .getImage()
                .getScaledInstance(Cell.length, Cell.length, Image.SCALE_SMOOTH));
        this.defaultSecond = new ImageIcon(
            new ImageIcon(".\\src\\resources\\assets\\inky2.png")
                .getImage()
                .getScaledInstance(Cell.length, Cell.length, Image.SCALE_SMOOTH));
        setIcon(defaultFirst);
        setBounds(x, y, Cell.length, Cell.length);
        setLocation(x, y);
        outside = false;
    }
}
