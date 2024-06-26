package net.mp3skater.main.obj;

import net.mp3skater.main.GamePanel;
import net.mp3skater.main.utils.Misc_Utils;
import net.mp3skater.main.utils.Draw_Utils;
import net.mp3skater.main.io.KeyHandler;
import net.mp3skater.main.utils.Sound_Utils;

import java.awt.*;

public class Obj_player extends Obj_moving {
    /*
    This is the player which you can control through the level.
    */

    public Obj_player(double x, double y) {
        super(x, y, 50, 80, 0, 0, 8, 15);
    }

    // If he touches the ground
    private boolean onGround;

    // Reverse gravity
    public boolean reverseGravity = false;

    // To draw the last direction the player was going to
    private boolean left = false;

    /*
    Gives the player movement vectors according to the key's pressed
    */
    public void movement() {
        // Movement
        if(KeyHandler.aPressed) this.addVec(-0.5, 0);
        if(KeyHandler.dPressed) this.addVec(0.5, 0);
        // Jump
        if(KeyHandler.spacePressed && onGround) {
            if(GamePanel.level == 3) KeyHandler.spacePressed = false;
            if(GamePanel.level == 3) reverseGravity = !reverseGravity;
            else this.addVec(0, -15);
            Sound_Utils.playSE(GamePanel.level==4?11:4);
        }

        // Gravity (positive value because the up-left corner is x:0,y:0)
        this.addVec(0, GamePanel.level==3? reverseGravity? -0.6 : 0.6 : 0.6);

    }

    @Override
    protected void collisionBool() {
        // Reset onGround
        onGround = false;

        // Kill Player if he falls down
        if(reverseGravity? pos[1]<0 :pos[1] > GamePanel.HEIGHT) {
            GamePanel.gameOver(true);
            return;
        }

        // Dev tools:
        // Reset camera
        //if(KeyHandler.zeroPressed) GamePanel.offset = pos[0] - GamePanel.WIDTH/2.0;
        // Change level
        //if(KeyHandler.onePressed) GamePanel.loadLevel(1);
        //else if(KeyHandler.twoPressed) GamePanel.loadLevel(2);
        //else if(KeyHandler.threePressed) GamePanel.loadLevel(3);
        //else if(KeyHandler.fourPressed) GamePanel.loadLevel(4);
        //else if(KeyHandler.fivePressed) GamePanel.loadLevel(5);

        // Collision with the left side of the screen
        if(pos[0]+vec[0]-(int)GamePanel.offset < 0)
            xCollision((int) (size[0]+GamePanel.offset), 0);

        // Update offset if the player moves forwards in the level
        if(pos[0]+size[0]-GamePanel.offset > GamePanel.WIDTH/2.0 && vec[0]>0)
            GamePanel.offset += vec[0];//pos[0]+size[0]-GamePanel.WIDTH/2.0;

        // Kill player if he hits an enemy
        for(Obj_enemy e : GamePanel.enemies)
            if(collidesSpecial(e)) {
                GamePanel.gameOver(true);
                return;
            }

        // Load new level if player reaches the end-bar
        if(pos[0] >= GamePanel.getLength()-50) {
            GamePanel.level++;
            if(GamePanel.leben<3) GamePanel.leben++;
            GamePanel.loadLevel(GamePanel.level);
        }
        // Variables to check nearest collision
        Obj nearX = null, nearY = null;
        // For all elements you could collide with in walls
        for(Obj w : GamePanel.walls) {
            if(w == null || !w.isImportant) continue;

            // Return if endbar to allow the player to finish the level
            if(w instanceof Obj_endBar)
                continue;
            // Test weather going vertically, horizontally or both would make you collide with something
            // Horizontally
            if(vec[0] != 0)
                if(collides(w.getX()- Misc_Utils.absRound(vec[0]), w.getY(), (int)w.getSX(), (int)w.getSY())) {
                    nearX = nearX==null? w : getNearestX(nearX, w, vec[0] > 0);
                }
            // Vertically
            if(vec[1] != 0)
                if(collides(w.getX(), w.getY()-Misc_Utils.absRound(vec[1]), (int)w.getSX(), (int)w.getSY())) {
                    if(GamePanel.level==3? reverseGravity? vec[1]<0 : vec[1]>0 : vec[1]>0) onGround = true;
                    nearY = nearY==null? w : getNearestY(nearY, w, vec[1] > 0);
                }
            // Both
            //if(vec[0] != 0 && vec[1] != 0)
            //    if(collides((int)(w.getX()-vec[0]), (int)(w.getY()-vec[1]), (int)w.getSX(), (int)w.getSY())) {
            //        System.out.println("colXY");
            //        // To avoid player from getting stuck
            //        if(!(!collides((int)(w.getX()-vec[0]), (int)w.getY(), (int)w.getSX(), (int)w.getSY()) &&
            //                !collides((int)w.getX(), w.getY()-vec[1], (int)w.getSX(), (int)w.getSY())))
            //            nearX = nearX==null? w : getNearestX(nearX, w, vec[0] > 0);
            //        nearY = nearY==null? w : getNearestY(nearY, w, vec[1] > 0);
            //    }
        }
        // For all elements you could collide with in platforms
        for(Obj p : GamePanel.platforms) {
            if(p == null || !p.isImportant) continue;
            // Test weather going vertically, horizontally or both would make you collide with something
            // Horizontally
            if(vec[0] != 0)
                if(collides(p.getX()- Misc_Utils.absRound(vec[0]), p.getY(), (int)p.getSX(), (int)p.getSY())) {
                    nearX = nearX==null? p : getNearestX(nearX, p, vec[0] > 0);
                }
            // Vertically
            if(vec[1] != 0)
                if(collides(p.getX(), p.getY()-Misc_Utils.absRound(vec[1]), (int)p.getSX(), (int)p.getSY())) {
                    if(GamePanel.level==3? reverseGravity? vec[1]<0 : vec[1]>0 : vec[1]>0) onGround = true;
                    nearY = nearY==null? p : getNearestY(nearY, p, vec[1] > 0);
                }
            // Both
            //if(vec[0] != 0 && vec[1] != 0)
            //    if(collides((int)(p.getX()-vec[0]), (int)(p.getY()-vec[1]), (int)p.getSX(), (int)p.getSY())) {
            //        // To avoid player from getting stuck
            //        if(!(!collides((int)(p.getX()-vec[0]), (int)p.getY(), (int)p.getSX(), (int)p.getSY()) &&
            //                !collides((int)p.getX(), p.getY()-vec[1], (int)p.getSX(), (int)p.getSY())))
            //            nearX = nearX==null? p : getNearestX(nearX, p, vec[0] > 0);
            //        nearY = nearY==null? p : getNearestY(nearY, p, vec[1] > 0);
            //    }
        }
        //if(nearX != null)
        //    System.out.printf("x: "+(int)nearX.getX()+" ");
        //if(nearY != null)
        //    System.out.printf("y: "+nearY.getY());
        // Actual collision
        // Actual collision:

        // To avoid players from getting stuck
        if (nearX != null)
            if (nearY != null && nearX == nearY && (int) nearX.getX() == nearX.getX() && (int) nearY.getY() == nearY.getY()) {
                yCollision((int) nearY.getY(), vec[1] < 0/*going up*/ ? nearY.size[1] : 0);
                return;
            }

        if(nearX!=null) {
            xCollision((int)nearX.getX(), vec[0]<0/*going left*/? nearX.size[0] : 0);
        }
        if(nearY!=null) {
            yCollision((int)nearY.getY(), vec[1]<0/*going up*/? nearY.size[1] : 0);
        }
    }
    /*
    Returns the nearest Obj to collide with
    <positive> means the player is going right or down
     */
    private Obj getNearestX(Obj o1, Obj o2, boolean positive) {
        if(positive) return o1.getX()<o2.getY()? o1 : o2;
        else return o1.getX()+o1.getSX()>o2.getX()+o2.getSX()? o1 : o2;
    }
    private Obj getNearestY(Obj o1, Obj o2, boolean positive) {
        if(positive) return o1.getY()<o2.getY()? o1 : o2;
        else return o1.getY()+o1.getSY()>o2.getY()+o2.getSY()? o1 : o2;
    }


    /*
    Overrides the update class to include the <movement()> and the <turndownvec()> methods
    */
    @Override
    public void update() {
        // Adds the movement vectors
        movement();
        // Updates the collision-checks and changes the player
        super.update();
        // Make the horizontal vector's slower so the player doesn't glide forever
        turndownvec();
    }

    /*
    Makes the <Player> slow down horizontally to ensure it doesn't drift away
     */
    public void turndownvec() {
        // No movement
        if(vec[0] == 0)
            return;

        // In the air // EXPERIMENTAL: Not good for control, feeling: Strange
        //if(!onGround)
        //    return;

        // Value with which the <Obj> loses speed horizontally
        // Kind of like how "unslippery" the ground is for the <Obj>
        double turnDown = onGround? /*On the ground*/0.3 : /*In the air*/0.25;
        if(vec[0]>0) {
            if(vec[0]- turnDown < 0)
                vec[0] = 0;
            else
                vec[0] -= turnDown;
        }
        else {
            if(vec[0]+ turnDown > 0)
                vec[0] = 0;
            else
                vec[0] += turnDown;
        }
    }

    /*
    Returns the vector of the player, for the log
     */
    public int getVX() {
        return (int)vec[0];
    }
    public int getVY() {
        return (int)vec[1];
    }

    public boolean collidesSpecial(Obj_enemy e) {
        if(!e.isImportant) return false;
        if(e.getType() == 4) {
            return collidesPoint(e.getX(),e.getY() + e.getSY()) ||
                   collidesPoint(e.getX() + e.getSX()/2.0,e.getY()) ||
                   collidesPoint(e.getX() + e.getSX(), e.getY() + e.getSY()) ||
                   collidesPoint(e.getX() + e.getSX() / 4, e.getY() + e.getSY() / 2) ||
                   collidesPoint(e.getX() + (e.getSX() / 4) * 3, e.getY() + e.getSY() / 2) ;
        }
        else return super.collides(e.getX(), e.getY(), (int)e.getSX(), (int)e.getSY());
    }

    @Override
    public void draw(Graphics2D g2, Color color) {
        if(GamePanel.won) drawAmogus(g2, vec[0]==0? left : vec[0] < 0);
        else if(GamePanel.level == 3) {
            g2.setStroke(new BasicStroke(5));
            g2.setColor(color);
            g2.drawRect(getDrawX(), (int)pos[1], size[0], size[1]);
        }
        else Draw_Utils.fillRect(g2, this, color);
    }

    /*
    Easteregg if you finish the game
     */
    private void drawAmogus(Graphics2D g2, boolean left) {
        if(left) {
            g2.setColor(Color.red);
            g2.fillRect(getDrawX(), (int)pos[1], 50, 65);
            g2.fillRect(getDrawX(), (int)pos[1]+65, 18, 15);
            g2.fillRect(getDrawX()+32,(int)pos[1]+65, 18, 15);
            g2.fillRect(getDrawX()+size[0], (int)pos[1]+15, 15, 35);
            g2.setColor(Color.cyan);
            g2.fillRect(getDrawX()-10, (int)pos[1]+10, 50, 30);
            g2.setColor(Color.white);
            g2.fillRect(getDrawX(), (int)pos[1]+15, 20, 5);
            this.left = true;
        }
        else {
            g2.setColor(Color.red);
            g2.fillRect(getDrawX(), (int)pos[1], 50, 65);
            g2.fillRect(getDrawX(), (int)pos[1]+65, 18, 15);
            g2.fillRect(getDrawX()+32,(int)pos[1]+65, 18, 15);
            g2.fillRect(getDrawX()-15, (int)pos[1]+15, 15, 35);
            g2.setColor(Color.cyan);
            g2.fillRect(getDrawX()+10, (int)pos[1]+10, 50, 30);
            g2.setColor(Color.white);
            g2.fillRect(getDrawX()+33, (int)pos[1]+15, 20, 5);
            this.left = false;
        }
    }
}