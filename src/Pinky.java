import java.awt.Image;
import javax.swing.ImageIcon;

public class Pinky extends Ghost {
    public Pinky(int x, int y, Player player) {
        super(x, y, player);
        this.mode = Mode.CORNER;
        this.defaultFirst = new ImageIcon(
            new ImageIcon(".\\src\\resources\\assets\\pinky1.png")
                .getImage()
                .getScaledInstance(Cell.length, Cell.length, Image.SCALE_SMOOTH));
        this.defaultSecond = new ImageIcon(
            new ImageIcon(".\\src\\resources\\assets\\pinky2.png")
                .getImage()
                .getScaledInstance(Cell.length, Cell.length, Image.SCALE_SMOOTH));
        setIcon(defaultFirst);
        setBounds(x, y, Cell.length, Cell.length);
        setLocation(x, y);
        outside = false;
    }
}
