import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;
public class Main extends JPanel implements KeyListener {
    public static final int CELLSIZE=20;
    public static int width=400;
    public static int height=400;
    public static int row=height/CELLSIZE;
    public static int column=width/CELLSIZE;
    private Snake snake;
    private Fruit fruit;
    private Timer t;
    private int speed=100;
    private static String direction;
    private boolean allowKeyPress;
    private int score;
    private int highest_score;
    String desktop=System.getProperty("user.home")+"/Desktop/";
    String myFile=desktop+"filename.txt";
    public Main() {
        read_highest_score();
        reset();
        addKeyListener(this);
    }
    private void setTimer(){
        t=new Timer();
        t.scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run() {
                repaint();
            }
        },0,speed);
    }
    private void reset(){
        score=0;
        if(snake!=null){
            snake.getSnakeBody().clear();
        }
        allowKeyPress=true;
        direction="Right";
        snake=new Snake();
        fruit=new Fruit();
        setTimer();
    }

    @Override
    public void paintComponent(Graphics g){
        ArrayList<Node>snake_body=snake.getSnakeBody();
        Node head=snake_body.get(0);
        for(int i =1;i<snake_body.size();i++){
            if(snake_body.get(i).x==head.x && snake_body.get(i).y==head.y){
                    allowKeyPress=false;
                    t.cancel();
                    t.purge();
                    int response=JOptionPane.showOptionDialog(this,"你輸了，你的分數是"+score+"最高分是"+highest_score+"要再來一場嗎?","Game Over",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE,null,null,JOptionPane.YES_OPTION);
                    write_a_file(score);
                    switch (response){
                        case JOptionPane.CLOSED_OPTION:
                            System.exit(0);
                            break;
                        case JOptionPane.NO_OPTION:
                            System.exit(0);
                            break;
                        case JOptionPane.YES_OPTION:
                            reset();
                            return;
                    }
            }
        }



        g.fillRect(0,0,width,height);
        fruit.drawFruit(g);
        snake.drawSnake(g);


        //刪除尾巴放在頭
        int snakeX=snake.getSnakeBody().get(0).x;
        int snakeY=snake.getSnakeBody().get(0).y;
        if(direction.equals("Left")){
            snakeX-=CELLSIZE;
        } else if (direction.equals("Up")) {
            snakeY-=CELLSIZE;
        }else if (direction.equals("Right")) {
            snakeX+=CELLSIZE;
        }else if (direction.equals("Down")) {
            snakeY+=CELLSIZE;
        }
        Node newHead = new Node(snakeX, snakeY);

        if(snake.getSnakeBody().get(0).x==fruit.getX() && snake.getSnakeBody().get(0).y== fruit.getY()){
            fruit.setNewLocation(snake);
            fruit.drawFruit(g);
            score++;
        }else {
            snake.getSnakeBody().remove(snake.getSnakeBody().size()-1);
        }


        snake.getSnakeBody().add(0,newHead);
        allowKeyPress=true;
        requestFocusInWindow();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);

    }

    public static void main(String[] args) {
        JFrame window=new JFrame("This is a new Snake Game");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setContentPane(new Main());
        window.pack();
        window.setLocationRelativeTo(null) ;
        window.setVisible(true);
        window.setResizable(false);

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(allowKeyPress){
            if(e.getKeyCode() == KeyEvent.VK_LEFT && !direction.equals("Right")){
                direction="Left";
            }else if(e.getKeyCode() == KeyEvent.VK_UP && !direction.equals("Down")){
                direction="Up";
            }else if(e.getKeyCode()==KeyEvent.VK_RIGHT && !direction.equals("Left")){
                direction="Right";
            }else if(e.getKeyCode()==KeyEvent.VK_DOWN && !direction.equals("Up")){
                direction="Down";
            }
            allowKeyPress=false;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    public void read_highest_score(){
        try{
            File myObl=new File(myFile);
            Scanner myReader=new Scanner(myObl);
            highest_score=myReader.nextInt();
            myReader.close();
        }catch (FileNotFoundException e){
            highest_score=0;
            try {
                File myObl=new File(myFile);
                if (myObl.createNewFile()){
                    System.out.println("File created"+myObl.getName());
                }
                FileWriter myWriter=new FileWriter(myObl.getName());
                myWriter.write(""+0);
            }catch (IOException err){
                System.out.println("發生錯誤拉");
                err.printStackTrace();
            }
            }
        }

    public void write_a_file(int score){
        try{
            FileWriter myWriter=new FileWriter(myFile);
            if(score>highest_score){
                myWriter.write(""+score);
                highest_score=score;
            }else {
                myWriter.write(""+highest_score);
            }
            myWriter.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}