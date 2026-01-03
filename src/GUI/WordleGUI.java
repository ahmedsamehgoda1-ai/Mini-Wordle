package GUI;

import java.awt.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;
import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;

public class WordleGUI extends JFrame implements ActionListener {
    private static final int WORD_LENGTH = 5;
    private static final int MAX_ROWS = 6;
    JLabel[][] cells;
    JButton submit;
    JTextField input;
    JPanel gridpanel;
    String correct;
    String guess;
    JPanel actionpanel;
    int currentrow = 0;

    public WordleGUI() throws IOException {
        this.setTitle("Mini-Wordle");
        this.setSize(720, 800);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        //Loading Words process

        ArrayList<String> words = new ArrayList<>();
        try {
            File wordfile = new File("words.txt");
            Scanner scan = new Scanner(wordfile);
            while (scan.hasNextLine()) {
                String data = scan.nextLine().trim();
                words.add(data.toUpperCase());
            }
            scan.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "File not found");

        }
        Random rand = new Random();
        correct = words.get(rand.nextInt(words.size()));

        //Organising the Grid

        gridpanel = new JPanel(new GridLayout(6, 5, 10, 10));
        cells = new JLabel[6][5];
        for (int i = 0; i < MAX_ROWS; i++) {
            for (int j = 0; j < WORD_LENGTH; j++) {
                cells[i][j] = new JLabel("", SwingConstants.CENTER);
                cells[i][j].setBackground(Color.white);
                cells[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                cells[i][j].setFont(new Font("Arial", Font.BOLD, 28));
                cells[i][j].setOpaque(true);
                cells[i][j].setFocusable(false);
                gridpanel.add(cells[i][j]);
            }
        }

        //Input and Submit Buttons

        input = new JTextField();
        submit = new JButton("Submit");
        submit.addActionListener(this);
        actionpanel = new JPanel(new BorderLayout());
        actionpanel.add(input, BorderLayout.CENTER);
        actionpanel.add(submit, BorderLayout.EAST);
        this.add(gridpanel, BorderLayout.CENTER);
        this.add(actionpanel, BorderLayout.SOUTH);

    }

    //Handling Wordle Logic

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {
            guess = input.getText().toUpperCase();
            if (guess.length() != 5) {
                JOptionPane.showMessageDialog(null, "only 5 character-words are allowed", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Map<Character, Integer> correctChars = new HashMap<>();
            for (char c : correct.toCharArray()) {
                correctChars.put(c, correctChars.getOrDefault(c, 0) + 1);
            }
            //First pass(Marking the greens)
            for (int i = 0; i < WORD_LENGTH; i++) {
                char ch = guess.charAt(i);
                cells[currentrow][i].setText(String.valueOf(ch));
                if (ch == correct.charAt(i)) {
                    cells[currentrow][i].setBackground(Color.GREEN);
                    correctChars.put(ch, correctChars.get(ch) - 1);
                }
            }
            //Second pass (Marking the Yellows)
            for (int i = 0; i < WORD_LENGTH; i++) {
                if (cells[currentrow][i].getBackground() != Color.GREEN) {
                    if (correctChars.getOrDefault(guess.charAt(i), 0) > 0) {
                        cells[currentrow][i].setBackground(Color.YELLOW);
                    } else {
                        cells[currentrow][i].setBackground(Color.GRAY);
                    }
                }
            }

            if (guess.equals(correct)) {
                JOptionPane.showMessageDialog(null, "You win!", "WINNER", JOptionPane.INFORMATION_MESSAGE);
                submit.setEnabled(false);
            } else if (currentrow == 5) {
                JOptionPane.showMessageDialog(null, "You lost! Word was: " + correct, "LOSER", JOptionPane.ERROR_MESSAGE);
                submit.setEnabled(false);
            }

            currentrow++;
            input.setText("");

        }

    }

}

