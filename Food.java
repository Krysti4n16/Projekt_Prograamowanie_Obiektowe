import java.awt.*;

public class Food {
    int x, y; // Pozycja jedzenia
    static final int SIZE = GamePanel.UNIT_SIZE; // Rozmiar jedzenia

    // Konstruktor
    public Food(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Efekt jedzenia (do nadpisania)
    public void applyEffect(GamePanel gamePanel) {
        // Domyślnie nic nie robi
    }

    // Rysowanie jedzenia (do nadpisania)
    public void draw(Graphics g) {
        g.setColor(Color.white); // Domyślnie białe
        g.fillOval(x, y, SIZE, SIZE);
    }
}
