package Main;

import GUI.WordleGUI;

import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new WordleGUI().setVisible(true);
            } catch (IOException e) {
                System.out.println("An error occurred.");
            }
        });
    }
}
