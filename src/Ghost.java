import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import javax.swing.ImageIcon;

public class Ghost extends Entity implements Runnable {
    protected enum Mode {
        CHASE,
        CORNER,
        ESCAPE,
        PANIC
    }

    protected Player player;
    protected List<Cell> path;
    protected ImageIcon defaultFirst;
    protected ImageIcon defaultSecond;
    protected Mode mode;
    // private String eyesL = ".\\src\\resources\\assets\\eyesL.png";
    // private String eyesR = ".\\src\\resources\\assets\\eyesR.png";
    // private String eyesD = ".\\src\\resources\\assets\\eyesD.png";
    // private String eyesU = ".\\src\\resources\\assets\\eyesU.png";


    public Ghost(int x, int y, Player player) {
        super(x, y);
        System.out.println("ghost");
        this.player = player;
        this.speed = 10;
    }

    protected static class Node {
        Cell cell;
        Node predec;
        int cost;
        int hCost;

        Node(Cell cell, Node predec, int cost, int hCost) {
            this.cell = cell;
            this.predec = predec;
            this.cost = cost;
            this.hCost = hCost;
        }

        @Override
        public String toString() {
            return "Node: " + cell.getX() + ", " + cell.getY();
        }

    }

    @Override
    public void run() {
        while (true) {
            if (getCell(this).getType() == Cell.CellType.GATE) outside = !(outside);
            Cell destination;
            switch (this.mode) {
                case CHASE -> {
                    destination = getCell(this.player);
                }
                case CORNER -> {
                    destination = getCorner();
                }
                case ESCAPE -> {
                    destination = getEscapePoint();
                }
                default -> throw new AssertionError();
            }
            Cell[] optimalPath = getOptimalPath(getCell(this), destination);
            path = pathFinder(optimalPath);
            for (Cell tarCell : path) {
                go(tarCell);
                if (getIcon() == defaultFirst) setIcon(defaultSecond);
                else setIcon(defaultFirst);
            }
        }
    }

    protected void go(Cell tarCell) {
        String direction = getDirection(tarCell);
        int dx = 0;
        int dy = 0;
        switch (direction) {
            case "r" -> {
                if (tarCell.getType() == Cell.CellType.PORTAL && tarCell.getMoveR()){
                    x = tarCell.getX();
                    y = tarCell.getY();
                    setLocation(x, y);
                    return;
                }
                dx = 1; 
            }
            case "l" -> { 
                if (tarCell.getType() == Cell.CellType.PORTAL && tarCell.getMoveL()){
                    x = tarCell.getX();
                    y = tarCell.getY();
                    setLocation(x, y);
                    return;
                }
                dx = -1; 
            }
            case "d" -> { 
                if (tarCell.getType() == Cell.CellType.PORTAL && tarCell.getMoveR()){
                    x = tarCell.getX();
                    y = tarCell.getY();
                    setLocation(x, y);
                    return;
                }
                dy = 1; 
            }
            case "u" -> { 
                if (tarCell.getType() == Cell.CellType.PORTAL && tarCell.getMoveR()){
                    x = tarCell.getX();
                    y = tarCell.getY();
                    setLocation(x, y);
                    return;
                }
                dy = -1; 
            }
        }
        for (int i = 0; i < Cell.length; i++) {
            x += dx;
            y += dy;
            setLocation(x, y);
            try {
                Thread.sleep(speed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected String getDirection(Cell tarCell) {
        String direction = "";
        if (tarCell.getX() - this.getX() > 0) {
            direction = "r";
            if (tarCell.getType() == Cell.CellType.PORTAL && tarCell.getMoveL()) {
                direction = "l";
            }
        }
        else if (tarCell.getX() - this.getX() < 0) {
            direction = "l";
            if (tarCell.getType() == Cell.CellType.PORTAL && tarCell.getMoveR()) {
                direction = "r";
            }
        }
        else if (tarCell.getY() - this.getY() > 0) {
            direction = "d";
            if (tarCell.getType() == Cell.CellType.PORTAL && tarCell.getMoveD()) {
                direction = "u";
            }
        }
        else if (tarCell.getY() - this.getY() < 0) {
            direction = "u";
            if (tarCell.getType() == Cell.CellType.PORTAL && tarCell.getMoveU()) {
                direction = "d";
            }
        }

        return direction;
    }

    protected List<Cell> pathFinder(Cell[] optimalPath) {
        PriorityQueue<Node> pqueue = new PriorityQueue<>(Comparator.comparingInt(node -> node.hCost));
        Map<Cell, Node> nodes = new HashMap<>();
        Cell start = optimalPath[0];
        Cell destination = optimalPath[1];
        Cell portalIn = optimalPath[2];
        Cell portalOut = optimalPath[3];
        System.out.println(start.getX() + ", " + start.getY());
        
        boolean portalPath = (portalIn != null || portalOut != null);
        Cell tmpDest = (portalPath) ? portalIn : destination;
        Node startNode = new Node(start, null, 0, getHeuristic(start, tmpDest));
        System.out.println("init " + startNode);
        pqueue.add(startNode);
        nodes.put(start, startNode);


        while (!pqueue.isEmpty()) {
            Node curr = pqueue.poll();
            System.out.println("curr " + curr);
            
            if (curr.cell.equals(destination)) {
                System.out.println("bam");
                return getRoute(curr);
            }

            if (portalPath && curr.cell.equals(tmpDest)) {
                tmpDest = destination;
                Node neighNode = nodes.getOrDefault(portalOut, new Node(portalOut, null, Integer.MAX_VALUE, 0));
                neighNode.cost = curr.cost + 1;
                neighNode.hCost = neighNode.cost + getHeuristic(portalOut, tmpDest);
                neighNode.predec = curr;

                if (!pqueue.contains(neighNode)) {
                    pqueue.add(neighNode);
                }

                nodes.put(portalOut, neighNode);
            }

            for (Cell neighbour : getNeighbours(curr.cell)) {
                if (neighbour.getType() == Cell.CellType.WALL || (neighbour.getType() == Cell.CellType.GATE && outside)) continue;

                int tempCost = curr.cost + 1;
                Node neighNode = nodes.getOrDefault(neighbour, new Node(neighbour, null, Integer.MAX_VALUE, 0));

                if (tempCost < neighNode.cost) {
                    neighNode.cost = tempCost;
                    neighNode.hCost = tempCost + getHeuristic(neighbour, tmpDest);
                    neighNode.predec = curr;

                    if (!pqueue.contains(neighNode)) {
                        pqueue.add(neighNode);
                        System.out.println(neighNode);
                    }

                    nodes.put(neighbour, neighNode);
                }
            }
        }
        return Collections.emptyList();
    }

    protected int getHeuristic(Cell a, Cell b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }

    protected Cell[] getOptimalPath(Cell start, Cell destination) {
        Cell[] altPath = new Cell[]{start, destination, null, null};
        int heuristic = getHeuristic(getCell(this), getCell(this.player));
        
        for (Cell portalIn : Cell.portals) {
            Cell portalOut = null;
            for (Cell portal : Cell.portals) {
                if (portalIn.getPortal().equals(portal.getPortal()) && portalIn.getX() != portal.getX()) {
                    portalOut = portal;
                }
            }
            int toPortal = getHeuristic(getCell(this), portalIn);
            int fromPortal = getHeuristic(portalOut, getCell(this.player));
            int alternativeHeuristic = toPortal + fromPortal;
            if (heuristic > alternativeHeuristic) {
                altPath[2] = portalIn;
                altPath[3] = portalOut;
            }
        }
        return altPath;
    } 

    protected List<Cell> getRoute(Node node) {
        List<Cell> optimalPath = new ArrayList<>();
        while (node.predec != null) {
            optimalPath.add(node.cell);
            node = node.predec;
        }
        Collections.reverse(optimalPath);
        return optimalPath;
    }

    protected List<Cell> getNeighbours(Cell cell) {
        List<Cell> neighbours = new ArrayList<>();
        int i = cell.getX() / Cell.length;
        int j = cell.getY() / Cell.length;

        if (cell.getMoveL()) neighbours.add(Board.cells[j][i - 1]);
        if (cell.getMoveR()) neighbours.add(Board.cells[j][i + 1]);
        if (cell.getMoveU()) neighbours.add(Board.cells[j - 1][i]);
        if (cell.getMoveD()) neighbours.add(Board.cells[j + 1][i]);

        return neighbours;
    }

    private Cell getCorner() {
        int m = this.player.getX() / Cell.length;
        int n = this.player.getY() / Cell.length;
        Cell cell = getCell(this.player);
        Random random = new Random();

        int randInt = random.nextInt(2);
        switch (randInt) {
            case 0 -> {
                m += (this.player.getX() > Board.cells[0].length / 2) ? -(Board.cells[0].length - this.player.getX()) / Cell.length / 2 : this.player.getX() / Cell.length / 2;
            }
            case 1 -> {
                n += (this.player.getY() > Board.cells.length / 2) ? -(Board.cells.length - this.player.getY()) / Cell.length / 2 : this.player.getY() / Cell.length / 2;
            }
            default -> throw new AssertionError();
        }

        for (int j = n - 1; j < n + 1; j++) {
            for (int i = m - 1; i < m + 1; i++) {
                if (j > 0 && j < Board.cells.length && i > 0 && i < Board.cells[0].length) {
                    if (Board.cells[j][i].getType() != Cell.CellType.WALL || Board.cells[j][i].getType() != Cell.CellType.EMPTY) {
                        cell = Board.cells[j][i];
                    }
                }
            }
        }
        return cell;
    }

    private Cell getEscapePoint() {
        int m = (this.player.getX() > Board.cells[0].length / 2) ? 0 : Board.cells[0].length;
        int n = (this.player.getY() > Board.cells.length / 2) ? 0 : Board.cells.length;
        Cell cell = getCell(this);
        Random random = new Random();

        int randInt = random.nextInt(2);
        switch (randInt) {
            case 0 -> {
                m = this.player.getX();
            }
            case 1 -> {
                n = this.player.getY();
            }
            default -> throw new AssertionError();
        }

        for (int j = n - 1; j < n + 1; j++) {
            for (int i = m - 1; i < m + 1; i++) {
                if (j > 0 && j < Board.cells.length && i > 0 && i < Board.cells[0].length) {
                    if (Board.cells[j][i].getType() != Cell.CellType.WALL || Board.cells[j][i].getType() != Cell.CellType.EMPTY) {
                        cell = Board.cells[j][i];
                    }
                }
            }
        }

        return cell;
    }
}
