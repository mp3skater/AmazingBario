package net.mp3skater.main.obj;

import net.mp3skater.main.utils.DrawUtils;
import net.mp3skater.main.utils.Level_Utils;

import java.awt.*;

public class Obj_arrow extends Obj {
    /*
    Arrows can be used with texts to indicate at something
     */

    public Obj_arrow(double x, double y) {
        super(x, y, 0, 0);
    }

    @Override
    public void draw(Graphics2D g2, Color color) {
        DrawUtils.drawArrow(g2, getDrawX(), (int)pos[1], color);
    }
}
