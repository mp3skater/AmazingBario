package net.mp3skater.main.utils;

import net.mp3skater.main.GamePanel;
import net.mp3skater.main.obj.Obj;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Objects;

import static net.mp3skater.main.GamePanel.*;

public class Draw_Utils {
    /*
    Globally available Draw-Methods
     */


    public static void drawTitleScreen(Graphics2D g2) throws IOException, Draw_Utils.BufferedImageGetException {
        // Title Image
        Draw_Utils.drawImage(g2, "/images/Logo.png", new double[]{50,50}, new int[]{750,200});

        // Menu
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));

        String text = "New Game";
        int x = Draw_Utils.getXforCenteredText(g2,text);

        g2.setColor(new Color(78, 88, 78));
        g2.drawString(text,x+3,350+3);

        g2.setColor(new Color(145, 208, 129));
        g2.drawString(text,x,350);

        if(titleNum==0){
            g2.drawString(">",x-25,350);
            g2.setColor(new Color(217, 236, 214));
            g2.drawString(">",x-21,350);
            g2.drawString(text,x,350);
        }

        text = "Highscores";
        x = Draw_Utils.getXforCenteredText(g2,text);

        g2.setColor(new Color(78, 88, 78));
        g2.drawString(text,x+3,425+3);

        g2.setColor(new Color(145, 208, 129));
        g2.drawString(text,x,425);

        if(titleNum==1){
            g2.drawString(">",x-25,425);
            g2.setColor(new Color(217, 236, 214));
            g2.drawString(">",x-21,425);
            g2.drawString(text,x,425);
        }

        text = "Exit Game";
        x = Draw_Utils.getXforCenteredText(g2,text);

        g2.setColor(new Color(78, 88, 78));
        g2.drawString(text,x+3,500+3);

        g2.setColor(new Color(145, 208, 129));
        g2.drawString(text,x,500);

        if(titleNum==2){
            g2.drawString(">",x-25,500);
            g2.setColor(new Color(217, 236, 214));
            g2.drawString(">",x-21,500);
            g2.drawString(text,x,500);
        }
    }

    /*
    Displays the 10 highest scores
     */
    public static void drawHighscores(Graphics2D g2) {
        String path = "res/highscores.txt";


        // Getting the 10 highest scores
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            for(int i = 0; i<10;) {
                String text = reader.readLine(); // Read a new line
                text = (++i)+". "+(text==null? i==10? "---------" : "----------" : text);
                Draw_Utils.drawShadowString(g2,text,48F,297,150+39*i,new Color(144, 196, 144),new Color(78, 88, 78, 255));
            }
        } catch (IOException _) {
            Draw_Utils.drawShadowString(g2,"No scores yet",30,getXforCenteredText(g2, "No scores yet"),200,new Color(144, 196, 144),new Color(78, 88, 78, 255));
        }

        // Back and title
        Draw_Utils.drawShadowString(g2,"> back",48F,65,520,new Color(217, 236, 214),new Color(78, 88, 78, 255));
        Draw_Utils.drawShadowString(g2,"Highscores",48F,getXforCenteredText(g2, "Highscores"),50,new Color(144, 196, 144),new Color(78, 88, 78, 255));
    }

    public static void drawPauseScreen(Graphics2D g2) {

        //TITLE OPTION
        Draw_Utils.drawSubWindow(g2, 250, 75, 300, 450);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 30F));
        g2.setColor(new Color(255, 255, 255, 255));
        String text = "Options";
        int x = Draw_Utils.getXforCenteredText(g2, text);
        int y = 120;
        g2.drawString(text, x, y);


        //Music
        x = 280;
        y += 100;
        g2.drawString("Music", x, y);
        if (pauseNum == 0) {
            g2.drawString(">", x - 15, y);
        }


        //Music-Bar
        g2.setStroke(new BasicStroke(3));
        if (level == 3) {
            g2.setColor(Color.white);
        }else if (level == 4){
            g2.setColor(new Color(70, 98, 138, 255));
        }else {
            g2.setColor(currentLevel.getColor("wall"));
        }
        g2.drawRect(x + 100, y - 25, 120, 22);
        int volumeWidth = 24 * GamePanel.music.volumeScale;
        g2.fillRect(x + 100, y - 25, volumeWidth, 24);
        g2.setColor(new Color(255, 255, 255, 255));


        //SE
        y += 55;
        g2.drawString("SE", x, y);
        if (pauseNum == 1) {
            g2.drawString(">", x - 15, y);
        }
        //SE-Bar
        if (level == 3) {
            g2.setColor(Color.white);
        }else if (level == 4){
            g2.setColor(new Color(70, 98, 138, 255));
        }else{
            g2.setColor(currentLevel.getColor("wall"));
        }
        g2.drawRect(x+100,y-25,120,22);//120/5=24
        volumeWidth = 24*GamePanel.se.volumeScale;
        g2.fillRect(x+100,y-25,volumeWidth,24);
        g2.setColor(new Color(255, 255, 255, 255));


        //Control
        y +=55;
        g2.drawString("Control",x,y);
        if(pauseNum==2){g2.drawString(">",x-15,y);}


        //End Game
        y +=55;
        g2.setColor(new Color(245, 44, 44, 197));
        g2.drawString("End Game",x,y);
        if(pauseNum==3){g2.drawString(">",x-15,y);}
        g2.setColor(new Color(255, 255, 255, 255));


        //Back
        y +=95;
        g2.drawString("Back",x,y);
        if(pauseNum==4){g2.drawString(">",x-15,y);}


    }

    public static void drawOptionControl(Graphics2D g2){
        //TITLE OPTION
        Draw_Utils.drawSubWindow(g2,250,75,300,450);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,30F));
        g2.setColor(new Color(255, 255, 255, 255));
        String text = "Controls";
        int x =Draw_Utils.getXforCenteredText(g2,text);
        int y =120;
        g2.drawString(text,x,y);



        //Move
        x= 280;
        y +=100;
        g2.drawString("Move",x,y);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,20F));
        g2.drawString("WASD",x+145,y);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,30F));


        //Jump
        y +=55;
        g2.drawString("Jump",x,y);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,20F));
        g2.drawString("Space",x+145,y);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,30F));



        //Place
        y +=55;
        g2.drawString("Place",x,y);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,20F));
        g2.drawString("Left Click",x+145,y);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,30F));




        //Pause/Options
        y +=55;
        g2.drawString("Pause",x,y);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,20F));
        g2.drawString("Esc",x+145,y);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,30F));





        //Back
        y +=95;
        g2.drawString("Back",x,y);

            g2.drawString(">",x-15,y);

    }

    public static void drawDeathScreen(Graphics2D g2){

//        if(GamePanel.framesCounter<=60)
//            GamePanel.framesCounter++;
//        else
//            GamePanel.framesCounter=0;

        g2.setColor(new Color(112, 0, 0, 255));
        g2.fillRect(0,0,GamePanel.WIDTH,GamePanel.HEIGHT);
        g2.setColor(new Color(44, 1, 1, 255));
        g2.fillRect(10,10,GamePanel.WIDTH-20,GamePanel.HEIGHT-20);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,125F));

        //GAME OVER text
        String text ="Game Over";
        int x = Draw_Utils.getXforCenteredText(g2,text);

        //Main
        g2.setColor(Color.darkGray);
        g2.drawString(text,x+3,197);

        //Shadow
        g2.setColor(Color.WHITE);
        g2.drawString(text,x,200);


        //Retry
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,50F));
        text = "Press ENTER to RETRY!";
        x=Draw_Utils.getXforCenteredText(g2,text);

        g2.setColor(new Color(220, 86, 86));
        g2.drawString(text,x+3,375+3);

        g2.setColor(new Color(148, 4, 4));
        g2.drawString(text,x,375);

    }

    public static void drawWinScreen(Graphics2D g2){


        g2.setColor(new Color(37, 112, 0, 255));
        g2.fillRect(0,0,GamePanel.WIDTH,GamePanel.HEIGHT);
        g2.setColor(new Color(10, 44, 1, 255));
        g2.fillRect(10,10,GamePanel.WIDTH-20,GamePanel.HEIGHT-20);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,100F));

        //GAME OVER text
        String text ="Congrats!";
        int x = Draw_Utils.getXforCenteredText(g2,text);

        //Main
        g2.setColor(Color.darkGray);
        g2.drawString(text,x+3,100);

        //Shadow
        g2.setColor(Color.WHITE);
        g2.drawString(text,x,100);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD,150F));

        text ="You Won!";
        x = Draw_Utils.getXforCenteredText(g2,text);

        //Main
        g2.setColor(Color.darkGray);
        g2.drawString(text,x+3,250);

        //Shadow
        g2.setColor(Color.WHITE);
        g2.drawString(text,x,250);

        g2.setFont(g2.getFont().deriveFont(Font.BOLD,40F));
        text = "Your time was: "+GamePanel.timeTemp;
        x= Draw_Utils.getXforCenteredText(g2,text);
        g2.drawString(text,x,350);


        //Back to HomeScreen
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,50F));
        text = "Press ENTER for Home";
        x=Draw_Utils.getXforCenteredText(g2,text);

        g2.setColor(new Color(124, 220, 86));
        g2.drawString(text,x+3,500+3);

        g2.setColor(new Color(45, 148, 4));
        g2.drawString(text,x,500);

    }

    public  static void drawSubWindow(Graphics2D g2, int x, int y, int with, int height){

        Color c = new Color(0,0,0,220);
        g2.setColor(c);
        g2.fillRoundRect(x,y,with,height,35,35);

        if (level==3){
            c= Color.white;
        }else if (level == 4){
            c=new Color(70, 98, 138, 255);
        }else {
            c = (currentLevel.getColor("wall"));
        }

        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5,y+5,with-10,height-10,35,35);
    }

    public static void drawShadowString(Graphics2D g2, String text,float textSize, int x, int y, Color colorFront, Color colorShadow){
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,textSize));
        g2.setColor(colorShadow);
        g2.drawString(text,x+3,y+3);
        g2.setColor(colorFront);
        g2.drawString(text, x, y);
    }

    /*
    Draws a rectangle with the given dimensions(<pos>, <size>) and <color>
     */
    public static void fillRect(Graphics2D g2, Obj o, Color color) {
        g2.setColor(color);
        g2.fillRect(o.getDrawX(), (int) o.getY(), (int) o.getSX(), (int) o.getSY());
    }

    /*
    Draws a rectangle with the given dimensions(<pos>, <size>) and <color>
     */
    public static void drawRect(Graphics2D g2, Obj o, Color color) {
        g2.setColor(color);
        g2.drawRect(o.getDrawX(), (int) o.getY(), (int) o.getSX(), (int) o.getSY());
    }

    /*
    Draws an Arrow with fixed direction and size
    It's some complicated maths, magic if you will, don't ask!
     */
    public static void drawArrow(Graphics2D g2, int x, int y, Color color) {
        int length = 200;
        double x2 = x + length;
        int d = (int) (length/3.5), h = (int) (length*0.2);
        int dx = (int)(x2 - x), dy = (int)((double)y - y);
        double D = Math.sqrt(dx*dx + dy*dy);
        double xm = D - d, xn = xm, ym = h, yn = -h, x1;
        double sin = dy / D, cos = dx / D;

        x1 = xm*cos - ym*sin + x;
        ym = xm*sin + ym*cos + y;
        xm = x1;

        x1 = xn*cos - yn*sin + x;
        yn = xn*sin + yn*cos + y;
        xn = x1;

        int[] xpoints = {(int)x2, (int)xm, (int)xn};
        int[] ypoints = {(int) (double)y, (int)ym, (int)yn};

        g2.setColor(color);
        g2.setStroke(new BasicStroke(length/4f));
        g2.drawLine(x+(length/10), y, x+length-d-10, y);
        g2.fillPolygon(xpoints, ypoints, 3);
    }

    public static void drawSpike(Graphics2D g2, int x, int y, int sX, int sY, Color color) {
        Path2D path = new Path2D.Double();

        path.moveTo(x,y+sY); // first point
        path.lineTo(x+sX/2.0, y); // top
        path.lineTo(x+sX, y+sY); // third point
        path.closePath();

        g2.setColor(color);
        g2.fill(path);
        //g2.fillPolygon(new int[]{x, x+(sX/2), x}, new int[]{y+sY, y, y+sY}, 3);
    }

    /*
    Draws the <text>-value of a text inside the game
    Don't forget to set the font/size
     */
    public static void drawString(Graphics2D g2, String text, int[] pos, Color color) {
        g2.setColor(color);
        g2.drawString(text, pos[0], pos[1]);
    }

    /*
    Draws an image. IDK how, but I tried to make it more "protected" using robust logging
     */
    public static void drawImage(Graphics2D g2, String path, Obj o) throws BufferedImageGetException {
        drawImage(g2, path, new double[]{o.getDrawX(), (int) o.getY()}, new int[]{(int) o.getSX(), (int) o.getSY()});
    }
    public static void drawImage(Graphics2D g2, String path, double[] pos, int[] size) throws BufferedImageGetException {
        try {
            BufferedImage image = ImageIO.read(Objects.requireNonNull(Draw_Utils.class.getResourceAsStream(path)));
            drawBufferedImage(g2, image, pos, size);
        }
        catch(IOException e) {
            throw new BufferedImageGetException(e);
        }
    }
    private static void drawBufferedImage(Graphics2D g2, BufferedImage bImage, double[] pos, int[] size) {
        g2.drawImage(bImage, (int)(pos[0]),
                (int)(pos[1]), size[0], size[1], null);
    }

    public static class BufferedImageGetException extends Exception { BufferedImageGetException(Throwable cause)  { super(cause); } }

    public static int getXforCenteredText(Graphics2D g2,String text){
        //Gets the perfect X.Coordinates for the Text position
        int textLength = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        return GamePanel.WIDTH/2 - textLength/2;
    }


}