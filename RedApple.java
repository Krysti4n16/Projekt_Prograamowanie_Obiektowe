import java.awt.*;

public class RedApple extends Food {
    public RedApple(int x, int y) {
        super(x, y);
    }

    @Override
    public void applyEffect(GamePanel gamePanel) {
        gamePanel.bodyParts++;
        gamePanel.applesEaten++;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.red);
        g.fillOval(x, y, SIZE, SIZE);
    }
}
