import java.awt.*;
import java.io.Serializable;

public class RedApple extends Food implements Serializable {
    public RedApple(int x, int y) {
        super(x, y);
    }

    @Override
    public void applyEffect(GamePanel gamePanel) {
        // Efekt czerwonego jabłka: zwiększenie długości węża (Wymaganie: Dziedziczenie i interfejsy)
        gamePanel.bodyParts++;
        gamePanel.applesEaten++;
    }

    @Override
    public void draw(Graphics g) {
        // Rysowanie czerwonego jabłka (Wymaganie: GUI w języku Java)
        g.setColor(Color.red);
        g.fillOval(x, y, SIZE, SIZE);
    }
}
