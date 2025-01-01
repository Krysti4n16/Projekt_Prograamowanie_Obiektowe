import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class GameOver extends JPanel {

    private int score;
    private JFrame frame;
    private JTextField nameField;
    private boolean scoreSaved = false; // Flaga zapisu wyniku

    public GameOver(JFrame frame, int score) {
        this.frame = frame;
        this.score = score;

        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        JLabel message = new JLabel("Game Over", SwingConstants.CENTER);
        message.setFont(new Font("Ink Free", Font.BOLD, 50));
        message.setForeground(Color.RED);

        JLabel scoreLabel = new JLabel("Twój wynik: " + score, SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Ink Free", Font.BOLD, 30));
        scoreLabel.setForeground(Color.WHITE);

        JLabel promptLabel = new JLabel("Wpisz swój nick:", SwingConstants.CENTER);
        promptLabel.setFont(new Font("Ink Free", Font.PLAIN, 20));
        promptLabel.setForeground(Color.WHITE);

        nameField = new JTextField(10);
        nameField.setHorizontalAlignment(JTextField.CENTER);
        nameField.setFont(new Font("Ink Free", Font.PLAIN, 20));

        JButton saveButton = new JButton("Zapisz wynik");
        saveButton.setFont(new Font("Ink Free", Font.BOLD, 20));
        saveButton.addActionListener(e -> saveScore());

        // Panel do wpisywania nicku
        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(Color.BLACK);
        inputPanel.add(promptLabel);
        inputPanel.add(nameField);
        inputPanel.add(saveButton);

        add(message, BorderLayout.NORTH);
        add(scoreLabel, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        // Obsługa klawiszy
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (scoreSaved) { // Opcje działają dopiero po zapisaniu wyniku
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_ESCAPE -> System.exit(0); // Zamyka grę
                        case KeyEvent.VK_R -> restartGame();       // Restart gry
                    }
                }
            }
        });

        // Fokus na pole tekstowe
        nameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (scoreSaved) { // Opcje aktywne po zapisaniu wyniku
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_ESCAPE -> System.exit(0);
                        case KeyEvent.VK_R -> restartGame();
                    }
                }
            }
        });

        // Automatyczne ustawienie fokusu na polu tekstowym
        nameField.requestFocusInWindow();
    }

    private void saveScore() {
        if (!scoreSaved) {
            String name = nameField.getText().trim();

            // Walidacja nicku
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nick nie może być pusty!", "Błąd", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("wyniki.txt", true))) {
                writer.write(name + " " + score);
                writer.newLine();
                scoreSaved = true;

                // Komunikat o zapisaniu wyniku
                JOptionPane.showMessageDialog(this, "Wynik zapisany! Wybierz R, aby zacząć od nowa lub ESC, aby wyjść.", "Sukces", JOptionPane.INFORMATION_MESSAGE);

                // Ustaw fokus na panelu po zapisaniu wyniku
                requestFocusInWindow();

            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Błąd zapisu wyniku!", "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Wynik został już zapisany!", "Informacja", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void restartGame() {
        frame.getContentPane().removeAll();
        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel);
        frame.revalidate();
        frame.repaint();
        gamePanel.requestFocusInWindow(); // Fokus na panelu gry
    }
}
