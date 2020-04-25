package br.edu.dev.warz.entities;

import br.edu.dev.warz.Game;
import br.edu.dev.warz.world.Camera;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * @author Israel Gomes
 * @version 1.0
 * @since 1.0
 */
public class Entity {

    public static BufferedImage LIFEPACK_EN = Game.spritesheet.getSprite(6 * 16, 0, 16, 16);
    public static BufferedImage WEAPON_EN = Game.spritesheet.getSprite(7 * 16, 0, 16, 16);
    public static BufferedImage BULLET_EN = Game.spritesheet.getSprite(6 * 16, 16, 16, 16);
    public static BufferedImage ENEMY_EN = Game.spritesheet.getSprite(7 * 16, 16, 16, 16);

    protected double x;
    protected double y;
    protected int width;
    protected int height;
    protected BufferedImage sprite;

    public Entity(int x, int y, int width, int height, BufferedImage sprite) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sprite = sprite;
    }

    public void tick() {

    }

    public int getX() {
        return (int) x;
    }

    public int getY() {
        return (int) y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public BufferedImage getSprite() {
        return sprite;
    }

    public void render(Graphics g) {
        g.drawImage(sprite, (int) x - Camera.x, (int) y - Camera.y, null);
    }
}
