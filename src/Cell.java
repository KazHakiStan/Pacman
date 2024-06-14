import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class Cell extends JLabel {
    public enum CellType {
        WALL,
        DOT,
        ENERGIZER,
        TUNNEL,
        GATE,
        EMPTY,
        VOID,
        PORTAL
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
    private String portal = null;
    protected static ArrayList<Cell> portals = new ArrayList<>();

    public class Portal {
        private final int x;
        private final int y;
        private final String connector;

        public Portal(int x, int y, String connector) {
            this.x = x;
            this.y = y;
            this.connector = connector;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        public String getConnector() {
            return this.connector;
        }
    }

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
                case "tnl" -> {
                    if (parts.length >= 3) {
                        this.type = CellType.PORTAL;
                        this.portal = parts[2];
                    } else {
                        this.type = CellType.TUNNEL;
                    }
                }
                case "gate" -> this.type = CellType.GATE;
                case "dot" -> {
                    this.type = CellType.DOT;
                    this.imagePath = ".\\src\\resources\\assets\\dot.png";
                    ImageIcon icon = new ImageIcon(this.imagePath);
                    Image img = icon.getImage().getScaledInstance(Cell.length, Cell.length, Image.SCALE_SMOOTH);
                    setIcon(new ImageIcon(img));
                }
                case "enrgzr" -> {
                    this.type = CellType.ENERGIZER;
                    this.imagePath = ".\\src\\resources\\assets\\energizer.png";
                    ImageIcon icon = new ImageIcon(this.imagePath);
                    Image img = icon.getImage().getScaledInstance(Cell.length, Cell.length, Image.SCALE_SMOOTH);
                    setIcon(new ImageIcon(img));
                }
                default -> this.type = CellType.EMPTY;
            }

            String[] dirs = parts[1].split(",");
            for (String dir : dirs) {
                if (dir.equals("r")) this.moveR = true;
                if (dir.equals("l")) this.moveL = true;
                if (dir.equals("u")) this.moveU = true;
                if (dir.equals("d")) this.moveD = true;
            }
        }
        if (!this.imagePath.equals("")) {
            ImageIcon icon = new ImageIcon(this.imagePath);
            Image img = icon.getImage().getScaledInstance(Cell.length, Cell.length, Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(img));
        }
        setBounds(x, y, length, length);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public boolean getMoveR() {
        return this.moveR;
    }

    public boolean getMoveL() {
        return this.moveL;
    }

    public boolean getMoveU() {
        return this.moveU;
    }

    public boolean getMoveD() {
        return this.moveD;
    }

    public CellType getType() {
        return this.type;
    }

    public boolean checkTunnel() {
        return (this.getType() == CellType.TUNNEL);
    }

    public boolean checkPortal() {
        return (this.getType() == CellType.PORTAL);
    }

    public String getImagePath() {
        return this.imagePath;
    }
    
    public Rectangle getBounds() {
        return new Rectangle(this.x, this.y, length, length);
    }

    public String getPortal() {
        return this.portal;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Cell cell = (Cell) obj;
        return x == cell.x && y == cell.y;
    }

    @Override
    public int hashCode() {
        return 31 * x + y;
    }

    // @Override
    // protected void paintComponent(Graphics g) {
    //     super.paintComponent(g);
    //     g.drawImage(this.image, this.x, this.y, length, length, this);
    // }

}
