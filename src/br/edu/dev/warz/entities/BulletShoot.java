package br.edu.dev.warz.entities;

import br.edu.dev.warz.Game;
import br.edu.dev.warz.world.Camera;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * @author Israel Gomes
 * @version 1.0
 * @since 1.0
 */
public class BulletShoot extends Entity {

    private int dx;
    private int dy;
    private double speed = 3.0;
    private int life = 50;
    private int curLife = 0;

    public BulletShoot(int x, int y, int width, int height, BufferedImage sprite,
            int dx, int dy) {
        super(x, y, width, height, sprite);
        this.dx = dx;
        this.dy = dy;
    }

    @Override
    public void tick() {
        x += dx * speed;
        y += dy * speed;
        checkCollisionWithEnemies();
        curLife++;
        if (curLife == life) {
            Game.bullets.remove(this);
        }
    }

    public void checkCollisionWithEnemies() {
        for (int i = 0; i < Game.enemyies.size(); i++) {
            Enemy atual = Game.enemyies.get(i);
            if (Entity.isColidding(this, atual)) {
                Game.enemyies.remove(atual);
                Game.entities.remove(atual);
                Game.bullets.remove(this);
                return;
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.yellow);
        g.fillOval(this.getX() - Camera.x, this.getY() - 2 - Camera.y, 2, 2);
        g.setColor(Color.yellow);
        g.fillOval(this.getX() - Camera.x, this.getY() - Camera.y, 2, 2);
        g.setColor(Color.yellow);
        g.fillOval(this.getX() - Camera.x, this.getY() + 4 - Camera.y, 2, 2);
    }
}
