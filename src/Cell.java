
import java.awt.*;
import java.util.ArrayList;
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
        PORTAL,
        SPAWN
    }

    protected static final int length = 20;
    private final int x;
    private final int y;
    private final CellType type;
    private String imagePath = "";
    private boolean isContent = false;
    private boolean moveU = false;
    private boolean moveD = false;
    private boolean moveL = false;
    private boolean moveR = false;
    private String portal = null;
    protected static ArrayList<Cell> portals = new ArrayList<>();
    protected String upgrade = null;
    protected ImageIcon doubleMult = new ImageIcon(
        new ImageIcon(".\\src\\resources\\assets\\double.png")
            .getImage()
            .getScaledInstance(Cell.length, Cell.length, Image.SCALE_SMOOTH));
    protected ImageIcon immortality = new ImageIcon(
        new ImageIcon(".\\src\\resources\\assets\\immortality.png")
            .getImage()
            .getScaledInstance(Cell.length, Cell.length, Image.SCALE_SMOOTH));
    protected ImageIcon speed = new ImageIcon(
        new ImageIcon(".\\src\\resources\\assets\\speed.png")
            .getImage()
            .getScaledInstance(Cell.length, Cell.length, Image.SCALE_SMOOTH));
    protected ImageIcon food = new ImageIcon(
        new ImageIcon(".\\src\\resources\\assets\\food.png")
            .getImage()
            .getScaledInstance(Cell.length, Cell.length, Image.SCALE_SMOOTH));
    protected ImageIcon enrgzr = new ImageIcon(
        new ImageIcon(".\\src\\resources\\assets\\energizer.png")
            .getImage()
            .getScaledInstance(Cell.length, Cell.length, Image.SCALE_SMOOTH));
    protected ImageIcon dot = new ImageIcon(
        new ImageIcon(".\\src\\resources\\assets\\dot2.png")
            .getImage()
            .getScaledInstance(Cell.length, Cell.length, Image.SCALE_SMOOTH));

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
                    this.imagePath = ".\\src\\resources\\assets\\dot2.png";
                    ImageIcon icon = new ImageIcon(this.imagePath);
                    Image img = icon.getImage().getScaledInstance(Cell.length, Cell.length, Image.SCALE_SMOOTH);
                    setIcon(new ImageIcon(img));
                    this.isContent = true;
                }
                case "enrgzr" -> {
                    this.type = CellType.ENERGIZER;
                    this.imagePath = ".\\src\\resources\\assets\\energizer.png";
                    ImageIcon icon = new ImageIcon(this.imagePath);
                    Image img = icon.getImage().getScaledInstance(Cell.length, Cell.length, Image.SCALE_SMOOTH);
                    setIcon(new ImageIcon(img));
                    this.isContent = true;
                }
                case "spawn" -> {
                    this.type = CellType.SPAWN;
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

    @Override
    public int getX() {
        return this.x;
    }

    @Override
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

    protected synchronized boolean isContent() {
        return this.isContent;
    }

    protected synchronized void setContent(boolean isContent) {
        this.isContent = isContent;
    }

    protected synchronized void updateContent() {
        if (!isContent) {
            setIcon(null);
        } else {
            switch (this.upgrade) {
                case "double" -> {
                    setIcon(doubleMult);
                }
                case "speed" -> {
                    setIcon(speed);
                }
                case "immortality" -> {
                    setIcon(immortality);
                }
                case "food" -> {
                    setIcon(food);
                }
                case "enrgzr" -> {
                    setIcon(enrgzr);
                }
                default -> setIcon(null);
            }
        }
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
    
    @Override
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

    protected synchronized String getUpgrade() {
        return this.upgrade;
    }

    protected synchronized void setUpgrade(String upgrade) {
        this.upgrade = upgrade;
    }
}
