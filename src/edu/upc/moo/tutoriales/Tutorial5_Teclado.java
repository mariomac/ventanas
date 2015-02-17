/*------------------------------------------------------------------------------
 * Este código está distribuido bajo una licencia del tipo BEER-WARE.
 * -----------------------------------------------------------------------------
 * Mario Macías Lloret escribió este archivo. Teniendo esto en cuenta,
 * puedes hacer lo que quieras con él: modificarlo, redistribuirlo, venderlo,
 * etc, aunque siempre deberás indicar la autoría original en tu código.
 * Además, si algún día nos encontramos por la calle y piensas que este código
 * te ha sido de utilidad, estás obligado a invitarme a una cerveza (a ser
 * posible, de las buenas) como recompensa por mi contribución.
 * -----------------------------------------------------------------------------
 */
package edu.upc.moo.tutoriales;

import edu.upc.moo.Ventana;
import java.awt.Color;
import java.util.Random;

public class Tutorial5_Teclado {
    public static void main(String[] args) {
        Random rnd = new Random();
        
        // Crea una nueva ventana
        Ventana v = new Ventana("Tutorial 5: Uso del teclado");
        
        // Coordenadas del círculo que moveremos
        double posX = 0, posY = 0;
        double velocidad = 0.4;
        Color colorBola = Color.blue;
        v.setCamara(0, 0, 40);
        
        while(!v.isPulsadoEscape()) {
            if(v.isPulsadoArriba()) posY += velocidad;
            if(v.isPulsadoAbajo()) posY -= velocidad;
            if(v.isPulsadoDerecha()) posX += velocidad;
            if(v.isPulsadoIzquierda()) posX -= velocidad;
            if(v.isPulsadoEspacio()) {
                colorBola = new Color(rnd.nextInt());
            }
            v.dibujaCirculo(posX, posY, 3, colorBola);
            
            v.escribeTexto("Usa las teclas de dirección para mover la bola", -20, -15, 2, Color.WHITE);
            v.escribeTexto("Pulsa espacio para cambiar el color de la bola", -20, -17, 2, Color.WHITE);
            // Muestra el fotograma anterior, y crea uno nuevo
            v.actualizaFotograma();
        }   
        // Antes de acabar, cierra la ventana.
        v.cerrar();
    }
}
