package net.mp3skater.main.utils;

import net.mp3skater.main.GamePanel;
import net.mp3skater.main.io.KeyHandler;

import java.awt.event.KeyEvent;

import static net.mp3skater.main.GamePanel.loadLevel;
import static net.mp3skater.main.io.KeyHandler.*;
import static net.mp3skater.main.utils.Sound_Utils.playSE;
import static net.mp3skater.main.utils.Sound_Utils.stopMusic;

public class Menu_Utils {
    public static void update() {

        /*
        Start of the Game, Titlescreen
         */
        if(GamePanel.titleState){
            // Up
            if(wPressed || upPressed) {
                GamePanel.titleNum--;
                playSE(8);
                if(GamePanel.titleNum < 0) GamePanel.titleNum = 2;
            }
            // Down
            if(sPressed || downPressed) {
                GamePanel.titleNum++;
                playSE(8);
                if(GamePanel.titleNum > 2) GamePanel.titleNum = 0;
            }
            // Confirm choice
            if(enterPressed){
                playSE(9);
                if(GamePanel.comandNum==0) GamePanel.titleState=false; // Start game
                if(GamePanel.titleNum==1) GamePanel.comandNum=0; // In future: open highscore file
                if(GamePanel.titleNum==2) System.exit(0); // Close Game
            }
        }

        /*
        View Controls
         */
        if(GamePanel.controlState) if(enterPressed) GamePanel.controlState = false;

        /*
        Pause screen
         */
        if(GamePanel.isPause) {
            // Up
            if(wPressed || upPressed) {
                GamePanel.pauseNum--;
                playSE(8);
                if(GamePanel.pauseNum < 0) GamePanel.pauseNum = 4;
            }
            // Down
            if(sPressed || downPressed) {
                GamePanel.pauseNum++;
                playSE(8);
                if(GamePanel.pauseNum > 4) GamePanel.pauseNum = 0;
            }
            // Music volume
            if(GamePanel.pauseNum == 0){
                // Up
                if(aPressed && GamePanel.music.volumeScale>0){
                    GamePanel.music.volumeScale--;
                    GamePanel.music.checkVolume();
                    playSE(8);
                }
                // Down
                if(dPressed && GamePanel.music.volumeScale<5){
                    GamePanel.music.volumeScale++;
                    GamePanel.music.checkVolume();
                    playSE(8);
                }
            }
            // SE volume
            if(GamePanel.pauseNum==1){
                // Up
                if(aPressed && GamePanel.se.volumeScale>0){
                    GamePanel.se.volumeScale--;
                    playSE(8);
                }
                // Down
                if(dPressed && GamePanel.se.volumeScale<5){
                    GamePanel.se.volumeScale++;
                    playSE(8);
                }
            }
            // Confirm choice
            if(enterPressed) {
                // Controls
                if(GamePanel.pauseNum == 2) GamePanel.controlState=true;
                //
                if(GamePanel.pauseNum == 3) {
                    GamePanel.gameOver(false);
                    GamePanel.titleState=true;
                }
                if(GamePanel.pauseNum == 4) {
                    GamePanel.isPause=false;
                    GamePanel.exPause=true;
                }
            }
        }



        if(GamePanel.deathState) {
//            if(keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_UP) {
//                GamePanel.comandNum--;
//                if(GamePanel.deathNum < 0) {
//                    GamePanel.deathNum = 1;
//                }
//            }
//            if(keyCode == KeyEvent.VK_S || keyCode == KeyEvent.VK_DOWN) {
//                GamePanel.comandNum++;
//                if(GamePanel.deathNum > 1) {
//                    GamePanel.deathNum = 0;
//                }
//            }
            if(KeyHandler.enterPressed) {
                GamePanel.deathState = false;
            }
        }
    }
}