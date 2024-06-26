package net.mp3skater.main.utils;

import net.mp3skater.main.GamePanel;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static net.mp3skater.main.GamePanel.*;
import static net.mp3skater.main.utils.Sound_Utils.playSE;

public class Misc_Utils {

    /*
    Returns an int that is rounded up away from 0
    This method is used to ensure that the player doesn't "glitch" into a wall
     */
    public static int absRound(double number) {
        return number > 0 ? (int) Math.ceil(number) : (int) Math.floor(number);
    }

    /*
    Gets called when the player finishes all levels
    If it's a new highscore, the highscore gets saved in a text file
    That file will be created the first time the game gets played through
     */
    public static void gameWon() {
        if(winState) return; // To avoid multiple gameWon for whatever reason
        winState = true; // For the win screen
        GamePanel.won = true; // For amogus
        playSE(6); // Won sound effect

        timeTemp = time; // To not lose the time
        time = -1; // Reset the time (-1 because it works)
        System.out.println("Game won, time: " + timeTemp);

        String path = "res/highscores.txt";
        File scores = new File(path);

        // For the right order the highscores are written in
        StringBuilder file = new StringBuilder();

        try {
            // Creating file, if necessary
            if(scores.createNewFile()) System.out.println("Creating new file: " + path);

            // Rewriting the file and inserting the current score
            try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
                boolean written = false;
                String line;
                while((line = reader.readLine()) != null) {
                    String subString;
                    int iend = line.indexOf(" ");
                    if(iend==-1) subString = line;
                    else subString = line.substring(0, iend);
                    try {
                        if(!written && timeTemp < Integer.parseInt(subString)) {
                            file.append(timeTemp).append("\n");
                            written = true;
                        }
                        file.append(line).append("\n");
                    } catch(Exception e) {
                        System.out.println("You shouldn't mess with the "+path+" file!\n"+
                                "To solve this problem you can delete it and restart the game.");
                        e.printStackTrace();
                    }
                }
                if(!written) file.append(timeTemp);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("Problems while reading/creating the file \"res/info/highscores.txt\"");
            e.printStackTrace();
        }

        try(PrintWriter writer = new PrintWriter(path)){
            writer.print(file);
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        }
        gameOver(false);
    }
}