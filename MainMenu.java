import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MainMenu extends JPanel implements ActionListener {

    private JFrame frame;
    private String[] options = {"Kontynuuj grę!", "Graj", "Zasady gry", "Wyniki", "Wyjście"};
    private int currentOption = 0;

    public MainMenu(JFrame frame) {
        this.frame = frame;
        this.setFocusable(true);
        this.addKeyListener(new MenuKeyAdapter());
        this.setBackground(Color.BLACK);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawMenu(g);
    }

    private void drawMenu(Graphics g) {
        g.setColor(Color.GREEN);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics = getFontMetrics(g.getFont());

        for (int i = 0; i < options.length; i++) {
            if (i == currentOption) {
                g.setColor(Color.YELLOW);
            } else {
                g.setColor(Color.WHITE);
            }
            g.drawString(options[i], (getWidth() - metrics.stringWidth(options[i])) / 2, 150 + i * 50);
        }
    }

    private void selectOption() {
        switch (currentOption) {
            case 0 -> continueGame();
            case 1 -> startGame();
            case 2 -> showRules();
            case 3 -> showScores();
            case 4 -> System.exit(0);
        }
    }

    private void startGame() {
        GamePanel gamePanel = new GamePanel();
        frame.getContentPane().removeAll();
        frame.add(gamePanel);
        frame.revalidate();
        frame.repaint();
        gamePanel.requestFocusInWindow();
    }

    public void continueGame()
    {
        GamePanel gamePanel = new GamePanel();
        frame.getContentPane().removeAll();
        frame.add(gamePanel);
        frame.revalidate();
        frame.repaint();
        gamePanel.loadGame();
        gamePanel.requestFocusInWindow();

    }
    private void showRules() {
        JTextArea rulesArea = new JTextArea(
                "Zasady gry Snake:\n\n" +
                        "1. Steruj wężem za pomocą strzałek.\n" +
                        "2. Zbieraj czerwone jabłka, aby zdobyć punkty i urosnąć.\n" +
                        "3. Unikaj kolizji ze ścianami oraz własnym ciałem.\n\n" +
                        "Naciśnij ESC, aby powrócić do menu."
        );
        rulesArea.setEditable(false);
        showTextScreen("Zasady Gry", rulesArea);
    }

    private void showScores() {
        JTextArea scoresArea = new JTextArea();
        scoresArea.setEditable(false);

        try (BufferedReader reader = new BufferedReader(new FileReader("wyniki.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                scoresArea.append(line + "\n");
            }
        } catch (IOException e) {
            scoresArea.setText("Brak wyników.\n");
        }

        scoresArea.append("\nNaciśnij ESC, aby powrócić do menu.");
        showTextScreen("Wyniki", scoresArea);
    }

    private void showTextScreen(String title, JTextArea textArea) {
        JFrame textFrame = new JFrame(title);
        textFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        textFrame.setSize(400, 300);
        textFrame.setLocationRelativeTo(null);

        textArea.setMargin(new Insets(10, 10, 10, 10));
        textFrame.add(new JScrollPane(textArea));
        textFrame.setVisible(true);

        // Obsługa klawisza ESC
        textFrame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    textFrame.dispose(); // Zamknięcie okna
                }
            }
        });

        // Dodanie fokusu na okno, aby ESC działał
        textFrame.requestFocus();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    private class MenuKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP -> {
                    currentOption = (currentOption - 1 + options.length) % options.length;
                    repaint();
                }
                case KeyEvent.VK_DOWN -> {
                    currentOption = (currentOption + 1) % options.length;
                    repaint();
                }
                case KeyEvent.VK_ENTER -> selectOption();
            }
        }
    }
}
