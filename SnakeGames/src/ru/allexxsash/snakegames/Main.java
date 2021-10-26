package ru.allexxsash.snakegames;
import javax.swing.*;

public class Main extends JFrame{

    public Main(){
        setTitle("Змейка");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(320,360);
        setLocation(400,400);
        add(new GameField());
        setVisible(true);
    }

    public static void main(String[] args) {
        Main mw = new Main();
    }


}
