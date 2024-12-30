import java.awt.*;

public class BlueApple extends Food {
    public BlueApple(int x, int y) {
        super(x, y);
    }

    @Override
    public void applyEffect(GamePanel gamePanel) {
        if (gamePanel.bodyParts > 1) { // Minimalna długość węża = 1
            gamePanel.bodyParts--; // Zmniejsza węża
            gamePanel.applesEaten--; // do zastanoweinia czy jesli niebskiekie to wynik sie zmnjesza czy powieksza lub pozostaje bez zmian!!!
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.blue); // Niebieski kolor
        g.fillOval(x, y, SIZE, SIZE);
    }
}
