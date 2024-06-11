

import javax.swing.SwingUtilities;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            System.out.println("started");
            MainMenu mainMenu = new MainMenu();
            mainMenu.setVisible(true);
        });
    }
}
