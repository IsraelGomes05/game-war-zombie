package br.edu.dev.warz.world;

import br.edu.dev.warz.Game;
import br.edu.dev.warz.entities.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * @author Israel Gomes
 * @version 1.0
 * @since 1.0
 */
public class World {

    private static Tile[] tiles;
    public static int WIDTH, HEIGHT;
    public static final int TILE_SIZE = 16;

    public World(String path) {
        try {
            BufferedImage map = ImageIO.read(getClass().getResource(path));
            int[] pixels = new int[map.getWidth() * map.getHeight()];
            WIDTH = map.getWidth();
            HEIGHT = map.getHeight();
            tiles = new Tile[map.getWidth() * map.getHeight()];
            map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
            for (int xx = 0; xx < map.getWidth(); xx++) {
                for (int yy = 0; yy < map.getHeight(); yy++) {
                    int pixel = pixels[xx + (yy * map.getWidth())];
                    if (xx > 0 && pixels[(xx - 1) + ((yy) * map.getWidth())] == 0xFF007709
                            && xx + 1 < map.getWidth() && pixels[(xx + 1) + ((yy) * map.getWidth())] == 0xFF007709) {
                        tiles[xx + (yy * map.getWidth())] = new FloorTile(Tile.TILE_GRASS, xx * 16, yy * 16);
                    } else {
                        tiles[xx + (yy * map.getWidth())] = new FloorTile(Tile.TILE_FLOOR, xx * 16, yy * 16);
                    }
                    switch (pixel) {
                        case 0xFF000000: // Floor
                            tiles[xx + (yy * map.getWidth())] = new FloorTile(Tile.TILE_FLOOR, xx * 16, yy * 16);
                            break;
                        case 0xFF007709: // Grass
                            tiles[xx + (yy * map.getWidth())] = new FloorTile(Tile.TILE_GRASS, xx * 16, yy * 16);
                            break;
                        case 0xFFFFFFFF: // Wall
                            tiles[xx + (yy * map.getWidth())] = new WallTile(Tile.TILE_WALL, xx * 16, yy * 16);
                            break;
                        case 0xFF00137F: // Player
                            Game.player.setX(xx * 16);
                            Game.player.setY(yy * 16);
                            break;
                        case 0xFFFF0000: // Enemy
                            Enemy enemy = new Enemy(xx * 16, yy * 16, 16, 16, Entity.ENEMY_EN);
                            Game.enemyies.add(enemy);
                            Game.entities.add(enemy);
                            break;
                        case 0xFFFF6A00: // Weapon
                            Game.entities.add(new Weapon(xx * 16, yy * 16, 16, 16, Entity.WEAPON_EN));
                            break;
                        case 0xFFFF006E: // LifePack
                            LifePack lifePack = new LifePack(xx * 16, yy * 16, 16, 16, Entity.LIFEPACK_EN);
                            lifePack.setMask(8, 8, 8, 8);
                            Game.entities.add(lifePack);
                            break;
                        case 0xFFFFFF00: // Bullet
                            Game.entities.add(new Bullet(xx * 16, yy * 16, 16, 16, Entity.BULLET_EN));
                            break;
                        default:
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean isFree(int xNext, int yNext) {
        int x1 = xNext / TILE_SIZE;
        int y1 = yNext / TILE_SIZE;

        int x2 = (xNext + TILE_SIZE - 1) / TILE_SIZE;
        int y2 = yNext / TILE_SIZE;

        int x3 = xNext / TILE_SIZE;
        int y3 = (yNext + TILE_SIZE - 1) / TILE_SIZE;

        int x4 = (xNext + TILE_SIZE - 1) / TILE_SIZE;
        int y4 = (yNext + TILE_SIZE - 1) / TILE_SIZE;

        return !((tiles[x1 + (y1 * WIDTH)] instanceof WallTile)
                || (tiles[x2 + (y2 * WIDTH)] instanceof WallTile)
                || (tiles[x3 + (y3 * WIDTH)] instanceof WallTile)
                || (tiles[x4 + (y4 * WIDTH)] instanceof WallTile));
    }

    public void render(Graphics g) {
        int xStart = Camera.x >> 4;
        int yStart = Camera.y >> 4;

        int xFinal = xStart + (Game.WIDTH >> 4);
        int yFinal = yStart + (Game.HEIGHT >> 4);

        for (int xx = xStart; xx <= xFinal; xx++) {
            for (int yy = yStart; yy <= yFinal; yy++) {
                if (xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT) {
                    continue;
                }
                Tile tile = tiles[xx + (yy * WIDTH)];
                tile.render(g);
            }
        }
    }

}
