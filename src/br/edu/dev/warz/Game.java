package br.edu.dev.warz;

import br.edu.dev.warz.entities.BulletShoot;
import br.edu.dev.warz.entities.Enemy;
import br.edu.dev.warz.entities.Entity;
import br.edu.dev.warz.entities.Player;
import br.edu.dev.warz.graficos.Spritesheet;
import br.edu.dev.warz.graficos.UI;
import br.edu.dev.warz.world.World;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;

public class Game extends Canvas implements Runnable, KeyListener {

    private static JFrame frame;
    public static final int WIDTH = 240;
    public static final int HEIGHT = 160;
    public static final int SCALE = 3;
    private static final long serialVersionUID = 1L;
    private boolean isRunning;
    private Thread thread;
    private BufferedImage image;
    public static Spritesheet spritesheet;
    public static Player player;
    public static List<Entity> entities;
    public static List<Enemy> enemyies;
    public static List<BulletShoot> bullets;
    public static World world;
    public static Random rand;
    private static int CUR_LEVEl = 2;
    private static final int MAX_LEVEL = 2;
    public UI ui;

    public Game() {
        rand = new Random();
        addKeyListener(this);
        this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        initFrame();
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        startGame("level" + CUR_LEVEl + ".png");
        ui = new UI();
    }
    
    public static void restart(){
        CUR_LEVEl = 1;
        startGame("level" + CUR_LEVEl + ".png");
    }

    public static void startGame(String level) {
        entities = new ArrayList<>();
        enemyies = new ArrayList<>();
        bullets = new ArrayList<>();
        spritesheet = new Spritesheet("/img/spritesheet.png");
        player = new Player(0, 0, 16, 16, spritesheet.getSprite(32, 0, 16, 16));
        entities.add(player);
        world = new World("/img/" + level);
    }

    public synchronized void start() {
        thread = new Thread(this);
        isRunning = true;
        thread.start();
    }

    public synchronized void stop() {
        isRunning = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void initFrame() {
        frame = new JFrame("War Zombie");
        frame.add(this);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }

    private void tick() {
        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).tick();
        }

        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).tick();
        }
        
        if (enemyies.isEmpty()) {
            CUR_LEVEl++;
            if (CUR_LEVEl > MAX_LEVEL) {
                CUR_LEVEl = 1;
            }
            String newWorld = "level" + CUR_LEVEl + ".png";
            startGame(newWorld);
        }
    }

    public void render() {
        BufferStrategy bs = this.getBufferStrategy();

        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics graphics = image.getGraphics();
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, WIDTH, HEIGHT);

        //  Renderização do jogo
        world.render(graphics);
        for (Entity entity : entities) {
            entity.render(graphics);
        }

        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).render(graphics);
        }

        ui.render(graphics);

        graphics.dispose();
        graphics = bs.getDrawGraphics();
        graphics.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
        drawFps(graphics);
        drawAmmo(graphics);
        bs.show();
    }

    private int fpsExibicao = 0;
    private Color color = Color.YELLOW;

    private void drawAmmo(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.BOLD, 15));
        g.drawString("Munição: " + player.ammo, 10, 40);
    }

    private void drawFps(Graphics g) {
        g.setColor(color);
        g.setFont(new Font("Arial", Font.PLAIN, 15));
        g.drawString("FPS:" + fpsExibicao, (WIDTH * SCALE) - 50, 15);
    }

    @Override
    public void run() {
        long lessTime = System.nanoTime();
        final double amountOfTicks = 60.0;
        final double ns = 1000_000_000 / amountOfTicks;
        double delta = 0;
        int fps = 0;
        double timer = System.currentTimeMillis();
        requestFocus();
        while (isRunning) {
            long now = System.nanoTime();
            delta += (now - lessTime) / ns;
            lessTime = now;
            if (delta >= 1) {
                tick();
                render();
                fps++;
                delta--;
            }
            if (System.currentTimeMillis() - timer >= 1000) {
                fpsExibicao = fps;
                color = color == Color.ORANGE ? Color.YELLOW : Color.ORANGE;
                fps = 0;
                timer += 1000;
            }
        }
        stop();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT
                || e.getKeyCode() == KeyEvent.VK_D) {
            player.right = true;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT
                || e.getKeyCode() == KeyEvent.VK_A) {
            player.left = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_UP
                || e.getKeyCode() == KeyEvent.VK_W) {
            player.up = true;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN
                || e.getKeyCode() == KeyEvent.VK_S) {
            player.down = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            player.isShoot = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT
                || e.getKeyCode() == KeyEvent.VK_D) {
            player.right = false;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT
                || e.getKeyCode() == KeyEvent.VK_A) {
            player.left = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_UP
                || e.getKeyCode() == KeyEvent.VK_W) {
            player.up = false;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN
                || e.getKeyCode() == KeyEvent.VK_S) {
            player.down = false;
        }
//
//        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
//            player.isShoot = false;
//        }
    }
}
