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
package info.macias.tutoriales;

import info.macias.Ventana;
import java.awt.Color;

public class Tutorial1_HolaMundo {
    public static void main(String[] args) {
        // Crea una nueva ventana
        Ventana v = new Ventana("Tutorial 1: Hola, mundo!");
        // Mientras no se pulsa la tecla ESCAPE, se redibuja siempre lo mismo en pantalla
        while(!v.isPulsadoEscape()) {
            // Se escribe algo en el fotograma
            v.escribeTexto("Hola, mundo!", -9.5, 7, 2, Color.yellow);
            v.escribeTexto("Pulsa F1 para mostrar/ocultar coordenadas", -9, 5.5, 1, Color.yellow);
            v.escribeTexto("Pulsa F2 para activar/desactivar suavizado", -9, 4, 1, Color.yellow);
            v.escribeTexto("Pulsa ESC para\nterminar", -9, 2.5, 1, Color.yellow);
            // Muestra el fotograma anterior, y crea uno nuevo
            v.actualizaFotograma();
        }   
        // Antes de acabar, cierra la ventana.
        v.cerrar();
    }
}
