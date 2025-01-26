import java.awt.*;
import java.io.Serializable;

public class BlueApple extends Food implements Serializable {
    public BlueApple(int x, int y) {
        super(x, y);
    }

    @Override
    public void applyEffect(GamePanel gamePanel) {
        // Efekt niebieskiego jabłka: zmniejszenie długości węża (Wymaganie 6: Dziedziczenie i interfejsy)
        if (gamePanel.bodyParts > 1) {
            gamePanel.bodyParts--;
            gamePanel.applesEaten--; // Możesz zmienić logikę, jeśli niebieskie jabłka mają inaczej wpływać na wynik
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.blue);
        g.fillOval(x, y, SIZE, SIZE);
    }
}
