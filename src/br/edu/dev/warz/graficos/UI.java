package br.edu.dev.warz.graficos;

import br.edu.dev.warz.Game;
import java.awt.Color;
import java.awt.Graphics;

/**
 * @author Israel Gomes
 * @version 1.0
 * @since 1.0
 */
public class UI {
    
    public void render(Graphics g){
        g.setColor(Color.red);
        g.fillRect(3, 3, 50, 4);
        g.setColor(Color.green);
        g.fillRect(3, 3,(int)((Game.player.life / Game.player.maxLife) * 50), 4);
    }
}
