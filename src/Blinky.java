import java.awt.Image;
import javax.swing.ImageIcon;

public class Blinky extends Ghost {
    public Blinky(int x, int y, Player player) {
        super(x, y, player);
        this.mode = Mode.CHASE;
        System.out.println("blinky");
        this.defaultFirst = new ImageIcon(
            new ImageIcon(".\\src\\resources\\assets\\blinky1.png")
                .getImage()
                .getScaledInstance(Cell.length, Cell.length, Image.SCALE_SMOOTH));
        this.defaultSecond = new ImageIcon(
            new ImageIcon(".\\src\\resources\\assets\\blinky2.png")
                .getImage()
                .getScaledInstance(Cell.length, Cell.length, Image.SCALE_SMOOTH));
        setIcon(defaultFirst);
        setBounds(x, y, Cell.length, Cell.length);
        setLocation(x, y);
    }
}
