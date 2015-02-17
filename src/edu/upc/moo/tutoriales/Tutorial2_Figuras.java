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

public class Tutorial2_Figuras {
    public static void main(String[] args) {
        // Crea una nueva ventana
        Ventana v = new Ventana("Tutorial 2: Figuras geométricas");
        // Mientras no se pulsa la tecla ESCAPE, se redibuja siempre lo mismo en pantalla
        while(!v.isPulsadoEscape()) {
            v.dibujaCirculo(-5, 5, 5, Color.red);
            v.dibujaTriangulo(5, 10, 10, 0, 0, 0, Color.yellow);
            v.dibujaRectangulo(-10,0,10,10, Color.blue);
            // se pueden dibujar lineas de diferente grosor
            double grosor = 0.1;
            for(double x = 0; x < 10 ; x += grosor * 2) {
                v.dibujaLinea(x, 0, x, -10, grosor, Color.green);
                grosor *= 1.5;
            }
            v.escribeTexto("(Pulsa ESC para terminar)", -10, -9, 1, Color.white);
            // Muestra el fotograma anterior, y crea uno nuevo
            v.actualizaFotograma();
        }   
        // Antes de acabar, cierra la ventana.
        v.cerrar();
    }
}
