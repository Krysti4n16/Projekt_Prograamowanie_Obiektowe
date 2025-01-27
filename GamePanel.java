import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH=600;
    static final int SCREEN_HEIGHT=600;
    static final int UNIT_SIZE=25;
    static final int GAME_UNITS= (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY =75;
     static final int FOOD_SPAWN_DELAY = 2000;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts=6;
    int applesEaten=0;
    /*
    int appleX;
    int appleY; CHYBA DO WYRZUCENIA BO NIE UZYWAMY NIGDZIE!
    */
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Timer spawnowanieJedzenia;
    Random random;
    List<Food> foods;
    Food currentFood; // Polimorficzne pole dla jedzenia




    GamePanel(){
        random= new Random();
        foods = new ArrayList<>();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame(){
        newFood();
        running=true;
        timer= new Timer(DELAY, this);
        timer.start();

        spawnowanieJedzenia = new Timer(FOOD_SPAWN_DELAY, e -> spawnFoodIfNeeded());
        spawnowanieJedzenia.start();
    }
    private static Color snakeColor = Color.GREEN;

    public static void setSnakeColor(Color color) {
        snakeColor = color;
    }


    public void newFood()
    {
        addFood();
    }

    public void spawnFoodIfNeeded()
    {
        int maxFoodCount = 8;
        boolean allBlueApples = foods.stream().allMatch(food -> food instanceof BlueApple);

        if(foods.size() < maxFoodCount || allBlueApples)
        {
            addFood();
        }
    }

    public void addFood()
    {
        int x = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        int y = random.nextInt((int) (SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;

        if (random.nextBoolean())
        {
            foods.add(new RedApple(x, y));
        }
        else
        {
            foods.add(new BlueApple(x, y));
        }
    }

    public void checkApple()
    {
        for(int i =0; i< foods.size(); i++)
        {
            Food food = foods.get(i);
            if((x[0] == food.x) && (y[0] == food.y))
            {
                food.applyEffect(this);
                foods.remove(i);
                break;
            }
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (running) {
            for(Food food : foods)
            {
                food.draw(g);
            }

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(snakeColor);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(snakeColor.darker());
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD,40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten,(SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten))/2,g.getFont().getSize());
        }
        else{
            gameOver(g);
        }
    }

    public void move(){
        for(int i=bodyParts;i>0;i--){
            x[i]=x[i-1];
            y[i]=y[i-1];
        }
        switch(direction){
            case 'U':
                y[0]=y[0]-UNIT_SIZE;
                break;
            case 'D':
                y[0]=y[0]+UNIT_SIZE;
                break;
            case 'L':
                x[0]=x[0]-UNIT_SIZE;
                break;
            case 'R':
                x[0]=x[0]+UNIT_SIZE;
                break;
        }
    }

    public void checkCollisions(){
        //collisions head with body
        for(int i=bodyParts;i>0;i--){
            if((x[0]==x[i]) && (y[0]==y[i])){
                running=false;
            }
        }
        //collisions head with lef border
        if(x[0]<0){
            running=false;
        }
        //collisions head with right border
        if(x[0]>SCREEN_WIDTH){
            running=false;
        }
        //collisions head with top border
        if(y[0]<0){
            running=false;
        }
        //collisions head with bottom border
        if(y[0]>SCREEN_HEIGHT){
            running=false;
        }

        if(!running){
            timer.stop();
        }


    }

    public void gameOver(Graphics g) {
        timer.stop();

        JFrame gameOverFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        gameOverFrame.getContentPane().removeAll();
        gameOverFrame.add(new GameOver(gameOverFrame, applesEaten));
        gameOverFrame.revalidate();
        gameOverFrame.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter{

        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction != 'R'){
                        direction='L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L'){
                        direction='R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D'){
                        direction='U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U'){
                        direction='D';
                    }
                    break;
                case KeyEvent.VK_S : saveGame(); //przycisk S do save'ovania gry
            }
        }
    }

    public void saveGame() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("savegame.ser"))) {
            GameState gameState = new GameState(x, y, bodyParts, applesEaten, direction, foods);
            oos.writeObject(gameState);
            System.out.println("Gra została zapisana.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Błąd podczas zapisywania gry.");
        }
    }

    public void loadGame() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("savegame.ser"))) {
            GameState gameState = (GameState) ois.readObject();

            // Przywrócenie stanu gry
            System.arraycopy(gameState.x, 0, x, 0, gameState.x.length);
            System.arraycopy(gameState.y, 0, y, 0, gameState.y.length);
            bodyParts = gameState.bodyParts;
            applesEaten = gameState.applesEaten;
            direction = gameState.direction;
            foods = gameState.foods;

            running = true; // Wznów grę
            timer.start();
            System.out.println("Gra została wczytana.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Błąd podczas wczytywania gry.");
        }
    }


}
