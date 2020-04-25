package br.edu.dev.warz.entities;

import br.edu.dev.warz.Game;
import br.edu.dev.warz.world.World;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * funcion...
 *
 * @author Israel Gomes
 * @version 1.0
 * @since 1.0
 */
public class Enemy extends Entity {

    private double speed = 0.5;
    private boolean canSee = false;
    private static final int VISAO = 50;

    public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
        super(x, y, width, height, sprite);
    }

    @Override
    public void tick() {

        if (Game.player.getX() - x > 0 && Game.player.getX() - x < VISAO) {
            canSee = true;
        }

        if (Game.player.getY() - y < 0 && Game.player.getY() - y < VISAO) {
            canSee = true;
        }

        if (!canSee) {
            return;
        }

        if (isColiddingWithPlayer() == false) {
            if (x < Game.player.getX() && World.isFree((int) (x + speed), this.getY())
                    && !isColidding((int) (x + speed), this.getY())) {
                x += speed;
            } else if (x > Game.player.getX() && World.isFree((int) (x - speed), this.getY())
                    && !isColidding((int) (x - speed), this.getY())) {
                x -= speed;
            }

            if (y < Game.player.getY() && World.isFree(this.getX(), (int) (y + speed))
                    && !isColidding(this.getX(), (int) (y + speed))) {
                y += speed;
            } else if (y > Game.player.getY() && World.isFree(this.getX(), (int) (y - speed))
                    && !isColidding(this.getX(), (int) (y - speed))) {
                y -= speed;
            }
        } else {
            if (Game.rand.nextInt(100) < 10) {
                Game.player.life-= Game.rand.nextInt(3);
                if (Game.player.life <= 0) {
                    System.out.println("Game Over");
                }
                System.out.println("Life: " + Game.player.life--);
            }
        }
    }

    public boolean isColiddingWithPlayer() {
        Rectangle enemyCurrent = new Rectangle(this.getX(), this.getY(), World.TILE_SIZE, World.TILE_SIZE);
        Rectangle player = new Rectangle(Game.player.getX(), Game.player.getY(), World.TILE_SIZE, World.TILE_SIZE);

        return enemyCurrent.intersects(player);
    }

    public boolean isColidding(int xNext, int yNext) {
        Rectangle enemyCurrent = new Rectangle(xNext, yNext, World.TILE_SIZE, World.TILE_SIZE);

        for (int i = 0; i < Game.enemyies.size(); i++) {
            Enemy e = Game.enemyies.get(i);
            if (e == this) {
                continue;
            }

            Rectangle targetCurrent = new Rectangle(e.getX(), e.getY(), World.TILE_SIZE, World.TILE_SIZE);
            if (enemyCurrent.intersects(targetCurrent)) {
                return true;
            }
        }

        return false;
    }

}
