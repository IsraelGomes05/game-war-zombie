

package br.edu.dev.warz.world;

/**
 * @author Israel Gomes
 * @version 1.0
 * @since 1.0
 */
public class Camera {
    
    public static int x = 0;
    public static int y = 0;
    
    public static int cleamp(int xAtual, int xMin, int xMax){
        if (xAtual < xMin) {
            xAtual = xMin;
        } 
        
        if (xAtual > xMax) {
            xAtual = xMax;
        }
        return xAtual;
    }
}
