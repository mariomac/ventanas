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
import javafx.scene.paint.Color;

import java.util.Random;

public class Tutorial6_Raton {
    public static void main(String[] args) {
        Random rnd = new Random();
        
        // Crea una nueva ventana
        Ventana v = new Ventana("Tutorial 5: Uso del ratón", 900, 900);
        
        // Coordenadas del círculo que moveremos
        double posX = 0, posY = 0;
        Color colorBola = Color.BLUE;
        v.setCamara(0, 0, 40);
        
        while(!v.isPulsadoEscape()) {
            posX = v.getRatonX();
            posY = v.getRatonY();
            if(v.isRatonPulsado()) {
                colorBola = new Color(rnd.nextDouble(),rnd.nextDouble(),rnd.nextDouble(),1);
            }
            v.dibujaCirculo(posX, posY, 3, colorBola);
            
            v.escribeTexto("Usa el ratón para mover la bola", -20, -15, 2, Color.WHITE);
            v.escribeTexto("Haz click para cambiar el color de la bola", -20, -17, 2, Color.WHITE);
            // Muestra el fotograma anterior, y crea uno nuevo
            v.actualizaFotograma();
        }   
        // Antes de acabar, cierra la ventana.
        v.cerrar();
    }
}
