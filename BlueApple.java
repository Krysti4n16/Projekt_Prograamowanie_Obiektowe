import java.awt.*;

public class BlueApple extends Food {
    public BlueApple(int x, int y) {
        super(x, y);
    }

    @Override
    public void applyEffect(GamePanel gamePanel) {
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
