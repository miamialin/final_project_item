import de.looksgood.ani.Ani;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.Random;

public class Main extends PApplet {

    public static void main(String[] args) {
        PApplet.main("Main", args);
    }
    int num;
    Random random = new Random();

    public ArrayList<Ball> balls = new ArrayList<>();
    public ArrayList<Item> items = new ArrayList<>();
    public ArrayList<PImage> itemsImg =  new ArrayList<>();
    @Override
    public void setup() {

        Ani.init(this);
        num = 3;

        //calculate the position of the balls
        //you can assign by yourself
        for (int i = 0; i < num; i++) {
            int x = (i % 5 * 120) + 60;
            int y = i / 5 * 200 + 100;
            println("" + x + "," + y);
            int radius = 40;
            Ball b = new Ball(this, x, y, radius);
            balls.add(b);
        }

        itemsImg.add(loadImage("bin/Mushroom.png"));
        itemsImg.add(loadImage("bin/pill.png"));
        itemsImg.add(loadImage("bin/spiral.png"));
        itemsImg.add(loadImage("bin/wing.png"));
        itemsImg.add(loadImage("bin/question mark.png"));

        for (int i = 0; i < 5; i++) {
            int x = (i % 5 * 120) + 60;
            int y = i / 5 * 200 + 300;
            println("" + x + "," + y);
            int radius = 30;
            int mRandom = random.nextInt(4);

            Item t = new Item(this, itemsImg.get(4), itemsImg.get(mRandom), mRandom,x, y, radius);
            items.add(t);
        }

    }

    @Override
    public void draw() {
        background(51);
        for (Ball b : balls) {
            //physics calculation
            b.update();
            b.checkBoundaryCollision();
            //draw ball
            b.display();
        }

        for (Item t : items) {
            t.display();
        }
        int i, j;
        for (i = 0; i < balls.size(); i++) {
            for (j = 0; j < items.size(); j++) {
                if(balls.get(i).checkCollisionItem(items.get(j))) {
                    int n = random.nextInt(2) + 1;
                    if(items.get(j).getToolid() == 1) balls.get((i + n)%3).addItem(items.get(j));
                    else balls.get(i).addItem(items.get(j));
                }
            }
        }
    }
    @Override
    public void mousePressed() {
        println("mouse pressed");
        //TODO iterate through the balls and check if the mouse is inside the ball
        for (Ball b : balls) {
            if(b.checkMouseClicked()) break;
        }
    }
    @Override
    public void keyPressed() {
        switch (key)
        {
            case 'w': balls.get(0).setVelocity(0, -2); break;
            case 'a':  balls.get(0).setVelocity(-2, 0); break;
            case 's':  balls.get(0).setVelocity(0, 2); break;
            case 'd':  balls.get(0).setVelocity(2, 0); break;
            default:
        }
    }
    @Override
    public void keyReleased() {
        switch (key)
        {
            case 'w': balls.get(0).setVelocity(0, 0); break;
            case 'a':  balls.get(0).setVelocity(0, 0); break;
            case 's':  balls.get(0).setVelocity(0, 0); break;
            case 'd':  balls.get(0).setVelocity(0, 0); break;
            default:
        }
    }

    @Override
    public void settings() {
        super.settings();
        size(720, 480);
    }

}

