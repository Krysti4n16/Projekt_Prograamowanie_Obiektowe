import java.awt.*;

public class RedApple extends Food {
    public RedApple(int x, int y) {
        super(x, y);
    }

    @Override
    public void applyEffect(GamePanel gamePanel) {
        gamePanel.bodyParts++; // Powiększa węża
        gamePanel.applesEaten++;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.red); // Czerwony kolor
        g.fillOval(x, y, SIZE, SIZE);
    }
}
