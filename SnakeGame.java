import javax.swing.*;

public class SnakeGame {
    public static void main(String[] args){
        //new GameFrame();
        JFrame frame = new JFrame("Snake - Menu");
        MainMenu menu = new MainMenu(frame);

        frame.add(menu);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
