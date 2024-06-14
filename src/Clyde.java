import java.awt.Image;
import javax.swing.ImageIcon;

public class Clyde extends Ghost {
    public Clyde(int x, int y, Player player) {
        super(x, y, player);
        this.mode = Mode.ESCAPE;
        this.defaultFirst = new ImageIcon(
            new ImageIcon(".\\src\\resources\\assets\\clyde1.png")
                .getImage()
                .getScaledInstance(Cell.length, Cell.length, Image.SCALE_SMOOTH));
        this.defaultSecond = new ImageIcon(
            new ImageIcon(".\\src\\resources\\assets\\clyde2.png")
                .getImage()
                .getScaledInstance(Cell.length, Cell.length, Image.SCALE_SMOOTH));
        setIcon(defaultFirst);
        setBounds(x, y, Cell.length, Cell.length);
        setLocation(x, y);
        outside = false;
    }

    @Override
    public void run() {
        super.run();
        this.mode = (this.mode == Mode.CHASE) ? Mode.ESCAPE : Mode.CHASE;
    }
    
    
}
