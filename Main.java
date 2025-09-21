import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new wordleGUI().setVisible(true);
            } catch (IOException e) {
                System.out.println("An error occurred.");
            }
        });
    }
}
