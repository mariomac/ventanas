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

public class Tutorial3_Movimiento {
    public static void main(String[] args) {
        // En vez de crear una ventana con el tamaño por defecto (640x480 pixels)
        // crearemos una con un tamaño arbitrario (800x300 pixels)        
        Ventana v = new Ventana("Tutorial 3: Objetos en Movimiento", 800, 300);
        v.setColorFondo(Color.cyan);        
        
        //posicion de la pelota
        double pelotaX = 0;
        while(!v.isPulsadoEscape()) {
            // Se calcula en qué posición deberá estar la pelota en el nuevo fotograma
            pelotaX += 0.2;            
            // Se muestra la pelota en la nueva posición para ese fotograma
            v.dibujaCirculo(pelotaX, 0, 3, Color.blue);
            // Si la pelota sobrepasa la coordenada (10,0), la hacemos volver a la (-10,0)
            if(pelotaX > 10) {
                pelotaX = -10;
            }

            v.escribeTexto("TRUCO: Se pueden crear ventanas con el color de fondo y el tamaño que queramos",
                    -20, -9, 1, Color.black);
            v.actualizaFotograma();
            
        }
        v.cerrar();
        
    }
}
