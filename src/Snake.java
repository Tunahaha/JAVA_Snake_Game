import java.awt.*;
import java.util.ArrayList;

public class Snake {
    private ArrayList<Node> snakeBody;
    public Snake(){
        snakeBody = new ArrayList<Node>();
        snakeBody.add(new Node(80,0));
        snakeBody.add(new Node(60,0));
        snakeBody.add(new Node(40,0));
        snakeBody.add(new Node(20,0));

    }
    public ArrayList<Node> getSnakeBody(){
        return snakeBody;
    }
    public void drawSnake(Graphics g){
        g.setColor(Color.GREEN);
        for(int i=0;i<snakeBody.size();i++){
            if(i==0){
                g.setColor(Color.GREEN);
            }else{
                g.setColor(Color.cyan);
            }
            Node n=snakeBody.get(i);
            if(n.x>=Main.width){
                n.x=0;
            }
            if(n.x<0){
                n.x=Main.width-Main.CELLSIZE;
            }
            if(n.y>=Main.height){
                n.y=0;
            }
            if(n.y<0){
                n.y=Main.height-Main.CELLSIZE;
            }
            g.fillOval(n.x,n.y,Main.CELLSIZE,Main.CELLSIZE);
        }

    }
}

