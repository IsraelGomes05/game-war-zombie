package br.edu.dev.warz.entities;

import br.edu.dev.warz.Game;
import br.edu.dev.warz.world.Camera;
import br.edu.dev.warz.world.World;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * @author Israel Gomes
 * @version 1.0
 * @since 1.0
 */
public class Player extends Entity {

    public boolean right, up, left, down;
    public int rightDir = 0, leftDir = 1;
    public int dir = rightDir;
    public double speed = 1.5;
    private int frames = 0;
    private int maxFrames = 5;
    private int index = 0;
    private int maxIndex = 4;
    private boolean moved;
    private BufferedImage[] rightPlayer;
    private BufferedImage[] leftPlayer;
    public int life = 100;

    public Player(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
        rightPlayer = new BufferedImage[4];
        leftPlayer = new BufferedImage[4];
        for (int i = 0; i < 4; i++) {
            rightPlayer[i] = Game.spritesheet.getSprite(32 + (i * 16), 0, 16, 16);
        }

        for (int i = 0; i < 4; i++) {
            leftPlayer[i] = Game.spritesheet.getSprite(32 + (i * 16), 16, 16, 16);
        }
    }

    public boolean placeFree(int par, int y1) {
        return World.isFree(par, y1);
    }

    @Override
    public void tick() {
        moved = false;
        if (right && placeFree((int) (x + speed), this.getY())) {
            moved = true;
            dir = rightDir;
            x += speed;
        } else if (left && placeFree((int) (x - speed), this.getY())) {
            moved = true;
            dir = leftDir;
            x -= speed;
        } else if (down && placeFree(this.getX(), (int) (y + speed))) {
            moved = true;
            y += speed;
        } else if (up && placeFree(this.getX(), (int) (y - speed))) {
            moved = true;
            y -= speed;
        }

        if (moved) {
            frames++;
            if (frames == maxFrames) {
                frames = 0;
                index++;
                if (index >= maxIndex) {
                    index = 0;
                }
            }
        }

        Camera.x = Camera.cleamp(this.getX() - (Game.WIDTH / 2), 0, World.WIDTH * 16 - Game.WIDTH);
        Camera.y = Camera.cleamp(this.getY() - (Game.HEIGHT / 2), 0, World.HEIGHT * 16 - Game.HEIGHT);
    }

    @Override
    public void render(Graphics g) {
        if (dir == rightDir) {
            g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
        } else if (dir == leftDir) {
            g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
        }
    }
}
