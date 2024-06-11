import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JComponent;


public class Cell extends JComponent {
    public enum CellType {
        WALL,
        DOT,
        ENERGIZER,
        TUNNEL,
        GATE,
        EMPTY,
        VOID,
    }

    protected static final int length = 20;
    private final int x;
    private final int y;
    private final CellType type;
    private String imagePath = "";
    private Image image = null;
    private boolean moveU = false;
    private boolean moveD = false;
    private boolean moveL = false;
    private boolean moveR = false;

    public Cell(int x, int y, String type) {
        this.x = x;
        this.y = y;
        String[] parts = type.split(" ");
        if (parts.length == 1) {
            if (!parts[0].equals("void")) {
                this.type = CellType.WALL;
                this.imagePath = ".\\src\\resources\\assets\\" + parts[0] + ".png";
                try {
                    this.image = ImageIO.read(new File(this.imagePath));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                this.type = CellType.VOID;
            }
        } else {
            switch (parts[0]) {
                case "tnl":
                    this.type = CellType.TUNNEL;
                    break;
                case "gate":
                    this.type = CellType.GATE;
                    break;
                case "dot":
                    this.type = CellType.DOT;
                    break;
                case "enrgzr":
                    this.type = CellType.ENERGIZER;
                    break;
                default:
                    this.type = CellType.EMPTY;
                    break;
            }

            String[] dirs = parts[1].split(",");
            for (String dir : dirs) {
                if (dir.equals("r")) this.moveR = true;
                if (dir.equals("l")) this.moveL = true;
                if (dir.equals("u")) this.moveU = true;
                if (dir.equals("d")) this.moveD = true;
            }
        }
        
        setBounds(x, y, length, length);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public CellType getType() {
        return this.type;
    }

    public String getImagePath() {
        return this.imagePath;
    }
    
    public Rectangle getBounds() {
        return new Rectangle(this.x, this.y, length, length);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.image, this.x, this.y, length, length, this);
    }

}
