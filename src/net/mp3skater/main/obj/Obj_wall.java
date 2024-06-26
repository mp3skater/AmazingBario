package net.mp3skater.main.obj;

import net.mp3skater.main.utils.Draw_Utils;

import java.awt.*;

public class Obj_wall extends Obj {
    /*
    Walls are the platforms the player collides with and can stand on
    They don't disappear like <Obj_platform> because they are part of the level
     */

    public Obj_wall(double x, double y, int sX, int sY) {
        super(x, y, sX, sY, false);
    }

    @Override
    public void draw(Graphics2D g2, Color color) {
        Draw_Utils.fillRect(g2, this, color);
    }
}
