package net.mp3skater.main.io;

import net.mp3skater.main.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    /*
    This class handles the key presses and stores them in global variables
     */

    public static boolean pausePressed, aPressed, dPressed, spacePressed, zeroPressed, onePressed,
            twoPressed, threePressed, fourPressed, upPressed, downPressed, leftPressed, rightPressed;

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if(GamePanel.titleState){
            if(keyCode == KeyEvent.VK_W||keyCode == KeyEvent.VK_UP) {
                GamePanel.comandNum--;
                if (GamePanel.comandNum < 0) {
                    GamePanel.comandNum = 2;
                }
            }
            if(keyCode == KeyEvent.VK_S||keyCode == KeyEvent.VK_DOWN) {
                GamePanel.comandNum++;
                if (GamePanel.comandNum > 2) {
                    GamePanel.comandNum = 0;
                }
            }
            if(keyCode ==KeyEvent.VK_ENTER){
                if(GamePanel.comandNum==0){
                    GamePanel.titleState=false;
                }
                if(GamePanel.comandNum==1){
                    //Open Records File
                }
                if(GamePanel.comandNum==2)
                    System.exit(0);
            }
        }

        // Dev tools
        if(keyCode == KeyEvent.VK_0)
            zeroPressed = true;
        if(keyCode == KeyEvent.VK_1)
            onePressed = true;
        if(keyCode == KeyEvent.VK_2)
            twoPressed = true;
        if(keyCode == KeyEvent.VK_3)
            threePressed = true;
        if(keyCode == KeyEvent.VK_4)
            fourPressed = true;
        if(keyCode == KeyEvent.VK_UP)
            upPressed = true;
        if(keyCode == KeyEvent.VK_DOWN)
            downPressed = true;
        if(keyCode == KeyEvent.VK_LEFT)
            leftPressed = true;
        if(keyCode == KeyEvent.VK_RIGHT)
            rightPressed = true;

        // For the start of the game
        if(keyCode == KeyEvent.VK_ESCAPE || keyCode == KeyEvent.VK_ENTER)
            pausePressed = true;
        if(keyCode == KeyEvent.VK_A)
            aPressed = true;
        // Allow also H instead of d because of workman compatibility
        if(keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_H)
            dPressed = true;
        if(keyCode == KeyEvent.VK_SPACE)
            spacePressed = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        // Dev tools
        if(keyCode == KeyEvent.VK_0)
            zeroPressed = false;
        if(keyCode == KeyEvent.VK_1)
            onePressed = false;
        if(keyCode == KeyEvent.VK_2)
            twoPressed = false;
        if(keyCode == KeyEvent.VK_3)
            threePressed = false;
        if(keyCode == KeyEvent.VK_4)
            fourPressed = false;
        if(keyCode == KeyEvent.VK_UP)
            upPressed = false;
        if(keyCode == KeyEvent.VK_DOWN)
            downPressed = false;
        if(keyCode == KeyEvent.VK_LEFT)
            leftPressed = false;
        if(keyCode == KeyEvent.VK_RIGHT)
            rightPressed = false;

        if(keyCode == KeyEvent.VK_ESCAPE || keyCode == KeyEvent.VK_ENTER)
            pausePressed = false;
        if(keyCode == KeyEvent.VK_A)
            aPressed = false;
        if(keyCode == KeyEvent.VK_D || keyCode == KeyEvent.VK_H)
            dPressed = false;
        if(keyCode == KeyEvent.VK_SPACE)
            spacePressed = false;
    }
    @Override
    public void keyTyped(KeyEvent e) {}
}