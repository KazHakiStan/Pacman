
import java.awt.Image;
import javax.swing.ImageIcon;

public class Blinky extends Ghost {
    public Blinky(int x, int y, Thread thread, GameTimer timer, Player player) {
        super(x, y, thread, timer, player);
        this.mode = Mode.CHASE;
        this.defaultFirst = new ImageIcon(
            new ImageIcon(".\\src\\resources\\assets\\blinky1.png")
                .getImage()
                .getScaledInstance(Cell.length, Cell.length, Image.SCALE_SMOOTH));
        this.defaultSecond = new ImageIcon(
            new ImageIcon(".\\src\\resources\\assets\\blinky2.png")
                .getImage()
                .getScaledInstance(Cell.length, Cell.length, Image.SCALE_SMOOTH));

        upgrades = new String[]{"speed", "food", "0.5"};
        setIcon(defaultFirst);
        setBounds(x, y, Cell.length, Cell.length);
        setLocation(x, y);
    }
}
