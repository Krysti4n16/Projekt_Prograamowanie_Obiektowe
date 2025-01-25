import java.io.*;
import java.util.List;

public class GameState  implements Serializable
{
    private static final long serialVersionUID = 1L;

    public int[] x;
    public int[] y;
    public int bodyParts;
    public int applesEaten;
    public char direction;
    public List<Food> foods;

    public GameState(int[]x, int[] y, int bodyParts, int applesEaten, char direction, List<Food> foods)
    {
        this.x = x;
        this.y = y;
        this.bodyParts = bodyParts;
        this.applesEaten = applesEaten;
        this.direction = direction;
        this.foods = foods;
    }
}
