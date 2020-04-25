package br.edu.dev.warz.world;

import br.edu.dev.warz.Game;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * @author Israel Gomes
 * @version 1.0
 * @since 1.0
 */
public class Tile {

    public static BufferedImage TILE_FLOOR = Game.spritesheet.getSprite(0, 0, 16, 16);
    public static BufferedImage TILE_GRASS = Game.spritesheet.getSprite(0, 16, 16, 16);
    public static BufferedImage TILE_WALL = Game.spritesheet.getSprite(16, 0, 16, 16);

    protected BufferedImage sprite;
    protected int x, y;

    public Tile(BufferedImage sprite, int x, int y) {
        this.sprite = sprite;
        this.x = x;
        this.y = y;
    }

    public void render(Graphics g) {
        g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
    }
}
